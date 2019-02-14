package com.pepaproch.jproperties.parser;


import com.sonar.sslr.api.typed.ActionParser;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;


public class JpropertiesParserTest {


    @Test
    public void createParser() {
        JpNodeBuilder jpNodeBuilder = new JpNodeBuilder();
        JTreeFactory jTreeFactory = new JTreeFactory();
        ActionParser<PropertiesTree> tokenActionParser = JpropertiesParser.create(jTreeFactory, jpNodeBuilder);
        PropertiesTree parse = tokenActionParser.parse("key = value");
        assertEquals(1, parse.children().size());

    }

}