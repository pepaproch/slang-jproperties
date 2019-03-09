package org.pepaproch.properties.checks;

import org.pepaproch.properties.plugin.PropertiesContext;

import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

public interface ProjectCheck {


     void finish(List<Consumer<PropertiesContext>> consumers) ;
}
