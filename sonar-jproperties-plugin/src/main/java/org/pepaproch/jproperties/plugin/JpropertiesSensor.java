package org.pepaproch.jproperties.plugin;

import org.pepaproch.jproperties.parser.slang.JpropertiesConverter;
import org.sonar.api.batch.rule.CheckFactory;
import org.sonar.api.batch.rule.Checks;
import org.sonar.api.issue.NoSonarFilter;
import org.sonar.api.measures.FileLinesContextFactory;
import org.sonarsource.slang.api.ASTConverter;
import org.sonarsource.slang.checks.api.SlangCheck;
import org.sonarsource.slang.plugin.SlangSensor;

public class JpropertiesSensor extends SlangSensor {


    private final Checks<SlangCheck> checks;

    public JpropertiesSensor(CheckFactory checkFactory, NoSonarFilter noSonarFilter, FileLinesContextFactory fileLinesContextFactory, JpropertiesLanguage language) {
        super(noSonarFilter, fileLinesContextFactory, language);

        checks = checkFactory.create(JpropertiesPlugin.REPOSITORY_KEY);
        checks.addAnnotatedChecks((Iterable<?>) JpropertiesCheckList.checks());

    }

    @Override
    protected ASTConverter astConverter() {

        return JpropertiesConverter.create();
    }

    @Override
    protected Checks<SlangCheck> checks() {
        return checks;
    }

    @Override
    protected String repositoryKey() {
        return JpropertiesPlugin.REPOSITORY_KEY;
    }
}
