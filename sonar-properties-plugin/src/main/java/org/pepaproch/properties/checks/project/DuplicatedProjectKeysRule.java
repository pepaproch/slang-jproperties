package org.pepaproch.properties.checks.project;


import org.sonar.check.Rule;
import org.sonar.check.RuleProperty;


@Rule(key = "duplicatedCheck")
public class DuplicatedProjectKeysRule {

    private static final int DEFAULT_THRESHOLD = 1;
    @RuleProperty(
            key = "threshold",
            description = "Number of times a key must be duplicated across files to trigger an issue",
            defaultValue = "" + DEFAULT_THRESHOLD)
    public int threshold = DEFAULT_THRESHOLD;





}
