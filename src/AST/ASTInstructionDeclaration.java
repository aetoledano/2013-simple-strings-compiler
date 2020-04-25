package AST;

import java.util.LinkedList;
import Lexer.Types;

public class ASTInstructionDeclaration extends ASTInstruction {

    private LinkedList<ASTIdentifierDeclaration> identifiers;
    private Types type;

    public ASTInstructionDeclaration(LinkedList<ASTIdentifierDeclaration> identifiers, Types type, int position) {
        super(position);
        this.identifiers = identifiers;
        this.type = type;
    }

    public LinkedList<ASTIdentifierDeclaration> getIdentifiers() {
        return identifiers;
    }

    public Types getType() {
        return type;
    }

    public Object Visit(Visitor visitor) {
        return visitor.Visit(this);
    }
}
