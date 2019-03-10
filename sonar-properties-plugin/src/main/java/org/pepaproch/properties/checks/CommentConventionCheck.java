package org.pepaproch.properties.checks;

import org.pepaproch.properties.parser.slang.tree.PropsTree;
import org.sonar.check.Rule;
import org.sonarsource.slang.api.Comment;
import org.sonarsource.slang.checks.api.InitContext;
import org.sonarsource.slang.checks.api.SecondaryLocation;
import org.sonarsource.slang.checks.api.SlangCheck;

import java.util.List;
import java.util.stream.Collectors;

@Rule(key = "comment-convention")
public class CommentConventionCheck implements SlangCheck {
    private static final String ISSUE_MESSAGE = "Use starting comment token '%s' instead.";

    @Override
    public void initialize(InitContext init) {
        init.register(PropsTree.class, (ctx, tree) -> {
            List<Comment> comments = tree.metaData().commentsInside();
            if (null != comments && !comments.isEmpty()) {
                String text = comments.get(0).text().trim().substring(1, 2);
                List<Comment> collect = comments.stream().skip(1).
                        filter(c -> !c.text().trim().startsWith(text)).collect(Collectors.toList());


                    List<SecondaryLocation> secondaryLocations = collect.stream().skip(1).
                            map(c -> new SecondaryLocation(c.textRange(), String.format(ISSUE_MESSAGE, c.text().trim().substring(1, 2))))
                            .collect(Collectors.toList());
                    ctx.reportIssue(collect.get(0), ISSUE_MESSAGE, secondaryLocations);


                comments.stream().filter(c -> {
                    String comment = c.text();
                    if (comment.length() < 2) {
                        return true;
                    } else {
                        return !Character.isWhitespace(comment.charAt(1));

                    }
                }).forEach(c ->ctx.reportIssue(c.textRange(), "Add a whitespace after the starting comment token."));
            }

        });
    }


}
