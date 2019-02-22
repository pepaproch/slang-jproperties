package org.pepaproch.jproperties.parser.sslr;


import com.sonar.sslr.api.Trivia;
import com.sonar.sslr.api.typed.Optional;
import org.pepaproch.jproperties.parser.slang.tree.*;
import org.pepaproch.jproperties.parser.slang.visitor.TokensAndComentsVisitor;
import org.sonarsource.slang.api.*;
import org.sonarsource.slang.impl.TextRangeImpl;
import org.sonarsource.slang.impl.TreeMetaDataProvider;
import org.sonarsource.slang.visitors.TreeContext;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;


public class JTreeFactory {
    private final TokensAndComentsVisitor v;

    public JTreeFactory() {
        v = new TokensAndComentsVisitor();
    }

    public PropertiesTree properties(Optional<Token> byteOrderMark, Optional<List<PropertyTree>> properties, Token eof) {
        PropertyTree[] propertiesTrees = properties.get().toArray(new PropertyTree[]{});
        TreeMetaData treeMetaData = metaData(propertiesTrees);
        return new PropertiesTree(treeMetaData, byteOrderMark.orNull(), properties.orNull(), eof);
    }

    public PropertyTree property(PropertyKeyTree key, SeparatorTree separator, Optional<PropertyValueTree> value) {
        TreeMetaData treeMetaData = metaData(key, separator, value.orNull());
        return new PropertyTree(treeMetaData, key, separator, value.orNull());
    }


    public PropertyKeyTree key(SyntaxToken key) {
        return new PropertyKeyTree(metaData(key), key);
    }

    public PropertyValueTree value(SyntaxToken value) {
        return new PropertyValueTree(metaData(value), value);
    }

    public SeparatorTree separator(SyntaxToken separator) {
        JpropertiesLexicalGrammar t = null;

        switch (separator.text()) {
            case ":": {
                t = JpropertiesLexicalGrammar.COLON_SEPARATOR;
                break;
            }
            case "=": {
                t = JpropertiesLexicalGrammar.EQUALS_SEPARATOR;
                break;
            }
            // ADD SPACE SEPARATOR
            default: {

            }
        }

        return new SeparatorTree(metaData(separator), separator, t);
    }

    private TreeMetaData metaData(SyntaxToken token) {
        List<HasTextRange> ranges = new ArrayList<>();
        ranges.add(token);

        List<Comment> triviasComments = token.trivias.stream().map(triviaToComent).collect(Collectors.toList());
        ranges.addAll(token.trivias.stream().map(triviaToComent).collect(Collectors.toList()));
        HasTextRange[] hasTextRanges = ranges.toArray(new HasTextRange[]{});

        return new TreeMetaDataProvider(triviasComments, Collections.singletonList(token)).metaData(TextRangeUtils.wholeRange(hasTextRanges));

    }


    private <T extends Tree> TreeMetaData metaData(T... trees) {

        List<Comment> comments = new ArrayList<>();
        List<Token> tokens = new ArrayList<>();
        Stream.of(trees).forEach(t -> {
                    v.scan(new TreeContext(), t);
                    comments.addAll(v.getComments());
                    tokens.addAll(v.getTokens());
                }
        );

        TreeMetaDataProvider provider = new TreeMetaDataProvider(comments, tokens);
        return provider.metaData(TextRangeUtils.wholeRange(trees));

    }


    private Function<Trivia, Comment> triviaToComent = (Trivia t) -> {
        if (t.isComment()) {
            return new CommentImpl(t.getToken().getOriginalValue(), commentRange(t));
        }
        return null;

    };


    private TextRange commentRange(Trivia t) {
        return new TextRangeImpl(t.getToken().getLine(), t.getToken().getColumn(), t.getToken().getLine(), t.getToken().getOriginalValue().length());
    }


}
