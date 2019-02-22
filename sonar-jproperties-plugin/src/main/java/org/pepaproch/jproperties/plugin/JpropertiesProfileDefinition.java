package org.pepaproch.jproperties.plugin;

import org.sonar.api.server.profile.BuiltInQualityProfilesDefinition;
import org.sonarsource.analyzer.commons.BuiltInQualityProfileJsonLoader;

public class JpropertiesProfileDefinition implements BuiltInQualityProfilesDefinition {
    public static String PROFILE_NAME = "Sonar way";
    public static String PROFILE_PATH = "org/sonar/l10n/jproperties/Sonar_way_profile.json";


    @Override
    public void define(Context context) {
        NewBuiltInQualityProfile profile = context.createBuiltInQualityProfile(PROFILE_NAME, JpropertiesLanguage.JPROPERTIES_LANG_KEY);
        BuiltInQualityProfileJsonLoader.load(profile, JpropertiesPlugin.REPOSITORY_KEY, PROFILE_PATH);
        profile.done();
    }
}
