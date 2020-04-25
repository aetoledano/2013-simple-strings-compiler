package AST;

public class ASTBinExpDifference extends ASTBinaryExpression {


    public ASTBinExpDifference(int position, ASTExpression left, ASTExpression right) {
        super(position, left, right);
    }

    @Override
    public Object Visit(Visitor visitor) {
        return visitor.Visit(this);
    }
}
