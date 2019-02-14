package com.pepaproch.jproperties.parser;


import org.sonarsource.slang.api.TextRange;
import org.sonarsource.slang.impl.TokenImpl;

public class SyntaxToken extends TokenImpl {
    public SyntaxToken(TextRange textRange, String text, Type type) {
        super(textRange, text, type);
    }
}
