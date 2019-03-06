package org.pepaproch.properties.checks;

import org.junit.Test;
import org.pepaproch.properties.parser.SensorTestBase;
import org.sonar.api.batch.rule.CheckFactory;
import static org.junit.Assert.assertEquals;
public class HardcodedIPCheckTest  extends SensorTestBase {


    public static final String HARDCODED_IP = "hardcoded=77.75.77.39\n" +
            "nothardcoded=127.0.0.1\n" +
            "hardcoded=192.168.12.42";

    @Test
    public void testHardcodedIp() {
        CheckFactory f = checkFactory(R_HARDCODED_IP_CHECK);
        addFile("ip.properties", HARDCODED_IP);
       sensor(f).execute(context);
       assertEquals(2,context.allIssues().size());



    }

}
