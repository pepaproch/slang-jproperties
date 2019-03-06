package org.pepaproch.properties.checks;

import org.junit.Test;
import org.pepaproch.properties.parser.SensorTestBase;
import org.pepaproch.properties.parser.PropertiesExampleS;
import org.sonar.api.batch.rule.CheckFactory;
import org.sonar.api.batch.sensor.issue.Issue;

import java.util.Collection;
import java.util.HashMap;

import static org.junit.Assert.assertEquals;

public class HardcodedCredentialsCheckTest extends SensorTestBase {

    private final String ruleKey = "S2068";

    @Test
    public void testRuleHardcodedCredentialsCheck() {
        String content = "password=sdsdsss\n" +
                "pwd=ENC(G6N718UuyPE5bHyWKyuLQSm02auQPUtm)" + PropertiesExampleS.WIKY_PROP_EXAMPLE;
        addFile("hardcoded_cred_props.properties", content);
        CheckFactory f = checkFactory(ruleKey);

        sensor(f).execute(context);
        Collection<Issue> issues = context.allIssues();
        assertEquals(2, context.allIssues().size());
    }

    @Test
    public void testRuleHardcodedCredentialsCheckConfig() {
        String content = "password=sdsdsss\n" +
                "pwd=ENC(G6N718UuyPE5bHyWKyuLQSm02auQPUtm\n" +
                "iamcredentials=sdsdsd)" + PropertiesExampleS.WIKY_PROP_EXAMPLE;
        addFile("hardcoded_cred_props.properties", content);
        CheckFactory f = checkFactory(ruleKey, new HashMap<String, String>() {{
            put("encryptedCredentialsToIgnore", "ENC(");
            put("credentialWords" ,"iamcredentials,pwd");
        }});

        sensor(f).execute(context);
        Collection<Issue> issues = context.allIssues();
        assertEquals(1, context.allIssues().size());
    }

    @Test
    public void testRuleHardcodedCredentialsCheckConfigNotValid() {
        String content = "password=sdsdsss\n" +
                "pwd=ENC(G6N718UuyPE5bHyWKyuLQSm02auQPUtm\n" +
                "iamcredentials=sdsdsd)" + PropertiesExampleS.WIKY_PROP_EXAMPLE;
        addFile("hardcoded_cred_props.properties", content);
        CheckFactory f = checkFactory(ruleKey, new HashMap<String, String>() {{
            put("encryptedCredentialsToIgnore", " ");
            put("credentialWords" ," ");
        }});

        sensor(f).execute(context);
        Collection<Issue> issues = context.allIssues();
        assertEquals(2, context.allIssues().size());
    }
}