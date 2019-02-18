package com.pepaproch.jproperties.parser.sslr;


import com.pepaproch.jproperties.parser.PropertiesTree;

import com.sonar.sslr.api.typed.ActionParser;


import java.nio.charset.StandardCharsets;

public class JpropertiesParser {


    public static ActionParser<PropertiesTree> create(JTreeFactory factory, JpNodeBuilder jpNodeBuilder) {
        return new ActionParser<PropertiesTree>(StandardCharsets.UTF_8,
                JpropertiesLexicalGrammar.createBuilder(),
                JpropertiesGrammar.class,
                factory,
                jpNodeBuilder,
                JpropertiesLexicalGrammar.PROPERTIES
        );


    }

}
