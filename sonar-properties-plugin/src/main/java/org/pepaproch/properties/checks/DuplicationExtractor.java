package org.pepaproch.properties.checks;

import org.pepaproch.properties.parser.slang.tree.PropTree;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public  class DuplicationExtractor<C> {


    private final Map<C, List<PropTree>> props;

    public DuplicationExtractor(Map<C, List<PropTree>> props) {
        this.props = props;
    }

    private Map<String, LinkedList<TokenLocations<C>>> extractDuplications() {

        final Map<String, LinkedList<TokenLocations<C>>> map = new HashMap<>();

        props.entrySet().forEach(e ->
             e.getValue().forEach(i-> {
                 if (map.containsKey(i.key.identifier())) {
                     map.get(i.key.identifier()).add(new TokenLocations<>(i.key.textRange(),  e.getKey()));
                 } else {
                     LinkedList<TokenLocations<C>> tokenLocations = new LinkedList<>();
                     tokenLocations.add(new TokenLocations(i.key.textRange(), e.getKey()));
                     map.put(i.key.identifier(), tokenLocations);

                 }

             })

        );
        return map;
    }




    public Map<String, LinkedList<TokenLocations<C>>> duplications(int treshold) {
        return extractDuplications().entrySet().stream().filter(m -> m.getValue().size() > treshold).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

    }


}
