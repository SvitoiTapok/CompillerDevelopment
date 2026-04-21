package semantic;


import parcer.ast.expression.*;
import parcer.ast.statement.*;

import java.util.ArrayList;
import java.util.List;

public class SemanticAnalyzer {
    private SemanticEnvironment environment = new SemanticEnvironment();
    private final List<String> errors = new ArrayList<>();

    public void analyze(Iterable<Statement> statements) {
        for (Statement statement : statements) {
            visitStatement(statement);
        }
    }

    public void visitStatement(Statement statement) {
        if (statement instanceof VarStatement varStatement) {

            if (varStatement.getValue() != null) {
                visitExpression(varStatement.getValue());
            }

            if (!environment.defineVariable(varStatement.getName())) {
                errors.add("Variable '" + varStatement.getName() + "' is already defined.");
            }

        } else if (statement instanceof PrintStatement printStatement) {
            visitExpression(printStatement.getExpression());

        } else if (statement instanceof ExpressionStatement expressionStatement) {
            visitExpression(expressionStatement.getExpression());

        } else if (statement instanceof BlockStatement blockStatement) {

            SemanticEnvironment previousEnvironment = environment;
            environment = new SemanticEnvironment(previousEnvironment);

            for (Statement innerStatement : blockStatement.getStatements()) {
                visitStatement(innerStatement);
            }

            environment = previousEnvironment;

        } else if (statement instanceof IfStatement ifStatement) {

            visitExpression(ifStatement.getCondition());
            visitStatement(ifStatement.getThenStatement());

            if (ifStatement.getElseStatement() != null) {
                visitStatement(ifStatement.getElseStatement());
            }

        } else if (statement instanceof WhileStatement whileStatement) {

            visitExpression(whileStatement.getCondition());
            visitStatement(whileStatement.getBody());

        } else {
            errors.add("Unsupported statement type: " + statement.getClass().getSimpleName());
        }
    }

    public void visitExpression(Expression expression) {
        if (expression instanceof NumberExpression || expression instanceof StringExpression) {

        } else if (expression instanceof VariableExpression v) {

            if (!environment.isVariableDefined(v.getName())) {
                errors.add("Variable '" + v.getName() + "' is not defined.");
            }

        } else if (expression instanceof AssignExpression a) {

            visitExpression(a.getLeft());

            if (!environment.isVariableDefined(a.getName())) {
                errors.add("Variable '" + a.getName() + "' is not defined.");
            }

        } else if (expression instanceof BinaryExpression b) {

            visitExpression(b.getLeft());
            visitExpression(b.getRight());

        } else if (expression instanceof UnaryExpression u) {

            visitExpression(u.getExpression());

        } else {
            errors.add("Unsupported expression type: " + expression.getClass().getSimpleName());
        }
    }

    public Iterable<String> getErrors() {
        return errors;
    }
}
