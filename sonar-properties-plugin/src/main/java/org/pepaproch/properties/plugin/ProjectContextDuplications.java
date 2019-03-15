package org.pepaproch.properties.plugin;

import org.pepaproch.properties.checks.DuplicationExtractor;
import org.pepaproch.properties.checks.TokenLocations;
import org.pepaproch.properties.parser.slang.tree.TextRangeUtils;
import org.sonar.api.batch.fs.InputFile;
import org.sonar.api.batch.rule.ActiveRule;
import org.sonar.api.batch.sensor.SensorContext;
import org.sonar.api.batch.sensor.issue.NewIssue;
import org.sonar.api.rule.RuleKey;
import org.sonar.api.utils.log.Logger;
import org.sonar.api.utils.log.Loggers;

import java.util.LinkedList;
import java.util.Map;
import java.util.function.Consumer;

public class ProjectContextDuplications implements Consumer<PropertiesContext> {
    private static final Logger LOG = Loggers.get(ProjectContextDuplications.class);

    private final SensorContext ctx;



    public ProjectContextDuplications(SensorContext ctx) {
        this.ctx = ctx;
    }

    @Override
    public void accept(PropertiesContext propertiesContext) {

        ActiveRule rule = this.ctx.activeRules().find(RuleKey.of(propertiesContext.propertiesSensor.repositoryKey(), "duplicatedCheck"));

        if (rule != null) {
            String treshold = rule.param("treshold");
            Integer integer = treshold == null ? 1 : Integer.valueOf(treshold);
            DuplicationExtractor<InputFile> inputFileDuplicationExtractor = new DuplicationExtractor<>(propertiesContext.getProjectProperties().getProps());
            Map<String, LinkedList<TokenLocations<InputFile>>> duplications = inputFileDuplicationExtractor.duplications(integer);
            duplications.keySet().forEach(k -> {
                NewIssue newIssue = this.ctx.newIssue().forRule(rule.ruleKey());
                LinkedList<TokenLocations<InputFile>> tokenLocations = duplications.get(k);
                newIssue.at(newIssue.newLocation().on(tokenLocations.get(0).module).at(TextRangeUtils.sonarTextRange(tokenLocations.get(0).location)));
                //secondary issues
                tokenLocations.stream().skip(1).forEach(l ->
                        newIssue.addLocation(newIssue.newLocation().on(l.module).at(TextRangeUtils.sonarTextRange(l.location)).message("Key was used before"))
                );
                newIssue.save();


            });
        } else {
              LOG.debug("Duplicated string literal is not activated");
        }

    }
}
