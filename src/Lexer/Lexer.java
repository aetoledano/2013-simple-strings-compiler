/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Lexer;

import Errors.*;
import Stream.SourceStream;
import SymbolTable.*;

import java.io.IOException;
import java.util.Hashtable;

public class Lexer {

    private final char EOF = '\0'; // character end of file
    char c; //currentChar   
    SourceStream input;
    SymbolTable symbolsTable;
    Hashtable<String, TokenKind> keywordsTable;
    Errors.ErrorReporter errorReporter;

    public Lexer(SourceStream input, SymbolTable symbolsTable, ErrorReporter errorReporter) throws IOException {
        this.input = input;
        this.symbolsTable = symbolsTable;
        this.errorReporter = errorReporter;
        c = input.Read();
        keywordsTable = new Hashtable<String, TokenKind>();
        FillKeywordtable();
    }

    public SymbolTable getSymbolsTable() {
        return symbolsTable;
    }

    private void FillKeywordtable() {
        TokenKind.GetReservedWords().forEach(tk -> {
            keywordsTable.put(tk.toString().toLowerCase(), tk);
        });
    }

    boolean isLetter() {
        return Character.isLetter(c) || c == '_';
    }

    boolean isDigit() {
        return Character.isDigit(c);
    }

    void consume() throws IOException {
        c = input.Read();
    }

    void skip() throws IOException {
        while (c == ' ' || c == '\t' || c == '\n' || c == '\r') {
            consume();
        }
    }

    Token ID() throws IOException {
        StringBuilder buf = new StringBuilder();
        do {
            buf.append(c);
            consume();
        } while (isLetter() || isDigit());
        String lexeme = buf.toString();
        TokenKind key = keywordsTable.get(lexeme);
        if (key != null) {
            return new Token(key, lexeme, input.getCurrentLine());
        }
        TokenKind tk = TokenKind.Id;
        return new Token(tk, lexeme, input.getCurrentLine(), symbolsTable.add(lexeme, tk));
    }

    Token StringLiteral() throws IOException {
        consume();//consume begin quotation
        StringBuilder buf = new StringBuilder();
        while (c != '"' && c != EOF) {
            buf.append(c);
            consume();
        }
        if (c != '"') {
            errorReporter.add(new LexicalError(
                    input.getCurrentLine(), "Lexical Error: Unexpected Char \"" + c + "\""));
            return new Token(TokenKind.Error, String.valueOf(c), input.getCurrentLine());
        }
        consume();//consume end quotation
        TokenKind tk = TokenKind.StringLiteral;
        String lexeme = buf.toString();
        return new Token(tk, lexeme, input.getCurrentLine(), symbolsTable.add(lexeme, tk));
    }

    Token IntLiteral() throws IOException {
        StringBuilder buf = new StringBuilder();
        do {
            buf.append(c);
            consume();
        } while (isDigit());
        TokenKind tk = TokenKind.IntLiteral;
        String lexeme = buf.toString();
        return new Token(tk, lexeme, input.getCurrentLine(), symbolsTable.add(lexeme, tk));
    }

    public Token nextToken() throws IOException {
        while (c != EOF) {
            if (c == ' ' || c == '\t' || c == '\n' || c == '\r') {
                skip(); // skip
            }
            switch (c) { // which token approaches?
                case '(':
                    consume();
                    return new Token(TokenKind.LeftParen, "(", input.getCurrentLine());
                case ')':
                    consume();
                    return new Token(TokenKind.RigthParen, ")", input.getCurrentLine());
                case '{':
                    consume();
                    return new Token(TokenKind.LeftKey, "{", input.getCurrentLine());
                case '}':
                    consume();
                    return new Token(TokenKind.RigthKey, "}", input.getCurrentLine());
                case ';':
                    consume();
                    return new Token(TokenKind.SemiColon, ";", input.getCurrentLine());
                case ',':
                    consume();
                    return new Token(TokenKind.Comma, ",", input.getCurrentLine());
                case '=':
                    consume();
                    return new Token(TokenKind.Assignment, "=", input.getCurrentLine());
                case '"':
                    return StringLiteral(); // match string literal
                case EOF:
                    break;
                default: {
                    if (isLetter()) {
                        return ID(); // match variable identifier
                    } else {
                        if (isDigit()) {
                            return IntLiteral(); //match IntLiteral
                        } else {
                            errorReporter.add(new LexicalError(
                                    input.getCurrentLine(), "Lexical Error: Unexpected Char \"" + c + "\""));
                            Token tk_error = new Token(TokenKind.Error, String.valueOf(c), input.getCurrentLine());
                            consume();
                            return tk_error;
                        }
                    }
                }
            }
        }
        return new Token(TokenKind.EOF, String.valueOf(EOF), input.getCurrentLine());
    }

    public ErrorReporter getErrorList() {
        return errorReporter;
    }
}
