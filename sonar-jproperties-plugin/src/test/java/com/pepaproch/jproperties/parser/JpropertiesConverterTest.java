package com.pepaproch.jproperties.parser;

import com.pepaproch.jproperties.parser.slang.JpropertiesConverter;
import com.pepaproch.jproperties.parser.sslr.JTreeFactory;
import com.pepaproch.jproperties.parser.slang.JpNodeBuilder;
import org.junit.Before;
import org.junit.Test;
import org.sonarsource.slang.api.Tree;
import org.sonarsource.slang.visitors.TreeContext;
import org.sonarsource.slang.visitors.TreeVisitor;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class JpropertiesConverterTest {

    JpropertiesConverter converter;

    @Before
    public void setUp() throws Exception {
        JpNodeBuilder jpNodeBuilder = new JpNodeBuilder();
        JTreeFactory jTreeFactory = new JTreeFactory();
        converter = JpropertiesConverter.create();
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
}