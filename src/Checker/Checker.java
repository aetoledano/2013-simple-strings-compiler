package Checker;

import AST.*;
import Errors.*;
import Lexer.Lexer;
import SymbolTable.SymbolInfo;
import SymbolTable.SymbolTable;

import java.util.ListIterator;

import Lexer.Types;

import java.util.Iterator;

public class Checker implements Visitor {

    private SymbolTable symbolsTable;
    private ErrorReporter errorReporter;

    public Checker(SymbolTable symbolsTable, ErrorReporter errorReporter) {
        this.symbolsTable = symbolsTable;
        this.errorReporter = errorReporter;
    }

    public Object Visit(ASTProgram a) {
        ListIterator<ASTInstruction> it = a.getInstructions().listIterator();
        ASTInstruction currentInst;
        while (it.hasNext()) {
            currentInst = it.next();
            if (currentInst == null && it.hasPrevious()) {
                errorReporter.add(new CompilerError(it.previous().getPosition(), "Semantic Error: An null AST node has been found!"));
                it.next();
            } else {
                currentInst.Visit(this);
            }
        }
        return 0;
    }

    public Object Visit(ASTIntValue a) {
        a.setType(Types.Int);
        return a.getType();
    }

    @Override
    public Object Visit(ASTStringValue a) {
        a.setType(Types.String);
        return a.getType();
    }

    public Object Visit(ASTIdentifierDeclaration a) {
        SymbolInfo sInfo = symbolsTable.entry(a.getEntry());
        if (sInfo.isDeclared()) {
            errorReporter.add(new SemanticError(a.getPosition(), "Semantic Error: variable \"" + a.Lexeme() + "\" already declared"));
        } else {
            sInfo.setDeclared(true);
        }
        return null;
    }

    public Object Visit(ASTIdentifierReference a) {
        SymbolInfo sInfo = symbolsTable.entry(a.getEntry());
        if (sInfo.isDeclared()) {
            sInfo.setInit(true);
            a.setType(sInfo.getType());
        } else {
            errorReporter.add(new SemanticError(a.getPosition(), "Semantic Error: Variable \"" + a.Lexeme() + "\" undeclared"));
            a.setType(Types.Undefined);
        }
        return a.getType();
    }

    public Object Visit(ASTIdentifierValue a) {
        SymbolInfo sInfo = symbolsTable.entry(a.getEntry());
        if (sInfo.isDeclared() && sInfo.isInit()) {
            a.setType(sInfo.getType());
        } else {
            if (!sInfo.isDeclared()) {
                errorReporter.add(new SemanticError(a.getPosition(), "Semantic Error: Variable \"" + a.Lexeme() + "\" undeclared."));
                a.setType(Types.Undefined);
            }
            if (!sInfo.isInit()) {
                errorReporter.add(new SemanticError(a.getPosition(), "Semantic Error: Variable \"" + a.Lexeme() + "\" has not been initialized."));
            }
        }
        return a.getType();
    }

    @Override
    public Object Visit(ASTBinExpConcat exp) {
        return checkBothSidesAreString(exp, "concat");
    }

    @Override
    public Object Visit(ASTBinExpIntercept exp) {
        return checkBothSidesAreString(exp, "intercept");
    }

    @Override
    public Object Visit(ASTBinExpDifference exp) {
        return checkBothSidesAreString(exp, "difference");
    }

    private Object checkBothSidesAreString(ASTBinaryExpression exp, String operation) {
        Types left = (Types) exp.getLeft().Visit(this);
        Types right = (Types) exp.getRight().Visit(this);
        if (left != Types.String || right != Types.String) {
            errorReporter.add(new SemanticError(exp.getPosition(),
                    "Semantic Error: Operation " + operation + " require both sides to be string."));
            exp.setType(Types.Undefined);
        } else
            exp.setType(exp.getLeft().getType());
        return exp.getType();
    }

    @Override
    public Object Visit(ASTBinExpSubstring exp) {
        Types left = (Types) exp.getLeft().Visit(this);
        Types right = (Types) exp.getRight().Visit(this);
        if (!(left == Types.String && right == Types.Int)) {
            errorReporter.add(new SemanticError(exp.getPosition(),
                    "Semantic Error: Operation substring require string,int types."));
            exp.setType(Types.Undefined);
        } else
            exp.setType(exp.getLeft().getType());
        return exp.getType();
    }

    public Object Visit(ASTInstructionDeclaration a) {
        Iterator<ASTIdentifierDeclaration> it = a.getIdentifiers().iterator();
        ASTIdentifierDeclaration current;
        SymbolInfo sInfo;
        while (it.hasNext()) {
            current = it.next();
            current.Visit(this);
            sInfo = symbolsTable.entry(current.getEntry());
            sInfo.setType(a.getType());
        }
        return null;
    }

    public Object Visit(ASTInstructionAssignment a) {
        Types t1 = (Types) a.getExpression().Visit(this);
        Types t2 = (Types) a.getIdentifier().Visit(this);
        if (t1 != t2) {
            errorReporter.add(new SemanticError(a.getIdentifier().getPosition(), "Semantic Error: Incompatible types assignment."));
        }
        return null;
    }

    @Override
    public Object Visit(ASTInstructionPrint a) {
        a.getExpToPrint().Visit(this);
        return null;
    }

    //this methods should not be called because this AST nodes are not leafs
    //and does not perform a concrete action
    @Override
    public Object Visit(ASTIdentifier a) {
        return null;
    }

    @Override
    public Object Visit(ASTBinaryExpression a) {
        return null;
    }

    @Override
    public Object Visit(AST a) {
        return null;
    }

    @Override
    public Object Visit(ASTExpression a) {
        return null;
    }

    @Override
    public Object Visit(ASTSymbol a) {
        return null;
    }

    @Override
    public Object Visit(ASTInstruction a) {
        return null;
    }
}
