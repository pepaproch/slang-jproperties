package com.pepaproch.jproperties.parser;

import org.sonarsource.slang.api.Token;
import org.sonarsource.slang.api.Tree;
import org.sonarsource.slang.api.TreeMetaData;

import java.util.Collections;
import java.util.List;

public class SeparatorTree implements Tree {

    final Token token;
    final JpropertiesLexicalGrammar separator;

    public SeparatorTree(SyntaxToken token, JpropertiesLexicalGrammar separator) {
        this.token = token;
        this.separator = separator;
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
