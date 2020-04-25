package Coder;

import Runtime.*;
import AST.*;
import Lexer.Types;

import java.util.LinkedList;
import java.util.List;

import SymbolTable.SymbolInfo;
import SymbolTable.SymbolTable;

public class Encoder implements Visitor {

    private List<RuntimeEntity> code;
    private SymbolTable st;
    private int currentMemDir;

    public List<RuntimeEntity> getCode() {
        return code;
    }

    public Encoder(SymbolTable st) {
        code = new LinkedList<RuntimeEntity>();
        this.st = st;
        currentMemDir = 0;
    }

    public Object Visit(ASTProgram a) {
        for (ASTInstruction i : a.getInstructions()) {
            i.Visit(this);
        }
        code.add(new RuntimeHalt());
        return null;
    }

    public Object Visit(ASTIdentifierDeclaration a) {
        SymbolInfo info = st.entry(a.getEntry());
        if (info.getType() == Types.Int) {
            code.add(new RuntimeNewInt());
            info.setAddress(currentMemDir++);
        } else if (info.getType() == Types.String) {
            code.add(new RuntimeNewString());
            info.setAddress(currentMemDir++);
        }
        return null;
    }

    public Object Visit(ASTIdentifierReference a) {
        SymbolInfo info = st.entry(a.getEntry());
        IntValue address = new IntValue(info.getAddress());
        code.add(new RuntimeCargaDir());
        code.add(address);
        return null;
    }

    public Object Visit(ASTIdentifierValue a) {
        SymbolInfo info = st.entry(a.getEntry());
        IntValue address = new IntValue(info.getAddress());
        code.add(new RuntimeCarga());
        code.add(address);
        return null;
    }

    public Object Visit(ASTIntValue a) {
        SymbolInfo info = st.entry(a.getEntry());
        if (info.getAddress() == -1) {
            code.add(new RuntimeNewInt());
            info.setAddress(currentMemDir);
            code.add(new RuntimeCargaDir());
            IntValue address = new IntValue(currentMemDir);
            code.add(address);
            IntValue value = new IntValue(Integer.parseInt(info.getLexeme()));
            code.add(new RuntimePush());
            code.add(value);
            code.add(new RuntimeStore());
            currentMemDir++;
        }
        IntValue address = new IntValue(info.getAddress());
        code.add(new RuntimeCarga());
        code.add(address);
        return null;
    }

    @Override
    public Object Visit(ASTStringValue a) {
        SymbolInfo info = st.entry(a.getEntry());
        if (info.getAddress() == -1) {
            code.add(new RuntimeNewString());
            info.setAddress(currentMemDir);
            code.add(new RuntimeCargaDir());
            IntValue address = new IntValue(currentMemDir);
            code.add(address);
            StringValue value = new StringValue(info.getLexeme());
            code.add(new RuntimePush());
            code.add(value);
            code.add(new RuntimeStore());
            currentMemDir++;
        }
        IntValue address = new IntValue(info.getAddress());
        code.add(new RuntimeCarga());
        code.add(address);
        return null;
    }

    public Object Visit(ASTInstructionAssignment a) {
        a.getIdentifier().Visit(this);
        a.getExpression().Visit(this);
        code.add(new RuntimeStore());
        return null;
    }

    public Object Visit(ASTInstructionDeclaration a) {
        List<ASTIdentifierDeclaration> ids = a.getIdentifiers();
        for (ASTIdentifierDeclaration id : ids) {
            id.Visit(this);
        }
        return null;
    }

    @Override
    public Object Visit(ASTInstructionPrint a) {
        a.getExpToPrint().Visit(this);
        code.add(new RuntimePrint());
        return null;
    }

    @Override
    public Object Visit(ASTBinExpConcat a) {
        a.getLeft().Visit(this);
        a.getRight().Visit(this);
        code.add(new RuntimeConcat());
        return null;
    }

    @Override
    public Object Visit(ASTBinExpIntercept a) {
        a.getLeft().Visit(this);
        a.getRight().Visit(this);
        code.add(new RuntimeIntercept());
        return null;
    }

    @Override
    public Object Visit(ASTBinExpDifference a) {
        a.getLeft().Visit(this);
        a.getRight().Visit(this);
        code.add(new RuntimeDifference());
        return null;
    }

    @Override
    public Object Visit(ASTBinExpSubstring a) {
        a.getLeft().Visit(this);
        a.getRight().Visit(this);
        code.add(new RuntimeSubstring());
        return null;
    }

    //nothing to do here
    @Override
    public Object Visit(ASTBinaryExpression a) {
        return null;
    }

    public Object Visit(AST a) {
        return null;
    }

    public Object Visit(ASTInstruction a) {
        return null;
    }

    public Object Visit(ASTSymbol a) {
        return null;
    }

    public Object Visit(ASTExpression a) {
        return null;
    }

    public Object Visit(ASTIdentifier a) {
        return null;
    }

}
