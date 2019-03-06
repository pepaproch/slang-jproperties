package org.pepaproch.properties.checks;

import org.junit.Test;
import org.pepaproch.properties.parser.SensorTestBase;
import org.pepaproch.properties.parser.PropertiesExampleS;
import org.sonar.api.batch.rule.CheckFactory;

import static org.junit.Assert.assertEquals;

public class ParrsingErrorCheckTest extends SensorTestBase {

    @Test
    public void testParsingError() {
        CheckFactory f = checkFactory("ParsingError");
        addFile("error.properties", PropertiesExampleS.WIKY_PROP_PARSE_ERROR );
        sensor(f).execute(context);
        assertEquals(1,context.allIssues().size());

    }



}
