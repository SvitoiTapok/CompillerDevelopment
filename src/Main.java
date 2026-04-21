 import lexer.Lexer;
import lexer.Token;
 import parcer.AstPrinter;
 import parcer.Parser;
import parcer.ast.statement.Statement;
import semantic.SemanticAnalyzer;

import java.util.List;

public class Main {
    public static void main(String[] args) throws Exception {
        String example = """
                var x = 123;
                var x = 123;
                var z = 23;
                print y * 9;
                if (x == 1) x = 2;
                while (x != 5) x=x+4;""";
        Lexer lexer = new Lexer(example, example.length(), 0, 0, 0);
        List<Token> tokens = (List<Token>) lexer.Tokenize();
        Parser parser = new Parser(tokens);

        for (Token token : tokens) {
            System.out.println(token);
        }
        List<Statement> st = parser.parse();
        AstPrinter astPrinter = new AstPrinter();
        astPrinter.print(st);
        var semanticAnalyzer = new SemanticAnalyzer();
        semanticAnalyzer.analyze(st);
        for (String e: semanticAnalyzer.getErrors())
        {
            System.out.println(e);
        }
    }
}