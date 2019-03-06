package org.pepaproch.properties.parser;

import org.pepaproch.properties.plugin.PropertiesLanguage;
import org.pepaproch.properties.plugin.PropertiesPlugin;
import org.pepaproch.properties.plugin.PropertiesSensor;
import org.sonar.api.batch.fs.InputFile;
import org.sonar.api.batch.fs.internal.TestInputFileBuilder;
import org.sonar.api.batch.rule.CheckFactory;
import org.sonar.api.batch.rule.internal.ActiveRulesBuilder;
import org.sonar.api.batch.rule.internal.NewActiveRule;
import org.sonar.api.config.internal.MapSettings;
import org.sonar.api.issue.NoSonarFilter;
import org.sonar.api.resources.Language;
import org.sonar.api.rule.RuleKey;
import org.sonarsource.slang.testing.AbstractSensorTest;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Map;



public class SensorTestBase extends AbstractSensorTest {

    public static final String R_PARSING_ERROR = "S1135";


    public static final String R_HARDCODED_IP_CHECK = "S1313";

    @Override
    protected String repositoryKey() {
        return PropertiesPlugin.REPOSITORY_KEY;
    }

    @Override
    protected Language language() {
        return new PropertiesLanguage(new MapSettings().asConfig());
    }


    protected PropertiesSensor sensor(CheckFactory f) {
        return new PropertiesSensor(f, new NoSonarFilter(), fileLinesContextFactory, (PropertiesLanguage) language());
}

    private InputFile createInputFile(String relativePath, String content, Charset charsets) {

        return new TestInputFileBuilder("moduleKey", relativePath)
                .setModuleBaseDir(baseDir.toPath())
                .setType(InputFile.Type.MAIN)
                .setLanguage(language().getKey())
                .setCharset(charsets)
                .setContents(content)
                .build();

    }


    @Override
    protected InputFile createInputFile(String relativePath, String content) {
        return createInputFile(relativePath, content, StandardCharsets.UTF_8);
    }

    protected void addFile(String relativePath, String content, Charset charset) {
        context.fileSystem().add(createInputFile(relativePath, content, charset));
    }

    protected void addFile(String relativePath, String content) {
        context.fileSystem().add(createInputFile(relativePath, content, StandardCharsets.UTF_8));
    }

    protected CheckFactory checkFactory(String ruleKey, Map<String, String> parameters) {
        ActiveRulesBuilder builder = new ActiveRulesBuilder();


        NewActiveRule newActiveRule = builder.create(RuleKey.of(repositoryKey(), ruleKey)).setName(ruleKey);
        parameters.keySet().stream().forEach(k -> newActiveRule.setParam(k, parameters.get(k)));
        newActiveRule.activate();


        context.setActiveRules(builder.build());
        return new CheckFactory(context.activeRules());

    }
}