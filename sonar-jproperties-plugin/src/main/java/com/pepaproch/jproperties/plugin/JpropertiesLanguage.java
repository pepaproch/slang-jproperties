package com.pepaproch.jproperties.plugin;

import org.sonar.api.config.Configuration;
import org.sonar.api.config.internal.MapSettings;
import org.sonar.api.resources.AbstractLanguage;

public class JpropertiesLanguage extends AbstractLanguage {

    private final Configuration configuration;
    public static final String JPROPERTIES_FILE_SUFFIXES_KEY = "sonar.jproperties.file.suffixes";
    public static final String JPROPERTIES_FILE_SUFFIXES_DEFAULT_VALUE = ".properties";

    public static final String JPROPERTIES_LANG_KEY = "jpropertiesslang";
    public static final String JPROPERTIES_LANG_NAME = "jpropertieslang";


    public JpropertiesLanguage(String key, String name) {
        super(key, name);
        this.configuration = new MapSettings().asConfig();
    }

    public JpropertiesLanguage(Configuration asConfig) {
        super(JPROPERTIES_LANG_KEY, JPROPERTIES_LANG_NAME);
        configuration = asConfig;
    }


    @Override
    public String[] getFileSuffixes() {
        String[] suffixes = configuration.getStringArray(JPROPERTIES_FILE_SUFFIXES_KEY);
        if (null != suffixes || suffixes.length == 0) {
            suffixes = JPROPERTIES_FILE_SUFFIXES_DEFAULT_VALUE.split(",");
        }
        return suffixes;

    }
}
