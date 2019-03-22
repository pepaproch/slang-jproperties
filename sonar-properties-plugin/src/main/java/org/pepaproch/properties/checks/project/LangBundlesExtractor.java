package org.pepaproch.properties.checks.project;

import org.pepaproch.properties.parser.slang.tree.PropTree;
import org.sonar.api.batch.fs.InputFile;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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

        return bundleMap.entrySet().stream().map(Map.Entry::getValue).collect(Collectors.toList());

    }






}
