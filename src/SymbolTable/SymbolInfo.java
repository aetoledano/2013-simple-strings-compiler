
package SymbolTable;

import Lexer.TokenKind;
import Lexer.Types;

public class    SymbolInfo {

    public void setLexeme(String lexeme) {
        this.lexeme = lexeme;
    }

    private String lexeme;
    private TokenKind kind;
    private Types type;
    private boolean declared;
    private boolean init;
    
    private int address;

    public SymbolInfo(String lexeme, TokenKind kind) {
        this.lexeme = lexeme;
        this.kind = kind;
        this.declared = false;
        this.address = -1;
        type = (kind.toString().equals(Types.Int.toString())) ? Types.Int: Types.String;
    }

    public TokenKind getKind() {
        return kind;
    }

    
    public void setKind(TokenKind kind) {
        this.kind = kind;
    }

    public String getLexeme() {
        return lexeme;
    }

    public boolean isDeclared() {
        return declared;
    }

    public void setDeclared(boolean declared) {
        this.declared = declared;
    }

    public int getAddress() {
        return address;
    }

    public void setAddress(int address) {
        this.address = address;
    }

    public boolean equals(Object obj) {
        String objLexeme = ((SymbolInfo) obj).getLexeme();
        return objLexeme.equals(lexeme);
    }

    public int hashCode() {
        return lexeme.hashCode();
    }

    public boolean isInit() {
        return init;
    }

    public void setInit(boolean init) {
        this.init = init;
    }

    public Types getType() {
        return type;
    }

    public void setType(Types type) {
        this.type = type;
    }

}
