package com.pepaproch.jproperties.plugin;

import org.junit.Test;
import org.sonar.api.SonarQubeSide;
import org.sonar.api.config.internal.MapSettings;
import org.sonar.api.internal.SonarRuntimeImpl;
import org.sonar.api.resources.Language;
import org.sonar.api.server.profile.BuiltInQualityProfilesDefinition;
import org.sonar.api.utils.Version;
import org.sonarsource.slang.testing.AbstractSensorTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.*;

public class JpropertiesProfileDefinitionTest {

    @Test
    public void define() {
        BuiltInQualityProfilesDefinition.Context context = new BuiltInQualityProfilesDefinition.Context();
        new JpropertiesProfileDefinition().define(context);
        BuiltInQualityProfilesDefinition.BuiltInQualityProfile profile = context.profile(JpropertiesLanguage.JPROPERTIES_LANG_KEY, "Sonar way");
        assertThat(profile.rules().size()).isEqualTo(4);
        assertThat(profile.rules()).extracting(BuiltInQualityProfilesDefinition.BuiltInActiveRule::ruleKey).contains("dummyCheck");
    }

}