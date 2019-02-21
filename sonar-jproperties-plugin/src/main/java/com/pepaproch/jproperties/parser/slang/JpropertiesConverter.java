package com.pepaproch.jproperties.parser.slang;

import com.pepaproch.jproperties.parser.sslr.JTreeFactory;
import com.pepaproch.jproperties.parser.sslr.JpropertiesParser;
import org.sonarsource.slang.api.ASTConverter;
import org.sonarsource.slang.api.Tree;

public class JpropertiesConverter implements ASTConverter {


    final JTreeFactory factory;
    final JpNodeBuilder nodeBUilder;

    private JpropertiesConverter(JpNodeBuilder nodeBUilder, JTreeFactory factory) {
        this.factory = factory;
        this.nodeBUilder = nodeBUilder;
    }

    public static JpropertiesConverter create() {
        return new JpropertiesConverter(new JpNodeBuilder(), new JTreeFactory());
    }

    @Override
    public Tree parse(String content) {
        return JpropertiesParser.create(factory, nodeBUilder).parse(content);
    }
}
