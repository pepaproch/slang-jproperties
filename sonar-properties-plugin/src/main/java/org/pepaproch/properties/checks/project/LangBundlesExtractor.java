package org.pepaproch.properties.checks.project;

import org.pepaproch.properties.parser.slang.tree.PropTree;
import org.sonar.api.batch.fs.InputFile;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.Locale.getAvailableLocales;

public class LangBundlesExtractor {


    private final Map<InputFile, List<PropTree>> props;


    public LangBundlesExtractor(Map<InputFile, List<PropTree>> props) {
        this.props = props;
    }


    public List<LangBundle> getLangBundles() {
        Map<String, LangBundle> bundleMap = new HashMap<>();

        props.forEach((k, v) -> {
            if (bundleMap.containsKey(LangBundle.defaultFileName(k))) {
                LangBundle langBundle = bundleMap.get(LangBundle.defaultFileName(k));
                langBundle.getBundleMembers().put(k, v);
            } else {
                LangBundle langBundle = new LangBundle(LangBundle.defaultFileName(k));
                langBundle.getBundleMembers().put(k, v);
                bundleMap.put(langBundle.getBundleName(), langBundle);
            }

        });

        return bundleMap.entrySet().stream().map(e -> e.getValue()).collect(Collectors.toList());

    }


    private boolean hasLangBundleName(InputFile f) {
        Locale[] availableLocales = getAvailableLocales();
        return Stream.of(availableLocales).anyMatch(l -> stripExtension(f.filename()).endsWith(l.getLanguage()));

    }


    private String stripLang(String fileName) {
        int i = fileName.lastIndexOf('_');
        return i == -1 ? fileName : fileName.substring(0, i);

    }

    private String stripExtension(String fileName) {
        int ie = fileName.lastIndexOf('.');
        return fileName.substring(0, ie);
    }


}
