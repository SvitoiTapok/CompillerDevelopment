package parcer.ast;


import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ExpressionStatement implements Statement{
    private Expression expression;
}
