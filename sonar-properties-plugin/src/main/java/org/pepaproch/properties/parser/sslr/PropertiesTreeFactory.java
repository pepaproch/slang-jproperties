package org.pepaproch.properties.parser.sslr;


import com.sonar.sslr.api.Trivia;
import com.sonar.sslr.api.typed.Optional;
import org.pepaproch.properties.parser.slang.tree.*;
import org.sonarsource.slang.api.*;
import org.sonarsource.slang.impl.TextRangeImpl;
import org.sonarsource.slang.impl.TreeMetaDataProvider;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;


public class PropertiesTreeFactory {


    public PropertiesTreeFactory() {
    }

    public PropsTree properties(Optional<Token> byteOrderMark, Optional<List<PropTree>> properties, Token eof) {

        List<Comment> comentsTrivia = comments((PropSyntaxToken) eof);




        TreeMetaData treeMetaData = metaData(comentsTrivia, properties.or(Collections.emptyList()).toArray(new Tree[]{}));
        return new PropsTree(treeMetaData, byteOrderMark.orNull(), properties, eof);
    }


    public PropTree property(PropKeyTree key, PropSeparatorTree separator, Optional<PropValueTree> value) {
        TreeMetaData treeMetaData = metaData(null, key, separator, value.orNull());
        return new PropTree(treeMetaData, key, separator, value.orNull());
    }


    public PropKeyTree key(PropSyntaxToken key) {
        return new PropKeyTree(metaData(key), key);
    }

    public PropValueTree value(PropSyntaxToken value) {
        return new PropValueTree(metaData(value), value);
    }

    public PropSeparatorTree separator(PropSyntaxToken separator) {
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

        return new PropSeparatorTree(metaData(separator), separator, t);
    }

    private TreeMetaData metaData(PropSyntaxToken token) {
        List<HasTextRange> ranges = new ArrayList<>();
        ranges.add(token);
        List<Comment> triviasComments = comments(token);
        ranges.addAll(token.trivias.stream().map(triviaToComent).collect(Collectors.toList()));
        HasTextRange[] hasTextRanges = ranges.toArray(new HasTextRange[]{});

        return new TreeMetaDataProvider(triviasComments, Collections.singletonList(token)).metaData(TextRangeUtils.wholeRange(hasTextRanges));

    }


    private List<Comment> comments(PropSyntaxToken token) {

        return token.trivias.stream().map(triviaToComent).collect(Collectors.toList());
    }

    //TODO handle token better
    private <T extends Tree> TreeMetaData metaData(@Nullable List<Comment> aditionalTokens, T... trees) {

        List<Comment> comments = java.util.Optional.ofNullable(aditionalTokens).orElse(new ArrayList<>());

        List<Token> tokens = new ArrayList<>();

        Stream.of(trees).filter(t -> t != null).forEach(t -> {
                    comments.addAll(t.metaData().commentsInside());
                    tokens.addAll(t.metaData().tokens());
                }
        );


        List<HasTextRange> ranges = Stream.concat(
                java.util.Optional.ofNullable(aditionalTokens).orElse(Collections.emptyList()).stream().map(ac -> (HasTextRange) ac),
                        comments.stream().map(c -> (HasTextRange) c)).collect(Collectors.toList());
        ranges.addAll(tokens);


        TreeMetaDataProvider provider = new TreeMetaDataProvider(comments, tokens);


        return provider.metaData(TextRangeUtils.wholeRange(ranges.toArray(new HasTextRange[]{})));

    }


    private Function<Trivia, Comment> triviaToComent = (Trivia t) -> {
        if (t.isComment()) {
            return new PropComment(t.getToken().getOriginalValue(), commentRange(t));
        }
        return null;

    };


    private TextRange commentRange(Trivia t) {
        return new TextRangeImpl(t.getToken().getLine(), t.getToken().getColumn(), t.getToken().getLine(), t.getToken().getOriginalValue().length());
    }


}
