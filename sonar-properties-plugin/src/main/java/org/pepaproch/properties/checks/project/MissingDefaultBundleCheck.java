package org.pepaproch.properties.checks.project;


import org.pepaproch.properties.parser.slang.tree.PropTree;
import org.pepaproch.properties.plugin.PostAnalyseCheks;
import org.sonar.api.batch.fs.InputFile;
import org.sonar.check.Rule;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Rule(key = "missing-default-bundle")
public class MissingDefaultBundleCheck implements ProjectCheck {

    public static final String ISSUE_MESSAGE = "Default bundle %s should be present";


    @Override
    public void accept(PostAnalyseCheks.PCheckContext pctx) {
        new LangBundlesExtractor(pctx.getPctx().getProjectProperties().getProps()).getLangBundles().forEach(b -> {
            Optional<Map.Entry<InputFile, List<PropTree>>> inputFileListEntry = b.defaulBudleFile();
            if (!inputFileListEntry.isPresent()) {
                b.getBundleMembers().entrySet().forEach((e) -> {
                    pctx.reportIssue(e.getKey(), String.format(MissingDefaultBundleCheck.ISSUE_MESSAGE, b.getBundleName()));
                });

            }

        });

    }
}
