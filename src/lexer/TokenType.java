package lexer;

public enum TokenType {
    NUMBER,
    ID,
    STRING,
    VAR,
    PRINT,
    IF, ELSE, WHILE,

    PLUS, MINUS, STAR, SLASH,
    EQ, EQEQ, NEQ,
    LT, GT, LTEQ, GTEQ,
    AND, OR, NOT,

    LPAREN, RPAREN,
    LBRACE, RBRACE,
    SEMICOLON,

    EOF

}
