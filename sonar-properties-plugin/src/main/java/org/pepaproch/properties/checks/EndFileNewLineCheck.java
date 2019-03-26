package org.pepaproch.properties.checks;

import org.pepaproch.properties.parser.slang.tree.PropsTree;
import org.sonar.check.Rule;
import org.sonarsource.slang.checks.api.InitContext;
import org.sonarsource.slang.checks.api.SlangCheck;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Rule(key = "empty-line-end-of-file")
public class EndFileNewLineCheck implements SlangCheck {


    private static final String ISSUE_MESSAGE = "Add an empty new line at the end of this file.";

final Pattern pattern = Pattern.compile("\\Z([\\n]|[\\r]|[\\r\\n])");




    @Override
    public void initialize(InitContext init) {
      init.register(PropsTree.class, (ctx, tree) -> {
          Matcher matcher = pattern.matcher(ctx.fileContent());
          if(!matcher.find()) {
              ctx.reportFileIssue(ISSUE_MESSAGE);
          }
      });

    }
}
