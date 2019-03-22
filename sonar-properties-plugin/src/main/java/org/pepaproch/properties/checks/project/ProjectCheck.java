package org.pepaproch.properties.checks.project;


import org.pepaproch.properties.plugin.PostAnalyseCheks;

public interface ProjectCheck {

    void accept(PostAnalyseCheks.PCheckContext pctx);
}
