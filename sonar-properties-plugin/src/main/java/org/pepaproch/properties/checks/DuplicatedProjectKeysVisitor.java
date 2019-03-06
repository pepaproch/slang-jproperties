package org.pepaproch.properties.checks;


import org.pepaproch.properties.parser.slang.tree.PropKeyTree;
import org.pepaproch.properties.plugin.PropertiesContext;
import org.sonar.check.Rule;

import org.sonar.check.RuleProperty;

import org.sonarsource.slang.plugin.InputFileContext;
import org.sonarsource.slang.visitors.TreeVisitor;


@Rule(key = "duplicatedCheck")
public class DuplicatedProjectKeysVisitor extends TreeVisitor<InputFileContext>   {

    private static final int DEFAULT_THRESHOLD = 1;
    @RuleProperty(
            key = "threshold",
            description = "Number of times a key must be duplicated across files to trigger an issue",
            defaultValue = "" + DEFAULT_THRESHOLD)
    public int threshold = DEFAULT_THRESHOLD;


    PropertiesContext projectContext;




    public DuplicatedProjectKeysVisitor(PropertiesContext projectContext) {
        this.projectContext = projectContext;
        projectContext.duplicationTreshold = threshold;
        register(PropKeyTree.class , (ctx, tree ) -> {
            projectContext.addToken(tree.metaData().tokens().get(0), ctx.inputFile);

        } );

    }






}
