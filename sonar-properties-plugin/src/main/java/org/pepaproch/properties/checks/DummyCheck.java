package org.pepaproch.properties.checks;

import org.pepaproch.properties.parser.slang.tree.PropsTree;
import org.sonar.check.Rule;
import org.sonarsource.slang.checks.api.CheckContext;
import org.sonarsource.slang.checks.api.InitContext;
import org.sonarsource.slang.checks.api.SlangCheck;

import java.util.function.BiConsumer;

@Rule(key = "dummyCheck")
public class DummyCheck implements SlangCheck {


    BiConsumer<CheckContext, PropsTree> check = (CheckContext ctx, PropsTree tree) -> {
        tree.getChildren().stream().forEach(p -> {
            if (p.key.value().equalsIgnoreCase("dummy")) {
                ctx.reportIssue(p.key.textRange(), "Dummy issue detected");
            }
        });
    };

    @Override
    public void initialize(InitContext init) {
        init.register(PropsTree.class, check);
    }
}
