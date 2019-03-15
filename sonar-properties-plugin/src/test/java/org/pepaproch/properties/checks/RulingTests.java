package org.pepaproch.properties.checks;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses( {
        HardcodedCredentialsCheckTest.class,
        DuplicatedKeysCheckTest.class,
        DuplicatedProjectPropertiesVisitorTest.class,
        CommentConventionCheckTest.class,
        StringLiteralDuplicatedCheckTest.class,
        ParrsingErrorCheckTest.class,
        HardcodedIPCheckTest.class,
        TodoMeCommentCheckTest.class,
        TooManyLinesOfCodeFileCheckTest.class,
        TooLongLineCheckTest.class,
        FixMeCommentCheckTest.class,
        FileNameCheckTest.class
})
public class RulingTests {
}
