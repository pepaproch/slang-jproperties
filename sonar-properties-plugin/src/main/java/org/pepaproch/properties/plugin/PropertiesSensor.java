package org.pepaproch.properties.plugin;

import org.pepaproch.properties.checks.DuplicatedKeysVisitor;
import org.pepaproch.properties.checks.TokenLocations;
import org.pepaproch.properties.parser.slang.PropertiesConverter;
import org.pepaproch.properties.parser.slang.tree.TextRangeUtils;
import org.sonar.api.batch.fs.InputFile;
import org.sonar.api.batch.rule.ActiveRule;
import org.sonar.api.batch.rule.CheckFactory;
import org.sonar.api.batch.rule.Checks;
import org.sonar.api.batch.sensor.SensorContext;
import org.sonar.api.batch.sensor.issue.NewIssue;
import org.sonar.api.batch.sensor.issue.NewIssueLocation;
import org.sonar.api.issue.NoSonarFilter;
import org.sonar.api.measures.FileLinesContextFactory;
import org.sonar.api.rule.RuleKey;
import org.sonar.api.utils.log.Logger;
import org.sonar.api.utils.log.Loggers;
import org.sonarsource.slang.api.ASTConverter;
import org.sonarsource.slang.checks.api.SlangCheck;
import org.sonarsource.slang.plugin.DurationStatistics;
import org.sonarsource.slang.plugin.InputFileContext;
import org.sonarsource.slang.plugin.SlangSensor;
import org.sonarsource.slang.visitors.TreeVisitor;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class PropertiesSensor extends SlangSensor {
    private static final Logger LOG = Loggers.get(PropertiesSensor.class);
    private final PropertiesContext projectContext;
    private final Checks<SlangCheck> checks;
    public SensorContext ctx;
    Consumer<PropertiesContext> f = (ctxp) -> {
        ActiveRule rule = this.ctx.activeRules().find(RuleKey.of(repositoryKey(), "duplicatedCheck"));

        if(rule!=null) {
            String treshold = rule.param("treshold");
            Integer integer = treshold==null ? 1 : Integer.valueOf(treshold);

            Map<String, LinkedList<TokenLocations<InputFile>>> duplications = ctxp.getDuplications().duplications(integer);
            duplications.keySet().stream().forEach((k) -> {
                LOG.info("KEY P: " + k +  " " + duplications.get(k).get(0).module.uri() + " " + duplications.get(k).get(0).location.start().line());
                NewIssue newIssue = this.ctx.newIssue().forRule(rule.ruleKey());
                LinkedList<TokenLocations<InputFile>> tokenLocations = duplications.get(k);
                NewIssueLocation at = newIssue.newLocation().on(tokenLocations.get(0).module).at(TextRangeUtils.sonarTextRange(tokenLocations.get(0).location));
                newIssue.at(at);
                tokenLocations.stream().skip(1).forEach((l) -> {
                    LOG.info("KEY S: " + k +  l.module.uri() + " " + l.location.start().line());
                            //secondary
                            newIssue.addLocation(newIssue.newLocation().on(l.module).at(TextRangeUtils.sonarTextRange(l.location)).message("Key was used before"));

                        }
                );
                newIssue.save();


            });
        }else  {
            LOG.info("Duplicated is not activae");
        }
    };

    public PropertiesSensor(CheckFactory checkFactory, NoSonarFilter noSonarFilter, FileLinesContextFactory fileLinesContextFactory, PropertiesLanguage language) {
        super(noSonarFilter, fileLinesContextFactory, language);
        checks = checkFactory.create(PropertiesPlugin.REPOSITORY_KEY);
        checks.addAnnotatedChecks((Iterable<?>) PropertiesCheckList.checks());
        projectContext = new PropertiesContext(this, f);

    }

    @Override
    public void execute(SensorContext sensorContext) {
        ctx = sensorContext;
        super.execute(sensorContext);
        projectContext.finish();


    }

    @Override
    protected List<TreeVisitor<InputFileContext>> visitors(SensorContext sensorContext, DurationStatistics statistics) {
        List<TreeVisitor<InputFileContext>> visitors = super.visitors(sensorContext, statistics);
        return Collections.unmodifiableList(
                Stream.concat(visitors.stream(),
                        Stream.of(new DuplicatedKeysVisitor(projectContext))).collect(Collectors.toList())
        );


    }

    @Override
    protected ASTConverter astConverter() {

        return PropertiesConverter.create();
    }

    @Override
    protected Checks<SlangCheck> checks() {
        return checks;
    }

    @Override
    protected String repositoryKey() {
        return PropertiesPlugin.REPOSITORY_KEY;
    }


}
