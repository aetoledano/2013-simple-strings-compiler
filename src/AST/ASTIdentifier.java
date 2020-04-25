package AST;

public abstract class ASTIdentifier extends ASTSymbol {

    private String lexeme;

    public String Lexeme() {
        return lexeme;
    }

    public ASTIdentifier(String lexeme, int entry, int position) {
        super(entry, position);
        this.lexeme = lexeme;
    }
}
