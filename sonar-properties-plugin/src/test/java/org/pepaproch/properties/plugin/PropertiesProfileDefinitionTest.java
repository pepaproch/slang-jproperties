package org.pepaproch.properties.plugin;

import org.junit.Test;
import org.sonar.api.server.profile.BuiltInQualityProfilesDefinition;

import static org.assertj.core.api.Assertions.assertThat;

public class PropertiesProfileDefinitionTest {

    @Test
    public void define() {
        BuiltInQualityProfilesDefinition.Context context = new BuiltInQualityProfilesDefinition.Context();
        new PropertiesProfileDefinition().define(context);
        BuiltInQualityProfilesDefinition.BuiltInQualityProfile profile = context.profile(PropertiesLanguage.JPROPERTIES_LANG_KEY, "Sonar way");
        assertThat(profile.rules().size()).isEqualTo(15);
        assertThat(profile.rules()).extracting(BuiltInQualityProfilesDefinition.BuiltInActiveRule::ruleKey).contains("dummyCheck");
    }

}