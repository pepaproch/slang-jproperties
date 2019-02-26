package org.pepaproch.properties.plugin;

import org.pepaproch.properties.checks.DummyCheck;
import org.sonarsource.slang.checks.*;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class PropertiesCheckList {





    public static List<Class> checks() {

        return Collections.unmodifiableList(Arrays.asList(
                DummyCheck.class,
                HardcodedIpCheck.class,
                HardcodedCredentialsCheck.class,
                StringLiteralDuplicatedCheck.class,
                TodoCommentCheck.class,
                ParsingErrorCheck.class));
    }
}
