package org.pepaproch.properties.plugin;

import org.sonar.api.Plugin;

public class PropertiesPlugin implements Plugin {

    public static final String REPOSITORY_KEY = "jpropertiesslang";

    @Override
    public void define(Context context) {
        context.addExtensions(
                PropertiesLanguage.class,
                PropertiesRulesDefinition.class,
                PropertiesSensor.class,
                PropertiesProfileDefinition.class);

    }
}
