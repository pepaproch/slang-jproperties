package com.pepaproch.jproperties.parser.slang.tree;

import org.sonarsource.slang.api.Comment;
import org.sonarsource.slang.api.TextRange;
import org.sonarsource.slang.impl.TextRangeImpl;


public class CommentImpl implements Comment {

    private final String text;
    private final TextRange range;
    private final TextRange rangeText;

    public CommentImpl(String text, TextRange range) {
        this.text = text;
        this.range = range;
        rangeText = new TextRangeImpl(range.start().line(), range.start().lineOffset(),
                range.end().line(), range.end().lineOffset());
    }

    @Override
    public String contentText() {
        return text.substring(1);
    }

    @Override
    public String text() {
        return text;
    }

    @Override
    public TextRange contentRange() {
        return range;
    }

    @Override
    public TextRange textRange() {
        return rangeText;
    }
}
