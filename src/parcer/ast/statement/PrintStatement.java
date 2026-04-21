package parcer.ast.statement;

import lombok.AllArgsConstructor;
import lombok.Getter;
import parcer.ast.expression.Expression;

@Getter
@AllArgsConstructor
public class PrintStatement implements Statement{
    private Expression expression;
}
