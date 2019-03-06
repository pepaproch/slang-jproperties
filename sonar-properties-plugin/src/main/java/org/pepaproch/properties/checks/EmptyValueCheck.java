package org.pepaproch.properties.checks;

import org.pepaproch.properties.parser.slang.tree.PropTree;
import org.sonar.check.Rule;
import org.sonarsource.slang.checks.api.InitContext;
import org.sonarsource.slang.checks.api.SlangCheck;

@Rule(key="emptyValueCheck")
public class EmptyValueCheck implements SlangCheck {

    private final String ISSUE_MESAGE = "Property with empty value should be removed";

    @Override
    public void initialize(InitContext init) {
        init.register(PropTree.class, (ctx, tree) -> {
            if (null == tree.value || tree.value.content().isEmpty()) {
                ctx.reportIssue(tree.key.metaData().tokens().get(0), ISSUE_MESAGE);

            }
        });
    }
}
