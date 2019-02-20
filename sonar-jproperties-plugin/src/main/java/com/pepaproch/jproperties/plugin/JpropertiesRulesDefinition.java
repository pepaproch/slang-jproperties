package com.pepaproch.jproperties.plugin;


import org.sonar.api.server.rule.RulesDefinition;
import org.sonarsource.analyzer.commons.RuleMetadataLoader;

import java.util.List;

public class JpropertiesRulesDefinition implements RulesDefinition {
    public static final String RESOURCE_FOLDER = "org/sonar/l10n/jproperties";

    @Override
    public void define(Context context) {
        NewRepository repository = context.createRepository(JpropertiesPlugin.REPOSITORY_KEY, JpropertiesLanguage.JPROPERTIES_LANG_NAME).setName(JpropertiesPlugin.REPOSITORY_KEY);
        RuleMetadataLoader ruleMetadataLoader = new RuleMetadataLoader(RESOURCE_FOLDER);
        List<Class> checks = JpropertiesCheckList.checks();
        ruleMetadataLoader.addRulesByAnnotatedClass(repository, checks);
        repository.done();
    }
}
