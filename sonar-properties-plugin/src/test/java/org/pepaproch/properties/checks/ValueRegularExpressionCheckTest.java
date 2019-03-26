package org.pepaproch.properties.checks;

import org.junit.Test;
import org.pepaproch.properties.parser.SensorTestBase;
import org.sonar.api.batch.rule.CheckFactory;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;

public class ValueRegularExpressionCheckTest extends SensorTestBase {

    @Test
    public void valueRegularTest() {
        Map<String,String> param = new HashMap<>();
        param.put("regularExpression" , ".*vole.*");
        CheckFactory f = checkFactory("value-regular-expression" ,param);
        addFile("ip.properties", "key=vole\n\r" +
                "#key=vole\n" +
                "vole=\n" +
                "#tyvole : dont be nasty");
        sensor(f).execute(context);
        assertEquals(1, context.allIssues().size());
    }

}