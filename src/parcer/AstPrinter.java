package parcer;

import parcer.ast.expression.AssignExpression;
import parcer.ast.expression.BinaryExpression;
import parcer.ast.expression.UnaryExpression;
import parcer.ast.expression.VariableExpression;
import parcer.ast.statement.*;

import java.util.List;

public class AstPrinter {

    // Главный метод для вывода всей программы
    public void print(List<Statement> statements) {
        System.out.println("Root (Program)");
        for (int i = 0; i < statements.size(); i++) {
            printNode(statements.get(i), "", i == statements.size() - 1);
        }
    }

    // Рекурсивный метод отрисовки
    private void printNode(Object node, String indent, boolean isLast) {
        if (node == null) return;

        // Рисуем веточку
        String marker = isLast ? "└── " : "├── ";
        System.out.print(indent + marker);

        // Подготавливаем отступ для дочерних элементов
        String childIndent = indent + (isLast ? "    " : "│   ");

        if (node instanceof VarStatement v) {
            System.out.println("VarStatement: " + v.getName());

            if (v.getValue() != null) {
                printNode(v.getValue(), childIndent, true);
            }

        } else if (node instanceof PrintStatement p) {
            System.out.println("PrintStatement");
            printNode(p.getExpression(), childIndent, true);

        } else if (node instanceof IfStatement i) {
            System.out.println("IfStatement");
            printNode(i.getCondition(), childIndent, false);
            printNode(i.getThenStatement(), childIndent, i.getElseStatement() == null);

            if (i.getElseStatement() != null) {
                printNode(i.getElseStatement(), childIndent, true);
            }

        } else if (node instanceof WhileStatement w) {
            System.out.println("WhileStatement");
            printNode(w.getCondition(), childIndent, false);
            printNode(w.getBody(), childIndent, true);

        } else if (node instanceof BlockStatement b) {
            System.out.println("BlockStatement");
            List<Statement> statements = b.getStatements();
            for (int j = 0; j < statements.size(); j++) {
                printNode(statements.get(j), childIndent, j == statements.size() - 1);
            }

        } else if (node instanceof ExpressionStatement e) {
            System.out.println("ExpressionStatement");
            printNode(e.getExpression(), childIndent, true);

        } else if (node instanceof BinaryExpression bin) {
            System.out.println("BinaryExpression: " + bin.getOperator());
            printNode(bin.getLeft(), childIndent, false);
            printNode(bin.getRight(), childIndent, true);

        } else if (node instanceof UnaryExpression un) {
            System.out.println("UnaryExpression: " + un.getToken());
            printNode(un.getExpression(), childIndent, true);

        } else if (node instanceof AssignExpression assign) {
            System.out.println("AssignExpression: " + assign.getName() + " =");
            printNode(assign.getLeft(), childIndent, true);

        } else if (node instanceof NumberExpression num) {
            System.out.println("Number: " + num.getValue());

        } else if (node instanceof VariableExpression varExpr) {
            System.out.println("Variable: " + varExpr.getName());

        } else {
            System.out.println("Unknown Node: " + node.getClass().getSimpleName());
        }
    }
}