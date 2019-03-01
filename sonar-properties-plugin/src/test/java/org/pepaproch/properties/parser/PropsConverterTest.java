package org.pepaproch.properties.parser;

import org.pepaproch.properties.parser.slang.PropertiesConverter;
import org.pepaproch.properties.parser.sslr.PropertiesTreeFactory;
import org.pepaproch.properties.parser.slang.PropertiesNodeBuilder;
import org.junit.Before;
import org.junit.Test;
import org.sonarsource.slang.api.Tree;
import org.sonarsource.slang.visitors.TreeContext;
import org.sonarsource.slang.visitors.TreeVisitor;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class PropsConverterTest {

    PropertiesConverter converter;

    @Before
    public void setUp() throws Exception {
        PropertiesNodeBuilder propertiesNodeBuilder = new PropertiesNodeBuilder();
        PropertiesTreeFactory propertiesTreeFactory = new PropertiesTreeFactory();
        converter = PropertiesConverter.create();
    }

    @Test
    public void parse() {
        Tree parsedTree = converter.parse(JpropertiesTestBase.WIKY_PROP_EXAMPLE);
        TreeVisitor<TreeContext> visitor = new TreeVisitor<>();
        List<Tree> visited = new ArrayList<>();
        visitor.register(Tree.class, (ctx, tree) -> visited.add(tree));
        visitor.scan(new TreeContext(), parsedTree);
        int size = parsedTree.metaData().commentsInside().size();
        assertEquals(6, parsedTree.children().size());
    }

    @Test
    public void parse1() {
        Tree parsedTree = converter.parse("dummy=dummykey\n" +
                "                        \"notdummykey=notdummyvalue");
        TreeVisitor<TreeContext> visitor = new TreeVisitor<>();
        List<Tree> visited = new ArrayList<>();
        visitor.register(Tree.class, (ctx, tree) -> visited.add(tree));
        visitor.scan(new TreeContext(), parsedTree);

        assertEquals(2, parsedTree.children().size());
    }

    @Test
    public void parseC() {
        Tree parsedTree = converter.parse("    #\n" +
                "# Copyright (C) 2009-2018 Lightbend Inc. <https://www.lightbend.com>\n" +
                "#");
        TreeVisitor<TreeContext> visitor = new TreeVisitor<>();
        List<Tree> visited = new ArrayList<>();
        visitor.register(Tree.class, (ctx, tree) -> visited.add(tree));
        visitor.scan(new TreeContext(), parsedTree);

        assertEquals(0, parsedTree.children().size());
    }



    @Test
    public void parseMisingValue() {
        Tree parsedTree = converter.parse("key=");
        TreeVisitor<TreeContext> visitor = new TreeVisitor<>();
        List<Tree> visited = new ArrayList<>();
        visitor.register(Tree.class, (ctx, tree) -> visited.add(tree));
        visitor.scan(new TreeContext(), parsedTree);

        assertEquals(1, parsedTree.children().size());
    }


}