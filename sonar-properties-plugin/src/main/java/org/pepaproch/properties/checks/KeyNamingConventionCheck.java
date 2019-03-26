package org.pepaproch.properties.checks;

import org.sonar.check.Rule;
import org.sonar.check.RuleProperty;
import org.sonarsource.slang.api.IdentifierTree;
import org.sonarsource.slang.checks.api.InitContext;
import org.sonarsource.slang.checks.api.SlangCheck;

import java.util.regex.Pattern;

@Rule(key="key-naming-convention")
public class KeyNamingConventionCheck implements SlangCheck {

    private static final String DEFAULT_FORMAT = "^[a-zA-Z0-9\\.]+[a-zA-Z0-9]$";
    private static final String ISSUE_MESSAGE =   "Rename key \"%s\" to match the regular expression: %s";


    @RuleProperty(
            key = "regularExpression",
            description = "The regular expression. See http://docs.oracle.com/javase/8/docs/api/java/util/regex/Pattern.html  for detailed regular expression syntax.",
            defaultValue = DEFAULT_FORMAT)
    public String regularExpression = DEFAULT_FORMAT;

    @Override
    public void initialize(InitContext init) {
      Pattern  pattern = Pattern.compile(regularExpression);
        init.register(IdentifierTree.class, (ctx, tree)->{
            if(!pattern.matcher(tree.identifier()).find()) {
                ctx.reportIssue(tree,String.format(ISSUE_MESSAGE, tree.identifier(), pattern.pattern()));
            }
        });

    }
}
