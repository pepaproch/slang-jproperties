package org.pepaproch.properties.parser.slang.tree;


import org.sonarsource.slang.api.Token;
import org.sonarsource.slang.api.Tree;
import org.sonarsource.slang.api.TreeMetaData;
import org.sonarsource.slang.impl.LiteralTreeImpl;

import java.util.Collections;
import java.util.List;

public class PropKeyTree extends LiteralTreeImpl {

    public PropKeyTree(TreeMetaData metaData, Token name) {
        super(metaData, name.text());
    }


    @Override
    public List<Tree> children() {
        return Collections.emptyList();
    }
}
