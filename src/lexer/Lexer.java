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
    private int debugPos;
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
            debugPos = -1;
        }
        debugPos++;
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
        tokens.add(new Token(TokenType.NUMBER, str.substring(start, pos), line, debugPos));
    }
    private void TokenizeWord(List<Token> tokens){
        int start = pos;
        while (Character.isLetterOrDigit(peek()))
            next();
        String word = str.substring(start, pos);
        switch (word){
            case "var":
                tokens.add(new Token(TokenType.VAR, word,line,  debugPos));
                break;
            case "print":
                tokens.add(new Token(TokenType.PRINT, word,line,  debugPos));
                break;
            case "if":
                tokens.add(new Token(TokenType.IF, word,line,  debugPos));
                break;
            case "while":
                tokens.add(new Token(TokenType.WHILE, word,line,  debugPos));
                break;
            case "else":
                tokens.add(new Token(TokenType.ELSE, word,line,  debugPos));
                break;
            default:
                tokens.add(new Token(TokenType.ID, word,line,  debugPos));
        }
    }
    private void TokenizeOperator(List<Token> tokens) throws Exception{
        char x = peek();
        switch (x){
            case '+':
                next();
                tokens.add(new Token(TokenType.PLUS, "+",line,  debugPos));
                break;
            case '-':
                next();
                tokens.add(new Token(TokenType.MINUS, "-",line,  debugPos));
                break;
            case '*':
                next();
                tokens.add(new Token(TokenType.STAR, "*",line,  debugPos));
                break;
            case '/':
                next();
                tokens.add(new Token(TokenType.SLASH, "/",line,  debugPos));
                break;
            case '=':
                next();
                if(peek()=='='){
                    next();
                    tokens.add(new Token(TokenType.EQEQ, "==", line, debugPos));
                }else {
                    tokens.add(new Token(TokenType.EQ, "=",line,  debugPos));
                }


                break;
            case ';':
                next();
                tokens.add(new Token(TokenType.SEMICOLON, ";",line,  debugPos));
                break;
            case '(':
                next();
                tokens.add(new Token(TokenType.LPAREN, "(",line,  debugPos));
                break;
            case ')':
                next();
                tokens.add(new Token(TokenType.RPAREN, "(",line,  debugPos));
                break;
            case '!':
                next();
                if(peek()=='='){
                    next();
                    tokens.add(new Token(TokenType.NEQ, "!=", line, debugPos));
                }
                else {
                    tokens.add(new Token(TokenType.NOT, "(",line,  debugPos));
                }

                break;
            default:
                throw new Exception("Unexpected character " + x + " at position " + debugPos);
        }
    }
}
