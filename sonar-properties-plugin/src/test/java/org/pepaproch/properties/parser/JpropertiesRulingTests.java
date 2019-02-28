package org.pepaproch.properties.parser;

import org.junit.Before;
import org.junit.Test;
import org.pepaproch.properties.plugin.PropertiesSensor;
import org.sonar.api.batch.rule.CheckFactory;
import org.sonar.api.batch.sensor.issue.Issue;

import java.util.Collection;

import static org.junit.Assert.assertEquals;

public class JpropertiesRulingTests extends JpSensorTestBase {

    private PropertiesSensor sensor;

    @Before
    public void before() {

    }
    @Test
    public void testParsingError() {
        CheckFactory f = checkFactory("ParsingError");
        addFile("error.properties", JpropertiesTestBase.WIKY_PROP_PARSE_ERROR );
        executeAndConfirm(f,1);

    }

    @Test
    public void testParsingErrorAST() {
        CheckFactory f = checkFactory("ParsingError");
        addFile("error.properties", JpropertiesTestBase.parsingErrorAST );
        executeAndConfirm(f,0);

    }

    @Test
    public void testDuplicatedStrings() {
        CheckFactory f = checkFactory(R_STRING_LITERAL_DUPLICATE_CHECK);

        addFile("triplicatedWiky.properties", JpropertiesTestBase.WIKY_PROP_EXAMPLE + JpropertiesTestBase.WIKY_PROP_EXAMPLE + JpropertiesTestBase.WIKY_PROP_EXAMPLE);
       executeAndConfirm(f,5);
    }

    @Test
    public void testHardcodedIp() {
        CheckFactory f = checkFactory(R_HARDCODED_IP_CHECK);
        addFile("ip.properties", JpropertiesTestBase.HARDCODED_IP);
        executeAndConfirm(f,2);

    }

    @Test
    public void testComment() {
        CheckFactory f = checkFactory(R_TODO_COMMENT_CHECK);
        addFile("ip.properties", JpropertiesTestBase.WIKY_PROP_TODO_COMENT);
        executeAndConfirm(f,1);

    }

    @Test
    public void testTodoComment() {
        CheckFactory f = checkFactory(R_TODO_COMMENT_CHECK);
        addFile("ip.properties", JpropertiesTestBase.WIKY_PROP_TODO_COMENT);
        executeAndConfirm(f,1);

    }

    private void executeAndConfirm( CheckFactory f , int expected) {

        sensor(f).execute(context);
        Collection<Issue> issues = context.allIssues();
        assertEquals(expected, context.allIssues().size());

    }


}
