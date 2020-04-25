package Parser;

import AST.*;
import Errors.ErrorReporter;
import Errors.SyntacticError;
import Lexer.Lexer;
import Lexer.Token;
import Lexer.TokenKind;

import java.beans.Expression;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import Lexer.Types;

/**
 * Descendant Predictive Parser
 */
public class Parser {

    Lexer input; //from where do we get the tokens
    Token lookahead; //current token
    ErrorReporter errorReporter;

    public Parser(Lexer input, ErrorReporter errorReporter) throws IOException {
        this.input = input;
        this.errorReporter = errorReporter;
        lookahead = input.nextToken();
    }

    private void consume() throws IOException {
        lookahead = input.nextToken();
    }

    public void match(TokenKind tk_expected) throws IOException {
        if (tk_expected == lookahead.getKind()) {
            consume();
        } else {
            errorReporter.add(new SyntacticError(lookahead.getLine(), "Syntactic Error: Expected \"" + toHuman(tk_expected) + "\" found \"" + toHuman(lookahead.getKind()) + "\""));
        }
    }

    public ASTProgram Parse() throws IOException {
        return new ASTProgram(Program(), lookahead.getLine());
    }

    private List<ASTInstruction> Program() throws IOException {
        match(TokenKind.Main);
        match(TokenKind.LeftParen);
        match(TokenKind.RigthParen);
        match(TokenKind.LeftKey);
        List<ASTInstruction> insts = instList();
        match(TokenKind.RigthKey);
        return insts;
    }

    private List<ASTInstruction> instList() throws IOException {
        LinkedList<ASTInstruction> instructions = new LinkedList<>();
        ASTInstruction i;
        while (lookahead.getKind() == TokenKind.Int
                || lookahead.getKind() == TokenKind.String
                || lookahead.getKind() == TokenKind.Id
                || lookahead.getKind() == TokenKind.Print) {
            i = Instruction();
            match(TokenKind.SemiColon);
            instructions.add(i);
        }
        return instructions;
    }

    private ASTInstruction Instruction() throws IOException {
        if (lookahead.getKind() == TokenKind.String || lookahead.getKind() == TokenKind.Int) {
            return Declaration();
        } else if (lookahead.getKind() == TokenKind.Id) {
            return Assignment();
        } else if (lookahead.getKind() == TokenKind.Print) {
            return Print();
        }
        return null;
    }

    ///////
    //begin declaration
    ///////
    public ASTInstructionDeclaration Declaration() throws IOException {
        Types type;
        if (lookahead.getKind() == TokenKind.Int) {
            type = Types.Int;
            match(TokenKind.Int);
        } else {
            type = Types.String;
            match(TokenKind.String);
        }
        if (lookahead.getKind() == TokenKind.Id) {
            LinkedList<ASTIdentifierDeclaration> identifiers = IdsLst();
            if (identifiers != null) {
                return new ASTInstructionDeclaration(identifiers, type, lookahead.getLine());
            }
        } else {
            match(TokenKind.Id);
        }
        return null;

    }

    private LinkedList<ASTIdentifierDeclaration> IdsLst() throws IOException {
        LinkedList<ASTIdentifierDeclaration> identifiers = new LinkedList<ASTIdentifierDeclaration>();
        ASTIdentifierDeclaration id = new ASTIdentifierDeclaration(lookahead.getLexeme(), lookahead.getEntry(), lookahead.getLine());
        match(TokenKind.Id);
        identifiers.add(id);
        LinkedList<ASTIdentifierDeclaration> rest = MoreIds();
        if (rest != null) {
            identifiers.addAll(rest);
            return identifiers;
        } else {
            return null;
        }
    }

    private LinkedList<ASTIdentifierDeclaration> MoreIds() throws IOException {
        if (lookahead.getKind() == TokenKind.Comma) {
            match(TokenKind.Comma);
            if (lookahead.getKind() == TokenKind.Id) {
                return IdsLst();
            } else {
                match(TokenKind.Id);
            }
            return null;
        } else {
            return new LinkedList<ASTIdentifierDeclaration>();
        }
    }
    ///////
    //end declaration
    ///////

    ///////
    //begin assignment
    ///////
    private ASTInstructionAssignment Assignment() throws IOException {
        if (lookahead.getKind() == TokenKind.Id) {
            ASTIdentifierReference id = new ASTIdentifierReference(lookahead.getLexeme(), lookahead.getEntry(), lookahead.getLine());
            match(TokenKind.Id);
            match(TokenKind.Assignment);
            ASTExpression value = Expression();
            if (value != null) {
                return new ASTInstructionAssignment(id, value, id.Position());
            }
            return null;
        }
        return null;
    }
    //end assignment
    ///////

    ///////
    //begin print
    public ASTInstructionPrint Print() throws IOException {
        if (lookahead.getKind() == TokenKind.Print) {
            match(TokenKind.Print);
            ASTExpression expToPrint = Expression();
            if (expToPrint != null)
                return new ASTInstructionPrint(expToPrint.Position(), expToPrint);
        }
        return null;
    }
    //end print
    ///////

    ///////
    //begin expression
    ///////
    private ASTExpression Expression() throws IOException {
        ASTExpression t = Terminal();
        ASTExpression MoreExp = MoreExp(t);
        if (MoreExp != null)
            return MoreExp;
        return t;
    }

    private ASTExpression MoreExp(ASTExpression left) throws IOException {
        ASTExpression right;
        if (lookahead.getKind() == TokenKind.Concat) {
            match(TokenKind.Concat);
            right = Terminal();
            if (right != null)
                return new ASTBinExpConcat(right.Position(), left, right);
        } else if (lookahead.getKind() == TokenKind.Intercept) {
            match(TokenKind.Intercept);
            right = Terminal();
            if (right != null)
                return new ASTBinExpIntercept(right.Position(), left, right);
        } else if (lookahead.getKind() == TokenKind.Difference) {
            match(TokenKind.Difference);
            right = Terminal();
            if (right != null)
                return new ASTBinExpDifference(right.Position(), left, right);
        } else if (lookahead.getKind() == TokenKind.Substring) {
            match(TokenKind.Substring);
            right = Terminal();
            if (right != null)
                return new ASTBinExpSubstring(right.Position(), left, right);
        }
        return null;
    }
    ///////
    //end expression
    ///////

    ///////
    //begin terminal
    ///////
    private ASTExpression Terminal() throws IOException {
        if (lookahead.getKind() == TokenKind.LeftParen) {
            match(TokenKind.LeftParen);
            ASTExpression nestedExp = Expression();
            match(TokenKind.RigthParen);
            return nestedExp;
        } else if (lookahead.getKind() == TokenKind.Id) {
            ASTIdentifierValue idValue = new ASTIdentifierValue(lookahead.getLexeme(), lookahead.getEntry(), lookahead.getLine());
            match(TokenKind.Id);
            return idValue;
        } else if (lookahead.getKind() == TokenKind.IntLiteral) {
            ASTIntValue i = new ASTIntValue(lookahead.getLexeme(), lookahead.getEntry(), lookahead.getLine());
            match(TokenKind.IntLiteral);
            return i;
        } else if (lookahead.getKind() == TokenKind.StringLiteral) {
            ASTStringValue i = new ASTStringValue(lookahead.getLexeme(), lookahead.getEntry(), lookahead.getLine());
            match(TokenKind.StringLiteral);
            return i;
        }
        return null;
    }
    ///////
    //end terminal
    ///////

    public Lexer getInput() {
        return input;
    }

    private String toHuman(TokenKind token) {
        if (token == TokenKind.Main) {
            return "main";
        }
        if (token == TokenKind.Substring) {
            return "substring";
        }
        if (token == TokenKind.Difference) {
            return "difference";
        }
        if (token == TokenKind.Intercept) {
            return "intercept";
        }
        if (token == TokenKind.Concat) {
            return "concat";
        }
        if (token == TokenKind.StringLiteral) {
            return "string value";
        }
        if (token == TokenKind.String) {
            return "string";
        }
        if (token == TokenKind.Print) {
            return "print";
        }
        if (token == TokenKind.Assignment) {
            return "assignment";
        }
        if (token == TokenKind.Comma) {
            return "comma";
        }
        if (token == TokenKind.EOF) {
            return "EOF";
        }
        if (token == TokenKind.Id) {
            return "identifier";
        }
        if (token == TokenKind.Int) {
            return "integer";
        }
        if (token == TokenKind.IntLiteral) {
            return "integer value";
        }
        if (token == TokenKind.LeftKey) {
            return "{";
        }
        if (token == TokenKind.LeftParen) {
            return "(";
        }
        if (token == TokenKind.RigthKey) {
            return "}";
        }
        if (token == TokenKind.RigthParen) {
            return ")";
        }
        if (token == TokenKind.SemiColon) {
            return ";";
        }
        return TokenKind.Error.toString();
    }
}
