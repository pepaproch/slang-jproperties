package org.pepaproch.properties.checks;

import org.junit.Test;
import org.pepaproch.properties.parser.SensorTestBase;
import org.sonar.api.batch.rule.CheckFactory;

import static org.junit.Assert.assertEquals;

public class EndFileNewLineCheckTest extends SensorTestBase {
    @Test
    public void testMissing() {
        CheckFactory f = checkFactory("empty-line-end-of-file");
        addFile("ip.properties", "key=value\n\r" +
                "key=\n" +
                "key2=val");
        sensor(f).execute(context);
        assertEquals(1, context.allIssues().size());

    }

    @Test
    public void testNotMissingLF() {
        CheckFactory f = checkFactory("empty-line-end-of-file");
        addFile("ip.properties", "key=value\n\r" +
                "key=\n" +
                "key2=val\n");
        sensor(f).execute(context);
        assertEquals(0, context.allIssues().size());

    }

    @Test
    public void testNotMissingR() {
        CheckFactory f = checkFactory("empty-line-end-of-file");
        addFile("ip.properties", "key=value\n\r" +
                "key=\n" +
                "key2=val\r");
        sensor(f).execute(context);
        assertEquals(0, context.allIssues().size());

    }

    @Test
    public void testNotMissingCRLF() {
        CheckFactory f = checkFactory("empty-line-end-of-file");
        addFile("ip.properties", "key=value\n\r" +
                "key=\n" +
                "key2=val\r\n");
        sensor(f).execute(context);
        assertEquals(0, context.allIssues().size());

    }

}