import lexer.Lexer;
import lexer.Token;

import java.util.List;

public class Main {
    public static void main(String[] args) throws Exception {
        String example = "var x = 123; print x * 9;";
        Lexer lexer = new Lexer(example, example.length(), 0);
        List<Token> tokens = (List<Token>) lexer.Tokenize();
        for (Token token : tokens) {
            System.out.println(token);
        }
    }
}