package org.pepaproch.properties.checks;

import org.pepaproch.properties.parser.slang.tree.PropsTree;
import org.sonar.check.Rule;
import org.sonarsource.slang.checks.api.InitContext;
import org.sonarsource.slang.checks.api.SlangCheck;

@Rule(key="no-properties")
public class NoPropertiesCheck implements SlangCheck {

    private  static final String ISSUE_MESSAGE = "Remove this file that does not define any properties";

    @Override
    public void initialize(InitContext init) {
        init.register(PropsTree.class, (ctx, tree) -> {
            if (null == tree.declarations()|| tree.declarations().isEmpty()) {
                ctx.reportIssue(tree, ISSUE_MESSAGE);
            }
        });
    }
}
