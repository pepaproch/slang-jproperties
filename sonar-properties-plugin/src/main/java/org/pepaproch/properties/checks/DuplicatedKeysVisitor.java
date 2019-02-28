package org.pepaproch.properties.checks;


import org.pepaproch.properties.parser.slang.tree.PropKeyTree;
import org.pepaproch.properties.plugin.PropertiesContext;
import org.sonar.check.Rule;

import org.sonarsource.slang.checks.api.InitContext;
import org.sonarsource.slang.checks.api.SlangCheck;
import org.sonarsource.slang.plugin.InputFileContext;
import org.sonarsource.slang.visitors.TreeVisitor;


@Rule(key = "duplicatedCheck")
public class DuplicatedKeysVisitor extends TreeVisitor<InputFileContext> implements SlangCheck {


    PropertiesContext projectContext;
    private int cn = 0;


    public DuplicatedKeysVisitor(PropertiesContext projectContext) {
        this.projectContext = projectContext;
        register(PropKeyTree.class , (ctx, tree ) -> {
            projectContext.addToken(tree.metaData().tokens().get(0), ctx.inputFile);

        } );

    }

    public DuplicatedKeysVisitor() {

    }


    @Override
    public void initialize(InitContext init) {
        // errors are reported in ProjectContext
    }


}
