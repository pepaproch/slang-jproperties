package org.pepaproch.properties.plugin;

import org.pepaproch.properties.checks.DummyCheck;
import org.pepaproch.properties.checks.DuplicatedKeysCheck;
import org.pepaproch.properties.checks.DuplicatedProjectKeysVisitor;
import org.pepaproch.properties.checks.EmptyValueCheck;
import org.sonarsource.slang.checks.*;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class PropertiesCheckList {





    public static List<Class> checks() {

        return Collections.unmodifiableList(Arrays.asList(
                DummyCheck.class,
                DuplicatedKeysCheck.class,
                CommentedCodeCheck.class,
                HardcodedIpCheck.class,
                org.pepaproch.properties.checks.HardcodedCredentialsCheck.class,
                StringLiteralDuplicatedCheck.class,
                TodoCommentCheck.class,
                FixMeCommentCheck.class,
                TooLongLineCheck.class,
                TooManyLinesOfCodeFileCheck.class,
                ParsingErrorCheck.class,
                EmptyValueCheck.class));
    }

    public static List<Class> visitors() {
        return Collections.unmodifiableList(Arrays.asList(
                DuplicatedProjectKeysVisitor.class
     ));
    }


}
