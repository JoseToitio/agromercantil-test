# Sistema de Gerenciamento de Frota de Caminhões

## Descrição
Este projeto implementa um sistema completo para gerenciamento de frota de caminhões para uma empresa de transporte no agronegócio. O sistema permite cadastrar, listar e atualizar informações de caminhões, com integração à API FIPE para validação de dados e obtenção de valores de referência.

## Estrutura do Projeto

### Backend (Java/Spring Boot)
- API REST para gerenciamento de caminhões
- Integração com API FIPE para validação de dados e obtenção de valores de referência
- Validações de negócio (placa única, dados válidos na FIPE)

### Frontend (AngularJS)
- Interface para listagem, cadastro e atualização de caminhões
- Validação de formulários
- Tratamento de erros e feedback ao usuário

## Funcionalidades
- Cadastro de caminhões com validação na API FIPE
- Listagem de caminhões cadastrados
- Atualização de dados de caminhões existentes
- Validação para evitar duplicidade de placas
- Obtenção automática do valor de referência da FIPE

## Tecnologias Utilizadas
- Backend: Java, Spring Boot, Spring Data JPA, Spring Web
- Frontend: AngularJS (1.8.2), HTML, CSS, Bootstrap
- Banco de Dados: H2 (para desenvolvimento)
- Integração: API FIPE (https://deividfortuna.github.io/fipe/v2/)

## Como Executar

### Backend
1. Navegue até a pasta `backend`
2. Execute `mvn spring-boot:run`
3. A API estará disponível em `http://localhost:8080`

### Frontend
1. Navegue até a pasta `frontend`
2. Execute `npm install` para instalar as dependências
3. Execute `npm start` para iniciar o servidor HTTP
4. A aplicação estará disponível em `http://localhost:4200`

Alternativamente, você pode abrir o arquivo `index.html` diretamente no navegador para desenvolvimento local.

## Documentação da API
A documentação da API está disponível em `http://localhost:8080/swagger-ui.html` quando o backend estiver em execução.