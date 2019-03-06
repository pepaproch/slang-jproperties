package org.pepaproch.properties.parser;


import org.pepaproch.properties.parser.slang.tree.PropsTree;
import org.pepaproch.properties.parser.sslr.PropertiesTreeFactory;
import org.pepaproch.properties.parser.slang.PropertiesNodeBuilder;
import org.pepaproch.properties.parser.sslr.JpropertiesParser;
import com.sonar.sslr.api.typed.ActionParser;
import org.junit.Test;
import org.sonarsource.slang.api.Tree;
import org.sonarsource.slang.visitors.TreeContext;
import org.sonarsource.slang.visitors.TreeVisitor;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;



public class JpropertiesParserTest extends PropertiesExampleS {


    @Test
    public void createParser() {
        PropertiesNodeBuilder propertiesNodeBuilder = new PropertiesNodeBuilder();
        PropertiesTreeFactory propertiesTreeFactory = new PropertiesTreeFactory();
        ActionParser<PropsTree> tokenActionParser = JpropertiesParser.create(propertiesTreeFactory, propertiesNodeBuilder);
        PropsTree parse = tokenActionParser.parse("# You are reading the \".properties\" entry.\n" +
                "! The exclamation mark can also mark text as comments.\n" +
                "# The key characters =, and : should be written with\n" +
                "# a preceding backslash to ensure that they are properly loaded.\n" +
                "# However, there is no need to precede the value characters =, and : by a backslash.\n" +
                "website = https://en.wikipedia.org/\n" +
                "language = English\n" +
                "# The backslash below tells the application to continue reading\n" +
                "# the value onto the next line.\n" +
                "message = Welcome to \\\n" +
                "          Wikipedia!\n" +
                "# Add spaces to the key\n" +
                "key\\ with\\ spaces = This is the value that could be looked up with the key \"key with spaces\".\n" +
                "# Unicode\n" +
                "tab : \\u0009\n" +
                "# If you want your property to include a backslash, it should be escaped by another backslash\n" +
                "path=c:\\wiki\\templates\n" +
                "# However, some editors will handle this automatically");


        TreeVisitor<TreeContext> visitor = new TreeVisitor<>();
        List<Tree> visited = new ArrayList<>();
        visitor.register(Tree.class, (ctx, tree) -> visited.add(tree));
        visitor.scan(new TreeContext(), parse);

        assertEquals(6, parse.children().size());
    }

}