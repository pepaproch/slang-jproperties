package org.pepaproch.properties.parser.sslr;


import com.sonar.sslr.api.typed.ActionParser;
import org.pepaproch.properties.parser.slang.PropertiesNodeBuilder;
import org.pepaproch.properties.parser.slang.tree.PropsTree;

import java.nio.charset.StandardCharsets;

public class JpropertiesParser {


    public static ActionParser<PropsTree> create(PropertiesTreeFactory factory, PropertiesNodeBuilder propertiesNodeBuilder) {
        return new ActionParser<PropsTree>(StandardCharsets.UTF_8,
                JpropertiesLexicalGrammar.createBuilder(),
                JpropertiesGrammar.class,
                factory,
                propertiesNodeBuilder,
                JpropertiesLexicalGrammar.PROPERTIES
        );


    }

}
