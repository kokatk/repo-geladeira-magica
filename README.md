# 🧊 Geladeira Mágica - API Java com IA

<div align="center">
 
</div>

<div align="center">

[![Java](https://img.shields.io/badge/Java-21-blue?logo=java)](https://www.oracle.com/java/)  [![Spring Boot](https://img.shields.io/badge/Spring_Boot-3.3.2-success?logo=springboot)](https://spring.io/projects/spring-boot)  [![Maven](https://img.shields.io/badge/Maven-3.8+-brightgreen?logo=apachemaven)](https://maven.apache.org/)  [![OpenAI](https://img.shields.io/badge/OpenAI-Integrado-brightgreen?logo=openai)](https://openai.com/)

</div>

---

## 📌 Sobre o Projeto

**Geladeira Mágica** é uma API desenvolvida como parte do curso **Java10x**, com o objetivo de aplicar conceitos avançados de desenvolvimento backend utilizando **Java 21** e **Spring Boot 3**.

Ela simula uma geladeira virtual onde você pode:

- 🧊 Gerenciar alimentos: cadastrar, listar, atualizar e remover itens  
- 🍽️ Gerar receitas inteligentes com base nos ingredientes disponíveis, integrando com a **API da OpenAI (ChatGPT)**  
- 🔁 Trabalhar com APIs síncronas e reativas (Spring MVC + WebFlux)  
- 📄 Testar todos os endpoints via Swagger UI  

---

## ✅ Recursos

- CRUD de itens da geladeira  
- Geração de receitas com IA (OpenAI)  
- Documentação interativa com Swagger  
- Banco de dados H2 + Flyway  
- Integração com WebClient (assíncrono) e MVC (síncrono)  

---

## 🧰 Tecnologias

- Java 21  
- Spring Boot 3.3.2  
- Spring MVC + WebFlux  
- Spring Data JPA + H2 + Flyway  
- Lombok, java-dotenv  
- OpenAI API (ChatGPT)  
- Maven, Swagger (Springdoc)  

---

## 📦 Instalação

```bash
git clone https://github.com/seu-usuario/geladeira-magica.git
cd geladeira-magica
```

Crie um arquivo `.env` na raiz com o conteúdo:

```
DATABASE_URL=jdbc:h2:mem:geladeira;DB_CLOSE_DELAY=-1
DATABASE_USERNAME=sa
DATABASE_PASSWORD=
API_URL=https://api.openai.com/v1/chat/completions
API_KEY=sua-chave-da-openai
```

```bash
mvn clean install
mvn spring-boot:run
```

Acesse:
- API: http://localhost:8080  
- Swagger: http://localhost:8080/swagger-ui.html  
- H2 Console: http://localhost:8080/h2-console  

---

## 📬 Endpoints

### 📥 Itens da Geladeira (CRUD)

- **POST** `/food/cadastrar` – Cadastrar item  
- **PUT** `/food/atualizar/{id}` – Atualizar item  
- **GET** `/food/listar` – Listar todos  
- **GET** `/food/listar/{id}` – Buscar por ID  
- **DELETE** `/food/deletar/{id}` – Remover item  

### 🍽️ Receitas com IA

- **GET** `/generate` – Gera com itens do banco  
- **GET** `/generate-manual` – Ingredientes via query  
- **POST** `/generate` – Ingredientes via JSON  

---

## 🤝 Contribuição

1. Faça um fork  
2. Crie uma branch: `git checkout -b feature/sua-feature`  
3. Commit: `git commit -m 'feat: nova funcionalidade'`  
4. Push: `git push origin feature/sua-feature`  
5. Abra um Pull Request  


