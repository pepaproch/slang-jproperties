package com.pepaproch.jproperties.checks;

import com.pepaproch.jproperties.parser.PropertiesTree;
import org.sonar.check.Rule;
import org.sonarsource.slang.checks.api.CheckContext;
import org.sonarsource.slang.checks.api.InitContext;
import org.sonarsource.slang.checks.api.SlangCheck;

import java.util.function.BiConsumer;

@Rule(key = "DummyCheck")
public class DummyCheck implements SlangCheck {


    BiConsumer<CheckContext, PropertiesTree> check = (CheckContext ctx, PropertiesTree tree) -> {
        tree.getChildren().stream().forEach(p -> {
            if (p.key.value().equalsIgnoreCase("dummy")) {
                ctx.reportIssue(p.key.textRange(), "Dummy issue detected");
            }
        });
    };

    @Override
    public void initialize(InitContext init) {
        init.register(PropertiesTree.class, check);
    }
}
