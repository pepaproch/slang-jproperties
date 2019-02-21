package com.pepaproch.jproperties.parser.slang.tree;

import org.sonarsource.slang.api.TextRange;
import org.sonarsource.slang.impl.TokenImpl;

public class ShouldSkipToken extends TokenImpl {

    public ShouldSkipToken(TextRange textRange, String text, Type type) {
        super(textRange, text, type);
    }
}
