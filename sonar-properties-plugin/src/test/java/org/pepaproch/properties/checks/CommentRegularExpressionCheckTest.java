package org.pepaproch.properties.checks;

import org.junit.Test;
import org.pepaproch.properties.parser.SensorTestBase;
import org.pepaproch.properties.plugin.PropertiesCheckList;
import org.sonar.api.batch.rule.CheckFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

public class CommentRegularExpressionCheckTest extends SensorTestBase {

    @Test
    public void commentRegularExpressionTest() {
        Map<String,String> param = new HashMap<>();
        param.put("regularExpression" , ".*vole.*");
        CheckFactory f = checkFactory("comment-regular-expression" ,param);
        addFile("ip.properties", "key=value\n\r" +
                "#key=vole\n" +
                "key2=\n" +
                "#tyvole : dont be nasty");
        sensor(f).execute(context);
        assertEquals(2, context.allIssues().size());
    }
    
    @Test
    public void testChekListFilter() {
        List<Class> checks = PropertiesCheckList.checks(PropertiesCheckList.FILTER_SPECIAL_INIT);
        System.out.println("s");

    }

}