package org.pepaproch.properties.checks;

import org.junit.Test;
import org.pepaproch.properties.parser.JpSensorTestBase;
import org.pepaproch.properties.parser.PropertiesExampleS;
import org.sonar.api.batch.rule.CheckFactory;

import static org.junit.Assert.assertEquals;

public class StringLiteralDuplicatedCheckTest extends JpSensorTestBase {
    public static final String R_STRING_LITERAL_DUPLICATE_CHECK = "S1192";

    @Test
    public void testDuplicatedStrings() {
        CheckFactory f = checkFactory(R_STRING_LITERAL_DUPLICATE_CHECK);
        addFile("triplicatedWiky.properties", PropertiesExampleS.WIKY_PROP_EXAMPLE + PropertiesExampleS.WIKY_PROP_EXAMPLE + PropertiesExampleS.WIKY_PROP_EXAMPLE);
        sensor(f).execute(context);
        assertEquals(5, context.allIssues().size());

    }

    @Test
    public void testMisingValueDuplication() {
        CheckFactory f = checkFactory(R_STRING_LITERAL_DUPLICATE_CHECK);
        addFile("triplicatedWiky.properties", PropertiesExampleS.WIKY_PROP_EXAMPLE + PropertiesExampleS.WIKY_PROP_EXAMPLE + PropertiesExampleS.WIKY_PROP_EXAMPLE + PropertiesExampleS.MISSING_VALUE_EXAMPLE + PropertiesExampleS.MISSING_VALUE_EXAMPLE + PropertiesExampleS.MISSING_VALUE_EXAMPLE);
        sensor(f).execute(context);
        assertEquals(5, context.allIssues().size());
    }
}
