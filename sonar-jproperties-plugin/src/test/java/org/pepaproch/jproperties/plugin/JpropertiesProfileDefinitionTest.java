package org.pepaproch.jproperties.plugin;

import org.junit.Test;
import org.sonar.api.server.profile.BuiltInQualityProfilesDefinition;

import static org.assertj.core.api.Assertions.assertThat;

public class JpropertiesProfileDefinitionTest {

    @Test
    public void define() {
        BuiltInQualityProfilesDefinition.Context context = new BuiltInQualityProfilesDefinition.Context();
        new JpropertiesProfileDefinition().define(context);
        BuiltInQualityProfilesDefinition.BuiltInQualityProfile profile = context.profile(JpropertiesLanguage.JPROPERTIES_LANG_KEY, "Sonar way");
        assertThat(profile.rules().size()).isEqualTo(6);
        assertThat(profile.rules()).extracting(BuiltInQualityProfilesDefinition.BuiltInActiveRule::ruleKey).contains("dummyCheck");
    }

}