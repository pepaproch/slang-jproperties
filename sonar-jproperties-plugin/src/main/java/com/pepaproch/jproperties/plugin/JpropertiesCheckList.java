package com.pepaproch.jproperties.plugin;

import com.pepaproch.jproperties.checks.DummyCheck;
import org.sonarsource.slang.checks.HardcodedCredentialsCheck;
import org.sonarsource.slang.checks.HardcodedIpCheck;
import org.sonarsource.slang.checks.ParsingErrorCheck;
import org.sonarsource.slang.checks.StringLiteralDuplicatedCheck;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class JpropertiesCheckList {





    public static List<Class> checks() {

        return Collections.unmodifiableList(Arrays.asList(
                DummyCheck.class,
                HardcodedIpCheck.class,
                HardcodedCredentialsCheck.class,
                StringLiteralDuplicatedCheck.class));
    }
}
