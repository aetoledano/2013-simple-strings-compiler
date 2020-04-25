package AST;

public class ASTBinExpSubstring extends ASTBinaryExpression {


    public ASTBinExpSubstring(int position, ASTExpression left, ASTExpression right) {
        super(position, left, right);
    }

    @Override
    public Object Visit(Visitor visitor) {
        return visitor.Visit(this);
    }
}
