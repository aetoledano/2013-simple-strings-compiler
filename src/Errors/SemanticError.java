package Errors;


public class SemanticError extends CompilerError{

    public SemanticError(int line, String text)
    {
        super(line, text);
    }
}
