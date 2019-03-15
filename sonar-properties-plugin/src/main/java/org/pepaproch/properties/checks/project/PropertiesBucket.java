package org.pepaproch.properties.checks.project;

import org.pepaproch.properties.parser.slang.tree.PropTree;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PropertiesBucket<C> {


    private final Map<C, List<PropTree>>props = new HashMap();

    public void processItem(PropTree value, C groupComponent) {
        if(props.containsKey(groupComponent)) {
            List<PropTree> propTrees = getProps().get(groupComponent);
            propTrees.add(value);
        }else {
            List p = new ArrayList();
            p.add(value);
            props.put(groupComponent,p);
        }

    }

    public void processItem(List<PropTree> value, C groupComponent) {
        getProps().put(groupComponent, value);

    }


    public Map<C,List<PropTree>> getProps() {
        return props;
    }
}
