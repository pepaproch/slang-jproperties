package org.pepaproch.properties.checks.project;


import org.pepaproch.properties.checks.DuplicationExtractor;
import org.pepaproch.properties.checks.TokenLocations;
import org.pepaproch.properties.plugin.PostAnalyseCheks;
import org.sonar.api.batch.fs.InputFile;
import org.sonar.check.Rule;
import org.sonar.check.RuleProperty;
import org.sonarsource.slang.api.TextRange;

import java.util.LinkedList;
import java.util.Map;
import java.util.stream.Collectors;


@Rule(key = "duplicatedCheck")
public class DuplicatedProjectKeysRule implements ProjectCheck {
    private static final String ISSUE_MESSAGE = "The key %s is duplicated in other files";
    private static final int DEFAULT_THRESHOLD = 1;
    @RuleProperty(
            key = "threshold",
            description = "Number of times a key must be duplicated across files to trigger an issue",
            defaultValue = "" + DEFAULT_THRESHOLD)
    public int threshold = DEFAULT_THRESHOLD;


    @Override
    public void accept(PostAnalyseCheks.PCheckContext pctx) {


        DuplicationExtractor<InputFile> inputFileDuplicationExtractor = new DuplicationExtractor<>(pctx.getPctx().getProjectProperties().getProps());
        Map<String, LinkedList<TokenLocations<InputFile>>> duplications = inputFileDuplicationExtractor.duplications(threshold );

        duplications.entrySet().forEach(e-> {
            TokenLocations<InputFile> primary = e.getValue().iterator().next();
            Map<InputFile, TextRange> secondary = e.getValue().stream().skip(1).collect(Collectors.toMap(TokenLocations::getModule, TokenLocations::textRange));
            pctx.reportIssue(primary.getModule(),primary.textRange(), String.format(ISSUE_MESSAGE , e.getKey()) , secondary, "Key was used before in " + primary.getModule().filename() );

        });
    }

}
