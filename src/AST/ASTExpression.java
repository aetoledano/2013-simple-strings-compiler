package AST;

import Lexer.Types;

public abstract class ASTExpression extends AST {

    private Types type;

    public ASTExpression(int position) {
        super(position);
    }

    public Types getType() {
        return type;
    }

    public void setType(Types type) {
        this.type = type;
    }

}
