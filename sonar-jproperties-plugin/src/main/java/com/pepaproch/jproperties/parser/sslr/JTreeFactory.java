package com.pepaproch.jproperties.parser.sslr;


import com.pepaproch.jproperties.parser.*;
import com.sonar.sslr.api.Trivia;
import com.sonar.sslr.api.typed.Optional;
import org.sonarsource.slang.api.*;
import org.sonarsource.slang.impl.TextRangeImpl;
import org.sonarsource.slang.impl.TreeMetaDataProvider;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public class JTreeFactory {


    public JTreeFactory() {
    }

    public PropertiesTree properties(Optional<Token> byteOrderMark, Optional<List<PropertyTree>> properties, Token eof) {
        PropertyTree[] propertiesTrees = properties.get().toArray(new PropertyTree[]{});
        TreeMetaData treeMetaData = metaDataMerge(propertiesTrees);
        return new PropertiesTree(treeMetaData, byteOrderMark.orNull(), properties.orNull(), eof);
    }

    public PropertyTree property(PropertyKeyTree key, SeparatorTree separator, Optional<PropertyValueTree> value) {

        TreeMetaData treeMetaData = metaDataMerge((Tree) key, (Tree) separator, (Tree) value.orNull());

        return new PropertyTree(treeMetaData, key, separator, value.orNull());
    }


    public PropertyKeyTree key(SyntaxToken key) {

        return new PropertyKeyTree(metaData(Collections.singletonList(key), key.textRange()), key);
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
        List<Comment> triviasComments = token.trivias.stream().map(triviaToComent).collect(Collectors.toList());

        return new TreeMetaDataProvider(triviasComments, Collections.singletonList(token)).metaData(token.textRange());

    }

    private TreeMetaData metaData(List<SyntaxToken> tokens, TextRange range) {
        List<Comment> collect = tokens.stream().map(t -> t.trivias).flatMap(l -> l.stream()).map(triviaToComent).collect(Collectors.toList());
        return new TreeMetaDataProvider(collect, new ArrayList<>(tokens)).metaData(range);
    }

    private <T extends Tree> TreeMetaData metaDataMerge(T... trees) {
        List<Comment> comments = Arrays.stream(trees).flatMap(st -> st.metaData().commentsInside().stream()).collect(Collectors.toList());
        List<Token> tokens = Arrays.stream(trees).flatMap(st -> st.metaData().tokens().stream()).collect(Collectors.toList());
        TreeMetaDataProvider provider = new TreeMetaDataProvider(comments, tokens);
        TextRange range = new TextRangeImpl(provider.allTokens().get(0).textRange().start(), provider.allTokens().get(provider.allTokens().size() - 1).textRange().end());
        return provider.metaData(range);

    }


    private Function<Trivia, Comment> triviaToComent = (Trivia t) -> {
        if (t.isComment()) {
            return new SyntaxTrivia(t.getToken().getOriginalValue(), commentRange(t));
        }
        return null;

    };


    private TextRange commentRange(Trivia t) {
        return new TextRangeImpl(t.getToken().getLine(), t.getToken().getColumn(), t.getToken().getLine(), t.getToken().getOriginalValue().length());
    }


}
