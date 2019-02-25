package org.pepaproch.jproperties.parser.slang.tree;

import com.sonar.sslr.api.typed.Optional;
import org.sonarsource.slang.api.*;
import org.sonarsource.slang.impl.TreeMetaDataProvider;

import javax.annotation.CheckForNull;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import java.util.stream.Collectors;

public class PropertiesTree implements TopLevelTree {


    private final Token byteOrderMark;
    private final Token eof;
    private final List<PropertyTree> children;
    private final TreeMetaData metaData;


    public PropertiesTree(TreeMetaData metaData, Token byteOrderMark, Optional<List<PropertyTree>> children, Token eof) {
        this.metaData = metaData;
        this.children = children.or(Collections.emptyList());
        this.eof = eof;
        this.byteOrderMark = byteOrderMark;
    }

    @Override
    public List<Tree> children() {
         return   getChildren().stream().collect(Collectors.toList());
    }

    @Override
    public TreeMetaData metaData() {
        return metaData;
    }

    public Token getByteOrderMark() {
        return byteOrderMark;
    }

    public Token getEof() {
        return eof;
    }

    public List<PropertyTree> getChildren() {
        return children;
    }

    @Override
    public List<Tree> declarations() {
        return children.stream().map(p -> p).collect(Collectors.toList());
    }

    @Override
    public List<Comment> allComments() {
        return metaData.commentsInside();
    }

    @CheckForNull
    @Override
    public Token firstCpdToken() {

        return   null;

    }
}
