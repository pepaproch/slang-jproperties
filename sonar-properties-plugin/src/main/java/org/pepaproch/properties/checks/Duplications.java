package org.pepaproch.properties.checks;

import org.sonarsource.slang.api.Token;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.stream.Collectors;

public class Duplications<T extends Token , C> {

    public Map<String, LinkedList<TokenLocations<C>>> map = new HashMap<>();


    public Duplications() {

    }


    public void processToken(T value, C file) {
        if (map.containsKey(value.text())) {
            map.get(value.text()).add(new TokenLocations<>(value.textRange(), file));
        } else {
            LinkedList<TokenLocations<C>> tokenLocations = new LinkedList<TokenLocations<C>>() {{
                add(new TokenLocations(value.textRange(), file));
            }};
            map.put(value.text(), tokenLocations);

        }

    }

    public Map<String, LinkedList<TokenLocations<C>>> duplications() {
        return map.entrySet().stream().filter(m -> m.getValue().size() > 1).collect(Collectors.toMap(l -> l.getKey(), l -> l.getValue()));

    }

    public Map<String, LinkedList<TokenLocations<C>>> duplications(int treshold) {
        return map.entrySet().stream().filter(m -> m.getValue().size() > treshold).collect(Collectors.toMap(l -> l.getKey(), l -> l.getValue()));

    }
}