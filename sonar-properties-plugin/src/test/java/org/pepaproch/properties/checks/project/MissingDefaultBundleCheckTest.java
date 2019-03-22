package org.pepaproch.properties.checks.project;

import org.junit.Test;
import org.pepaproch.properties.parser.SensorTestBase;
import org.sonar.api.batch.rule.CheckFactory;

import static org.junit.Assert.assertEquals;

public class MissingDefaultBundleCheckTest  extends SensorTestBase {



    @Test
    public void testMissing() {
        CheckFactory f = checkFactory("missing-default-bundle");
        addFile("lang_cz.properties", "key=value\n");
        addFile("lang_en.properties", "key=value\n");

        sensor(f).execute(context);
        assertEquals(2, context.allIssues().size());
    }


    @Test
    public void testPresent() {
        CheckFactory f = checkFactory("missing-default-bundle");
        addFile("lang.properties", "key=value\n");
        addFile("lang_cz.properties", "key=value\n");
        addFile("lang_en.properties", "key=value\n");

        sensor(f).execute(context);
        assertEquals(0, context.allIssues().size());
    }

}