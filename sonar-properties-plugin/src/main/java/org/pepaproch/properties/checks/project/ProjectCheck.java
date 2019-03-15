package org.pepaproch.properties.checks.project;

import org.pepaproch.properties.plugin.PropertiesContext;

import java.util.List;
import java.util.function.Consumer;

public interface ProjectCheck {


     void finish(List<Consumer<PropertiesContext>> consumers) ;
}
