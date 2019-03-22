package org.pepaproch.properties.checks.project;


import org.pepaproch.properties.parser.slang.tree.PropTree;
import org.pepaproch.properties.plugin.PostAnalyseCheks;
import org.sonar.api.batch.fs.InputFile;
import org.sonar.check.Rule;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Rule(key = "missing-in-default-bundle")
public class MissingInDefaultBundleCheck  implements ProjectCheck {

    public static final String ISSUE_MESSAGE = "This key %s is present in descendant bundle.";



    @Override
    public void accept(PostAnalyseCheks.PCheckContext pctx) {
        List<LangBundle> langBundles = new LangBundlesExtractor(pctx.getPctx().getProjectProperties().getProps()).getLangBundles();
        langBundles.stream().filter(b->b.defaulBudleFile().isPresent()).forEach(b-> {
            Map.Entry<InputFile, List<PropTree>> inputFileListEntry = b.defaulBudleFile().get();
            Map<InputFile, List<PropTree>> descendants = b.descendants();
              descendants.entrySet().forEach(d->{
                  List<PropTree> propTrees = missingKeysIn(inputFileListEntry.getValue(), d.getValue());
                  propTrees.forEach(p-> pctx.reportIssue(inputFileListEntry.getKey(), String.format(ISSUE_MESSAGE, p.key.value())));

              });

        });

    }

    private List<PropTree> missingKeysIn(List<PropTree> in, List<PropTree> keys ) {
        List<String> inl = in.stream().map(p -> p.key.value()).collect(Collectors.toList());
        List<String> keyl = keys.stream().map(p -> p.key.value()).collect(Collectors.toList());
        keyl.removeAll(inl);
        return   in.stream().filter(p-> keyl.contains(p.key.value())).collect(Collectors.toList());


    }
}
