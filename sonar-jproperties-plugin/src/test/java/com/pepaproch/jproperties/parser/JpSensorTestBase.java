package com.pepaproch.jproperties.parser;

import com.pepaproch.jproperties.plugin.JpropertiesLanguage;
import com.pepaproch.jproperties.plugin.JpropertiesPlugin;
import com.pepaproch.jproperties.plugin.JpropertiesSensor;
import org.sonar.api.batch.fs.InputFile;
import org.sonar.api.batch.fs.internal.TestInputFileBuilder;
import org.sonar.api.batch.rule.CheckFactory;
import org.sonar.api.config.internal.MapSettings;
import org.sonar.api.issue.NoSonarFilter;
import org.sonar.api.resources.Language;
import org.sonarsource.slang.testing.AbstractSensorTest;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

public class JpSensorTestBase extends AbstractSensorTest {

    public static  final String R_HARDCODED_IP_CHECK = "S1313";
    public static final String R_STRING_LITERAL_DUPLICATE_CHECK = "S1192";


    @Override
    protected String repositoryKey() {
        return JpropertiesPlugin.REPOSITORY_KEY;
    }

    @Override
    protected Language language() {
        return new JpropertiesLanguage(new MapSettings().asConfig());
    }


    protected JpropertiesSensor sensor(CheckFactory f) {
        return new JpropertiesSensor(f, new NoSonarFilter(), fileLinesContextFactory, (JpropertiesLanguage) language());
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

    protected  void addFile(String relativePath, String content, Charset charset) {
       context.fileSystem().add(createInputFile(relativePath,content, charset));
    }

    protected  void addFile(String relativePath, String content) {
        context.fileSystem().add(createInputFile(relativePath,content, StandardCharsets.UTF_8));
    }
}
