package org.pepaproch.properties.parser.slang.tree;

import org.sonarsource.slang.api.Tree;
import org.sonarsource.slang.api.TreeMetaData;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class PropTree implements Tree {

    public final Operator op;

    public final PropKeyTree key;
    public final PropValueTree value;
    public final TreeMetaData md;

    public PropTree(TreeMetaData md, PropKeyTree key, PropSeparatorTree separator, PropValueTree value) {

        this.key = key;
        this.value = value;
        this.md = md;

        switch (separator.token.text()) {
            case ":":
                op = Operator.COLON;
                break;
            case "=":
                op = Operator.EQUALS;
                break;
            default:
                op = null;
                break;
        }
    }

    enum Operator {
        EQUALS,
        SPACE,
        COLON
    }


    @Override
    public List<Tree> children() {
       return  Stream.of(key,value).filter(Objects::nonNull).collect(Collectors.toList());
    }

    @Override
    public TreeMetaData metaData() {
        return md;
    }

}
