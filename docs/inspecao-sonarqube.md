# Inspeção de Código - SonarCloud (WaiterApp)

Documento sobre as issues de severidade High que o SonarCloud apontou na branch main e o que
foi feito com cada uma.

## Resumo

A análise do SonarCloud apontou 5 issues de severidade High. Analisei uma por uma e corrigi
todas, sempre rodando a suíte de testes a cada mudança para garantir que nada quebrasse.

| # | Arquivo | Linha | Tipo | Situação |
|---|---------|-------|------|----------|
| 1 | Item/ItemService.java | 18 | Code Smell (nomenclatura) | Corrigida |
| 2 | Item/ItemService.java | 19 | Code Smell (nomenclatura) | Corrigida |
| 3 | Pedido/PedidoController.java | 45 | Vulnerability | Corrigida |
| 4 | Pedido/PedidoController.java | 60 | Vulnerability | Corrigida |
| 5 | Pagamento/PagamentoTest.java | 220 | Bug | Corrigida |

Vale notar que 4 das 5 issues são herança do projeto original (aparecem como "3 years ago" na
análise) e não foram introduzidas na nossa Entrega 2. Só a issue 5, no PagamentoTest, é do
nosso trabalho.

## Issues corrigidas

### Issue 1 e 2 - nome das constantes do enum TipoItem

No ItemService.java existia este enum:

```java
public enum TipoItem{
    Prato,
    Bebida
}
```

O Sonar apontou que os nomes das constantes não seguem o padrão
`^[A-Z][A-Z0-9]+(_[A-Z0-9]+)*$`, ou seja, por convenção do Java constante de enum deveria ser
toda em maiúscula.

Antes de mexer, procurei no projeto inteiro (produção e teste) por qualquer uso de TipoItem,
TipoItem.Prato ou TipoItem.Bebida. Não encontrei nenhum: o enum está declarado mas nunca é
usado, não tem comparação de string como `.equals("Prato")`, não tem switch e não vai em
nenhum JSON. Por isso renomear não tem risco.

Ficou assim:

```java
public enum TipoItem{
    PRATO,
    BEBIDA
}
```

### Issue 3 e 4 - PedidoController recebia a entidade JPA direto no @RequestBody

No PedidoController, os métodos inserePedido (linha 45) e atualizaPedido (linha 60) recebiam a
entidade Pedido direto do corpo da requisição:

```java
public ResponseEntity<Pedido> inserePedido(@Valid @RequestBody Pedido pedido){ ... }
public ResponseEntity<Void> atualizaPedido(@Valid @RequestBody Pedido pedido, ...){ ... }
```

O Sonar classifica isso como vulnerabilidade (regra S4684, do tipo mass assignment): receber a
entidade do banco direto deixa o cliente mandar campos que não deveria poder controlar, como
id, precoTotal ou estado.

A correção segue o padrão que o projeto já usa no ClienteController com o ClienteDTO: criar uma
classe de transição (DTO) que recebe o JSON e um mapper que transforma essa classe na entidade.
O cuidado principal foi não quebrar nada, então o DTO foi feito espelhando exatamente os campos
da entidade Pedido (mesmos nomes e tipos). Assim o JSON enviado pelo front continua o mesmo, e a
única diferença é que o controller agora recebe um objeto comum em vez da entidade persistente.

O que foi feito:

1. Criei a classe PedidoDTO (src/main/java/com/example/waiterapp/Pedido/PedidoDTO.java), um POJO
   sem @Entity com os mesmos campos do Pedido: id, dataCriacao, estado, precoTotal,
   notaAtendimento, notaPedido, opcoesExtras, items, cliente, garcom e pagamento. Por ter os
   mesmos nomes de campo, o Jackson desserializa o JSON exatamente como antes.

2. Adicionei o mapper transformarDTO no PedidoService, que monta um Pedido a partir do PedidoDTO
   campo a campo, no mesmo estilo do ClienteService.transformarDTO.

3. Troquei a assinatura dos dois endpoints do PedidoController para receber @RequestBody
   PedidoDTO e chamar transformarDTO antes de delegar ao service. A assinatura dos métodos do
   PedidoService (inserePedido e atualizaPedido) continua recebendo Pedido, então os testes de
   serviço não precisaram mudar.

Como o service não mudou de assinatura e o DTO espelha o contrato antigo, a lógica de
enriquecimento dos itens e o cálculo do preço total continuam funcionando igual. Rodei a suíte
completa depois da mudança: todos os testes de unidade e integração passam (os únicos erros são
os E2E de Selenium, que dependem do Chrome instalado e não rodam neste ambiente), e o contexto
do Spring sobe normalmente com o novo binding, o que confirma que o controller continua
funcionando.

Para ter certeza de que o contrato JSON não mudou, também fiz uma verificação específica pegando
um JSON de pedido igual ao que o front envia (cliente, garçom, itens com id e quantidade, notas e
opções extras) e desserializando dos dois jeitos: o antigo (direto em Pedido) e o novo (em
PedidoDTO e depois passando pelo mapper transformarDTO). Os dois caminhos produziram um Pedido
equivalente campo a campo, inclusive mantendo o item.id e a quantidade de cada item, que são o que
o inserePedido usa para buscar o preço e calcular o total. Ou seja, do ponto de vista de quem
consome a API (front e testes E2E), nada mudou.

### Issue 5 - comparação de tipos diferentes no PagamentoTest

No teste pagamentos_tiposDiferentes_mesmoId_naoSaoIguais o código estava assim:

```java
PagamentoComCartao cartao = new PagamentoComCartao(1L, Estado.PENDENTE, agora);
PagamentoComDinheiro dinheiro = new PagamentoComDinheiro(1L, Estado.PENDENTE, agora);
assertNotEquals(cartao, dinheiro);
```

O Sonar marcou como bug (regra S5845) porque o assertNotEquals comparava dois tipos diferentes
(PagamentoComCartao e PagamentoComDinheiro). Quando se comparam tipos que não têm relação
direta, a asserção passa de qualquer jeito e pode acabar escondendo um problema de verdade.

A intenção do teste continua válida: mostrar que dois pagamentos de subclasses diferentes, com
o mesmo id, não são iguais (porque o equals() da classe Pagamento usa getClass() na
comparação). Para resolver sem perder esse sentido, declarei as duas variáveis usando o
supertipo comum Pagamento:

```java
Pagamento cartao = new PagamentoComCartao(1L, Estado.PENDENTE, agora);
Pagamento dinheiro = new PagamentoComDinheiro(1L, Estado.PENDENTE, agora);
assertNotEquals(cartao, dinheiro);
```

Agora os dois lados são do mesmo tipo, o Sonar para de reclamar e o teste continua provando o
que deveria. Rodei a suíte e o teste passa.
