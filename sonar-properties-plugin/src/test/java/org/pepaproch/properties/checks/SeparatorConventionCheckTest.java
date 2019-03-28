package org.pepaproch.properties.checks;

import org.junit.Test;
import org.pepaproch.properties.parser.SensorTestBase;
import org.sonar.api.batch.rule.CheckFactory;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;

public class SeparatorConventionCheckTest extends SensorTestBase {



    @Test
    public void separatorOK() {
        CheckFactory f = checkFactory("separator-convention");
        addFile("ip.properties", "key=value\n\r" +
                "key=asasas\n" +
                "key2=sss");
        sensor(f).execute(context);
        assertEquals(0, context.allIssues().size());
    }



    @Test
    public void separatorColonOK() {
        Map<String,String> settings = new HashMap<>();
        settings.put("separator", ":");
        CheckFactory f = checkFactory("separator-convention" ,settings);
        addFile("ip.properties", "key: value\n\r" +
                "key: asasas\n" +
                "key2: sss");
        sensor(f).execute(context);
        assertEquals(0, context.allIssues().size());
    }

    @Test
    public void separatorColonNoOK() {
        Map<String,String> settings = new HashMap<>();
        settings.put("separator", ":");
        CheckFactory f = checkFactory("separator-convention" ,settings);
        addFile("ip.properties", "key :value\n\r" +
                "key: asasas\n" +
                "key2: sss");
        sensor(f).execute(context);
        assertEquals(1, context.allIssues().size());
    }

    @Test
    public void separatorColonNoOK1() {
        Map<String,String> settings = new HashMap<>();
        settings.put("separator", ":");
        CheckFactory f = checkFactory("separator-convention" ,settings);
        addFile("ip.properties", "key :value\n\r" +
                "key: asasas\n" +
                "key2:sss");
        sensor(f).execute(context);
        assertEquals(2, context.allIssues().size());
    }
    @Test
    public void separatorWithWhites() {
        CheckFactory f = checkFactory("separator-convention");
        addFile("ip.properties", "key1=value\n\r" +
                "key2=asasas\n" +
                "key3= sss");
        sensor(f).execute(context);
        assertEquals(1, context.allIssues().size());
    }

    @Test
    public void separatorNoAllowed() {
        CheckFactory f = checkFactory("separator-convention");
        addFile("ip.properties", "key:value\n\r" +
                "key:asasas\n" +
                "key2:sss");
        sensor(f).execute(context);
        assertEquals(3, context.allIssues().size());
    }
}