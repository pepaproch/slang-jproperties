package com.pepaproch.jproperties.plugin;

import com.pepaproch.jproperties.checks.DummyCheck;
import org.sonarsource.slang.checks.HardcodedIpCheck;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class JpropertiesCheckList {


    public static List<Class> checks() {

        return Collections.unmodifiableList(Arrays.asList(DummyCheck.class, HardcodedIpCheck.class));
    }
}
