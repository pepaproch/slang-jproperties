package com.pepaproch.jproperties.parser;


import com.sonar.sslr.api.typed.GrammarBuilder;
import org.sonarsource.slang.api.Token;

public class JpropertiesGrammar {

    final GrammarBuilder<SyntaxToken> b;
    final JTreeFactory f;

    public JpropertiesGrammar(GrammarBuilder b, JTreeFactory f) {
        this.b = b;
        this.f = f;
    }


    public PropertiesTree PROPERTIES() {
        return b.<PropertiesTree>nonterminal(JpropertiesLexicalGrammar.PROPERTIES).is(
                f.properties(
                        b.optional(b.token(JpropertiesLexicalGrammar.BOM)),
                        b.zeroOrMore(PROPERTY()),
                        b.token(JpropertiesLexicalGrammar.EOF)));
    }

    public PropertyTree PROPERTY() {
        return b.<PropertyTree>nonterminal(JpropertiesLexicalGrammar.PROPERTY).is(
                f.property(
                        KEY(),
                        SEPARATOR(),
                        b.optional(VALUE())));
    }

    public PropertyValueTree VALUE() {
        return b.<PropertyValueTree>nonterminal(JpropertiesLexicalGrammar.VALUE).is(
                f.value(b.token(JpropertiesLexicalGrammar.VALUE_LITERAL)));
    }

    public PropertyKeyTree KEY() {
        return b.<PropertyKeyTree>nonterminal(JpropertiesLexicalGrammar.KEY).is(
                f.key(b.token(JpropertiesLexicalGrammar.KEY_LITERAL)));
    }

    public SeparatorTree SEPARATOR() {
        return b.<SeparatorTree>nonterminal(JpropertiesLexicalGrammar.SEPARATOR).is(
                f.separator(
                        b.firstOf(
                                b.token(JpropertiesLexicalGrammar.EQUALS_SEPARATOR),
                                b.token(JpropertiesLexicalGrammar.COLON_SEPARATOR))));
    }
}
