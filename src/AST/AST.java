package AST;

public abstract class AST {

    private int position;

    public int Position() {
        return position;
    }

    public AST(int position) {
        this.position = position;
    }

    public int getPosition() {
        return position;
    }

    public abstract Object Visit(Visitor visitor);
}
