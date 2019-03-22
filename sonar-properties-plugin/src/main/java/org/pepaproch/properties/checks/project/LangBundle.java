package org.pepaproch.properties.checks.project;

import org.pepaproch.properties.parser.slang.tree.PropTree;
import org.sonar.api.batch.fs.InputFile;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class LangBundle {


    private final String bundleName;
    private final Map<InputFile, List<PropTree>> bundleMembers = new HashMap();

    LangBundle(String bundleName) {
        this.bundleName = bundleName;

    }

    public static boolean isDefaultBundleName(InputFile f) {
        String fileName = stripExtension(f.filename());
        int i = fileName.lastIndexOf('/');
        String substring = fileName.substring(0, i == -1 ? fileName.length() : i);
        return !substring.contains("_");
    }

    public static String defaultFileName(InputFile file) {
        String fileName = stripExtension(file.filename());
        int i = fileName.lastIndexOf('_');

        return fileName.substring(0, i == -1 ? fileName.length() : i);
    }

    public String getBundleName() {
        return bundleName;
    }

    public Map<InputFile, List<PropTree>> getBundleMembers() {
        return bundleMembers;
    }

    public Optional<Map.Entry<InputFile, List<PropTree>>> defaulBudleFile() {
        return bundleMembers.entrySet().stream().filter(e -> isDefaultBundleName(e.getKey())).findFirst();
    }

    public Map<InputFile, List<PropTree>> descendants() {
        return bundleMembers.entrySet().stream().filter(e -> !isDefaultBundleName(e.getKey())).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    public static String stripExtension(String fileName) {
        int i = fileName.lastIndexOf('.');
        return fileName.substring(0, i == -1 ? fileName.length() : i);
    }


}
