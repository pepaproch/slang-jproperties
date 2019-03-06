package org.pepaproch.properties.checks;

import org.junit.Test;
import org.pepaproch.properties.parser.SensorTestBase;
import org.pepaproch.properties.parser.PropertiesExampleS;
import org.sonar.api.batch.rule.CheckFactory;

import static org.junit.Assert.*;

public class DuplicatedKeysCheckTest extends SensorTestBase {

    private static final String R_DUPLICATED_KEYS_IN_FILE = "duplicatedKeys";
    @Test
    public void testDuplicatedKys() {
        CheckFactory f = checkFactory(R_DUPLICATED_KEYS_IN_FILE);
        addFile("ip.properties", PropertiesExampleS.WIKY_PROP_EXAMPLE + PropertiesExampleS.WIKY_PROP_EXAMPLE);
        sensor(f).execute(context);
        assertEquals(6, context.allIssues().size());

    }

    @Test
    public void testDuplicatedKeysMissingValue() {
        CheckFactory f = checkFactory(R_DUPLICATED_KEYS_IN_FILE);
        addFile("ip.properties", PropertiesExampleS.WIKY_PROP_EXAMPLE + PropertiesExampleS.WIKY_PROP_EXAMPLE + PropertiesExampleS.MISSING_VALUE_EXAMPLE);
        sensor(f).execute(context);
        assertEquals(6, context.allIssues().size());
    }
}