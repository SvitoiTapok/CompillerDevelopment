package parcer.ast.statement;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class BlockStatement implements Statement{
    private List<Statement> statements;
}
