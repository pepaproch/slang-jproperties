package org.pepaproch.properties.checks;

import org.pepaproch.properties.parser.slang.tree.PropTree;
import org.sonar.check.Rule;
import org.sonar.check.RuleProperty;
import org.sonarsource.slang.checks.api.InitContext;
import org.sonarsource.slang.checks.api.SlangCheck;

import java.util.function.Predicate;

@Rule(key = "separator-convention")
public class SeparatorConventionCheck implements SlangCheck {
    private static final String ISSUE_MESSAGE_NOTALLOWED_SEP = "Use %s to separate key and value.";
    private static final String ISSUE_MESSAGE_REMOWE_WH = "Remove whitespace after and before separator";
    private static final String ISSUE_MESSAGE_COLON_CONVENTION = "Do no use whitespaces before separator and use exactly one whitespace after the separator";

    private static final String DEFAULT_FORMAT = "=";

    @RuleProperty(
            key = "Separator",
            description = "Allowed values: ':', '='",
            defaultValue = DEFAULT_FORMAT)
    private String separator = DEFAULT_FORMAT;
    private Predicate<PropTree> hasWhitespaceBeforeSep = (PropTree tree) -> tree.key != null && tree.separator.textRange().start().lineOffset() != tree.key.textRange().end().lineOffset();
    private Predicate<PropTree> hasWhitespaceAfterSep = (PropTree tree) -> tree.value != null && tree.value.textRange().start().lineOffset() != tree.separator.textRange().end().lineOffset();

    @Override
    public void initialize(InitContext init) {
        init.register(PropTree.class, (ctx, tree) -> {


            if (!this.separator.equalsIgnoreCase(tree.separator.getToken().text())) {
                ctx.reportIssue(tree.separator, String.format(ISSUE_MESSAGE_NOTALLOWED_SEP, this.separator));
                return;
            }

            if (this.separator.equalsIgnoreCase("=") && hasWhitespaceBeforeSep.or(hasWhitespaceAfterSep).test(tree)) {
                ctx.reportIssue(tree.separator, ISSUE_MESSAGE_REMOWE_WH);
            }

            if (this.separator.equalsIgnoreCase(":") && (hasWhitespaceBeforeSep.test(tree) || !hasWhitespaceAfterSep.test(tree))) {
                ctx.reportIssue(tree.separator, ISSUE_MESSAGE_COLON_CONVENTION);
            }

        });
    }


}
