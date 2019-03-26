package org.pepaproch.properties.checks.project;


import org.pepaproch.properties.parser.slang.tree.PropTree;
import org.pepaproch.properties.plugin.PostAnalyseCheks;
import org.sonar.api.batch.fs.InputFile;
import org.sonar.check.Rule;

import java.util.List;
import java.util.Map;


@Rule(key = "missing-in-descendant-bundle")
public class MissingInDescendantBundleCheck extends MissingInBundles implements ProjectCheck {

    public static final String ISSUE_MESSAGE = "This key %s is present in default bundle. Translation may not be complete";


    @Override
    public void accept(PostAnalyseCheks.PCheckContext pctx) {
        super.proccess(pctx);
    }




    @Override
    void processIssues(Map.Entry<InputFile, List<PropTree>> defaultBundle, Map<InputFile, List<PropTree>> descendants, PostAnalyseCheks.PCheckContext pctx) {

        descendants.entrySet().forEach(d -> {
            List<PropTree> descProps = d.getValue();
            List<PropTree> propTrees = missingKeysIn(descProps, defaultBundle.getValue());
            propTrees.forEach(p -> pctx.reportIssue(d.getKey(), String.format(ISSUE_MESSAGE, p.key.identifier())));

        });

    }
}
