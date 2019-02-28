package org.pepaproch.properties.parser.sslr;


import com.sonar.sslr.api.GenericTokenType;
import org.sonar.sslr.grammar.GrammarRuleKey;
import org.sonar.sslr.grammar.LexerlessGrammarBuilder;

public enum JpropertiesLexicalGrammar implements GrammarRuleKey {
    PROPERTIES,
    PROPERTY,
    KEY,
    VALUE,
    KEY_LITERAL,
    VALUE_LITERAL,
    SEPARATOR,
    COLON_SEPARATOR,
    EQUALS_SEPARATOR,
    SPACING,
    WHITESPACES_WITHOUT_LINE_BREAK,
    BOM,
    EOF;

    public static LexerlessGrammarBuilder createBuilder() {
        LexerlessGrammarBuilder b = LexerlessGrammarBuilder.create();

        b.rule(EOF).is(SPACING, b.token(GenericTokenType.EOF, b.endOfInput()));
        b.rule(BOM).is("\ufeff");

        b.rule(SPACING).is(
                b.skippedTrivia(b.regexp("(?<!\\\\)[\\s]*+")),
                b.zeroOrMore(
                        b.commentTrivia(b.regexp("(#|!)+.*")),
                        b.skippedTrivia(b.regexp("(?<!\\\\)[\\s]*+"))));
        b.rule(WHITESPACES_WITHOUT_LINE_BREAK).is(b.skippedTrivia(b.regexp("(?<!\\\\)[ \\t\\x0B\\f]*+")));

        b.rule(KEY_LITERAL).is(SPACING, b.regexp("([^=:\\s]|(?<=\\\\)\\ |(?<=\\\\)\\=|(?<=\\\\)\\:)+"));
        b.rule(COLON_SEPARATOR).is(WHITESPACES_WITHOUT_LINE_BREAK, b.nextNot("\\"), ":");
        b.rule(EQUALS_SEPARATOR).is(WHITESPACES_WITHOUT_LINE_BREAK, b.nextNot("\\"), "=");
        b.rule(VALUE_LITERAL).is(WHITESPACES_WITHOUT_LINE_BREAK, b.regexp("[^\\n\\r]+((?<=\\\\)\\r?\\n[^\\n\\r]*)*"));
        b.setRootRule(PROPERTIES);

        return b;
    }
}
