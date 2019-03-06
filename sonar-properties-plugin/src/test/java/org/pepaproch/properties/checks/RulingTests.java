package org.pepaproch.properties.checks;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.sonarsource.slang.checks.TooLongLineCheck;

@RunWith(Suite.class)
@Suite.SuiteClasses( {
        HardcodedCredentialsCheckTest.class,
        DuplicatedKeysCheckTest.class,
        DuplicatedProjectKeysVisitorTest.class,
        StringLiteralDuplicatedCheckTest.class,
        ParrsingErrorCheckTest.class,
        HardcodedIPCheckTest.class,
        TodoMeCommentCheckTest.class,
        TooManyLinesOfCodeFileCheckTest.class,
        TooLongLineCheckTest.class,
        FixMeCommentCheckTest.class
})
public class RulingTests {
}
