package org.pepaproch.properties.parser.slang;

import com.sonar.sslr.api.Rule;
import com.sonar.sslr.api.TokenType;
import com.sonar.sslr.api.Trivia;
import com.sonar.sslr.api.typed.Input;
import com.sonar.sslr.api.typed.NodeBuilder;
import org.pepaproch.properties.parser.slang.tree.PropSyntaxToken;
import org.sonar.sslr.grammar.GrammarRuleKey;
import org.sonarsource.slang.api.TextPointer;
import org.sonarsource.slang.api.TextRange;
import org.sonarsource.slang.impl.TextPointerImpl;
import org.sonarsource.slang.impl.TextRangeImpl;

import java.util.List;

public class PropertiesNodeBuilder implements NodeBuilder {

    @Override
    public Object createNonTerminal(GrammarRuleKey ruleKey, Rule rule, List<Object> children, int startIndex, int endIndex) {
        for (Object child : children) {
            if (child instanceof PropSyntaxToken) {
                return child;
            }
        }

        return null;


    }

    @Override
    public Object createTerminal(Input input, int startIndex, int endIndex, List<Trivia> trivias, TokenType type) {
        return new PropSyntaxToken(textRange(input, startIndex, endIndex), input.substring(startIndex, endIndex), null, trivias);
    }

    private static TextPointer textPointer(Input input, int index) {
        int[] lc = input.lineAndColumnAt(index);
        return new TextPointerImpl(lc[0], lc[1] - 1);
    }

    private static TextRange textRange(Input input, int startIndex, int endIndex) {

        return new TextRangeImpl(textPointer(input, startIndex), textPointer(input, endIndex));
    }
}
