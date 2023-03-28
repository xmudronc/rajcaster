package com.xmudronc;

public enum Symbol {
    EMPTY("    "),
    BLOCK("████"),
    T_BLOCK("▀▀▀▀"),
    B_BLOCK("▄▄▄▄");

    public final String value;

    private Symbol(String value) {
        this.value = value;
    }
}
