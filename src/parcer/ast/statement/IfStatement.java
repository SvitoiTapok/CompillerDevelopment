package parcer.ast.statement;

import lombok.AllArgsConstructor;
import lombok.Getter;
import parcer.ast.expression.Expression;


@Getter
@AllArgsConstructor
public class IfStatement implements Statement{
    private Expression condition;
    private Statement thenStatement;
    private Statement elseStatement;
}
