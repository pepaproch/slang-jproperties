package com.pepaproch.jproperties.parser;


import com.sonar.sslr.api.typed.Optional;
import org.sonarsource.slang.api.Token;

import java.util.List;

public class JTreeFactory {

    public JTreeFactory() {
    }

    public PropertiesTree properties(Optional<Token> byteOrderMark, Optional<List<PropertyTree>> properties, Token eof) {
        return new PropertiesTree(byteOrderMark.orNull(), properties.orNull(), eof);
    }

    public PropertyTree property(PropertyKeyTree key, SeparatorTree separator, Optional<PropertyValueTree> value) {
        return new PropertyTree(key, separator, value.orNull());
    }

    public PropertyKeyTree key(SyntaxToken key) {
        return new PropertyKeyTree(null, key);
    }

    public PropertyValueTree value(SyntaxToken value) {
        return new PropertyValueTree(value);
    }

    public SeparatorTree separator(SyntaxToken separator) {
        JpropertiesLexicalGrammar t = null;

        switch (separator.text()) {
            case ":": {
                t = JpropertiesLexicalGrammar.COLON_SEPARATOR;
                break;
            }
            case "=": {
                t = JpropertiesLexicalGrammar.EQUALS_SEPARATOR;
                break;
            }
            // ADD SPACE SEPARATOR
            default: {

            }
        }

        return new SeparatorTree(separator, t);
    }

}
