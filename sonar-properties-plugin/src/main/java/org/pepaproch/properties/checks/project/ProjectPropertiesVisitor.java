package org.pepaproch.properties.checks.project;


import org.pepaproch.properties.parser.slang.tree.PropTree;
import org.pepaproch.properties.plugin.PropertiesContext;
import org.sonarsource.slang.plugin.InputFileContext;
import org.sonarsource.slang.visitors.TreeVisitor;



public class ProjectPropertiesVisitor extends TreeVisitor<InputFileContext> {



    PropertiesContext projectContext;


    public ProjectPropertiesVisitor(PropertiesContext projectContext) {
        this.projectContext = projectContext;
        register(PropTree.class, (ctx, tree) -> projectContext.addPropertyTree(tree, ctx.inputFile));

    }


}
