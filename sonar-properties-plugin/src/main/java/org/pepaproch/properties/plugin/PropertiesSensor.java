package org.pepaproch.properties.plugin;

import org.pepaproch.properties.checks.project.ProjectCheck;
import org.pepaproch.properties.parser.slang.PropertiesConverter;
import org.sonar.api.batch.rule.CheckFactory;
import org.sonar.api.batch.rule.Checks;
import org.sonar.api.batch.sensor.SensorContext;
import org.sonar.api.issue.NoSonarFilter;
import org.sonar.api.measures.FileLinesContextFactory;
import org.sonarsource.slang.api.ASTConverter;
import org.sonarsource.slang.api.CodeVerifier;
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
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class PropertiesSensor extends SlangSensor {
    private final PostAnalyseContext projectContext;
    private final Checks<SlangCheck> checks;
    private final Checks<ProjectCheck> pchecks;

    public PropertiesSensor(CheckFactory checkFactory, NoSonarFilter noSonarFilter, FileLinesContextFactory fileLinesContextFactory, PropertiesLanguage language) {
        super(noSonarFilter, fileLinesContextFactory, language);
        checks = checkFactory.create(PropertiesPlugin.REPOSITORY_KEY);
        this.checks.addAnnotatedChecks(    ( Iterable<Class> )  PropertiesCheckList.checks(PropertiesCheckList.FILTER_SPECIAL_INIT, SlangCheck.class::isAssignableFrom));
        this.pchecks = checkFactory.create(PropertiesPlugin.REPOSITORY_KEY);
        this.pchecks.addAnnotatedChecks(( Iterable<Class> ) PropertiesCheckList.checks(ProjectCheck.class::isAssignableFrom));
        this.checks.addAnnotatedChecks(new CommentedCodeCheck(new PropertiesCodeVerifier()));
        projectContext = new PostAnalyseContext(this);


    }

    @Override
    public void execute(SensorContext sensorContext) {
        super.execute(sensorContext);
        List<Consumer<PostAnalyseContext>> consumers = Collections.unmodifiableList(Arrays.asList( new PostAnalyseCheks(sensorContext)));
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

    public Checks<ProjectCheck> pchecks() {
        return this.pchecks;
    }

    @Override
    public String repositoryKey() {
        return PropertiesPlugin.REPOSITORY_KEY;
    }

    class PropertiesCodeVerifier implements CodeVerifier {

        private final Pattern commentedOutCodePattern = Pattern
                .compile("^(#|!){1}[ \\t\\x0B\\f]*(?!(?i)todo)(?!(?i)fixme)([^=:\\s]|(?<=\\\\)\\ |(?<=\\\\)\\=|(?<=\\\\)\\:)+[ \\t\\x0B\\f]*(:|=){1}.*$");

        @Override
        public boolean containsCode(String content) {
            return commentedOutCodePattern.matcher(content).find();
        }
    }
}
