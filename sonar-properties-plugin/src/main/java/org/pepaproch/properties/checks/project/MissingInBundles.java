package org.pepaproch.properties.checks.project;

import org.pepaproch.properties.parser.slang.tree.PropTree;
import org.pepaproch.properties.plugin.PostAnalyseCheks;
import org.sonar.api.batch.fs.InputFile;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public abstract class MissingInBundles {



    protected List<PropTree> missingKeysIn(List<PropTree> in, List<PropTree> keys) {
        List<String> inl = in.stream().map(p -> p.key.identifier()).collect(Collectors.toList());
        List<String> keyl = keys.stream().map(p -> p.key.identifier()).collect(Collectors.toList());
        keyl.removeAll(inl);
        return keys.stream().filter(p -> !inl.contains(p.key.identifier())).collect(Collectors.toList());

    }


    protected Stream<LangBundle> bundlesWithDefault(PostAnalyseCheks.PCheckContext pctx) {
        List<LangBundle> langBundles = new LangBundlesExtractor(pctx.getPctx().getProjectProperties().getProps()).getLangBundles();
        return langBundles.stream().filter(b -> b.defaulBudleFile().isPresent());
    }


    abstract void processIssues(Map.Entry<InputFile, List<PropTree>> defaultBundle, Map<InputFile, List<PropTree>> descendants ,PostAnalyseCheks.PCheckContext pctx );


    protected void proccess(PostAnalyseCheks.PCheckContext pctx) {
        bundlesWithDefault(pctx).forEach(b -> {
            Map.Entry<InputFile, List<PropTree>> inputFileListEntry = b.defaulBudleFile().get();
            Map<InputFile, List<PropTree>> descendants = b.descendants();

                processIssues(inputFileListEntry, descendants ,pctx);



        });
    }


}
