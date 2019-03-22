package org.pepaproch.properties.plugin;

import org.pepaproch.properties.checks.project.ProjectCheck;
import org.pepaproch.properties.parser.slang.tree.TextRangeUtils;
import org.sonar.api.batch.fs.InputFile;
import org.sonar.api.batch.rule.Checks;
import org.sonar.api.batch.sensor.SensorContext;
import org.sonar.api.batch.sensor.issue.NewIssue;
import org.sonar.api.batch.sensor.issue.NewIssueLocation;
import org.sonar.api.rule.RuleKey;
import org.sonar.api.utils.log.Logger;
import org.sonar.api.utils.log.Loggers;
import org.sonarsource.slang.api.TextRange;
import org.sonarsource.slang.api.Tree;

import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.stream.Collectors;

public class PostAnalyseCheks implements Consumer<PostAnalyseContext> {
    private static final Logger LOG = Loggers.get(PostAnalyseCheks.class);
    private final SensorContext ctx;


    public PostAnalyseCheks(SensorContext ctx) {
        this.ctx = ctx;
    }

    @Override
    public void accept(PostAnalyseContext postAnalyseContext) {

        Checks<ProjectCheck> pchecks = postAnalyseContext.propertiesSensor.pchecks();

        pchecks.all().stream().forEach(c -> {
            c.accept(new PCheckContext(postAnalyseContext, pchecks.ruleKey(c), ctx));
        });
    }


    public class PCheckContext {

        private final PostAnalyseContext pctx;
        private final RuleKey ruleKey;
        private final SensorContext sctx;


        public PCheckContext(PostAnalyseContext pctx, RuleKey ruleKey, SensorContext sctx) {
            this.pctx = pctx;
            this.ruleKey = ruleKey;
            this.sctx = sctx;
        }


        public void reportIssue(InputFile file, TextRange range, String format, Map<InputFile, TextRange> secondary, String message) {
            NewIssue newIssue = sctx.newIssue().forRule(ruleKey);
            newIssue.at(newIssue.newLocation().on(file).at(TextRangeUtils.sonarTextRange(range)).message(message));
            List<NewIssueLocation> sl = secondary.entrySet().stream().map(e -> newIssue.newLocation().on(e.getKey()).at(TextRangeUtils.sonarTextRange(e.getValue()))).collect(Collectors.toList());
            sl.forEach(sli -> newIssue.addLocation(sli));
            newIssue.addFlow(sl);
            newIssue.save();

        }

        public void reportIssue(InputFile file, Tree tree, String message, List<NewIssueLocation> secondary) {
            NewIssue newIssue = sctx.newIssue().forRule(ruleKey);
            newIssue.at(newIssue.newLocation().on(file).at(TextRangeUtils.sonarTextRange(tree.textRange())).message(message));
            secondary.forEach(nl -> newIssue.addLocation(nl));
            newIssue.addFlow(secondary);
            newIssue.save();

        }


        public void reportIssue(InputFile file,  String message) {
            NewIssue newIssue = sctx.newIssue().forRule(ruleKey);
            newIssue.at(newIssue.newLocation().on(file).message(message));
            newIssue.save();

        }
        public void reportIssue(InputFile file, Tree tree, String message) {
            NewIssue newIssue = sctx.newIssue().forRule(ruleKey);
            newIssue.at(newIssue.newLocation().on(file).at(TextRangeUtils.sonarTextRange(tree.textRange())).message(message));
            newIssue.save();

        }




        public PostAnalyseContext getPctx() {
            return pctx;
        }
    }


}

