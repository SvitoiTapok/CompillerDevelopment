package parcer.ast;

import lexer.TokenType;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.function.BinaryOperator;

@Getter
@AllArgsConstructor
public class BinaryExpression implements Expression {
    private Expression left;
    private Expression right;
    private TokenType operator;
}
