package com.juviner.data;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Root {
    private final String description;
    private final List<Section> sections;
    
    public Root(String description, List<Section> sections) {
        this.description = description;
        this.sections = new ArrayList<>(sections);
    }
    
    public String getDescription() {
        return this.description;
    }
    
    public List<Section> getSections() {
        return Collections.unmodifiableList(this.sections);
    }
}
