package org.pepaproch.properties.plugin;

import org.pepaproch.properties.checks.project.ProjectCheck;
import org.pepaproch.properties.checks.project.PropertiesBucket;
import org.pepaproch.properties.parser.slang.tree.PropTree;
import org.sonar.api.batch.fs.InputFile;

import java.util.List;
import java.util.function.Consumer;

public class PropertiesContext implements ProjectCheck {


    private final PropertiesBucket<InputFile> projectProperties;
    public final PropertiesSensor propertiesSensor;

    public PropertiesContext(PropertiesSensor propertiesSensor) {
        projectProperties = new PropertiesBucket();
        this.propertiesSensor = propertiesSensor;
    }

    public PropertiesBucket<InputFile> getProjectProperties() {
        return  this.projectProperties;
    }

    public void addPropertyTree(PropTree value, InputFile file) {
        getProjectProperties().processItem(value, file);
    }


    @Override
    public void finish(List<Consumer<PropertiesContext>> consumers) {
        if(consumers!=null) {
            consumers.stream().forEach(t -> t.accept(this));
        }
    }
}
