package org.pepaproch.jproperties.plugin;


import org.junit.Test;
import org.sonar.api.batch.fs.InputFile;
import org.sonar.api.batch.rule.CheckFactory;
import org.sonar.api.batch.sensor.Sensor;
import org.sonar.api.batch.sensor.issue.Issue;
import org.sonar.api.config.internal.MapSettings;
import org.sonar.api.issue.NoSonarFilter;
import org.sonar.api.resources.Language;
import org.sonarsource.slang.testing.AbstractSensorTest;

import java.util.Collection;

import static org.assertj.core.api.Assertions.assertThat;


public class JpropertiesSensorTest extends AbstractSensorTest {

    @Test
    public void testDummyRule() {
        InputFile f = createInputFile("dummy_file" + JpropertiesLanguage.JPROPERTIES_FILE_SUFFIXES_DEFAULT_VALUE,
                "dummy=dummykey\n" +
                        "notdummykey=77.75.77.53");
        context.fileSystem().add(f);
        CheckFactory checkFactory = checkFactory("dummyCheck", "S1313");

        Sensor jpropertiesSensor = new JpropertiesSensor(checkFactory, new NoSonarFilter(), fileLinesContextFactory, new JpropertiesLanguage(new MapSettings().asConfig()));
        jpropertiesSensor.execute(context);
        Collection<Issue> issues = context.allIssues();
        assertThat(issues).hasSize(2);

    }

    @Test
    public void testOnlyComments() {
        InputFile f = createInputFile("onlycomments_file" + JpropertiesLanguage.JPROPERTIES_FILE_SUFFIXES_DEFAULT_VALUE,
                "#\n" +
                        "# Copyright (C) 2009-2018 Lightbend Inc. <https://www.lightbend.com>\n" +
                        "#");
        context.fileSystem().add(f);
        CheckFactory checkFactory = checkFactory("dummyCheck", "S1313");

        Sensor jpropertiesSensor = new JpropertiesSensor(checkFactory, new NoSonarFilter(), fileLinesContextFactory, new JpropertiesLanguage(new MapSettings().asConfig()));
        jpropertiesSensor.execute(context);
        Collection<Issue> issues = context.allIssues();

        assertThat(issues).hasSize(0);

    }


    @Test
    public void testError() {
        InputFile f = createInputFile("onlycomments_file" + JpropertiesLanguage.JPROPERTIES_FILE_SUFFIXES_DEFAULT_VALUE,
                parseError);
        context.fileSystem().add(f);
        CheckFactory checkFactory = checkFactory("dummyCheck", "S1313");

        Sensor jpropertiesSensor = new JpropertiesSensor(checkFactory, new NoSonarFilter(), fileLinesContextFactory, new JpropertiesLanguage(new MapSettings().asConfig()));
        jpropertiesSensor.execute(context);
        Collection<Issue> issues = context.allIssues();

        assertThat(issues).hasSize(0);

    }

 String parseError = "#\n" +
         "# Copyright (C) 2009-2018 Lightbend Inc. <https://www.lightbend.com>\n" +
         "#";


    @Override
    protected String repositoryKey() {
        return JpropertiesPlugin.REPOSITORY_KEY;
    }

    @Override
    protected Language language() {
        return new JpropertiesLanguage(new MapSettings().asConfig());
    }
}