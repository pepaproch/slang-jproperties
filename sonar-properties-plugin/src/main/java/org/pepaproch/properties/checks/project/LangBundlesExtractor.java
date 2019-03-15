package org.pepaproch.properties.checks.project;

import org.pepaproch.properties.parser.slang.tree.PropTree;
import org.sonar.api.batch.fs.InputFile;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.Locale.getAvailableLocales;

public class LangBundlesExtractor {


    private final Map<InputFile, List<PropTree>> props;


    public LangBundlesExtractor(Map<InputFile, List<PropTree>> props) {
        this.props = props;
    }


    public Map<InputFile, Map<InputFile, List<PropTree>>> getBundles() {
        Map<InputFile, Map<InputFile, List<PropTree>>> bundles = new HashMap<>();
        props.forEach((inputFile, propTrees) -> {
            if (hasLangBundleName(inputFile)) {
                processHasLangExtension(inputFile, propTrees, bundles);
            } else {
                Map<InputFile, List<PropTree>> bundleMap = new LinkedHashMap<>();
                bundleMap.put(inputFile, propTrees);
                bundles.put(inputFile, bundleMap);
            }


        });
        return bundles.entrySet().stream().filter(e -> e.getValue().size() > 1).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

    }

    private void processHasLangExtension(InputFile f, List<PropTree> props, Map<InputFile, Map<InputFile, List<PropTree>>> bundles) {
        Optional<Map.Entry<InputFile, Map<InputFile, List<PropTree>>>> inputFileMapEntry = ownerBudle(f, bundles);
        if (inputFileMapEntry.isPresent()) {
            bundles.get(inputFileMapEntry.get().getKey()).put(f, props);
        } else {
            Map<InputFile, List<PropTree>> bundleMap = new LinkedHashMap<>();
            bundleMap.put(f, props);
            bundles.put(f, bundleMap);
        }

    }


    private Optional<Map.Entry<InputFile, Map<InputFile, List<PropTree>>>> ownerBudle(InputFile f, Map<InputFile, Map<InputFile, List<PropTree>>> bundles) {
        return bundles.entrySet().
                stream().filter(ifi -> stripLang(stripExtension(ifi.getKey().filename())).equalsIgnoreCase(stripLang(stripExtension(f.filename())))).findFirst();

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
