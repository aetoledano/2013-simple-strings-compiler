package AST;

public class ASTIdentifierValue extends ASTIdentifier {

    public ASTIdentifierValue(String lexeme, int entry, int position) {
        super(lexeme, entry, position);
    }

    public Object Visit(Visitor visitor) {
        return visitor.Visit(this);
    }

}
