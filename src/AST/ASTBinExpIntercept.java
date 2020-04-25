package AST;

public class ASTBinExpIntercept extends ASTBinaryExpression {


    public ASTBinExpIntercept(int position, ASTExpression left, ASTExpression right) {
        super(position, left, right);
    }

    @Override
    public Object Visit(Visitor visitor) {
        return visitor.Visit(this);
    }
}
