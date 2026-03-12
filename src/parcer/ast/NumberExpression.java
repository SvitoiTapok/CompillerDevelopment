package parcer.ast;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class NumberExpression implements Expression {
    private int value;
}
