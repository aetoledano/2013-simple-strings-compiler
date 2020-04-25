package AST;

public class ASTBinExpConcat extends ASTBinaryExpression {


    public ASTBinExpConcat(int position, ASTExpression left, ASTExpression right) {
        super(position, left, right);
    }

    @Override
    public Object Visit(Visitor visitor) {
        return visitor.Visit(this);
    }
}
