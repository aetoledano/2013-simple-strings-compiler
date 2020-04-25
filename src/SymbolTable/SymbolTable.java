package SymbolTable;

import java.util.*;
import Lexer.TokenKind;

public class SymbolTable {

    private LinkedList<SymbolInfo> items;

    public SymbolTable() {
        items = new LinkedList<SymbolInfo>();
    }

    public int add(String lexeme, TokenKind kind) {
        SymbolInfo item = new SymbolInfo(lexeme, kind);

        int index = items.indexOf(item);
        if (index == -1) {
            items.add(item);
            index = items.size() - 1;
        }
        return index;
    }

    public SymbolInfo entry(int index) {
        return items.get(index);
    }

    public int Count() {
        return items.size();
    }
}
