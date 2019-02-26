package org.pepaproch.properties.parser.slang;

import com.sonar.sslr.api.RecognitionException;
import com.sonar.sslr.api.typed.ActionParser;
import org.pepaproch.properties.parser.slang.tree.PropsTree;
import org.pepaproch.properties.parser.sslr.PropertiesTreeFactory;
import org.pepaproch.properties.parser.sslr.JpropertiesParser;
import org.sonarsource.slang.api.ASTConverter;
import org.sonarsource.slang.api.ParseException;
import org.sonarsource.slang.api.Tree;
import org.sonarsource.slang.impl.TextPointerImpl;

public class PropertiesConverter implements ASTConverter {


    final PropertiesTreeFactory factory;
    final PropertiesNodeBuilder nodeBUilder;

    private PropertiesConverter(PropertiesNodeBuilder nodeBUilder, PropertiesTreeFactory factory) {
        this.factory = factory;
        this.nodeBUilder = nodeBUilder;
    }

    public static PropertiesConverter create() {
        return new PropertiesConverter(new PropertiesNodeBuilder(), new PropertiesTreeFactory());
    }

    @Override
    public Tree parse(String content) {

        ActionParser<PropsTree> propertiesTreeActionParser = JpropertiesParser.create(factory, nodeBUilder);

        try {
            return propertiesTreeActionParser.parse(content);
        } catch (RecognitionException e) {
            throw new ParseException( e.getMessage() , new TextPointerImpl(e.getLine(),0));
        }
    }
}
