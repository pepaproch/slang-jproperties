package org.pepaproch.properties.checks;

import org.junit.Test;
import org.pepaproch.properties.parser.SensorTestBase;
import org.sonar.api.batch.rule.CheckFactory;

import static org.junit.Assert.assertEquals;

public class KeyNamingConventionCheckTest extends SensorTestBase {
    @Test
    public void testOK() {
        CheckFactory f = checkFactory("key-naming-convention");

        addFile("ip.properties", "key=value\n\r" +
                "key=\n" +
                "key2=val");
        sensor(f).execute(context);
        assertEquals(0, context.allIssues().size());

    }

    @Test
    public void testIssue() {
        CheckFactory f = checkFactory("key-naming-convention");

        addFile("ip.properties", "key_11=value\n\r" +
                "key=\n" +
                "key2=val");
        sensor(f).execute(context);
        assertEquals(1, context.allIssues().size());

    }
}