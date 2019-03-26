package org.pepaproch.properties.parser.sslr;


import com.sonar.sslr.api.Trivia;
import com.sonar.sslr.api.typed.Optional;
import org.pepaproch.properties.parser.slang.tree.*;
import org.sonarsource.slang.api.*;
import org.sonarsource.slang.impl.*;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;


public class PropertiesTreeFactory {


    private Function<Trivia, Comment> triviaToComment = (Trivia t) -> {
        if (t.isComment()) {
            String text = t.getToken().getOriginalValue();
            String contentText = text.substring(1);
            TextRangeImpl textRange = new TextRangeImpl(t.getToken().getLine(), t.getToken().getColumn(), t.getToken().getLine(), t.getToken().getOriginalValue().length());
            TextRangeImpl contentRange = new TextRangeImpl(t.getToken().getLine(), t.getToken().getColumn()+1, t.getToken().getLine(), t.getToken().getOriginalValue().length());
            return new CommentImpl(text,contentText,textRange, contentRange);
        }
        return null;

    };



    public PropsTree properties(Optional<Token> byteOrderMark, Optional<List<PropTree>> properties, Token eof) {
        List<Comment> comentsTrivia = comments((PropSyntaxToken) eof);
        TreeMetaData treeMetaData = metaData(comentsTrivia, properties.or(Collections.emptyList()).toArray(new Tree[]{}));
        return new PropsTree(treeMetaData, byteOrderMark.orNull(), properties, eof);
    }

    public PropTree property(IdentifierTree key, PropSeparatorTree separator, Optional<StringLiteralTree> value) {
        TreeMetaData treeMetaData = metaData(null, key, separator, value.orNull());
        return new PropTree(treeMetaData, key, separator, value.orNull());
    }

    public IdentifierTree key(PropSyntaxToken key) {
        return new IdentifierTreeImpl(metaData(key), key.text());
    }

    public StringLiteralTree value(PropSyntaxToken value) {
        return new StringLiteralTreeImpl(metaData(value), value.text() ,value.text());
    }

    public PropSeparatorTree separator(PropSyntaxToken separator) {
        JpropertiesLexicalGrammar t = null;

        switch (separator.text()) {
            case ":":
                t = JpropertiesLexicalGrammar.COLON_SEPARATOR;
                break;

            case "=":
                t = JpropertiesLexicalGrammar.EQUALS_SEPARATOR;
                break;

            default:
                break;
        }

        return new PropSeparatorTree(metaData(separator), separator, t);
    }

    private TreeMetaData metaData(PropSyntaxToken token) {
        List<HasTextRange> ranges = new ArrayList<>();
        ranges.add(token);
        List<Comment> triviasComments = comments(token);
        ranges.addAll(token.getTrivias().stream().map(triviaToComment).collect(Collectors.toList()));
        HasTextRange[] hasTextRanges = ranges.toArray(new HasTextRange[]{});

        return new TreeMetaDataProvider(triviasComments, Collections.singletonList(token)).metaData(TextRangeUtils.wholeRange(hasTextRanges));

    }

    private List<Comment> comments(PropSyntaxToken token) {

        return token.getTrivias().stream().map(triviaToComment).collect(Collectors.toList());
    }

    //TODO handle token better
    private <T extends Tree> TreeMetaData metaData(@Nullable List<Comment> aditionalTokens, T... trees) {

        List<Comment> comments = java.util.Optional.ofNullable(aditionalTokens).orElse(new ArrayList<>());

        List<Token> tokens = new ArrayList<>();

        Stream.of(trees).filter(Objects::nonNull).forEach(t -> {
                    comments.addAll(t.metaData().commentsInside());
                    tokens.addAll(t.metaData().tokens());
                }
        );


        List<HasTextRange> ranges = Stream.concat(
                java.util.Optional.ofNullable(aditionalTokens).orElse(Collections.emptyList()).stream().map(ac -> (HasTextRange) ac),
                comments.stream().map(c -> (HasTextRange) c)).collect(Collectors.toList());
        ranges.addAll(tokens);

        return new TreeMetaDataProvider(comments, tokens).metaData(TextRangeUtils.wholeRange(ranges.toArray(new HasTextRange[]{})));

    }



}
