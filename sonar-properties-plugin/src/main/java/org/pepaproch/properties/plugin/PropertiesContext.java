package org.pepaproch.properties.plugin;

import org.pepaproch.properties.checks.ProjectCheck;
import org.sonar.api.batch.fs.InputFile;
import org.sonarsource.slang.api.TextRange;
import org.sonarsource.slang.api.Token;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.function.Consumer;
import java.util.stream.Collectors;

public class PropertiesContext implements ProjectCheck {

    private final Consumer<PropertiesContext> finishFunction;
    private final Duplications duplications;

    public PropertiesContext(PropertiesSensor propertiesSensor, Consumer<PropertiesContext> finishFunction) {
        this.finishFunction = finishFunction;
        duplications = new Duplications();
    }

    public Duplications getDuplications() {
        return duplications;
    }

    public void addToken(Token value, InputFile file) {
        getDuplications().processToken(value, file);

    }

    public void finish() {
        finishFunction.accept(this);
    }

    class TokenLocations {
        final TextRange location;
        final InputFile module;

        TokenLocations(TextRange location, InputFile module) {
            this.location = location;
            this.module = module;
        }
    }

    class Duplications<T extends Token> {
        public Map<String, LinkedList<TokenLocations>> map = new HashMap<>();


        public void processToken(T value, InputFile file) {
            if (map.containsKey(value.text())) {
                map.get(value.text()).add(new TokenLocations(value.textRange(), file));
            } else {
                LinkedList<TokenLocations> tokenLocations = new LinkedList<TokenLocations>() {{
                    add(new TokenLocations(value.textRange(), file));
                }};
                map.put(value.text(), tokenLocations);

            }

        }

        public Map<String, LinkedList<TokenLocations>> duplications() {
            return map.entrySet().stream().filter(m -> m.getValue().size() > 1).collect(Collectors.toMap(l -> l.getKey(), l -> l.getValue()));

        }
    }
}
