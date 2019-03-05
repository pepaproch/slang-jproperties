package org.pepaproch.properties.checks;

import org.junit.Test;
import org.pepaproch.properties.parser.JpSensorTestBase;
import org.sonar.api.batch.rule.CheckFactory;
import org.sonar.api.batch.sensor.issue.Issue;

import java.util.Collection;
import java.util.HashMap;

import static org.junit.Assert.assertEquals;

public class HardcodedCredentialsCheckTest extends JpSensorTestBase {

    private final String ruleKey = "S2068";

    @Test
    public void testRuleHardcodedCredentialsCheck() {
        String content = "password=sdsdsss\n" +
                "pwd=ENC(G6N718UuyPE5bHyWKyuLQSm02auQPUtm)";
        addFile("hardcoded_cred_props.properties", content);
        CheckFactory f = checkFactory(ruleKey, new HashMap<String, String>() {{
            put("encryptedCredentialsToIgnore", "ENC(");
        }});


        sensor(f).execute(context);
        Collection<Issue> issues = context.allIssues();
        assertEquals(1, context.allIssues().size());
    }

}