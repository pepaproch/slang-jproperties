package org.pepaproch.properties.checks.project;

import com.sonar.sslr.api.typed.ActionParser;
import org.mockito.Mockito;
import org.pepaproch.properties.parser.slang.PropertiesNodeBuilder;
import org.pepaproch.properties.parser.slang.tree.PropTree;
import org.pepaproch.properties.parser.slang.tree.PropsTree;
import org.pepaproch.properties.parser.sslr.JpropertiesParser;
import org.pepaproch.properties.parser.sslr.PropertiesTreeFactory;
import org.sonar.api.batch.fs.InputFile;

import java.net.URI;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import static org.mockito.Mockito.when;

public class LangBundleTestBase {

    protected Map<InputFile, List<PropTree>> prepare(){
        InputFile defaultf = Mockito.mock(InputFile.class);
        InputFile enf = Mockito.mock(InputFile.class);
        InputFile czf = Mockito.mock(InputFile.class);
        InputFile otherf = Mockito.mock(InputFile.class);
        when(defaultf.filename()).thenReturn("lang.properties");
        when(defaultf.uri()).thenReturn(URI.create("lang.properties"));
        when(enf.filename()).thenReturn("lang_en.properties");
        when(enf.uri()).thenReturn(URI.create("lang_en.properties"));
        when(czf.filename()).thenReturn("lang_cz.properties");
        when(czf.uri()).thenReturn(URI.create("lang_cz.properties"));
        when(otherf.filename()).thenReturn("parent/notlang_cz.properties");
        when(otherf.uri()).thenReturn(URI.create("parent/notlang_cz.properties"));

        Map<InputFile, List<PropTree>> props = new HashMap<>();
        PropertiesNodeBuilder propertiesNodeBuilder = new PropertiesNodeBuilder();
        PropertiesTreeFactory propertiesTreeFactory = new PropertiesTreeFactory();
        ActionParser<PropsTree> tokenActionParser = JpropertiesParser.create(propertiesTreeFactory, propertiesNodeBuilder);

        Stream.of(defaultf,enf,czf,otherf).forEach(f-> {
            PropsTree parse = tokenActionParser.parse("k1=v1" +f.filename()+ "\n" );
            props.put(f,parse.getChildren());


        });
return props;
    }

    protected Map<InputFile, List<PropTree>> prepareNoDefult(){

        InputFile enf = Mockito.mock(InputFile.class);
        InputFile czf = Mockito.mock(InputFile.class);
        InputFile otherf = Mockito.mock(InputFile.class);

        when(enf.filename()).thenReturn("lang_en.properties");
        when(enf.uri()).thenReturn(URI.create("lang_en.properties"));
        when(czf.filename()).thenReturn("lang_cz.properties");
        when(czf.uri()).thenReturn(URI.create("lang_cz.properties"));
        when(otherf.filename()).thenReturn("parent/notlang_cz.properties");
        when(otherf.uri()).thenReturn(URI.create("parent/notlang_cz.properties"));

        Map<InputFile, List<PropTree>> props = new HashMap<>();
        PropertiesNodeBuilder propertiesNodeBuilder = new PropertiesNodeBuilder();
        PropertiesTreeFactory propertiesTreeFactory = new PropertiesTreeFactory();
        ActionParser<PropsTree> tokenActionParser = JpropertiesParser.create(propertiesTreeFactory, propertiesNodeBuilder);

        Stream.of(enf,czf,otherf).forEach(f-> {
            PropsTree parse = tokenActionParser.parse("k1=v1" +f.filename()+ "\n" );
            props.put(f,parse.getChildren());


        });
        return props;
    }

}
