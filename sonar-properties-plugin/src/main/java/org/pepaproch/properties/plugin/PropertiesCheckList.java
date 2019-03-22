package org.pepaproch.properties.plugin;

import org.pepaproch.properties.checks.*;
import org.pepaproch.properties.checks.project.DuplicatedProjectKeysRule;
import org.pepaproch.properties.checks.project.MissingDefaultBundleCheck;
import org.pepaproch.properties.checks.project.MissingInDefaultBundleCheck;
import org.sonarsource.slang.checks.*;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class PropertiesCheckList {
    public static final Predicate<Class> FILTER_SPECIAL_INIT = c-> c != CommentedCodeCheck.class ;

    private PropertiesCheckList() {
    }

    public static List<Class> checks() {



        return Collections.unmodifiableList(Arrays.asList(
                DummyCheck.class,
                DuplicatedKeysCheck.class,
                CommentedCodeCheck.class,
                CommentConventionCheck.class,
                CommentRegularExpressionCheck.class,
                EndLineCharactersCheck.class,
                HardcodedIpCheck.class,
                org.pepaproch.properties.checks.HardcodedCredentialsCheck.class,
                StringLiteralDuplicatedCheck.class,
                TodoCommentCheck.class,
                MissingDefaultBundleCheck.class,
                MissingInDefaultBundleCheck.class,
                KeyRegularExpressionCheck.class,
                FixMeCommentCheck.class,
                TooLongLineCheck.class,
                TooManyLinesOfCodeFileCheck.class,
                ParsingErrorCheck.class,
                EmptyValueCheck.class,
                FileNameCheck.class,
                ValueRegularExpressionCheck.class,
                DuplicatedProjectKeysRule.class
                ));
    }

    public static List<Class> checks(Predicate<Class> filter) {
       return checks().stream().filter(filter).collect(Collectors.toList());
    }


    public static List<Class> checks(Predicate<Class> ... orFilters) {
        return checks().stream().filter(Stream.of(orFilters).reduce(Predicate::and).orElse(c -> false)).collect(Collectors.toList());
    }

    public static List<Class> visitors() {
        return Collections.unmodifiableList(Arrays.asList(
                ProjectPropertiesVisitor.class
     ));
    }


}
