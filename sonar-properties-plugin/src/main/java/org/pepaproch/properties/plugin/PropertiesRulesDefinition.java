package org.pepaproch.properties.plugin;


import org.pepaproch.properties.checks.CheckList;
import org.pepaproch.properties.checks.RuleTemplate;
import org.sonar.api.SonarRuntime;
import org.sonar.api.server.rule.RulesDefinition;
import org.sonarsource.analyzer.commons.RuleMetadataLoader;

import java.util.List;

public class PropertiesRulesDefinition implements RulesDefinition {
    public static final String RESOURCE_FOLDER = "org/sonar/l10n/properties";
    private final SonarRuntime sonarRuntime;

    public PropertiesRulesDefinition(SonarRuntime sonarRuntime) {
        this.sonarRuntime = sonarRuntime;
    }

    @Override
    public void define(Context context) {
        NewRepository repository = context.createRepository(PropertiesPlugin.REPOSITORY_KEY, PropertiesLanguage.JPROPERTIES_LANG_NAME).setName(PropertiesPlugin.REPOSITORY_KEY);

        RuleMetadataLoader ruleMetadataLoader = new RMetadataLoader(RESOURCE_FOLDER, PropertiesProfileDefinition.PROFILE_PATH, sonarRuntime);
        ruleMetadataLoader.addRulesByAnnotatedClass(repository, CheckList.checks());
        repository.done();
    }

    private class RMetadataLoader extends  RuleMetadataLoader {
        @Override
        public void addRulesByAnnotatedClass(NewRepository repository, List<Class> ruleClasses) {
            super.addRulesByAnnotatedClass(repository, ruleClasses);
            ruleClasses.stream().filter(c -> c.isAnnotationPresent(RuleTemplate.class)).forEach(c-> {
                org.sonar.check.Rule ruleAnnotation  = (org.sonar.check.Rule) c.getAnnotation(org.sonar.check.Rule .class);
                NewRule rule = repository.rule(ruleAnnotation.key());
                rule.setTemplate(true);
            });
        }

        public RMetadataLoader(String resourceFolder, String defaultProfilePath, SonarRuntime sonarRuntime) {
            super(resourceFolder, defaultProfilePath, sonarRuntime);
        }
    }
}
