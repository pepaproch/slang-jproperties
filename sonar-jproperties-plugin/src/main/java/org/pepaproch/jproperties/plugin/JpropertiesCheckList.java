package org.pepaproch.jproperties.plugin;

import org.pepaproch.jproperties.checks.DummyCheck;
import org.sonarsource.slang.checks.*;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class JpropertiesCheckList {





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
