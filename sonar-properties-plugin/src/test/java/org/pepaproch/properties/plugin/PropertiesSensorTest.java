package org.pepaproch.properties.plugin;


import org.sonar.api.batch.rule.CheckFactory;
import org.sonar.api.batch.rule.internal.ActiveRulesBuilder;
import org.sonar.api.batch.rule.internal.NewActiveRule;
import org.sonar.api.config.internal.MapSettings;
import org.sonar.api.resources.Language;
import org.sonar.api.rule.RuleKey;
import org.sonarsource.slang.testing.AbstractSensorTest;

import java.util.HashMap;
import java.util.Map;


public class PropertiesSensorTest extends AbstractSensorTest {




    @Override
    protected String repositoryKey() {
        return PropertiesPlugin.REPOSITORY_KEY;
    }

    @Override
    protected Language language() {
        return new PropertiesLanguage(new MapSettings().asConfig());
    }

    protected CheckFactory checkFactory(String ruleKey, Map<String, String> setting) {
        ActiveRulesBuilder builder = new ActiveRulesBuilder();
        NewActiveRule newActiveRule = builder.create(RuleKey.of(repositoryKey(), ruleKey))
                .setName(ruleKey);
        setting.keySet().stream().forEach((k) -> {
            newActiveRule.setParam(k, setting.get(k));
        });

        newActiveRule.activate();
        context.setActiveRules(builder.build());
        return new CheckFactory(context.activeRules());
    }

    protected CheckFactory checkFactory(String ruleKey, String paramName, String paramValue) {
        Map<String, String> m = new HashMap() {{
            put(paramName, paramValue);
        }};
        return checkFactory(ruleKey, m);
    }


}