package org.pepaproch.properties.checks;

import org.junit.Test;
import org.pepaproch.properties.parser.SensorTestBase;
import org.sonar.api.batch.rule.CheckFactory;

import static org.junit.Assert.assertEquals;


public class EmptyValueCheckTest extends SensorTestBase{


    @Test
    public void emptyValueCheck() {
        CheckFactory f = checkFactory("emptyValueCheck");
        addFile("ip.properties", "key=value\n\r" +
                "key=\n" +
                "key2=");
        sensor(f).execute(context);
        assertEquals(2, context.allIssues().size());
    }
}