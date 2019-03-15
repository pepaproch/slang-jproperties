package org.pepaproch.properties.parser.slang.tree;


import com.sonar.sslr.api.Trivia;
import org.sonarsource.slang.api.TextRange;
import org.sonarsource.slang.impl.TokenImpl;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class PropSyntaxToken extends TokenImpl {
    private List<Trivia> trivias;

    public PropSyntaxToken(TextRange textRange, String text, Type type, List<Trivia> trivias) {
        super(textRange, text, type);
        this.trivias = trivias!=null ? trivias.stream().collect(Collectors.toList()) : Collections.EMPTY_LIST;

    }

    public List<Trivia> getTrivias() {
        return trivias;
    }
}
