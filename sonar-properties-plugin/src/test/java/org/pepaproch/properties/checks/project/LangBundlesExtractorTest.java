package org.pepaproch.properties.checks.project;

import org.junit.Before;
import org.junit.Test;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static junit.framework.TestCase.assertEquals;

public class LangBundlesExtractorTest extends LangBundleTestBase {


    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void extratc() {
        LangBundlesExtractor e = new LangBundlesExtractor(prepare());
        List<LangBundle> langBundles = e.getLangBundles();
        assertEquals(2, langBundles.size());
        assertEquals(3, langBundles.get(1).getBundleMembers().size());

    }

    @Test
    public void extratMissingDefault() {
        LangBundlesExtractor e = new LangBundlesExtractor(prepareNoDefult());
        List<LangBundle> langBundles = e.getLangBundles();
        assertEquals(2, langBundles.size());
        assertEquals(2, langBundles.get(1).getBundleMembers().size());

    }

}