package com.pepaproch.jproperties.parser.slang.tree;

import org.sonarsource.slang.api.HasTextRange;
import org.sonarsource.slang.api.TextRange;
import org.sonarsource.slang.impl.TextRangeImpl;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class TextRangeUtils {

    public static TextRange wholeRange(HasTextRange... l) {
        List<HasTextRange> sorted = Stream.of(l).sorted(Comparator.comparing(e -> e.textRange().start())).collect(Collectors.toList());
        TextRange wholeRange = new TextRangeImpl(sorted.get(0).textRange().start(), sorted.get(sorted.size() - 1).textRange().end());
        return wholeRange;
    }
}
