package AST;

public class ASTStringValue extends ASTSymbol {

    private String lexeme;

    public ASTStringValue(String lexeme, int entry, int position) {
        super(entry, position);
        this.lexeme = lexeme;
    }

    public String Value() {
        return lexeme;
    }

    public Object Visit(Visitor visitor) {
        return visitor.Visit(this);
    }
}
