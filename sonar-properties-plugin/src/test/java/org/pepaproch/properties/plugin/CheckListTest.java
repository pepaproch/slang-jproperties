package org.pepaproch.properties.plugin;

import org.junit.Test;
import org.pepaproch.properties.checks.CheckList;
import org.pepaproch.properties.checks.CommentRegularExpressionCheck;
import org.sonarsource.slang.checks.CommentedCodeCheck;

import java.util.List;

import static org.junit.Assert.*;

public class CheckListTest {


    @Test
    public void checks1() {
        List<Class> checks = CheckList.checks(CheckList.FILTER_SPECIAL_INIT);
        boolean contains = checks.contains(CommentedCodeCheck.class);
        assertEquals(false,contains);
    }

    @Test
    public void checks2() {
        List<Class> checks = CheckList.checks(CheckList.FILTER_SPECIAL_INIT , c-> true , c->true) ;

        assertFalse(checks.isEmpty());

        List<Class> checksa = CheckList.checks(CheckList.FILTER_SPECIAL_INIT , c-> true , c->false) ;
        assertTrue(checksa.isEmpty());

        List<Class> checksb = CheckList.checks(CheckList.FILTER_SPECIAL_INIT , c-> c== CommentRegularExpressionCheck.class) ;
        assertEquals(1,checksb.size());
    }


}