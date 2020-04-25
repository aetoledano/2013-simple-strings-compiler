package Lexer;

import jdk.nashorn.internal.ir.Assignment;
import run.Main;

import java.util.ArrayList;
import java.util.List;

public enum TokenKind {
    Id,
    IntLiteral,
    StringLiteral,
    //reserved words
    Main,
    Int,
    String,
    Intercept,
    Substring,
    Concat,
    Difference,
    Print,
    //signs
    LeftParen,
    RigthParen,
    LeftKey,
    RigthKey,
    SemiColon,
    Comma,
    Assignment,
    Error,
    EOF,
    //input parsing incorrect tokens
    Undefined, Unknown;

    //return all of the keywords
    public static List<TokenKind> GetReservedWords() {
        List<TokenKind> list = new ArrayList<TokenKind>();
        list.add(Concat);
        list.add(Difference);
        list.add(Int);
        list.add(Intercept);
        list.add(Main);
        list.add(Print);
        list.add(String);
        list.add(Substring);
        return list;
    }
}
