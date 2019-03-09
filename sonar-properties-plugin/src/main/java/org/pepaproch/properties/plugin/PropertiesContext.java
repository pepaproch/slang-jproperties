package org.pepaproch.properties.plugin;

import org.pepaproch.properties.checks.Duplications;
import org.pepaproch.properties.checks.ProjectCheck;
import org.sonar.api.batch.fs.InputFile;
import org.sonarsource.slang.api.Token;

import java.util.List;
import java.util.function.Consumer;

public class PropertiesContext implements ProjectCheck {


    private final Duplications duplications;
    public final PropertiesSensor propertiesSensor;

    public PropertiesContext(PropertiesSensor propertiesSensor) {
        duplications = new Duplications();
        this.propertiesSensor = propertiesSensor;
    }

    public Duplications getDuplications() {
        Duplications duplications = this.duplications;
        return duplications;
    }

    public void addDuplicationToken(Token value, InputFile file) {
        getDuplications().processToken(value, file);
    }


    @Override
    public void finish(List<Consumer<PropertiesContext>> consumers) {
        if(consumers!=null) {
            consumers.stream().forEach(t -> t.accept(this));
        }
    }
}
