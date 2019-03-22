package org.pepaproch.properties.checks.project;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.pepaproch.properties.parser.SensorTestBase;
import org.sonar.api.batch.fs.InputFile;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.mockito.Mockito.when;

public class LangBundleTest extends SensorTestBase {


     InputFile inputFile;
     LangBundle bundle;

     @Before
     public void before() {
         inputFile = Mockito.mock(InputFile.class);
         when(inputFile.filename()).thenReturn("/lang/bundle/lang_cz.properties");
         bundle = new LangBundle(LangBundle.defaultFileName(inputFile));
     }

    @Test
    public void isDefaultBundleName() {
         assertFalse(LangBundle.isDefaultBundleName(inputFile));
    }

    @Test
    public void defaultFileName() {
         assertFalse(bundle.defaulBudleFile().isPresent());
    }

    @Test
    public void getBundleName() {
    }

    @Test
    public void getBundleMembers() {
         assertEquals(1,bundle.getBundleMembers().size());
    }

    @Test
    public void defaulBudleFile() {
    }

    @Test
    public void descendants() {
    }

    @Test
    public void stripExtension() {
    }
}