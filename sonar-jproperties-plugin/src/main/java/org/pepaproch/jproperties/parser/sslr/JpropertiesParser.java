package org.pepaproch.jproperties.parser.sslr;


import com.sonar.sslr.api.typed.ActionParser;
import org.pepaproch.jproperties.parser.slang.JpNodeBuilder;
import org.pepaproch.jproperties.parser.slang.tree.PropertiesTree;

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
