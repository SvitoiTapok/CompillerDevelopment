package parcer.ast.expression;


import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class VariableExpression implements Expression {
    private String name;
}
