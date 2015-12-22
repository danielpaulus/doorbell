/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.devtopics.doorbell;

import de.devtopics.doorbell.bellcontrolls.DoorBell;
import de.devtopics.doorbell.resources.DoorBellResource;
import io.dropwizard.Application;
import io.dropwizard.assets.AssetsBundle;
import io.dropwizard.forms.MultiPartBundle;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import java.util.concurrent.ConcurrentHashMap;
import javax.servlet.ServletRegistration;
import org.atmosphere.cpr.ApplicationConfig;
import org.atmosphere.cpr.AtmosphereServlet;
import org.atmosphere.cpr.BroadcasterFactory;
import org.eclipse.jetty.websocket.server.WebSocketHandler;

/**
 *
 * @author ganjalf
 */
public class DoorBellApplication extends Application<DoorBellConfiguration> {

    public static final ConcurrentHashMap<String, Object> keyValueStore= new ConcurrentHashMap<>();
    
    @Override
    public String getName() {
        return "hello-world";
    }

    @Override
    public void initialize(Bootstrap<DoorBellConfiguration> bootstrap) {
         bootstrap.addBundle(new AssetsBundle("/assets/app", "/", "index.html"));
        bootstrap.addBundle(new MultiPartBundle());
       
    }

    @Override
    public void run(DoorBellConfiguration configuration,
            Environment environment) {
        final DoorBell doorBell= ListenGpioExample.doorbell;
        AtmosphereServlet servlet = new AtmosphereServlet();
        servlet.framework().addInitParameter(ApplicationConfig.ANNOTATION_PACKAGE, WebSocketHandler.class.getPackage().getName());
        servlet.framework().addInitParameter(ApplicationConfig.WEBSOCKET_SUPPORT, "true");

        
          environment.getApplicationContext().setAttribute("doorbell", doorBell);
          
        ServletRegistration.Dynamic registration = environment.servlets().addServlet("atmosphere", servlet);
        registration.addMapping("/websocket/*");

        BroadcasterFactory broadcasterFactory = servlet.framework().getBroadcasterFactory();
        doorBell.setBroadcasterFactory(broadcasterFactory);
        
        
        
        final DoorBellResource resource = new DoorBellResource(
                doorBell, broadcasterFactory
        );
        environment.jersey().register(resource);
    }

}
