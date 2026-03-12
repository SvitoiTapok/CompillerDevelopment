package parcer.ast;


import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class VariableExpression implements Expression {
    private String name;
}
