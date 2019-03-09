package org.pepaproch.properties.plugin;

import org.pepaproch.properties.checks.TokenLocations;
import org.pepaproch.properties.parser.slang.tree.TextRangeUtils;
import org.sonar.api.batch.fs.InputFile;
import org.sonar.api.batch.rule.ActiveRule;
import org.sonar.api.batch.sensor.SensorContext;
import org.sonar.api.batch.sensor.issue.NewIssue;
import org.sonar.api.batch.sensor.issue.NewIssueLocation;
import org.sonar.api.rule.RuleKey;

import java.util.LinkedList;
import java.util.Map;
import java.util.function.Consumer;

public class ProjectContextConsumer implements Consumer<PropertiesContext> {


    private final SensorContext ctx;

    public ProjectContextConsumer(SensorContext ctx) {
        this.ctx = ctx;
    }

    @Override
    public void accept(PropertiesContext propertiesContext) {
        ActiveRule rule = this.ctx.activeRules().find(RuleKey.of(propertiesContext.propertiesSensor.repositoryKey(), "duplicatedCheck"));

        if (rule != null) {
            String treshold = rule.param("treshold");
            Integer integer = treshold == null ? 1 : Integer.valueOf(treshold);

            Map<String, LinkedList<TokenLocations<InputFile>>> duplications = propertiesContext.getDuplications().duplications(integer);
            duplications.keySet().stream().forEach((k) -> {
                NewIssue newIssue = this.ctx.newIssue().forRule(rule.ruleKey());
                LinkedList<TokenLocations<InputFile>> tokenLocations = duplications.get(k);
                NewIssueLocation at = newIssue.newLocation().on(tokenLocations.get(0).module).at(TextRangeUtils.sonarTextRange(tokenLocations.get(0).location));
                newIssue.at(at);
                tokenLocations.stream().skip(1).forEach((l) -> {
                            //secondary
                            newIssue.addLocation(newIssue.newLocation().on(l.module).at(TextRangeUtils.sonarTextRange(l.location)).message("Key was used before"));

                        }
                );
                newIssue.save();


            });
        } else {
          //  LOG.debug("Duplicated is not activated");
        }
    }
}
