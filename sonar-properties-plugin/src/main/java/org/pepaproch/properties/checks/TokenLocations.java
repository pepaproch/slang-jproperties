package org.pepaproch.properties.checks;


import org.sonarsource.slang.api.HasTextRange;
import org.sonarsource.slang.api.TextRange;

public class TokenLocations<C>  implements HasTextRange {
    public final TextRange location;
    private final C module;

    TokenLocations(TextRange location, C module) {
        this.location = location;
        this.module = module;
    }

    @Override
    public TextRange textRange() {
        return location;
    }

    public C getModule() {
        return module;
    }
}
