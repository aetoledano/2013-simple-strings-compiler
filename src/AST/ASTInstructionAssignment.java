package AST;

public class ASTInstructionAssignment extends ASTInstruction {

    private ASTIdentifierReference identifier;
    private ASTExpression expression;

    public ASTInstructionAssignment(ASTIdentifierReference identifier, ASTExpression expression, int position) {
        super(position);
        this.identifier = identifier;
        this.expression = expression;
    }

    public ASTIdentifierReference getIdentifier() {
        return identifier;
    }

    public ASTExpression getExpression() {
        return expression;
    }

    public Object Visit(Visitor visitor) {
        return visitor.Visit(this);
    }
}
