package lexer;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@AllArgsConstructor
@ToString
public class Token {
    private TokenType type;
    private String value;
    private int pos;

}
