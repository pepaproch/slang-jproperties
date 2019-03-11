package org.pepaproch.properties.checks;

import org.pepaproch.properties.parser.slang.tree.PropsTree;
import org.sonar.check.Rule;
import org.sonar.check.RuleProperty;
import org.sonarsource.slang.checks.api.InitContext;
import org.sonarsource.slang.checks.api.SlangCheck;

import java.util.regex.Pattern;

@Rule(key = "comment-regular-expression")
@RuleTemplate
public class CommentRegularExpressionCheck implements SlangCheck {

    private static final String DEFAULT_REGULAR_EXPRESSION = ".";

    private static final String DEFAULT_MESSAGE = "Comment contains this disallowed pattern \"%s\".";


    @RuleProperty(
            key = "regularExpression",
            description = "The regular expression to check in comments.",
            defaultValue = DEFAULT_REGULAR_EXPRESSION)
    public String regularExpression = DEFAULT_REGULAR_EXPRESSION;

    @RuleProperty(
            key = "message",
            description = "The issue message",
            defaultValue = DEFAULT_MESSAGE)
    public String message = DEFAULT_MESSAGE;

    @Override
    public void initialize(InitContext init) {
        Pattern pattern;
        pattern = Pattern.compile(regularExpression);
        init.register(PropsTree.class, (ctx, tree) -> tree.metaData().commentsInside().stream()
                .filter(c -> pattern.matcher(c.text()).find()).forEach(c ->
                        ctx.reportIssue(c, String.format(message, pattern.pattern()))
                ));
    }
}
