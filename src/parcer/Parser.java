package parcer;

//import CompilerLabs.Core.Lexer.Token;
//import CompilerLabs.Core.Lexer.TokenType;
//import CompilerLabs.Core.Parser.Ast.*;

import lexer.Token;
import lexer.TokenType;
import parcer.ast.expression.*;
import parcer.ast.statement.*;

import java.util.ArrayList;
import java.util.List;

public class Parser {
    private final List<Token> tokens;
    private int position;

    public Parser(Iterable<Token> tokens) {
        this.tokens = new ArrayList<>();
        for (Token token : tokens) {
            this.tokens.add(token);
        }
        this.position = 0;
    }

    public List<Statement> parse() {
        List<Statement> statements = new ArrayList<>();
        while (!isAtEnd()) {
            statements.add(parseDeclaration());
        }
        return statements;
    }

    private Statement parseDeclaration() {
        if (match(TokenType.VAR)) return parseVarDeclaration();
        return parseStatement();
    }

    private Statement parseStatement() {
        if (match(TokenType.IF)) return parseIfStatement();
        if (match(TokenType.WHILE)) return parseWhileStatement();
        if (match(TokenType.PRINT)) return parsePrintStatement();
        if (match(TokenType.LBRACE)) return new BlockStatement(parseBlock());

        return parseExpressionStatement();
    }

    private Statement parseVarDeclaration() {
        Token name = consume(TokenType.ID, "Ожидается имя переменной.");
        Expression initializer = null;

        if (match(TokenType.EQ)) {
            initializer = parseExpression();
        }

        consume(TokenType.SEMICOLON, "Ожидается ';' после объявления переменной.");
        return new VarStatement(name.getValue(), initializer);
    }

    private Statement parseIfStatement() {
        consume(TokenType.LPAREN, "Ожидается '(' после 'if'.");
        Expression condition = parseExpression();
        consume(TokenType.RPAREN, "Ожидается ')' после условия 'if'.");

        Statement thenBranch = parseStatement();
        Statement elseBranch = null;

        if (match(TokenType.ELSE)) {
            elseBranch = parseStatement();
        }

        return new IfStatement(condition, thenBranch, elseBranch);
    }

    private Statement parseWhileStatement() {
        consume(TokenType.LPAREN, "Ожидается '(' после 'while'.");
        Expression condition = parseExpression();
        consume(TokenType.RPAREN, "Ожидается ')' после условия 'while'.");

        Statement body = parseStatement();
        return new WhileStatement(condition, body);
    }

    private Statement parsePrintStatement() {
        Expression value = parseExpression();
        consume(TokenType.SEMICOLON, "Ожидается ';' после значения.");
        return new PrintStatement(value);
    }

    private Statement parseExpressionStatement() {
        Expression expr = parseExpression();
        consume(TokenType.SEMICOLON, "Ожидается ';' после выражения.");
        return new ExpressionStatement(expr);
    }

    private List<Statement> parseBlock() {
        List<Statement> statements = new ArrayList<>();

        while (!check(TokenType.RBRACE) && !isAtEnd()) {
            statements.add(parseDeclaration());
        }

        consume(TokenType.RBRACE, "Ожидается '}' после блока.");
        return statements;
    }

    private Expression parseExpression() {
        return parseAssignment();
    }

    // 1. Присваивание (самый низкий приоритет)
    private Expression parseAssignment() {
        Expression expr = parseLogicalOr();

        if (match(TokenType.EQ)) {
            Token equals = previous();
            Expression value = parseAssignment(); // Рекурсия для a = b = 5

            if (expr instanceof VariableExpression varExpr) {
                return new AssignExpression(value, varExpr.getName());
            }

            throw new RuntimeException("[Parser Error] Line " + equals.getLine() +
                    ": Недопустимая цель для присваивания.");
        }

        return expr;
    }

    // 2. Логическое ИЛИ (||)
    private Expression parseLogicalOr() {
        Expression expr = parseLogicalAnd();

        while (match(TokenType.OR)) {
            TokenType op = previous().getType();
            Expression right = parseLogicalAnd();
            expr = new BinaryExpression(expr, right, op);
        }

        return expr;
    }

    // 3. Логическое И (&&)
    private Expression parseLogicalAnd() {
        Expression expr = parseEquality();

        while (match(TokenType.AND)) {
            TokenType op = previous().getType();
            Expression right = parseEquality();
            expr = new BinaryExpression(expr, right, op);
        }

        return expr;
    }

    // 4. Сравнения на равенство (==, !=)
    private Expression parseEquality() {
        Expression expr = parseComparison();

        while (match(TokenType.EQEQ, TokenType.NEQ)) {
            TokenType op = previous().getType();
            Expression right = parseComparison();
            expr = new BinaryExpression(expr, right, op);
        }

        return expr;
    }

    // 5. Меньше, больше (<, >, <=, >=)
    private Expression parseComparison() {
        Expression expr = parseTerm();

        while (match(TokenType.LT, TokenType.LTEQ, TokenType.GT, TokenType.GTEQ)) {
            TokenType op = previous().getType();
            Expression right = parseTerm();
            expr = new BinaryExpression(expr, right, op);
        }

        return expr;
    }

    // 6. Сложение и вычитание (+, -)
    private Expression parseTerm() {
        Expression expr = parseFactor();

        while (match(TokenType.PLUS, TokenType.MINUS)) {
            TokenType op = previous().getType();
            Expression right = parseFactor();
            expr = new BinaryExpression(expr, right,  op);
        }

        return expr;
    }

    // 7. Умножение и деление (*, /)
    private Expression parseFactor() {
        Expression expr = parseUnary();

        while (match(TokenType.STAR, TokenType.SLASH)) {
            TokenType op = previous().getType();
            Expression right = parseUnary();
            expr = new BinaryExpression(expr, right, op);
        }

        return expr;
    }

    // 8. Унарные операции (!, -)
    private Expression parseUnary() {
        if (match(TokenType.NOT, TokenType.MINUS)) {
            TokenType op = previous().getType();
            Expression right = parseUnary();
            return new UnaryExpression(op, right);
        }

        return parsePrimary();
    }

    // 9. Примитивы (Числа, Переменные, Скобки) - наивысший приоритет
    private Expression parsePrimary() {
        if (match(TokenType.NUMBER)) {
            double value = Double.parseDouble(previous().getValue());
            return new NumberExpression(value);
        }

        if (match(TokenType.ID)) {
            return new VariableExpression(previous().getValue());
        }

        if (match(TokenType.LPAREN)) {
            Expression expr = parseExpression();
            consume(TokenType.RPAREN, "Ожидается ')' после выражения.");
            return expr;
        }

        Token token = peek();
        throw new RuntimeException("[Parser Error] Line " + token.getLine() + "pos " + token.getPos() +
                " Ожидается выражение.");
    }

    private boolean match(TokenType... types) {
        for (TokenType type : types) {
            if (check(type)) {
                advance();
                return true;
            }
        }
        return false;
    }

    private boolean check(TokenType type) {
        if (isAtEnd()) return false;
        return peek().getType() == type;
    }

    private Token advance() {
        if (!isAtEnd()) position++;
        return previous();
    }

    private boolean isAtEnd() {
        return position==tokens.size() || peek().getType() == TokenType.EOF;
    }

    private Token peek() {
        return tokens.get(position);
    }

    private Token previous() {
        return tokens.get(position - 1);
    }

    private Token consume(TokenType type, String message) {
        if (check(type)) return advance();
        Token token = peek();
        throw new RuntimeException("[Parser Error] Line " + token.getLine() + " pos " + token.getPos() +
                ": " + message);
    }
}
