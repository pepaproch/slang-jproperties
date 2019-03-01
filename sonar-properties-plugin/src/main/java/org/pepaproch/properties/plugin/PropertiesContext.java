package org.pepaproch.properties.plugin;

import org.pepaproch.properties.checks.Duplications;
import org.pepaproch.properties.checks.ProjectCheck;
import org.sonar.api.batch.fs.InputFile;
import org.sonarsource.slang.api.Token;

import java.util.function.Consumer;

public class PropertiesContext implements ProjectCheck {

    private final Consumer<PropertiesContext> finishFunction;
    private final Duplications duplications;
    public int duplicationTreshold;

    public PropertiesContext(PropertiesSensor propertiesSensor, Consumer<PropertiesContext> finishFunction) {
        this.finishFunction = finishFunction;
        duplications = new Duplications();
    }

     public  Duplications getDuplications() {
         Duplications duplications = this.duplications;
         return duplications;
    }

    public void addToken(Token value, InputFile file) {
        getDuplications().processToken(value, file);
    }



    public void finish() {
        finishFunction.accept(this);
    }

}
