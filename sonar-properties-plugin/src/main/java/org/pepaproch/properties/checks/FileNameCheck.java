package org.pepaproch.properties.checks;

import org.pepaproch.properties.parser.slang.tree.PropsTree;
import org.sonar.check.Rule;
import org.sonar.check.RuleProperty;
import org.sonarsource.slang.checks.api.InitContext;
import org.sonarsource.slang.checks.api.SlangCheck;

import java.util.regex.Pattern;

@Rule(  key = "S1578")
public class FileNameCheck implements SlangCheck {


    Pattern pattern;

    private static final String DEFAULT = "^[A-Za-z][-_A-Za-z0-9]*\\.properties$";

    @RuleProperty(
            key = "format",
            defaultValue = DEFAULT,
            description = "Regular expression that file names should match. See http://docs.oracle.com/javase/8/docs/api/java/util/regex/Pattern.html for detailed regular expression syntax.")
    private String format = DEFAULT;
    private String ISSUE_MESSAGE = "Rename this file to match the regular expression: " + format;

    @Override
    public void initialize(InitContext init) {
        init.register(PropsTree.class, (ctx, tree) -> {

            if (!matches(ctx.filename())) {
                ctx.reportFileIssue(ISSUE_MESSAGE);
            }
        });
    }

    private boolean matches(String fileName) {
        if (pattern == null) {
            pattern = Pattern.compile(format);
        }
        return pattern.matcher(fileName).find();
    }
}
