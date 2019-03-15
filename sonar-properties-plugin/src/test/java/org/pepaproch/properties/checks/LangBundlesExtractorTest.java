package org.pepaproch.properties.checks;

import org.junit.Before;
import org.junit.Test;
import org.mockito.MockitoAnnotations;
import org.pepaproch.properties.checks.project.LangBundlesExtractor;
import org.pepaproch.properties.parser.slang.tree.PropTree;
import org.sonar.api.batch.fs.InputFile;

import java.util.List;
import java.util.Map;

import static junit.framework.TestCase.assertEquals;

public class LangBundlesExtractorTest extends LangBundleTestBase {


    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void extratc() {
        LangBundlesExtractor e = new LangBundlesExtractor(prepare());
        Map<InputFile, Map<InputFile, List<PropTree>>> bundles = e.getBundles();
        assertEquals(1, bundles.size());
        assertEquals(3, bundles.entrySet().stream().findFirst().get().getValue().size());

    }

    @Test
    public void extratMissingDefault() {
        LangBundlesExtractor e = new LangBundlesExtractor(prepareNoDefult());
        Map<InputFile, Map<InputFile, List<PropTree>>> bundles = e.getBundles();
        assertEquals(1, bundles.size());
        assertEquals(2, bundles.entrySet().stream().findFirst().get().getValue().size());

    }

}