# Equipe

- Cícero Arthur Otaviano Feitosa
- Carlos Henrique Dantas Melo

# API SINAN - Ficha de Notificação/Conclusão

API REST para cadastrar, consultar, alterar e excluir notificações de agravos no formato da
**Ficha de Notificação/Conclusão do SINAN** (Ministério da Saúde). O projeto já inclui as páginas
web (HTML/CSS/JS puro), então basta rodar a aplicação e abrir o navegador.

**Tecnologias:** Java 21, Spring Boot, PostgreSQL.

## O que você precisa ter instalado

- Java 21 (JDK)
- PostgreSQL
- IntelliJ IDEA

## Como executar

### 1. Criar o banco de dados

No PostgreSQL (pgAdmin ou `psql`), crie um banco com o nome **`sinan_api`**:

```sql
CREATE DATABASE sinan_api;
```

> Só o banco precisa ser criado. As tabelas são criadas automaticamente na primeira execução.

### 2. Configurar usuário e senha

Abra o arquivo `src/main/resources/application.properties` e troque as linhas de usuário e senha
pelos dados do **seu** PostgreSQL:

```properties
spring.datasource.username=postgres
spring.datasource.password=SUA_SENHA
```

- Se o seu usuário do PostgreSQL **não for `postgres`**, troque também o `username`.
- A porta padrão usada é `5432`. Se o seu PostgreSQL usa outra porta, ajuste a linha
  `spring.datasource.url`.

### 3. Rodar no IntelliJ

1. Abra a pasta do projeto no IntelliJ (`File > Open`) e aguarde o Maven baixar as dependências.
2. Abra a classe `ApiSinanApplication`
   (`src/main/java/br/edu/ifpb/apisinan/ApiSinanApplication.java`).
3. Clique no botão verde **Run** ao lado do `main`.

### 4. Usar o sistema

Abra no navegador: **<http://localhost:8080>**
