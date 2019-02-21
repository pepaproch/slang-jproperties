package com.pepaproch.jproperties.parser.slang.tree;

import org.sonarsource.slang.api.Tree;
import org.sonarsource.slang.api.TreeMetaData;

import java.util.Arrays;
import java.util.List;

public class PropertyTree implements Tree {

    public Operator op;

    public final PropertyKeyTree key;
    public final PropertyValueTree value;
    public final TreeMetaData md;

    public PropertyTree(TreeMetaData md, PropertyKeyTree key, SeparatorTree separator, PropertyValueTree value) {

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
        return Arrays.asList(key, value);
    }

    @Override
    public TreeMetaData metaData() {
        return md;
    }

}
