package org.pepaproch.properties.parser.sslr;


import com.sonar.sslr.api.typed.GrammarBuilder;
import org.pepaproch.properties.parser.slang.tree.PropSeparatorTree;
import org.pepaproch.properties.parser.slang.tree.PropSyntaxToken;
import org.pepaproch.properties.parser.slang.tree.PropTree;
import org.pepaproch.properties.parser.slang.tree.PropsTree;
import org.sonarsource.slang.api.IdentifierTree;
import org.sonarsource.slang.api.StringLiteralTree;

public class JpropertiesGrammar {

    final GrammarBuilder<PropSyntaxToken> b;
    final PropertiesTreeFactory f;

    public JpropertiesGrammar(GrammarBuilder b, PropertiesTreeFactory f) {
        this.b = b;
        this.f = f;
    }


    public PropsTree PROPERTIES() {
        return b.<PropsTree>nonterminal(JpropertiesLexicalGrammar.PROPERTIES).is(
                f.properties(
                        b.optional(b.token(JpropertiesLexicalGrammar.BOM)),
                        b.zeroOrMore(PROPERTY()),
                        b.token(JpropertiesLexicalGrammar.EOF)));
    }

    public PropTree PROPERTY() {
        return b.<PropTree>nonterminal(JpropertiesLexicalGrammar.PROPERTY).is(
                f.property(
                        KEY(),
                        SEPARATOR(),
                        b.optional(VALUE())));
    }

    public StringLiteralTree VALUE() {
        return b.<StringLiteralTree>nonterminal(JpropertiesLexicalGrammar.VALUE).is(
                f.value(b.token(JpropertiesLexicalGrammar.VALUE_LITERAL)));
    }

    public IdentifierTree KEY() {
        return b.<IdentifierTree>nonterminal(JpropertiesLexicalGrammar.KEY).is(
                f.key(b.token(JpropertiesLexicalGrammar.KEY_LITERAL)));
    }

    public PropSeparatorTree SEPARATOR() {
        return b.<PropSeparatorTree>nonterminal(JpropertiesLexicalGrammar.SEPARATOR).is(
                f.separator(
                        b.firstOf(
                                b.token(JpropertiesLexicalGrammar.EQUALS_SEPARATOR),
                                b.token(JpropertiesLexicalGrammar.COLON_SEPARATOR))));
    }
}
