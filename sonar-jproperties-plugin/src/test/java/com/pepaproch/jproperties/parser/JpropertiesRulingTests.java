package com.pepaproch.jproperties.parser;

import com.pepaproch.jproperties.plugin.JpropertiesSensor;
import org.junit.Before;
import org.junit.Test;
import org.sonar.api.batch.rule.CheckFactory;

public class JpropertiesRulingTests extends JpSensorTestBase {

private JpropertiesSensor sensor;
@Before
public void before() {

}

@Test
public  void testDuplicatedStrings() {
    CheckFactory f = checkFactory(R_STRING_LITERAL_DUPLICATE_CHECK);
    addFile("triplicatedWiky.properties" , JpropertiesTestBase.WIKY_PROP_EXAMPLE + JpropertiesTestBase.WIKY_PROP_EXAMPLE + JpropertiesTestBase.WIKY_PROP_EXAMPLE );
    sensor(f).execute(context);
    System.out.println("");
}

}
