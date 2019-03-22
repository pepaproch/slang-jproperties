package org.pepaproch.properties.checks;

import org.pepaproch.properties.parser.slang.tree.PropKeyTree;
import org.sonar.check.Rule;
import org.sonar.check.RuleProperty;
import org.sonarsource.slang.checks.api.InitContext;
import org.sonarsource.slang.checks.api.SlangCheck;

import java.util.regex.Pattern;

@Rule(key="key-regular-expression")
@RuleTemplate
public class KeyRegularExpressionCheck implements SlangCheck {

    private static final String DEFAULT_REGULAR_EXPRESSION = ".*";
    private static final String DEFAULT_MESSAGE = "The regular expression \"%s\" matches this key.";


    @RuleProperty(
            key = "regularExpression",
            description = "The regular expression. See http://docs.oracle.com/javase/8/docs/api/java/util/regex/Pattern.html  for detailed regular expression syntax.",
            defaultValue = DEFAULT_REGULAR_EXPRESSION)
    public String regularExpression = DEFAULT_REGULAR_EXPRESSION;

    @Override
    public void initialize(InitContext init) {
      Pattern  pattern = Pattern.compile(regularExpression);
        init.register(PropKeyTree.class, (ctx,tree)->{
            if(pattern.matcher(tree.value()).find()) {
                ctx.reportIssue(tree,String.format(DEFAULT_MESSAGE, pattern.pattern()));
            }
        });

    }
}
