package org.pepaproch.properties.checks;

import org.junit.Test;
import org.pepaproch.properties.parser.SensorTestBase;
import org.pepaproch.properties.plugin.PropertiesLanguage;
import org.pepaproch.properties.plugin.PropertiesSensor;
import org.sonar.api.batch.fs.InputFile;
import org.sonar.api.batch.rule.CheckFactory;
import org.sonar.api.batch.sensor.Sensor;
import org.sonar.api.batch.sensor.issue.Issue;
import org.sonar.api.config.internal.MapSettings;
import org.sonar.api.issue.NoSonarFilter;

import java.util.Collection;

import static org.assertj.core.api.Assertions.assertThat;



public class DuplicatedProjectPropertiesVisitorTest extends SensorTestBase {

    @Test
    public void testProjectDuplicatedPropsInProject() {

        InputFile f = createInputFile("sub/onlycomments_file2" + PropertiesLanguage.JPROPERTIES_FILE_SUFFIXES_DEFAULT_VALUE,
                "dummy=dummykey\n" +
                        "notdummykey=77.75.77.53");
        context.fileSystem().add(f);

        InputFile f1 = createInputFile("onlycomments_file1" + PropertiesLanguage.JPROPERTIES_FILE_SUFFIXES_DEFAULT_VALUE,
                "dummy=dummykey\n" +
                        "notdummykey=77.75.77.53");
        context.fileSystem().add(f1);

        CheckFactory checkFactory = checkFactory("duplicated-keys-across-project", "treshold", "1");


        Sensor jpropertiesSensor = new PropertiesSensor(checkFactory, new NoSonarFilter(), fileLinesContextFactory, new PropertiesLanguage(new MapSettings().asConfig()));
        jpropertiesSensor.execute(context);
        Collection<Issue> issues = context.allIssues();
        assertThat(issues).hasSize(2);


    }


}