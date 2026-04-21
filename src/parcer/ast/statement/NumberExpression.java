package parcer.ast.statement;

import lombok.AllArgsConstructor;
import lombok.Getter;
import parcer.ast.expression.Expression;

@AllArgsConstructor
@Getter
public class NumberExpression implements Expression {
    private double value;
}
