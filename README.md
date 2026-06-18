# Storage Controller API

> Sistema de gestão e monitoramento de recursos operacionais para bases e setores.

![Java](https://img.shields.io/badge/Java-21-orange?style=flat-square&logo=java)
![Spring Boot](https://img.shields.io/badge/Spring_Boot-3.x-brightgreen?style=flat-square&logo=springboot)
![Oracle](https://img.shields.io/badge/Oracle_DB-21c-red?style=flat-square&logo=oracle)
![Maven](https://img.shields.io/badge/Maven-build-blue?style=flat-square&logo=apachemaven)
![Swagger](https://img.shields.io/badge/Swagger-OpenAPI-85EA2D?style=flat-square&logo=swagger)

---

## Sobre o Projeto

O **Storage Controller** é uma API REST desenvolvida em Java com Spring Boot para gerenciar recursos distribuídos em bases e setores operacionais. A aplicação permite o cadastro de recursos, o registro de consumos e reabastecimentos, o monitoramento automático dos níveis de estoque e a geração de alertas quando recursos críticos atingem níveis insuficientes.

O projeto foi desenvolvido com foco em boas práticas de arquitetura REST, separação de responsabilidades em camadas, regras de negócio consistentes e persistência em Oracle Database via JPA/Hibernate.

---

## Funcionalidades

- Cadastro e gerenciamento de bases, setores e recursos
- Registro de movimentações de consumo e reabastecimento
- Cálculo automático de status do recurso (OK, ATENÇÃO, CRÍTICO)
- Geração e resolução automática de alertas para recursos críticos
- Controle de acesso por perfil de usuário (Operator e Viewer)
- Histórico permanente e imutável de todas as movimentações
- Respostas com links HATEOAS para navegação entre recursos
- Documentação interativa via Swagger/OpenAPI

---

## Tecnologias

| Tecnologia        | Uso |
|-------------------|---|
| Java 21           | Linguagem principal |
| Spring Boot 3.x   | Framework da aplicação |
| Spring Data JPA   | Abstração de acesso ao banco |
| Hibernate         | Provedor JPA / ORM |
| H2 Database        | Banco de dados relacional |
| Spring HATEOAS    | Links de navegação nas respostas |
| Springdoc OpenAPI | Documentação Swagger |
| Lombok            | Redução de boilerplate |
| Maven             | Gerenciamento de dependências |

---

## Arquitetura

O projeto segue arquitetura em camadas com separação clara de responsabilidades:

```
br.com.storage_controller
  ├── config/         → Configurações (CORS)
  ├── controller/     → Recebimento das requisições HTTP
  ├── service/        → Regras de negócio
  ├── repository/     → Comunicação com o banco de dados
  ├── entity/         → Entidades JPA (tabelas do banco)
  ├── dto/            → Transferência de dados (entrada e saída)
  └── exception/      → Tratamento centralizado de exceções
```

### Controller
Recebe as requisições HTTP, valida os dados de entrada com `@Valid` e delega o processamento ao service. Retorna respostas com status HTTP corretos e links HATEOAS via assembler.

### Service
Concentra toda a lógica de negócio: validação de permissões por tipo de usuário, cálculo automático de status de recursos, sincronização de alertas e controle de integridade dos dados.

### Repository
Interfaces que estendem `JpaRepository`, utilizando a convenção de nomes do Spring Data JPA para geração automática de queries sem SQL explícito.

### Entity
Classes anotadas com `@Entity` que representam as tabelas do banco. Utiliza dois padrões avançados de modelagem:

- **Herança SINGLE_TABLE** — a entidade `Usuario` armazena todos os perfis em uma única tabela com coluna discriminadora `tipo_usuario`:
  ```
  Usuario (SINGLE_TABLE)
    ├── Operator  →  leitura e escrita
    └── Viewer    →  somente leitura
  ```

- **Embedded** — a entidade `Setor` usa `@Embeddable` na classe `SetorInfo` para encapsular `nome` e `descricao` diretamente na tabela, sem joins adicionais.

### DTO
Records Java imutáveis para transferência de dados entre cliente e API. Cada entidade tem DTOs separados por operação: `CadastroDTO`, `AtualizarDTO` e `ListagemDTO`.

### Assembler
Implementam `RepresentationModelAssembler` do Spring HATEOAS, adicionando links de navegação às respostas via `WebMvcLinkBuilder`.

---

## Modelagem de Dados

```
Base
 └── Setor
      └── Recurso
           └── Movimentação → Alerta
```

### Tabelas

| Tabela | Descrição |
|---|---|
| `st_t_bases` | Bases operacionais |
| `st_t_setores` | Setores de cada base |
| `st_t_usuarios` | Usuários (Operator e Viewer via SINGLE_TABLE) |
| `st_t_recursos` | Recursos com quantidade, capacidade e status |
| `st_t_movimentacoes` | Histórico imutável de consumos e reabastecimentos |
| `st_t_alertas` | Alertas gerados automaticamente para recursos críticos |

---

## Regras de Negócio

- Apenas `Operator` pode criar, editar e deletar recursos, registrar movimentações e resolver alertas — `Viewer` recebe `403 Forbidden`
- A quantidade atual do recurso não pode ser negativa nem ultrapassar a capacidade máxima
- O valor mínimo deve ser menor que a capacidade máxima
- O status é calculado automaticamente com tolerância de ponto flutuante (`0.0001`) para evitar inconsistências com decimais
- Alertas são gerados apenas para recursos marcados como `crítico = true`
- Quando um recurso crítico atinge status ATENÇÃO ou CRÍTICO, o alerta é gerado ou atualizado — nunca duplicado
- Quando o recurso volta ao status OK, o alerta é resolvido automaticamente
- Tentativas de resolver um alerta já resolvido retornam `400 Bad Request`
- Movimentações são imutáveis — sem endpoints de edição ou exclusão
- Recursos com movimentações vinculadas não podem ser deletados (`409 Conflict`)
- Emails de usuários são únicos no sistema

---

## Endpoints

**Base URL:** `http://localhost:8080`

<details>
<summary><strong>Bases</strong></summary>

```
GET    /bases
POST   /bases
GET    /bases/{id}
PUT    /bases/{id}
DELETE /bases/{id}
```
</details>

<details>
<summary><strong>Setores</strong></summary>

```
GET    /setores
POST   /setores
GET    /setores/{id}
GET    /setores/base/{baseId}
PUT    /setores/{id}
DELETE /setores/{id}
```
</details>

<details>
<summary><strong>Recursos</strong></summary>

```
GET    /recursos
POST   /recursos?usuarioId={id}
GET    /recursos/{id}
GET    /recursos/setor/{setorId}
GET    /recursos/base/{baseId}
GET    /recursos/status/{status}
PUT    /recursos/{id}?usuarioId={id}
DELETE /recursos/{id}?usuarioId={id}
```
</details>

<details>
<summary><strong>Movimentações</strong></summary>

```
POST   /movimentacoes
GET    /movimentacoes/recurso/{recursoId}
GET    /movimentacoes/usuario/{usuarioId}
GET    /movimentacoes/setor/{setorId}
GET    /movimentacoes/setor/{setorId}/tipo/{tipo}
GET    /movimentacoes/base/{baseId}
```
</details>

<details>
<summary><strong>Alertas</strong></summary>

```
GET    /alertas
GET    /alertas/recurso/{recursoId}
GET    /alertas/setor/{setorId}
GET    /alertas/base/{baseId}
PATCH  /alertas/{id}/resolver?usuarioId={id}
```
</details>

<details>
<summary><strong>Usuários</strong></summary>

```
GET    /usuarios
POST   /usuarios
GET    /usuarios/{id}
GET    /usuarios/base/{baseId}
PUT    /usuarios/{id}
DELETE /usuarios/{id}
```
</details>

---

## Tratamento de Exceções

| Situação | Status HTTP |
|---|---|
| Registro não encontrado | `404 Not Found` |
| Acesso negado (Viewer tentando escrita) | `403 Forbidden` |
| Regra de negócio violada | `400 Bad Request` |
| Email ou dado duplicado / recurso com movimentações | `409 Conflict` |
| Método HTTP não suportado | `405 Method Not Allowed` |
| Erro interno | `500 Internal Server Error` |

---

## Acessar a documentação ao executar o projeto
http://localhost:8080/swagger-ui/index.html
