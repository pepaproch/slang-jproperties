package org.pepaproch.properties.checks.project;


import org.pepaproch.properties.parser.slang.tree.PropTree;
import org.pepaproch.properties.plugin.PostAnalyseCheks;
import org.sonar.api.batch.fs.InputFile;
import org.sonar.check.Rule;

import java.util.List;
import java.util.Map;


@Rule(key = "missing-in-default-bundle")
public class MissingInDefaultBundleCheck extends MissingInBundles implements ProjectCheck {

    public static final String ISSUE_MESSAGE = "This key %s is present in descendant bundle.";


    @Override
    public void accept(PostAnalyseCheks.PCheckContext pctx) {

        super.proccess(pctx);

    }


    @Override
    void processIssues(Map.Entry<InputFile, List<PropTree>> defaultBundle, Map<InputFile, List<PropTree>> descendants, PostAnalyseCheks.PCheckContext pctx) {

        descendants.entrySet().forEach(d -> {
            List<PropTree> propTrees = missingKeysIn(defaultBundle.getValue(), d.getValue());
            propTrees.forEach(p -> pctx.reportIssue(defaultBundle.getKey(), String.format(ISSUE_MESSAGE, p.key.identifier())));

        });
    }
}
