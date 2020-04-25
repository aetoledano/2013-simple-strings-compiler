package AST;

public class ASTInstructionPrint extends ASTInstruction {

    ASTExpression expToPrint;

    public ASTInstructionPrint(int position, ASTExpression expToPrint) {
        super(position);
        this.expToPrint = expToPrint;
    }


    public ASTExpression getExpToPrint() {
        return expToPrint;
    }

    public Object Visit(Visitor visitor) {
        return visitor.Visit(this);
    }
}
