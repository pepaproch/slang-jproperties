package org.pepaproch.properties.checks;

import org.junit.Test;
import org.pepaproch.properties.parser.SensorTestBase;
import org.sonar.api.batch.rule.CheckFactory;

import static org.junit.Assert.*;

public class EndLineCharactersCheckTest extends SensorTestBase {
@Test
public void endLineCharactersTest() {
    CheckFactory f = checkFactory("end-line-characters");
    addFile("ip-:prop.properties",
            "key=value\n\r" +
            "key=\n" +
            "key2=");
    sensor(f).execute(context);
    assertEquals(1, context.allIssues().size());

}
}