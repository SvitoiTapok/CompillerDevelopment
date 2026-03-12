package parcer.ast;

import lexer.Token;
import lexer.TokenType;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UnaryExpression implements Expression{
    private TokenType token;
    private Expression expression;
}
