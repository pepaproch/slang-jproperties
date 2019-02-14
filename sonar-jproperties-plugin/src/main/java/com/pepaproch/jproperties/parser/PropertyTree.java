package com.pepaproch.jproperties.parser;

import org.sonarsource.slang.api.Tree;
import org.sonarsource.slang.api.TreeMetaData;

import java.util.Collections;
import java.util.List;

public class PropertyTree implements Tree {

    public Operator op;

    public final PropertyKeyTree key;
    public final PropertyValueTree value;


    public PropertyTree(PropertyKeyTree key, SeparatorTree separator, PropertyValueTree value) {
        this.key = key;
        this.value = value;

        switch (separator.token.text()) {
            case ":":
                op = Operator.COLON;
                break;
            case "=":
                op = Operator.EQUALS;
                break;
            case " ":
                op = Operator.SPACE;
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
        return Collections.emptyList();
    }

    @Override
    public TreeMetaData metaData() {
        return null;
    }

}
