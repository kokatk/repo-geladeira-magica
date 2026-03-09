// Configuração da API
const API_BASE_URL = window.location.origin;

// Elementos do DOM
const addItemForm = document.getElementById('addItemForm');
const editItemForm = document.getElementById('editItemForm');
const itemsList = document.getElementById('itemsList');
const refreshItemsBtn = document.getElementById('refreshItems');
const generateFromFridgeBtn = document.getElementById('generateFromFridge');
const generateManualBtn = document.getElementById('generateManual');
const manualIngredientsInput = document.getElementById('manualIngredients');
const recipeResult = document.getElementById('recipeResult');
const editModal = document.getElementById('editModal');
const loadingSpinner = document.getElementById('loadingSpinner');
const toast = document.getElementById('toast');

// Estado da aplicação
let currentItems = [];

// Inicialização
document.addEventListener('DOMContentLoaded', function() {
    loadItems();
    setupEventListeners();
    setMinDate();
});

// Configurar event listeners
function setupEventListeners() {
    addItemForm.addEventListener('submit', handleAddItem);
    editItemForm.addEventListener('submit', handleEditItem);
    refreshItemsBtn.addEventListener('click', loadItems);
    generateFromFridgeBtn.addEventListener('click', generateRecipeFromFridge);
    generateManualBtn.addEventListener('click', generateRecipeManual);
    
    // Modal
    const closeBtn = document.querySelector('.close');
    closeBtn.addEventListener('click', closeModal);
    
    window.addEventListener('click', function(event) {
        if (event.target === editModal) {
            closeModal();
        }
    });
    
    // Enter key para receita manual
    manualIngredientsInput.addEventListener('keypress', function(e) {
        if (e.key === 'Enter') {
            generateRecipeManual();
        }
    });
}

// Definir data mínima como hoje
function setMinDate() {
    const today = new Date().toISOString().split('T')[0];
    document.getElementById('dataValidade').setAttribute('min', today);
    document.getElementById('editDataValidade').setAttribute('min', today);
}

// Mostrar/esconder loading
function showLoading() {
    loadingSpinner.style.display = 'block';
}

function hideLoading() {
    loadingSpinner.style.display = 'none';
}

// Sistema de notificações
function showToast(message, type = 'info') {
    toast.textContent = message;
    toast.className = `toast ${type} show`;
    
    setTimeout(() => {
        toast.classList.remove('show');
    }, 4000);
}

// Carregar itens da geladeira
async function loadItems() {
    try {
        showLoading();
        const response = await fetch(`${API_BASE_URL}/food/listar`);
        
        if (!response.ok) {
            throw new Error(`Erro HTTP: ${response.status}`);
        }
        
        currentItems = await response.json();
        displayItems(currentItems);
        showToast('Itens carregados com sucesso!', 'success');
    } catch (error) {
        console.error('Erro ao carregar itens:', error);
        showToast('Erro ao carregar itens da geladeira', 'error');
        displayEmptyState();
    } finally {
        hideLoading();
    }
}

// Exibir itens na tela
function displayItems(items) {
    if (items.length === 0) {
        displayEmptyState();
        return;
    }
    
    itemsList.innerHTML = items.map(item => {
        const validityStatus = getValidityStatus(item.dataValidade);
        const validityClass = getValidityClass(validityStatus);
        
        return `
            <div class="item-card">
                <div class="validity-status ${validityClass}">
                    ${validityStatus}
                </div>
                <h3>${item.nome}</h3>
                <div class="item-info">
                    <p><strong>Categoria:</strong> ${item.categoria}</p>
                    <p><strong>Quantidade:</strong> ${item.quantidade}</p>
                    <p><strong>Validade:</strong> ${formatDate(item.dataValidade)}</p>
                </div>
                <div class="item-actions">
                    <button class="btn btn-info" onclick="editItem(${item.id})">
                        <i class="fas fa-edit"></i> Editar
                    </button>
                    <button class="btn btn-danger" onclick="deleteItem(${item.id})">
                        <i class="fas fa-trash"></i> Excluir
                    </button>
                </div>
            </div>
        `;
    }).join('');
}

// Exibir estado vazio
function displayEmptyState() {
    itemsList.innerHTML = `
        <div class="empty-state">
            <i class="fas fa-refrigerator"></i>
            <h3>Sua geladeira está vazia!</h3>
            <p>Adicione alguns ingredientes para começar.</p>
        </div>
    `;
}

// Verificar status de validade
function getValidityStatus(dataValidade) {
    const today = new Date();
    const validityDate = new Date(dataValidade);
    const diffTime = validityDate - today;
    const diffDays = Math.ceil(diffTime / (1000 * 60 * 60 * 24));
    
    if (diffDays < 0) {
        return 'Vencido';
    } else if (diffDays <= 3) {
        return 'Vence em breve';
    } else {
        return 'Fresco';
    }
}

// Obter classe CSS para status de validade
function getValidityClass(status) {
    switch (status) {
        case 'Vencido':
            return 'validity-expired';
        case 'Vence em breve':
            return 'validity-expiring';
        case 'Fresco':
            return 'validity-fresh';
        default:
            return 'validity-fresh';
    }
}

// Formatar data
function formatDate(dateString) {
    const date = new Date(dateString);
    return date.toLocaleDateString('pt-BR');
}

// Adicionar novo item
async function handleAddItem(e) {
    e.preventDefault();
    
    const formData = new FormData(addItemForm);
    const itemData = {
        nome: formData.get('nome'),
        categoria: formData.get('categoria'),
        quantidade: parseInt(formData.get('quantidade')),
        dataValidade: formData.get('dataValidade')
    };
    
    try {
        showLoading();
        const response = await fetch(`${API_BASE_URL}/food/cadastrar`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(itemData)
        });
        
        if (!response.ok) {
            throw new Error(`Erro HTTP: ${response.status}`);
        }
        
        const newItem = await response.json();
        showToast('Item adicionado com sucesso!', 'success');
        addItemForm.reset();
        loadItems();
    } catch (error) {
        console.error('Erro ao adicionar item:', error);
        showToast('Erro ao adicionar item', 'error');
    } finally {
        hideLoading();
    }
}

// Editar item
function editItem(id) {
    const item = currentItems.find(item => item.id === id);
    if (!item) {
        showToast('Item não encontrado', 'error');
        return;
    }
    
    document.getElementById('editId').value = item.id;
    document.getElementById('editNome').value = item.nome;
    document.getElementById('editCategoria').value = item.categoria;
    document.getElementById('editQuantidade').value = item.quantidade;
    document.getElementById('editDataValidade').value = item.dataValidade;
    
    editModal.style.display = 'block';
}

// Salvar edição
async function handleEditItem(e) {
    e.preventDefault();
    
    const formData = new FormData(editItemForm);
    const id = document.getElementById('editId').value;
    const itemData = {
        nome: formData.get('nome'),
        categoria: formData.get('categoria'),
        quantidade: parseInt(formData.get('quantidade')),
        dataValidade: formData.get('dataValidade')
    };
    
    try {
        showLoading();
        const response = await fetch(`${API_BASE_URL}/food/atualizar/${id}`, {
            method: 'PUT',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(itemData)
        });
        
        if (!response.ok) {
            throw new Error(`Erro HTTP: ${response.status}`);
        }
        
        showToast('Item atualizado com sucesso!', 'success');
        closeModal();
        loadItems();
    } catch (error) {
        console.error('Erro ao atualizar item:', error);
        showToast('Erro ao atualizar item', 'error');
    } finally {
        hideLoading();
    }
}

// Excluir item
async function deleteItem(id) {
    if (!confirm('Tem certeza que deseja excluir este item?')) {
        return;
    }
    
    try {
        showLoading();
        const response = await fetch(`${API_BASE_URL}/food/deletar/${id}`, {
            method: 'DELETE'
        });
        
        if (!response.ok) {
            throw new Error(`Erro HTTP: ${response.status}`);
        }
        
        showToast('Item excluído com sucesso!', 'success');
        loadItems();
    } catch (error) {
        console.error('Erro ao excluir item:', error);
        showToast('Erro ao excluir item', 'error');
    } finally {
        hideLoading();
    }
}

// Gerar receita com ingredientes da geladeira
async function generateRecipeFromFridge() {
    if (currentItems.length === 0) {
        showToast('Adicione alguns ingredientes na geladeira primeiro!', 'error');
        return;
    }
    
    try {
        showLoading();
        const response = await fetch(`${API_BASE_URL}/generate`);
        
        if (!response.ok) {
            throw new Error(`Erro HTTP: ${response.status}`);
        }
        
        const recipe = await response.text();
        displayRecipe(recipe, 'Receita gerada com ingredientes da sua geladeira:');
        showToast('Receita gerada com sucesso!', 'success');
    } catch (error) {
        console.error('Erro ao gerar receita:', error);
        showToast('Erro ao gerar receita. Verifique se a API está configurada.', 'error');
    } finally {
        hideLoading();
    }
}

// Gerar receita manual
async function generateRecipeManual() {
    const ingredients = manualIngredientsInput.value.trim();
    
    if (!ingredients) {
        showToast('Digite alguns ingredientes primeiro!', 'error');
        return;
    }
    
    const ingredientsList = ingredients.split(',').map(ing => ing.trim()).filter(ing => ing);
    
    if (ingredientsList.length === 0) {
        showToast('Digite ingredientes válidos!', 'error');
        return;
    }
    
    try {
        showLoading();
        const response = await fetch(`${API_BASE_URL}/generate`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({ ingredients: ingredientsList })
        });
        
        if (!response.ok) {
            throw new Error(`Erro HTTP: ${response.status}`);
        }
        
        const recipe = await response.text();
        displayRecipe(recipe, `Receita gerada com: ${ingredients}`);
        showToast('Receita gerada com sucesso!', 'success');
        manualIngredientsInput.value = '';
    } catch (error) {
        console.error('Erro ao gerar receita:', error);
        showToast('Erro ao gerar receita. Verifique se a API está configurada.', 'error');
    } finally {
        hideLoading();
    }
}

// Exibir receita
function displayRecipe(recipe, title) {
    recipeResult.innerHTML = `
        <h3><i class="fas fa-utensils"></i> ${title}</h3>
        <div class="recipe-content">${recipe}</div>
    `;
    recipeResult.classList.add('show');
    
    // Scroll suave para a receita
    recipeResult.scrollIntoView({ behavior: 'smooth', block: 'start' });
}

// Fechar modal
function closeModal() {
    editModal.style.display = 'none';
}

// Função global para ser chamada pelos botões inline
window.editItem = editItem;
window.deleteItem = deleteItem;
window.closeModal = closeModal;

// Tratamento de erros globais
window.addEventListener('unhandledrejection', function(event) {
    console.error('Erro não tratado:', event.reason);
    showToast('Ocorreu um erro inesperado', 'error');
});

// Verificar se a API está disponível
async function checkApiHealth() {
    try {
        const response = await fetch(`${API_BASE_URL}/food/listar`);
        return response.ok;
    } catch (error) {
        return false;
    }
}

// Verificar saúde da API na inicialização
checkApiHealth().then(isHealthy => {
    if (!isHealthy) {
        showToast('Atenção: Verifique se o backend está rodando na porta 8080', 'error');
    }
});
