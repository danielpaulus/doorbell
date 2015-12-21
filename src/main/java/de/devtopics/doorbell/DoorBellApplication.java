/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.devtopics.doorbell;

import de.devtopics.doorbell.bellcontrolls.DoorBell;
import de.devtopics.doorbell.resources.DoorBellResource;
import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

/**
 *
 * @author ganjalf
 */
public class DoorBellApplication extends Application<DoorBellConfiguration> {

    @Override
    public String getName() {
        return "hello-world";
    }

    @Override
    public void initialize(Bootstrap<DoorBellConfiguration> bootstrap) {
        // nothing to do yet
    }

    @Override
    public void run(DoorBellConfiguration configuration,
            Environment environment) {
        final DoorBell doorBell= new DoorBell();
        final DoorBellResource resource = new DoorBellResource(
                doorBell
        );
        environment.jersey().register(resource);
    }

}
