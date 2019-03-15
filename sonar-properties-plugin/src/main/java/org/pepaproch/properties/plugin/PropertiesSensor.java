package org.pepaproch.properties.plugin;

import org.pepaproch.properties.checks.project.ProjectPropertiesVisitor;
import org.pepaproch.properties.parser.slang.PropertiesConverter;
import org.sonar.api.batch.rule.CheckFactory;
import org.sonar.api.batch.rule.Checks;
import org.sonar.api.batch.sensor.SensorContext;
import org.sonar.api.issue.NoSonarFilter;
import org.sonar.api.measures.FileLinesContextFactory;
import org.sonar.api.utils.log.Logger;
import org.sonar.api.utils.log.Loggers;
import org.sonarsource.slang.api.ASTConverter;
import org.sonarsource.slang.checks.CommentedCodeCheck;
import org.sonarsource.slang.checks.api.SlangCheck;
import org.sonarsource.slang.plugin.DurationStatistics;
import org.sonarsource.slang.plugin.InputFileContext;
import org.sonarsource.slang.plugin.SlangSensor;
import org.sonarsource.slang.visitors.TreeVisitor;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class PropertiesSensor extends SlangSensor {
    private static final Logger LOG = Loggers.get(PropertiesSensor.class);
    private final PropertiesContext projectContext;
    private final Checks<SlangCheck> checks;

    public PropertiesSensor(CheckFactory checkFactory, NoSonarFilter noSonarFilter, FileLinesContextFactory fileLinesContextFactory, PropertiesLanguage language) {
        super(noSonarFilter, fileLinesContextFactory, language);
        checks = checkFactory.create(PropertiesPlugin.REPOSITORY_KEY);
        Iterable<?> checks = PropertiesCheckList.checks(PropertiesCheckList.FILTER_SPECIAL_INIT,
                c -> SlangCheck.class.isAssignableFrom(c));
        this.checks.addAnnotatedChecks(checks);
        this.checks.addAnnotatedChecks(new CommentedCodeCheck(new PropertiesCodeVerifier()));
        projectContext = new PropertiesContext(this);


    }

    @Override
    public void execute(SensorContext sensorContext) {
        super.execute(sensorContext);
        List<Consumer<PropertiesContext>> consumers = Collections.unmodifiableList(Arrays.asList(new ProjectContextDuplications(sensorContext), new ProjectContextTranslations(sensorContext)));
        projectContext.finish(consumers);
    }

    @Override
    protected List<TreeVisitor<InputFileContext>> visitors(SensorContext sensorContext, DurationStatistics statistics) {
        List<TreeVisitor<InputFileContext>> visitors = super.visitors(sensorContext, statistics);
        return Collections.unmodifiableList(
                Stream.concat(visitors.stream(),
                        Stream.of(new ProjectPropertiesVisitor(projectContext))).collect(Collectors.toList())
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
