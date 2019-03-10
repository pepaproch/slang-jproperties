package org.pepaproch.properties.checks;

import org.junit.Test;
import org.pepaproch.properties.parser.SensorTestBase;
import org.sonar.api.batch.rule.CheckFactory;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

public class KeyRegularExpressionCheckTest extends SensorTestBase {

    @Test
    public void keyRegularTest() {
        Map<String,String> param = new HashMap<>();
        param.put("regularExpression" , ".*vole.*");
        CheckFactory f = checkFactory("key-regular-expression" ,param);
        addFile("ip.properties", "key=value\n\r" +
                "#key=vole\n" +
                "vole=\n" +
                "#tyvole : dont be nasty");
        sensor(f).execute(context);
        assertEquals(1, context.allIssues().size());
    }

}