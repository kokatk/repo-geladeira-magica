# 🧊✨ Geladeira Mágica

Uma aplicação web completa para gerenciar ingredientes da sua geladeira e gerar receitas incríveis usando Inteligência Artificial!

## 🚀 Funcionalidades

### 📦 Gerenciamento de Ingredientes
- ✅ Adicionar novos ingredientes com nome, categoria, quantidade e data de validade
- ✅ Visualizar todos os ingredientes da geladeira
- ✅ Editar informações dos ingredientes
- ✅ Excluir ingredientes
- ✅ Status visual de validade (fresco, vencendo em breve, vencido)

### 🍳 Geração de Receitas com IA
- ✅ Gerar receitas automaticamente com os ingredientes disponíveis na geladeira
- ✅ Gerar receitas com ingredientes inseridos manualmente
- ✅ Integração com OpenAI GPT para receitas criativas e detalhadas

### 🎨 Interface Moderna
- ✅ Design responsivo e moderno
- ✅ Notificações toast para feedback do usuário
- ✅ Loading spinners para melhor UX
- ✅ Modal para edição de ingredientes
- ✅ Ícones Font Awesome para melhor visual

## 🛠️ Tecnologias Utilizadas

### Backend
- **Java 21** - Linguagem de programação
- **Spring Boot 3.5.4** - Framework principal
- **Spring Data JPA** - Persistência de dados
- **Spring WebFlux** - Cliente reativo para API externa
- **H2 Database** - Banco de dados em memória
- **Flyway** - Migração de banco de dados
- **Lombok** - Redução de boilerplate
- **SpringDoc OpenAPI** - Documentação da API

### Frontend
- **HTML5** - Estrutura
- **CSS3** - Estilização moderna com gradientes e animações
- **JavaScript ES6+** - Lógica e comunicação com API
- **Font Awesome** - Ícones

## 📋 Pré-requisitos

- Java 21 ou superior
- Maven 3.6 ou superior
- Chave da API OpenAI (opcional, para geração de receitas)

## 🚀 Como Executar

### 1. Clone o repositório
```bash
git clone <url-do-repositorio>
cd projeto-geladeira-ia-teste
```

### 2. Configure a API OpenAI (Opcional)
Para usar a funcionalidade de geração de receitas, configure a variável de ambiente:

**Windows:**
```cmd
set OPENAI_API_KEY=sua_chave_aqui
```

**Linux/Mac:**
```bash
export OPENAI_API_KEY=sua_chave_aqui
```

### 3. Execute a aplicação
```bash
mvnw.cmd spring-boot:run
```

### 4. Acesse a aplicação
Abra seu navegador e acesse: [http://localhost:8080](http://localhost:8080)

## 📚 Endpoints da API

### Gerenciamento de Ingredientes
- `GET /food/listar` - Listar todos os ingredientes
- `GET /food/listar/{id}` - Buscar ingrediente por ID
- `POST /food/cadastrar` - Adicionar novo ingrediente
- `PUT /food/atualizar/{id}` - Atualizar ingrediente
- `DELETE /food/deletar/{id}` - Excluir ingrediente

### Geração de Receitas
- `GET /generate` - Gerar receita com ingredientes da geladeira
- `GET /generate-manual?ingredients=item1&ingredients=item2` - Gerar receita com ingredientes manuais (query params)
- `POST /generate` - Gerar receita com ingredientes manuais (JSON)

### Documentação
- `GET /swagger-ui.html` - Interface Swagger
- `GET /v3/api-docs` - Documentação OpenAPI JSON

## 🗄️ Banco de Dados

A aplicação usa H2 Database em memória. Para acessar o console:
- URL: [http://localhost:8080/h2-console](http://localhost:8080/h2-console)
- JDBC URL: `jdbc:h2:mem:geladeira`
- Username: `sa`
- Password: (deixe em branco)

## 🎯 Como Usar

### 1. Adicionar Ingredientes
1. Preencha o formulário "Adicionar Item"
2. Selecione a categoria apropriada
3. Defina a quantidade e data de validade
4. Clique em "Adicionar Item"

### 2. Gerenciar Ingredientes
- **Visualizar**: Os ingredientes aparecem em cards com status de validade
- **Editar**: Clique no botão "Editar" no card do ingrediente
- **Excluir**: Clique no botão "Excluir" (com confirmação)
- **Atualizar**: Use o botão "Atualizar Lista"

### 3. Gerar Receitas
- **Automática**: Clique em "Receita com Ingredientes da Geladeira"
- **Manual**: Digite ingredientes separados por vírgula e clique em "Gerar Receita Manual"

## 🔧 Configurações

O arquivo `application.properties` contém todas as configurações:

```properties
# Porta do servidor
server.port=8080

# Configurações do banco H2
spring.datasource.url=jdbc:h2:mem:geladeira

# API OpenAI
API_URL=https://api.openai.com/v1/chat/completions
API_KEY=${OPENAI_API_KEY:}

# CORS habilitado para frontend
spring.web.cors.allowed-origins=*
```

## 🎨 Características do Frontend

- **Design Responsivo**: Funciona em desktop, tablet e mobile
- **Tema Moderno**: Gradientes, sombras e animações suaves
- **UX Intuitiva**: Feedback visual para todas as ações
- **Acessibilidade**: Foco visível e navegação por teclado
- **Performance**: JavaScript vanilla otimizado

## 🚨 Tratamento de Erros

- Validação de formulários no frontend
- Tratamento de erros de API com mensagens amigáveis
- Fallbacks para quando a API OpenAI não está disponível
- Verificação de saúde da API na inicialização

## 🔮 Funcionalidades Futuras

- [ ] Autenticação de usuários
- [ ] Categorias personalizadas
- [ ] Histórico de receitas geradas
- [ ] Notificações de vencimento
- [ ] Lista de compras automática
- [ ] Integração com outras APIs de receitas
- [ ] Modo offline
- [ ] Exportar/importar dados

## 🤝 Contribuindo

1. Faça um fork do projeto
2. Crie uma branch para sua feature (`git checkout -b feature/AmazingFeature`)
3. Commit suas mudanças (`git commit -m 'Add some AmazingFeature'`)
4. Push para a branch (`git push origin feature/AmazingFeature`)
5. Abra um Pull Request

## 📄 Licença

Este projeto está sob a licença MIT. Veja o arquivo `LICENSE` para mais detalhes.

## 👨‍💻 Autor

Desenvolvido com ❤️ para facilitar o gerenciamento da sua geladeira e descobrir receitas incríveis!

---

**Dica**: Para obter uma chave da API OpenAI, visite [https://platform.openai.com/api-keys](https://platform.openai.com/api-keys)