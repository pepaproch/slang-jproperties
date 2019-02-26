package org.pepaproch.properties.plugin;

import org.pepaproch.properties.parser.slang.PropertiesConverter;
import org.sonar.api.batch.rule.CheckFactory;
import org.sonar.api.batch.rule.Checks;
import org.sonar.api.issue.NoSonarFilter;
import org.sonar.api.measures.FileLinesContextFactory;
import org.sonarsource.slang.api.ASTConverter;
import org.sonarsource.slang.checks.api.SlangCheck;
import org.sonarsource.slang.plugin.SlangSensor;

public class PropertiesSensor extends SlangSensor {


    private final Checks<SlangCheck> checks;

    public PropertiesSensor(CheckFactory checkFactory, NoSonarFilter noSonarFilter, FileLinesContextFactory fileLinesContextFactory, PropertiesLanguage language) {
        super(noSonarFilter, fileLinesContextFactory, language);

        checks = checkFactory.create(PropertiesPlugin.REPOSITORY_KEY);
        checks.addAnnotatedChecks((Iterable<?>) PropertiesCheckList.checks());

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
