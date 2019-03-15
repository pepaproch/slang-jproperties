package org.pepaproch.properties.parser.slang.tree;

import com.sonar.sslr.api.typed.Optional;
import org.sonarsource.slang.api.*;

import javax.annotation.CheckForNull;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class PropsTree implements TopLevelTree {


    private final Token byteOrderMark;
    private final Token eof;
    private final List<PropTree> children;
    private final TreeMetaData metaData;


    public PropsTree(TreeMetaData metaData, Token byteOrderMark, Optional<List<PropTree>> children, Token eof) {
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



    public List<PropTree> getChildren() {
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
