package com.pepaproch.jproperties.parser;

import org.sonarsource.slang.api.Comment;
import org.sonarsource.slang.api.TextRange;


public class SyntaxTrivia implements Comment {

    public SyntaxTrivia(String text, TextRange range) {

    }

    @Override
    public String contentText() {
        return null;
    }

    @Override
    public String text() {
        return null;
    }

    @Override
    public TextRange contentRange() {
        return null;
    }

    @Override
    public TextRange textRange() {
        return null;
    }
}
