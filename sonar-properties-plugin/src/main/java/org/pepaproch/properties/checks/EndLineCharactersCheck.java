package org.pepaproch.properties.checks;

import org.pepaproch.properties.parser.slang.tree.PropsTree;
import org.sonar.check.Rule;
import org.sonar.check.RuleProperty;
import org.sonarsource.slang.checks.api.InitContext;
import org.sonarsource.slang.checks.api.SlangCheck;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

@Rule(key = "end-line-characters")
public class EndLineCharactersCheck implements SlangCheck {


    private static final String ISSUE_MESSAGE = "Set all end-line characters to '%s' in this file.";

    @RuleProperty(
            key = "End-line character",
            description = "Allowed values: 'CR', 'CRLF', 'LF'",
            defaultValue = DEFAULT_FORMAT)
    private String endLineCharacters = DEFAULT_FORMAT;


    private enum PATTERNS {

        LF(Pattern.compile("(?s)\r")),
        CR(Pattern.compile("(?s)\n")),
        CRLF(Pattern.compile("(?s)(\r(?!\n)|(?<!\r)\n)"));

        private Pattern pattern;

        PATTERNS(Pattern pattern) {
            this.pattern = pattern;
        }
    }

    private List<PATTERNS> patternMap = new ArrayList<>();


    private static final String DEFAULT_FORMAT = "LF";


    @Override
    public void initialize(InitContext init) {

        Stream.of(PATTERNS.values()).filter(p -> p.name().equalsIgnoreCase(endLineCharacters)).
                forEach(e -> patternMap.add(e));

        init.register(PropsTree.class, (ctx, tree) -> {
            boolean b = patternMap.stream().map(p -> p.pattern.matcher(ctx.fileContent())).anyMatch(Matcher::find);
            if (b) {
                ctx.reportFileIssue(String.format(ISSUE_MESSAGE, endLineCharacters));
            }

        });
    }
}
