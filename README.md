# WaiterApp

**Disciplina:** TCC00346 — Qualidade e Teste de Software | UFF — 2026/1

---

## Projeto relacionado

Este repositorio contem a documentacao e os testes do **WaiterApp**. O **segundo software** avaliado no trabalho possui um README proprio e esta disponivel no repositorio:

- https://github.com/BielPimentaDev/Trabalho-POO-Simula-o-de-combate

## Descricao do Sistema

O **WaiterApp** e uma API REST de gerenciamento de pedidos para restaurantes. O sistema permite que garcons registrem e acompanhem pedidos de clientes, consultem o cardapio, gerenciem itens e processem pagamentos.

### Funcionalidades principais

| Modulo | Descricao |
|---|---|
| **Pedido** | Criacao, atualizacao, consulta e exclusao de pedidos; calculo automatico do preco total |
| **Cliente** | Cadastro de clientes, consulta por CPF, historico de pedidos |
| **Garcom** | Cadastro e gerenciamento de garcons vinculados a pedidos |
| **Cardapio** | Criacao e manutencao de cardapios com lista de itens |
| **Item (Prato/Bebida)** | Itens do menu com heranca: Prato (com ingredientes e calorias) e Bebida (com volume) |
| **ItemPedido** | Associacao entre pedido e item, com quantidade e calculo de subtotal |
| **Pagamento** | Suporte a pagamento com cartao e dinheiro (polimorfismo) |

### Tecnologias do sistema

- **Linguagem:** Java 11+
- **Framework:** Spring Boot 2.7.1
- **Persistencia:** Spring Data JPA / Hibernate + PostgreSQL
- **Documentacao da API:** Swagger (Springfox 2.9.2)
- **Build:** Maven

### Modulos testados neste trabalho

Os modulos selecionados para teste sao os que contem logica de negocio nao trivial (calculos, transicoes de estado, tratamento de excecoes):

- `PedidoService` — logica de criacao de pedido com laco sobre itens e calculo de total
- `Pedido` — calculo de preco total via stream, transicao de estado (`fecharPedido`)
- `ItemPedido` — calculo de subtotal (quantidade x preco)
- `Prato` — soma de calorias dos ingredientes via stream
- `ItemService`, `ClienteService`, `GarcomService`, `CardapioService` — CRUD com tratamento de excecoes

---

## Artefatos da Entrega 1 (27/04/2026)

> Todos os artefatos estao na branch `main` deste repositorio.

### 1. Plano de Teste

| Artefato | Link |
|---|---|
| Plano de Teste (Markdown) | [`docs/plano-de-teste.md`](docs/plano-de-teste.md) |

O plano contem: escopo, abordagem, tecnicas, ferramentas, casos de teste por classe, criterios de entrada/saida e bugs documentados.

---

### 2. Codigo-Fonte Original

| Modulo | Link |
|---|---|
| Codigo principal (todos os modulos) | [`src/main/java/com/example/waiterapp/`](src/main/java/com/example/waiterapp/) |
| Pedido (entidade + servico + repositorio) | [`src/main/java/com/example/waiterapp/Pedido/`](src/main/java/com/example/waiterapp/Pedido/) |
| Cliente | [`src/main/java/com/example/waiterapp/Cliente/`](src/main/java/com/example/waiterapp/Cliente/) |
| Garcom | [`src/main/java/com/example/waiterapp/Garcom/`](src/main/java/com/example/waiterapp/Garcom/) |
| Cardapio | [`src/main/java/com/example/waiterapp/Cardapio/`](src/main/java/com/example/waiterapp/Cardapio/) |
| Item / Prato / Bebida | [`src/main/java/com/example/waiterapp/Item/`](src/main/java/com/example/waiterapp/Item/) |
| ItemPedido | [`src/main/java/com/example/waiterapp/ItemPedido/`](src/main/java/com/example/waiterapp/ItemPedido/) |
| Pagamento | [`src/main/java/com/example/waiterapp/Pagamento/`](src/main/java/com/example/waiterapp/Pagamento/) |
| Ingrediente | [`src/main/java/com/example/waiterapp/Ingrediente/`](src/main/java/com/example/waiterapp/Ingrediente/) |
| Enums (Estado) | [`src/main/java/com/example/waiterapp/enums/Estado.java`](src/main/java/com/example/waiterapp/enums/Estado.java) |
| Excecoes customizadas | [`src/main/java/com/example/waiterapp/exceptions/`](src/main/java/com/example/waiterapp/exceptions/) |

---

### 3. Testes Unitarios Automatizados

**Ferramentas utilizadas nos testes:**

| Ferramenta | Versao | Finalidade |
|---|---|---|
| **JUnit 5** (JUnit Jupiter) | 5.8.x | Framework principal de testes unitarios |
| **Mockito** | 4.x | Criacao de mocks e verificacao de comportamento |
| **AssertJ** | 3.x | Assertions fluentes |
| **Spring Boot Test** | 2.7.1 | Infraestrutura de testes Spring (inclui JUnit + Mockito) |
| **H2 Database** | embutido | Banco em memoria para testes de contexto Spring |
| **Maven Surefire Plugin** | embutido | Execucao dos testes no ciclo `mvn test` |

**Como executar:**

```bash
mvn test
```

**Arquivos de teste:**

| Arquivo | Classe Testada | N. de Testes | Link Direto |
|---|---|---|---|
| `PedidoTest.java` | `Pedido` | 15 | [`src/test/.../Pedido/PedidoTest.java`](src/test/java/com/example/waiterapp/Pedido/PedidoTest.java) |
| `PedidoServiceTest.java` | `PedidoService` | 14 | [`src/test/.../Pedido/PedidoServiceTest.java`](src/test/java/com/example/waiterapp/Pedido/PedidoServiceTest.java) |
| `ItemPedidoTest.java` | `ItemPedido` | 14 | [`src/test/.../ItemPedido/ItemPedidoTest.java`](src/test/java/com/example/waiterapp/ItemPedido/ItemPedidoTest.java) |
| `PratoTest.java` | `Prato` | 13 | [`src/test/.../Item/Prato/PratoTest.java`](src/test/java/com/example/waiterapp/Item/Prato/PratoTest.java) |
| `ItemServiceTest.java` | `ItemService` | 15 | [`src/test/.../Item/ItemServiceTest.java`](src/test/java/com/example/waiterapp/Item/ItemServiceTest.java) |
| `ClienteServiceTest.java` | `ClienteService` | 16 | [`src/test/.../Cliente/ClienteServiceTest.java`](src/test/java/com/example/waiterapp/Cliente/ClienteServiceTest.java) |
| `GarcomServiceTest.java` | `GarcomService` | 16 | [`src/test/.../Garcom/GarcomServiceTest.java`](src/test/java/com/example/waiterapp/Garcom/GarcomServiceTest.java) |
| `CardapioServiceTest.java` | `CardapioService` | 16 | [`src/test/.../Cardapio/CardapioServiceTest.java`](src/test/java/com/example/waiterapp/Cardapio/CardapioServiceTest.java) |

**Total: ~119 testes unitarios**

### 3.1 Cobertura de Testes (JaCoCo)

Resultados de cobertura observados no relatorio do JaCoCo (pacote/agrupamento geral `com.example.waiterapp`):

| Grupo | Classes | Metodos | Linhas | Branches (decisoes) |
|---|---:|---:|---:|---:|
| `com.example.waiterapp` | **93%** (31/33) | **69%** (206/297) | **68%** (423/616) | **22%** (13/58) |

**Analise:**

- **Boa cobertura estrutural (classes):** 93% sugere que quase todos os modulos principais foram ao menos carregados/exercitados pelos testes.
- **Cobertura moderada de metodos/linhas:** 69%/68% indica que ainda existem fluxos internos nao executados (validacoes, excecoes, caminhos alternativos e limites).
- **Branches baixos (22%):** o principal ponto de melhoria. Normalmente significa que muitos `if/else` (ou condicoes de negocio) foram testados apenas em um lado da decisao (ex.: so caminho valido, faltando o invalido).

**Acoes recomendadas para aumentar branches:**

- Criar testes especificamente para cobrir ambos os lados de cada condicao (valores invalidos, listas vazias, IDs inexistentes, estados nao permitidos).
- Priorizar metodos com mais validacoes/condicionais (servicos e regras de transicao de estado), pois costumam concentrar branches.

**Como visualizar o relatorio (HTML):**

- Gere o relatorio de cobertura (ex.: `mvn clean test` + `mvn jacoco:report`, dependendo da configuracao do `pom.xml`).
- Abra o arquivo: `target/site/jacoco/index.html`

### 3.2 Sugestao: exigir cobertura minima (80%) via CI/CD

Para evitar regressao na qualidade dos testes (e incentivar a cobertura de branches/linhas ao longo do tempo), uma boa pratica e configurar o pipeline de CI/CD para **falhar o build** quando a cobertura ficar abaixo de um limite minimo.

**Sugestao de politica (exemplo):**

| Metrica | Limite minimo sugerido |
|---|---:|
| Linhas (line coverage) | 80% |
| Branches (branch coverage) | 80% |

**Como implementar (alto nivel):**

- Adicionar o **JaCoCo** no `pom.xml` com a meta `check`, definindo os limites desejados.
- No CI (ex.: GitHub Actions/GitLab CI), executar `mvn clean verify`.
- Se a cobertura ficar abaixo do limite, o job falha e a PR nao pode ser mesclada ate corrigir ou justificar.

> Observacao: como a metrica de *branches* costuma ser a mais dificil, um plano realista pode ser adotar 80% primeiro para **linhas** e aumentar gradualmente o limite de **branches** (por exemplo: 40% → 60% → 80%) a cada iteracao.

**Cobertura de cenarios em cada classe:**

| Categoria | Exemplos cobertos |
|---|---|
| Happy Path | Operacao bem-sucedida com dados validos |
| Edge Cases | Lista de itens vazia, quantidade zero, preco decimal |
| Negative Cases | ID inexistente lancando excecao, violacao de integridade |
| Boundary Values | Nota minima (1) e maxima (10), quantidade 0 e 1000 |

**Boas praticas aplicadas:**
- Padrao **AAA** (Arrange / Act / Assert) em todos os testes
- **Mockito** isola 100% das dependencias externas (sem banco de dados real)
- Testes independentes entre si (sem `@TestMethodOrder` nem estado compartilhado)
- Nomes no padrao `metodo_cenario_resultadoEsperado`

---

### 4. Configuracao de Teste

| Arquivo | Link | Descricao |
|---|---|---|
| `application.properties` (test) | [`src/test/resources/application.properties`](src/test/resources/application.properties) | Configuracao H2 in-memory para testes Spring |
| `pom.xml` | [`pom.xml`](pom.xml) | Dependencias do projeto (JUnit 5, Mockito, H2) |

---

## Estrutura do Repositorio

```
waiterapp/
├── docs/
│   └── plano-de-teste.md              # Plano de Teste (Entrega 1)
├── src/
│   ├── main/java/com/example/waiterapp/
│   │   ├── Cardapio/                  # Cardapio (entidade, servico, repositorio, DTO)
│   │   ├── Cliente/                   # Cliente
│   │   ├── Garcom/                    # Garcom
│   │   ├── Ingrediente/               # Ingrediente
│   │   ├── Item/                      # Item base + Prato + Bebida
│   │   ├── ItemPedido/                # ItemPedido (chave composta)
│   │   ├── Pagamento/                 # Pagamento (cartao e dinheiro)
│   │   ├── Pedido/                    # Pedido
│   │   ├── config/                    # Configuracao Swagger
│   │   ├── enums/                     # Estado do pedido
│   │   └── exceptions/                # ObjectNotFoundException
│   ├── test/java/com/example/waiterapp/
│   │   ├── Cardapio/CardapioServiceTest.java
│   │   ├── Cliente/ClienteServiceTest.java
│   │   ├── Garcom/GarcomServiceTest.java
│   │   ├── Item/ItemServiceTest.java
│   │   ├── Item/Prato/PratoTest.java
│   │   ├── ItemPedido/ItemPedidoTest.java
│   │   ├── Pedido/PedidoTest.java
│   │   └── Pedido/PedidoServiceTest.java
│   └── test/resources/application.properties
└── pom.xml
```

---

## Como Executar

```bash
# Clonar o repositorio
git clone <url-do-repositorio>
cd waiterapp

# Executar apenas os testes unitarios (sem banco de dados necessario)
mvn test

# Executar a aplicacao completa com Docker (PostgreSQL incluido)
docker-compose up
```

---

## Historico

| Versao | Data | Descricao |
|---|---|---|
| 1.0 | 2026-04-26 | Entrega 1: testes unitarios e plano de teste |
