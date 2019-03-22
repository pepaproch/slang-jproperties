package org.pepaproch.properties.plugin;


import org.pepaproch.properties.parser.slang.tree.PropsTree;
import org.sonarsource.slang.plugin.InputFileContext;
import org.sonarsource.slang.visitors.TreeVisitor;



public class ProjectPropertiesVisitor extends TreeVisitor<InputFileContext> {



    PostAnalyseContext projectContext;


    public ProjectPropertiesVisitor(PostAnalyseContext projectContext) {
        this.projectContext = projectContext;
        register(PropsTree.class, (ctx, tree) -> projectContext.addAllProps(tree, ctx.inputFile));

    }


}
