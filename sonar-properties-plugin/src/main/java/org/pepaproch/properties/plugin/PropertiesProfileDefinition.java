package org.pepaproch.properties.plugin;

import org.sonar.api.server.profile.BuiltInQualityProfilesDefinition;
import org.sonarsource.analyzer.commons.BuiltInQualityProfileJsonLoader;

public class PropertiesProfileDefinition implements BuiltInQualityProfilesDefinition {
    public static final String PROFILE_NAME = "Sonar way";
    public static final  String PROFILE_PATH = "org/sonar/l10n/properties/Sonar_way_profile.json";


    @Override
    public void define(Context context) {
        NewBuiltInQualityProfile profile = context.createBuiltInQualityProfile(PROFILE_NAME, PropertiesLanguage.JPROPERTIES_LANG_KEY);
        BuiltInQualityProfileJsonLoader.load(profile, PropertiesPlugin.REPOSITORY_KEY, PROFILE_PATH);
        profile.done();
    }
}
