package com.pepaproch.jproperties.parser;


import org.sonarsource.slang.api.Tree;


import org.sonarsource.slang.impl.LiteralTreeImpl;
import org.sonarsource.slang.impl.StringLiteralTreeImpl;

import java.util.Collections;
import java.util.List;

public class PropertyValueTree extends LiteralTreeImpl {


    public PropertyValueTree(SyntaxToken value) {
        super(null, value.text());
    }

    @Override
    public List<Tree> children() {
        return Collections.emptyList();
    }
}
