package org.pepaproch.properties.checks;

import org.junit.Test;
import org.pepaproch.properties.parser.SensorTestBase;
import org.sonar.api.batch.rule.CheckFactory;

import static org.junit.Assert.*;

public class FileNameCheckTest extends SensorTestBase {

    @Test
    public void fileNameCheck() {
        CheckFactory f = checkFactory("S1578");
        addFile("ip-:prop.properties", "key=value\n\r" +
                "key=\n" +
                "key2=");
        sensor(f).execute(context);
        assertEquals(1, context.allIssues().size());

    }

    @Test
    public void fileNameCheckComply() {
        CheckFactory f = checkFactory("S1578");
        addFile("ipprop.properties", "key=value\n\r" +
                "key=\n" +
                "key2=");
        sensor(f).execute(context);
        assertEquals(0, context.allIssues().size());

    }

}