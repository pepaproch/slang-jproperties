package com.pepaproch.jproperties.plugin;

import org.sonar.api.Plugin;

public class JpropertiesPlugin implements Plugin {

    public static final String REPOSITORY_KEY = "jpropertiesslang";

    @Override
    public void define(Context context) {
        context.addExtensions(
                JpropertiesLanguage.class,
                JpropertiesRulesDefinition.class,
                JpropertiesSensor.class,
                JpropertiesProfileDefinition.class);

    }
}