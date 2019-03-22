package org.pepaproch.properties.plugin;

import org.pepaproch.properties.parser.slang.tree.PropTree;
import org.pepaproch.properties.parser.slang.tree.PropsTree;
import org.sonar.api.batch.fs.InputFile;

import java.util.List;
import java.util.function.Consumer;

public class PostAnalyseContext {


    private final ProjectAllProperties<InputFile> projectProperties;
    public final PropertiesSensor propertiesSensor;

    public PostAnalyseContext(PropertiesSensor propertiesSensor) {
        projectProperties = new ProjectAllProperties();
        this.propertiesSensor = propertiesSensor;
    }

    public ProjectAllProperties<InputFile> getProjectProperties() {
        return  this.projectProperties;
    }

    public void addPropertyTree(PropTree value, InputFile file) {

        getProjectProperties().processItem(value, file);
    }

    public void addAllProps(PropsTree value, InputFile file) {

        getProjectProperties().processItems(value.getChildren(), file);
    }


    public void finish(List<Consumer<PostAnalyseContext>> consumers) {
        consumers.forEach(c->c.accept(this));
    }
}
