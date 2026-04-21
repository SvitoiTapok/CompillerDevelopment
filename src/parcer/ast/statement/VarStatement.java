package parcer.ast.statement;


import lombok.AllArgsConstructor;
import lombok.Getter;
import parcer.ast.expression.Expression;

@Getter
@AllArgsConstructor
public class VarStatement implements Statement{
    private String name;
    private Expression value;

}
