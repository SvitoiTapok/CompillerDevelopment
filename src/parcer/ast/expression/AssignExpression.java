package parcer.ast.expression;


import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class AssignExpression implements Expression {
    private Expression left;
    private String name;
}
