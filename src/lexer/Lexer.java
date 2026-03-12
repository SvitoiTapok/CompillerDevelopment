package lexer;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@AllArgsConstructor
@Getter
public class Lexer {
    private String str;
    private int length;
    private int pos=0;
//                case "var":
//                        tokens.add(new lexer.Token(lexer.TokenType.VAR, word, start));
//                break;
//            case "print":
//                    tokens.add(new lexer.Token(lexer.TokenType.PRINT, word, start));
//                break;
//            case "if":
//                    tokens.add(new lexer.Token(lexer.TokenType.IF, word, start));
//                break;
//            case "while":
//                    tokens.add(new lexer.Token(lexer.TokenType.WHILE, word, start));
//                break;
//            case "else":
//                    tokens.add(new lexer.Token(lexer.TokenType.ELSE, word, start));

    private char Peek(){
        if(pos == length){
            return '\0';
        }
        return str.charAt(pos);
    }
    private char Next(){
        if(pos == length){
            return '\0';
        }
        return str.charAt(pos++);
    }
    public Collection<Token> Tokenize() throws Exception {
        List<Token> tokens = new ArrayList<>();
        while(pos < length){
            char ch = str.charAt(pos);
                var current = Peek();

                if (Character.isWhitespace(ch))
                {
                    Next();
                    continue;
                }

                if (Character.isDigit(current))
                {
                    this.TokenizeNumber(tokens);
                    continue;
                }

                if (Character.isLetter(current))
                {
                    this.TokenizeWord(tokens);
                    continue;
                }

                TokenizeOperator(tokens);
            }
        return tokens;
    }
    private void TokenizeNumber(List<Token> tokens){
        int start = pos;
        while (Character.isDigit(Peek()))
            Next();
        tokens.add(new Token(TokenType.NUMBER, str.substring(start, pos), start));
    }
    private void TokenizeWord(List<Token> tokens){
        int start = pos;
        while (Character.isLetterOrDigit(Peek()))
            Next();
        String word = str.substring(start, pos);
        switch (word){
            case "var":
                tokens.add(new Token(TokenType.VAR, word, start));
                break;
            case "print":
                tokens.add(new Token(TokenType.PRINT, word, start));
                break;
            case "if":
                tokens.add(new Token(TokenType.IF, word, start));
                break;
            case "while":
                tokens.add(new Token(TokenType.WHILE, word, start));
                break;
            case "else":
                tokens.add(new Token(TokenType.ELSE, word, start));
                break;
            default:
                tokens.add(new Token(TokenType.ID, word, start));
        }
    }
    private void TokenizeOperator(List<Token> tokens) throws Exception{
        int start = pos;
        char x = Peek();
        switch (x){
            case '+':
                Next();
                tokens.add(new Token(TokenType.PLUS, "+", start));
                break;
            case '-':
                Next();
                tokens.add(new Token(TokenType.MINUS, "-", start));
                break;
            case '*':
                Next();
                tokens.add(new Token(TokenType.STAR, "*", start));
                break;
            case '/':
                Next();
                tokens.add(new Token(TokenType.SLASH, "/", start));
                break;
            case '=':
                Next();
                tokens.add(new Token(TokenType.EQ, "=", start));
                break;
            case ';':
                Next();
                tokens.add(new Token(TokenType.SEMICOLON, ";", start));
                break;
            default:
                throw new Exception("Unexpected character " + x + " at position " + pos);
        }
    }
}
