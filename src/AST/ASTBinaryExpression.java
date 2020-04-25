package AST;

public abstract class ASTBinaryExpression extends ASTExpression {

    private ASTExpression left, right;

    public ASTBinaryExpression(int position, ASTExpression left, ASTExpression right) {
        super(position);
        this.left = left;
        this.right = right;
    }

    public ASTExpression getLeft() {
        return left;
    }

    public ASTExpression getRight() {
        return right;
    }
    
}
