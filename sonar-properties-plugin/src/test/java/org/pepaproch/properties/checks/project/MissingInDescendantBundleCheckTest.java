package org.pepaproch.properties.checks.project;

import org.junit.Test;
import org.pepaproch.properties.parser.SensorTestBase;
import org.sonar.api.batch.rule.CheckFactory;

import static org.junit.Assert.assertEquals;

public class MissingInDescendantBundleCheckTest extends SensorTestBase {
    @Test
    public void testMissing() {
        CheckFactory f = checkFactory("missing-in-descendant-bundle");
        addFile("lang.properties", "keyf=default");
        addFile("lang_cz.properties", "key=cz\n");
        addFile("lang_en.properties", "key=en\nkeyd=jkio\"");

        sensor(f).execute(context);
        assertEquals(2, context.allIssues().size());
    }


    @Test
    public void testNotMissing() {
        CheckFactory f = checkFactory("missing-in-descendant-bundle");
        addFile("lang.properties", "key=default\n");
        addFile("lang_cz.properties", "key=cz\n");
        addFile("lang_en.properties", "key=en\n");

        sensor(f).execute(context);
        assertEquals(0, context.allIssues().size());
    }
}