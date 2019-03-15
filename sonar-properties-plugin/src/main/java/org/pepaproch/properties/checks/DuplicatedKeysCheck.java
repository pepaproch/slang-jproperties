package org.pepaproch.properties.checks;

import org.pepaproch.properties.checks.project.PropertiesBucket;
import org.pepaproch.properties.parser.slang.tree.PropTree;
import org.pepaproch.properties.parser.slang.tree.PropsTree;
import org.sonar.check.Rule;
import org.sonarsource.slang.checks.api.InitContext;
import org.sonarsource.slang.checks.api.SecondaryLocation;
import org.sonarsource.slang.checks.api.SlangCheck;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@Rule(key = "duplicatedKeys")
public class DuplicatedKeysCheck implements SlangCheck {


    @Override
    public void initialize(InitContext init) {
        init.register(PropsTree.class, (ctx, tree) -> {
            PropertiesBucket<String> allProperties = new PropertiesBucket();
            tree.declarations().stream().map(p -> (PropTree) p)
                    .forEach(p -> allProperties.processItem(p, ctx.filename()));

            Map<String, LinkedList<TokenLocations<String>>> locations = new DuplicationExtractor<>(allProperties.getProps()).duplications(1);
            if (locations.keySet().size() > 1) {
                locations.keySet().forEach(k -> {
                            List<SecondaryLocation> secondaryLocations = new ArrayList<>();
                            locations.get(k).stream().skip(1).forEach(
                                    l -> secondaryLocations.add(
                                            new SecondaryLocation(l.location, "key: " + k + "is already defined in this file")
                                    ));
                            ctx.reportIssue(locations.get(k).get(0), "Dont duplicate keys", secondaryLocations);

                        }

                );

            }


        });
    }


}
