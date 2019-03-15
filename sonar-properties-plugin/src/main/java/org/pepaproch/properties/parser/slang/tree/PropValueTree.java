package org.pepaproch.properties.parser.slang.tree;


import org.sonarsource.slang.api.Tree;
import org.sonarsource.slang.api.TreeMetaData;
import org.sonarsource.slang.impl.StringLiteralTreeImpl;

import java.util.Collections;
import java.util.List;

public class PropValueTree extends StringLiteralTreeImpl {


    public PropValueTree(TreeMetaData metaData, PropSyntaxToken value) {
        super(metaData, value.text(), value.text());
    }

    @Override
    public List<Tree> children() {
        return Collections.emptyList();
    }
}
