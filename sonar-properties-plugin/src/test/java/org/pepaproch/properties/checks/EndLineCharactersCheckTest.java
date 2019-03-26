package org.pepaproch.properties.checks;

import org.junit.Test;
import org.pepaproch.properties.parser.SensorTestBase;
import org.sonar.api.batch.rule.CheckFactory;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;

public class EndLineCharactersCheckTest extends SensorTestBase {
@Test
public void endLineCharactersTestOK() {
    CheckFactory f = checkFactory("end-line-characters");
    addFile("ip-:prop.properties",
            "key=Lorem ipsum dolor sit amet, consectetuer adipiscing elit. Aliquam in lorem sit amet leo accumsan lacinia. Curabitur sagittis hendrerit ante.\n" +
                    "key1=Lorem ipsum dolor sit amet, consectetuer adipiscing elit. Aliquam in lorem sit amet leo accumsan lacinia. Curabitur sagittis hendrerit ante.\n" +
                    "key2=Lorem ipsum dolor sit amet, consectetuer adipiscing elit. Aliquam in lorem sit amet leo accumsan lacinia. Curabitur sagittis hendrerit ante.\n" +
                    "key3=Lorem ipsum dolor sit amet, consectetuer adipiscing elit. Aliquam in lorem sit amet leo accumsan lacinia. Curabitur sagittis hendrerit ante.\n" +
                    "key4=Lorem ipsum dolor sit amet, consectetuer adipiscing elit. Aliquam in lorem sit amet leo accumsan lacinia. Curabitur sagittis hendrerit ante.\n");
    sensor(f).execute(context);
    assertEquals(0, context.allIssues().size());

}


    @Test
    public void endLineCharactersTestIssue() {
        CheckFactory f = checkFactory("end-line-characters");
        addFile("ip-:prop.properties",
                "key=Lorem ipsum dolor sit amet, consectetuer adipiscing elit. Aliquam in lorem sit amet leo accumsan lacinia. Curabitur sagittis hendrerit ante.\n" +
                        "key1=Lorem ipsum dolor sit amet, consectetuer adipiscing elit. Aliquam in lorem sit amet leo accumsan lacinia. Curabitur sagittis hendrerit ante.\r" +
                        "key2=Lorem ipsum dolor sit amet, consectetuer adipiscing elit. Aliquam in lorem sit amet leo accumsan lacinia. Curabitur sagittis hendrerit ante.\n" +
                        "key3=Lorem ipsum dolor sit amet, consectetuer adipiscing elit. Aliquam in lorem sit amet leo accumsan lacinia. Curabitur sagittis hendrerit ante.\n" +
                        "key4=Lorem ipsum dolor sit amet, consectetuer adipiscing elit. Aliquam in lorem sit amet leo accumsan lacinia. Curabitur sagittis hendrerit ante.\n");
        sensor(f).execute(context);
        assertEquals(1, context.allIssues().size());

    }



    @Test
    public void endLineCharactersTestCROK() {

        Map<String,String> config = new HashMap<>();
        config.put("End-line character","CR");
        CheckFactory f = checkFactory("end-line-characters" ,config);
        addFile("ip-:prop.properties",
                "key=Lorem ipsum dolor sit amet, consectetuer adipiscing elit. Aliquam in lorem sit amet leo accumsan lacinia. Curabitur sagittis hendrerit ante.\r" +
                        "key1=Lorem ipsum dolor sit amet, consectetuer adipiscing elit. Aliquam in lorem sit amet leo accumsan lacinia. Curabitur sagittis hendrerit ante.\r" +
                        "key2=Lorem ipsum dolor sit amet, consectetuer adipiscing elit. Aliquam in lorem sit amet leo accumsan lacinia. Curabitur sagittis hendrerit ante.\r" +
                        "key3=Lorem ipsum dolor sit amet, consectetuer adipiscing elit. Aliquam in lorem sit amet leo accumsan lacinia. Curabitur sagittis hendrerit ante.\r" +
                        "key4=Lorem ipsum dolor sit amet, consectetuer adipiscing elit. Aliquam in lorem sit amet leo accumsan lacinia. Curabitur sagittis hendrerit ante.\r");
        sensor(f).execute(context);
        assertEquals(0, context.allIssues().size());

    }


    @Test
    public void endLineCharactersTestIssueCR() {

        Map<String,String> config = new HashMap<>();
        config.put("End-line character","CR");
        CheckFactory f = checkFactory("end-line-characters" ,config);
        addFile("ip-:prop.properties",
                "key=Lorem ipsum dolor sit amet, consectetuer adipiscing elit. Aliquam in lorem sit amet leo accumsan lacinia. Curabitur sagittis hendrerit ante.\n" +
                        "key1=Lorem ipsum dolor sit amet, consectetuer adipiscing elit. Aliquam in lorem sit amet leo accumsan lacinia. Curabitur sagittis hendrerit ante.\n" +
                        "key2=Lorem ipsum dolor sit amet, consectetuer adipiscing elit. Aliquam in lorem sit amet leo accumsan lacinia. Curabitur sagittis hendrerit ante.\r" +
                        "key3=Lorem ipsum dolor sit amet, consectetuer adipiscing elit. Aliquam in lorem sit amet leo accumsan lacinia. Curabitur sagittis hendrerit ante.\n" +
                        "key4=Lorem ipsum dolor sit amet, consectetuer adipiscing elit. Aliquam in lorem sit amet leo accumsan lacinia. Curabitur sagittis hendrerit ante.\n");
        sensor(f).execute(context);
        assertEquals(1, context.allIssues().size());

    }

    @Test
    public void endLineCharactersTestIssueCRLF() {

        Map<String,String> config = new HashMap<>();
        config.put("End-line character","CR");
        CheckFactory f = checkFactory("end-line-characters" ,config);
        addFile("ip-:prop.properties",
                "key=Lorem ipsum dolor sit amet, consectetuer adipiscing elit. Aliquam in lorem sit amet leo accumsan lacinia. Curabitur sagittis hendrerit ante.\n" +
                        "key1=Lorem ipsum dolor sit amet, consectetuer adipiscing elit. Aliquam in lorem sit amet leo accumsan lacinia. Curabitur sagittis hendrerit ante.\n" +
                        "key2=Lorem ipsum dolor sit amet, consectetuer adipiscing elit. Aliquam in lorem sit amet leo accumsan lacinia. Curabitur sagittis hendrerit ante.\r" +
                        "key3=Lorem ipsum dolor sit amet, consectetuer adipiscing elit. Aliquam in lorem sit amet leo accumsan lacinia. Curabitur sagittis hendrerit ante.\n" +
                        "key4=Lorem ipsum dolor sit amet, consectetuer adipiscing elit. Aliquam in lorem sit amet leo accumsan lacinia. Curabitur sagittis hendrerit ante.\n");
        sensor(f).execute(context);
        assertEquals(1, context.allIssues().size());

    }

    @Test
    public void endLineCharactersTestCRLFOK() {

        Map<String,String> config = new HashMap<>();
        config.put("End-line character","CRLF");
        CheckFactory f = checkFactory("end-line-characters" ,config);
        addFile("ip-:prop.properties",
                "key=Lorem ipsum dolor sit amet, consectetuer adipiscing elit. Aliquam in lorem sit amet leo accumsan lacinia. Curabitur sagittis hendrerit ante.\r\n" +
                        "key1=Lorem ipsum dolor sit amet, consectetuer adipiscing elit. Aliquam in lorem sit amet leo accumsan lacinia. Curabitur sagittis hendrerit ante.\r\n" +
                        "key2=Lorem ipsum dolor sit amet, consectetuer adipiscing elit. Aliquam in lorem sit amet leo accumsan lacinia. Curabitur sagittis hendrerit ante.\r\n" +
                        "key3=Lorem ipsum dolor sit amet, consectetuer adipiscing elit. Aliquam in lorem sit amet leo accumsan lacinia. Curabitur sagittis hendrerit ante.\r\n" +
                        "key4=Lorem ipsum dolor sit amet, consectetuer adipiscing elit. Aliquam in lorem sit amet leo accumsan lacinia. Curabitur sagittis hendrerit ante.\r\n");
        sensor(f).execute(context);
        assertEquals(0, context.allIssues().size());

    }
}