package parcer.ast.expression;

import lexer.TokenType;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class BinaryExpression implements Expression {
    private Expression left;
    private Expression right;
    private TokenType operator;
}
