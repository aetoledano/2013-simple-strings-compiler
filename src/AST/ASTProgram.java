package AST;

import java.util.List;

public class ASTProgram extends AST {

    private List<ASTInstruction> instructions;

    public ASTProgram(List<ASTInstruction> instructions, int position) {
        super(position);
        this.instructions = instructions;
    }

    public List<ASTInstruction> getInstructions() {
        return instructions;
    }

    @Override
    public Object Visit(Visitor visitor) {
        return visitor.Visit(this);
    }

}
