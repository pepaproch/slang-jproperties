package org.pepaproch.properties.checks;

import org.junit.Test;
import org.pepaproch.properties.parser.SensorTestBase;
import org.sonar.api.batch.rule.CheckFactory;
import org.sonar.api.batch.sensor.issue.Issue;

import java.util.Collection;

import static org.junit.Assert.*;

public class CommentConventionCheckTest extends SensorTestBase {
    @Test
    public void commentConvetionCheckTest() {
        CheckFactory f = checkFactory("comment-convention");
        addFile("ip.properties", "key=value\n\r" +
                "#key=aaa\n" +
                "!key2=jjj\n" +
                "# keu=ssdasd\n" +
                "!222\n" +
                "!ssss\n" +
                "#eeeeeee");
        sensor(f).execute(context);
        assertEquals(6, context.allIssues().size());
        Collection<Issue> issues = context.allIssues();
        int sum = issues.stream().limit(1).mapToInt(i -> Integer.valueOf(i.flows().size())).sum();
        assertEquals(4, sum);
    }

}