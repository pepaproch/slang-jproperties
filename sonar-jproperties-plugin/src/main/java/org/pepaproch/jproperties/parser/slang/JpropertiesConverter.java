package org.pepaproch.jproperties.parser.slang;

import com.sonar.sslr.api.RecognitionException;
import com.sonar.sslr.api.typed.ActionParser;
import org.pepaproch.jproperties.parser.slang.tree.PropertiesTree;
import org.pepaproch.jproperties.parser.sslr.JTreeFactory;
import org.pepaproch.jproperties.parser.sslr.JpropertiesParser;
import org.sonarsource.slang.api.ASTConverter;
import org.sonarsource.slang.api.ParseException;
import org.sonarsource.slang.api.Tree;
import org.sonarsource.slang.impl.TextPointerImpl;

import java.lang.reflect.InvocationTargetException;

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

        ActionParser<PropertiesTree> propertiesTreeActionParser = JpropertiesParser.create(factory, nodeBUilder);

        try {
            return propertiesTreeActionParser.parse(content);
        } catch (RecognitionException e) {
            throw new ParseException( e.getMessage() , new TextPointerImpl(e.getLine(),0));
        }
    }
}
