package org.pepaproch.properties.parser.slang.tree;

import org.sonar.api.batch.fs.internal.DefaultTextPointer;
import org.sonar.api.batch.fs.internal.DefaultTextRange;
import org.sonarsource.slang.api.HasTextRange;
import org.sonarsource.slang.api.TextRange;
import org.sonarsource.slang.impl.TextRangeImpl;

import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class TextRangeUtils {

    private  TextRangeUtils() {
    }

    public static <T extends HasTextRange> TextRange wholeRange(T... l) {

        List<HasTextRange> sorted = Stream.of(l).filter(Objects::nonNull).sorted(Comparator.comparing(e -> e.textRange().start())).collect(Collectors.toList());
        return new TextRangeImpl(sorted.get(0).textRange().start(), sorted.get(sorted.size() - 1).textRange().end());

    }

    public static org.sonar.api.batch.fs.TextRange sonarTextRange(TextRange textRange) {
        return new DefaultTextRange(new DefaultTextPointer(textRange.start().line(), textRange.start().lineOffset()), new DefaultTextPointer(textRange.end().line(), textRange.end().lineOffset()));

    }
}
