package org.pepaproch.properties.checks;

import org.junit.Test;
import org.pepaproch.properties.parser.SensorTestBase;
import org.sonar.api.batch.rule.CheckFactory;

import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.junit.Assert.assertEquals;

public class TooManyLinesOfCodeFileCheckTest extends SensorTestBase {
    @Test
    public void toManyLinesOfCode() {
        String collect = IntStream.range(1, 1002).mapToObj((i) -> {
            String line = i + "key=value" + i;
            return line;
        }).collect(Collectors.joining("\n"));

        CheckFactory f = checkFactory("S104");
        addFile("TooManyLinesOfCodeFileCheckTest.properties" , collect);
        sensor(f).execute(context);
        int size = context.allIssues().size();
        assertEquals(1,size);

    }
}
