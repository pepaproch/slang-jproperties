package org.pepaproch.properties.checks;

import org.pepaproch.properties.parser.slang.tree.PropTree;
import org.sonar.check.Rule;
import org.sonar.check.RuleProperty;
import org.sonarsource.slang.checks.api.InitContext;
import org.sonarsource.slang.checks.api.SlangCheck;

import java.util.List;

import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Rule(key = "S2068")
public class HardcodedCredentialsCheck implements SlangCheck {


    private final String ISSUE_MEESAGE = "\"%s\" detected in this key, review this potentially hardcoded credential.";
    private static final String DEFAULT_VALUE = "password,pwd,username";
    private static final String DEFAULT_ENCRYPTED_CREDENTIALS_TO_IGNORE = "";
    private List<Pattern> patterns ;


    @RuleProperty(
            key = "credentialWords",
            description = "Comma separated list of words identifying potential credentials",
            defaultValue = DEFAULT_VALUE)
    public String credentialWords = DEFAULT_VALUE;

    @RuleProperty(
            key = "encryptedCredentialsToIgnore",
            description = "Regular expression of encrypted credentials to ignore. "
                    + "See http://docs.oracle.com/javase/8/docs/api/java/util/regex/Pattern.html for detailed regular expression syntax. "
                    + "For example, to ignore encrypted credentials starting with 'ENC(' and 'OBF:', set the parameter to '^(ENC\\(|OBF:).+$'. "
                    + "Leave empty if encrypted credentials should not be ignored.",
            defaultValue = DEFAULT_ENCRYPTED_CREDENTIALS_TO_IGNORE)


    private String encryptedCredentialsToIgnore = DEFAULT_ENCRYPTED_CREDENTIALS_TO_IGNORE;


    @Override
    public void initialize(InitContext init) {
        init.register(PropTree.class, (ctx, tree) -> {
            patterns().map(p-> p.matcher(tree.key.value())).filter(matcher-> matcher.find()
                    && (encryptedCredentialsToIgnore.trim().isEmpty()
                    || tree.value.content().startsWith(encryptedCredentialsToIgnore))).map( m-> m.group(0))
                    .forEach( s-> ctx.reportIssue(tree.key.textRange(),  String.format(ISSUE_MEESAGE, s)));

        });

    }



    private Stream<Pattern> patterns() {
        if(patterns == null) {
            patterns = Stream.of(credentialWords.split(",")).map(s-> Pattern.compile(s.trim())).collect(Collectors.toList());
        }
        return patterns.stream();
    }
}
