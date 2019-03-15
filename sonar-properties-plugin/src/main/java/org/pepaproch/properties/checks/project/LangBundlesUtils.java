package org.pepaproch.properties.checks.project;

import org.sonar.api.batch.fs.InputFile;

import java.nio.file.Paths;
import java.util.regex.Pattern;

public class LangBundlesUtils {
    private static final Pattern pattern  = Pattern.compile("(_[a-z]{2})(?:_[A-Z]{2})*(?:\\.properties)");


    private static boolean isBundleFile(InputFile f) {

        Paths.get(f.uri()).getFileName();
        return  false;
    }
}
