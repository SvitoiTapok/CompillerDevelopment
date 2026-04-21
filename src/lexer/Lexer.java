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
    private int line;
    private int pos;
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

    private char peek(){
        if(pos == length){
            return '\0';
        }
        return str.charAt(pos);
    }
    private void next(){
        if(str.charAt(pos) == '\n'){
            line++;
        }
        pos++;
    }
    public Collection<Token> Tokenize() throws Exception {
        List<Token> tokens = new ArrayList<>();
        while(pos < length){
            char ch = str.charAt(pos);
                var current = peek();

                if (Character.isWhitespace(ch))
                {
                    next();
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
        while (Character.isDigit(peek()))
            next();
        tokens.add(new Token(TokenType.NUMBER, str.substring(start, pos), line, start));
    }
    private void TokenizeWord(List<Token> tokens){
        int start = pos;
        while (Character.isLetterOrDigit(peek()))
            next();
        String word = str.substring(start, pos);
        switch (word){
            case "var":
                tokens.add(new Token(TokenType.VAR, word,line,  start));
                break;
            case "print":
                tokens.add(new Token(TokenType.PRINT, word,line,  start));
                break;
            case "if":
                tokens.add(new Token(TokenType.IF, word,line,  start));
                break;
            case "while":
                tokens.add(new Token(TokenType.WHILE, word,line,  start));
                break;
            case "else":
                tokens.add(new Token(TokenType.ELSE, word,line,  start));
                break;
            default:
                tokens.add(new Token(TokenType.ID, word,line,  start));
        }
    }
    private void TokenizeOperator(List<Token> tokens) throws Exception{
        int start = pos;
        char x = peek();
        switch (x){
            case '+':
                next();
                tokens.add(new Token(TokenType.PLUS, "+",line,  start));
                break;
            case '-':
                next();
                tokens.add(new Token(TokenType.MINUS, "-",line,  start));
                break;
            case '*':
                next();
                tokens.add(new Token(TokenType.STAR, "*",line,  start));
                break;
            case '/':
                next();
                tokens.add(new Token(TokenType.SLASH, "/",line,  start));
                break;
            case '=':
                next();
                tokens.add(new Token(TokenType.EQ, "=",line,  start));
                break;
            case ';':
                next();
                tokens.add(new Token(TokenType.SEMICOLON, ";",line,  start));
                break;
            default:
                throw new Exception("Unexpected character " + x + " at position " + pos);
        }
    }
}
