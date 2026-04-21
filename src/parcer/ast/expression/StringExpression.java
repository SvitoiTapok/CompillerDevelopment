package parcer.ast.expression;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class StringExpression implements Expression {
    private String str;
}
