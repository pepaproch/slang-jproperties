package com.pepaproch.jproperties.plugin;

import org.junit.Before;
import org.junit.Test;
import org.sonar.api.SonarQubeSide;
import org.sonar.api.SonarRuntime;
import org.sonar.api.internal.SonarRuntimeImpl;
import org.sonar.api.server.rule.RulesDefinition;
import org.sonar.api.utils.Version;

import static org.junit.Assert.*;

public class JpropertiesRulesDefinitionTest {

    SonarRuntime sonarRuntime;

    @Before
    public void before() {
        sonarRuntime = SonarRuntimeImpl.forSonarQube(Version.create(7, 2), SonarQubeSide.SERVER);
    }

    @Test
    public void define() {
        RulesDefinition.Context context = new RulesDefinition.Context();
        RulesDefinition rulesDefinition = new JpropertiesRulesDefinition(sonarRuntime);
        rulesDefinition.define(context);
        RulesDefinition.Repository repo = context.repository(JpropertiesPlugin.REPOSITORY_KEY);
        assertTrue(repo.name().equals(JpropertiesPlugin.REPOSITORY_KEY));
    }
}