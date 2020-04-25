package AST;

public class ASTIntValue extends ASTSymbol {

    private String lexeme;

    public ASTIntValue(String lexeme, int entry, int position) {
        super(entry, position);
        this.lexeme = lexeme;
    }

    public int Value() {
        return Integer.parseInt(lexeme);
    }

    public Object Visit(Visitor visitor) {
        return visitor.Visit(this);
    }
}
