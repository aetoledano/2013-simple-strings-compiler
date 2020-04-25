package AST;

public interface Visitor {

    //AST
    public Object Visit(AST a);

    public Object Visit(ASTProgram a);

    //Instructions
    public Object Visit(ASTInstruction a);

    public Object Visit(ASTInstructionAssignment a);

    public Object Visit(ASTInstructionDeclaration a);

    public Object Visit(ASTInstructionPrint a);

    //Expressions
    public Object Visit(ASTExpression a);

    public Object Visit(ASTIdentifier a);

    public Object Visit(ASTIdentifierDeclaration a);

    public Object Visit(ASTIdentifierReference a);

    public Object Visit(ASTIdentifierValue a);

    public Object Visit(ASTBinaryExpression a);

    public Object Visit(ASTBinExpConcat a);

    public Object Visit(ASTBinExpDifference a);

    public Object Visit(ASTBinExpIntercept a);

    public Object Visit(ASTBinExpSubstring a);

    //Values
    public Object Visit(ASTSymbol a);

    public Object Visit(ASTIntValue a);

    public Object Visit(ASTStringValue a);

}
