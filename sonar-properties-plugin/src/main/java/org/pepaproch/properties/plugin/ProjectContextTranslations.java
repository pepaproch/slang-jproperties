package org.pepaproch.properties.plugin;

import org.pepaproch.properties.checks.project.LangBundlesExtractor;
import org.pepaproch.properties.checks.project.MissingDefaultBundleCheck;
import org.pepaproch.properties.parser.slang.tree.PropTree;
import org.sonar.api.batch.fs.InputFile;
import org.sonar.api.batch.rule.ActiveRule;
import org.sonar.api.batch.rule.ActiveRules;
import org.sonar.api.batch.sensor.SensorContext;
import org.sonar.api.batch.sensor.issue.NewIssue;
import org.sonar.api.rule.RuleKey;
import org.sonar.api.utils.log.Logger;
import org.sonar.api.utils.log.Loggers;

import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

public class ProjectContextTranslations implements Consumer<PropertiesContext> {
    private static final Logger LOG = Loggers.get(ProjectContextTranslations.class);
    private final SensorContext ctx;

    ActiveRules activeRules = null;

    public ProjectContextTranslations(SensorContext ctx) {
        this.ctx = ctx;
    }

    @Override
    public void accept(PropertiesContext propertiesContext) {

        LangBundlesExtractor langBundlesExtractor = new LangBundlesExtractor(propertiesContext.getProjectProperties().getProps());
        Map<InputFile, Map<InputFile, List<PropTree>>> bundles = langBundlesExtractor.getBundles();
        processMissingDefault(bundles , propertiesContext);


    }


    private void processMissingDefault(Map<InputFile, Map<InputFile, List<PropTree>>> bundles, PropertiesContext propertiesContext) {

        ActiveRule rule = this.ctx.activeRules().find(RuleKey.of(PropertiesPlugin.REPOSITORY_KEY, "missing-default-bundle"));
        if(null!=rule) {
            bundles.forEach((f, b) -> {
                if (!isDefaultBundleName(f)) {
                    b.keySet().forEach(k -> {
                        NewIssue newIssue = ctx.newIssue().forRule(rule.ruleKey());
                        newIssue.at(newIssue.newLocation().on(k).message(String.format(MissingDefaultBundleCheck.ISSUE_MESSAGE, defaultFileName(k))));
                        newIssue.save();

                    });
                }

            });

        }
    }


    private boolean isDefaultBundleName(InputFile f) {
        int i = f.filename().lastIndexOf('/');
        String substring = f.filename().substring(0, i == -1 ? f.filename().length() : i);
        return !substring.contains("_");
    }

    private String defaultFileName(InputFile file) {
        int i = file.filename().lastIndexOf("_");
        return file.filename().substring(0, i == -1 ? file.filename().length() : i);
    }


}

