package org.pepaproch.properties.parser.slang.tree;

import org.sonarsource.slang.api.IdentifierTree;
import org.sonarsource.slang.api.StringLiteralTree;
import org.sonarsource.slang.api.Tree;
import org.sonarsource.slang.api.TreeMetaData;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class PropTree implements Tree {

    public final PropSeparatorTree separator;

    public final IdentifierTree key;
    public final StringLiteralTree value;
    public final TreeMetaData md;

    public PropTree(TreeMetaData md, IdentifierTree key, PropSeparatorTree separator, StringLiteralTree value) {

        this.key = key;
        this.value = value;
        this.md = md;
        this.separator = separator;
    }




    @Override
    public List<Tree> children() {
       return  Stream.of(key,separator,value).filter(Objects::nonNull).collect(Collectors.toList());
    }

    @Override
    public TreeMetaData metaData() {
        return md;
    }

}
