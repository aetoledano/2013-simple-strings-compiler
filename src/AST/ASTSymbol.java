package AST;

import Lexer.Types;

public abstract class ASTSymbol extends ASTExpression {

    private int entry;

    public ASTSymbol(int entry, int position) {
        super(position);
        this.entry = entry;
    }

    public int getEntry() {
        return entry;
    }

}
