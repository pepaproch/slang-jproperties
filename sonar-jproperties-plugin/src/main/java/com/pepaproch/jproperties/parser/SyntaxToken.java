package com.pepaproch.jproperties.parser;


import com.sonar.sslr.api.Trivia;
import org.sonarsource.slang.api.TextRange;
import org.sonarsource.slang.impl.TokenImpl;

import java.util.List;

public class SyntaxToken extends TokenImpl {
    List<Trivia> trivias;

    public SyntaxToken(TextRange textRange, String text, Type type, List<Trivia> trivias) {
        super(textRange, text, type);
        this.trivias = trivias;

    }
}
