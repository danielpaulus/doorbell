/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.devtopics.doorbell;

import io.dropwizard.Configuration;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.validator.constraints.NotEmpty;

public class DoorBellConfiguration extends Configuration {

    
    
    
  

    @NotEmpty
    private String template;

    @NotEmpty
    private String debugMode = "disabled";

    @JsonProperty
    public String getTemplate() {
        return template;
    }

    @JsonProperty
    public void setTemplate(String template) {
        this.template = template;
    }

    @JsonProperty
    public String getDebugMode() {
        return debugMode;
    }

    public boolean isDebugEnabled() {
        return "enabled".equals(debugMode);
    }

    @JsonProperty
    public void setDebugMode(String name) {
        this.debugMode = name;
    }

    
}
