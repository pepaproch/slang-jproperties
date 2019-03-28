package org.pepaproch.properties.parser.slang.tree;

import org.pepaproch.properties.parser.sslr.JpropertiesLexicalGrammar;
import org.sonarsource.slang.api.Token;
import org.sonarsource.slang.api.Tree;
import org.sonarsource.slang.api.TreeMetaData;

import java.util.Collections;
import java.util.List;

public class PropSeparatorTree implements Tree {

    private final Token token;
    final JpropertiesLexicalGrammar separator;
    final TreeMetaData metaData;

    public PropSeparatorTree(TreeMetaData metaData, PropSyntaxToken token, JpropertiesLexicalGrammar separator) {
        this.token = token;
        this.separator = separator;
        this.metaData = metaData;
    }


    @Override
    public List<Tree> children() {
        return Collections.emptyList();
    }

    @Override
    public TreeMetaData metaData() {
        return metaData;
    }

    public Token getToken() {
        return token;
    }
}
