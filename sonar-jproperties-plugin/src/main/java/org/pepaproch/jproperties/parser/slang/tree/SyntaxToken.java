package org.pepaproch.jproperties.parser.slang.tree;


import com.sonar.sslr.api.Trivia;
import org.sonarsource.slang.api.TextRange;
import org.sonarsource.slang.impl.TokenImpl;

import java.util.List;
import java.util.stream.Collectors;

public class SyntaxToken extends TokenImpl {
    public List<Trivia> trivias;

    public SyntaxToken(TextRange textRange, String text, Type type, List<Trivia> trivias) {
        super(textRange, text, type);
        this.trivias = trivias.stream().collect(Collectors.toList());

    }
}
