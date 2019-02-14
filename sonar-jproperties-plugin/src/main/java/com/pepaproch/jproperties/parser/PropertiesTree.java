package com.pepaproch.jproperties.parser;

import org.sonarsource.slang.api.Token;
import org.sonarsource.slang.api.Tree;
import org.sonarsource.slang.api.TreeMetaData;

import java.util.ArrayList;
import java.util.List;

public class PropertiesTree implements Tree {


    private final Token byteOrderMark;
    private final Token eof;
    private final List<PropertyTree> children;


    public PropertiesTree(Token byteOrderMark, List<PropertyTree> children, Token eof) {
        this.children = children;
        this.eof = eof;
        this.byteOrderMark = byteOrderMark;
    }

    @Override
    public List<Tree> children() {
        ArrayList<Tree> r = new ArrayList<>();
        r.addAll(children);
        return r;
    }

    @Override
    public TreeMetaData metaData() {
        return null;
    }
}
