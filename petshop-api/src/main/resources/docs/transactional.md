# Anotação `@Transactional` do Spring

A anotação `@Transactional` do Spring é usada para gerenciar transações de banco de dados de forma declarativa. Aqui
estão algumas das principais propriedades e como utilizá-las:

1. **`value` ou `transactionManager`**: Define o gerenciador de transações a ser usado.
2. **`propagation`**: Define o comportamento de propagação da transação. Valores comuns incluem `REQUIRED`,
   `REQUIRES_NEW`, `MANDATORY`, etc.
3. **`isolation`**: Define o nível de isolamento da transação. Valores comuns incluem `DEFAULT`, `READ_COMMITTED`,
   `REPEATABLE_READ`, etc.
4. **`timeout`**: Define o tempo limite da transação em segundos.
5. **`readOnly`**: Indica se a transação é somente leitura.
6. **`rollbackFor`**: Define exceções que devem causar rollback da transação.
7. **`noRollbackFor`**: Define exceções que não devem causar rollback da transação.

Exemplo de uso:

```java
import org.springframework.transaction.annotation.Transactional;

@Transactional(
        transactionManager = "transactionManager",
        propagation = Propagation.REQUIRED,
        isolation = Isolation.READ_COMMITTED,
        timeout = 30,
        readOnly = false,
        rollbackFor = {RuntimeException.class, Exception.class},
        noRollbackFor = {IllegalArgumentException.class}
)
public void myTransactionalMethod() {
    // código do método
}
```

Este exemplo configura uma transação com um gerenciador específico, propagação `REQUIRED`, isolamento `READ_COMMITTED`,
tempo limite de 30 segundos, não somente leitura, rollback para `RuntimeException` e `Exception`, e sem rollback para
`IllegalArgumentException`.