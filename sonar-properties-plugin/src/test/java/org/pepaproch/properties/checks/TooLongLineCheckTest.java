package org.pepaproch.properties.checks;

import org.junit.Test;
import org.pepaproch.properties.parser.SensorTestBase;
import org.sonar.api.batch.rule.CheckFactory;

import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.junit.Assert.assertEquals;

public class TooLongLineCheckTest extends SensorTestBase {
    @Test
    public void toManyLinesOfCode() {
        String collectValue = IntStream.range(1, 2002).mapToObj((i) -> Integer.valueOf(i).toString()).collect(Collectors.joining(""));

        CheckFactory f = checkFactory("S103");
        addFile("TooManyLinesOfCodeFileCheckTest.properties" , "key=" + collectValue);
        sensor(f).execute(context);
        int size = context.allIssues().size();
        assertEquals(1,size);

    }
}
