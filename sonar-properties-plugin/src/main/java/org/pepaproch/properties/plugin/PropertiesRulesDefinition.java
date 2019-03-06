package org.pepaproch.properties.plugin;


import org.sonar.api.SonarRuntime;
import org.sonar.api.server.rule.RulesDefinition;
import org.sonarsource.analyzer.commons.RuleMetadataLoader;

public class PropertiesRulesDefinition implements RulesDefinition {
    public static final String RESOURCE_FOLDER = "org/sonar/l10n/properties";
    private final SonarRuntime sonarRuntime;

    public PropertiesRulesDefinition(SonarRuntime sonarRuntime) {
        this.sonarRuntime = sonarRuntime;
    }

    @Override
    public void define(Context context) {
        NewRepository repository = context.createRepository(PropertiesPlugin.REPOSITORY_KEY, PropertiesLanguage.JPROPERTIES_LANG_NAME).setName(PropertiesPlugin.REPOSITORY_KEY);
        RuleMetadataLoader ruleMetadataLoader = new RuleMetadataLoader(RESOURCE_FOLDER, PropertiesProfileDefinition.PROFILE_PATH, sonarRuntime);
        ruleMetadataLoader.addRulesByAnnotatedClass(repository, PropertiesCheckList.checks());
        ruleMetadataLoader.addRulesByAnnotatedClass(repository,  PropertiesCheckList.visitors());
        repository.done();
    }
}
