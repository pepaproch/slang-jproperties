package org.pepaproch.properties.checks.project;


import org.sonar.check.Rule;

@Rule(key = "missing-default-bundle")
public class MissingDefaultBundleCheck {

    public static final String ISSUE_MESSAGE = "Default bundle %s should be present";

}
