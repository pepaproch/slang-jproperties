package com.pepaproch.jproperties.parser;


import org.sonarsource.slang.api.Tree;


import org.sonarsource.slang.api.TreeMetaData;
import org.sonarsource.slang.impl.LiteralTreeImpl;
import org.sonarsource.slang.impl.StringLiteralTreeImpl;
import org.sonarsource.slang.impl.TreeMetaDataProvider;

import java.util.Collections;
import java.util.List;

public class PropertyValueTree extends LiteralTreeImpl {


    public PropertyValueTree(TreeMetaData metaData, SyntaxToken value) {
        super(metaData, value.text());
    }

    @Override
    public List<Tree> children() {
        return Collections.emptyList();
    }
}
