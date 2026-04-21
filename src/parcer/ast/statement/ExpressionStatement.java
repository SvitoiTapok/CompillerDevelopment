package parcer.ast.statement;


import lombok.AllArgsConstructor;
import lombok.Getter;
import parcer.ast.expression.Expression;

@Getter
@AllArgsConstructor
public class ExpressionStatement implements Statement{
    private Expression expression;
}
