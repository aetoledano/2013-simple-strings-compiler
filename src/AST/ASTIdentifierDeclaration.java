package AST;

public class ASTIdentifierDeclaration extends ASTIdentifier {

    public ASTIdentifierDeclaration(String lexeme, int entry, int position) {
        super(lexeme, entry, position);
    }

    public Object Visit(Visitor visitor) {
        return visitor.Visit(this);
    }

}
