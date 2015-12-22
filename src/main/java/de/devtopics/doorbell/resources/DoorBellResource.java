/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.devtopics.doorbell.resources;

import de.devtopics.doorbell.bellcontrolls.DoorBell;
import java.util.concurrent.atomic.AtomicLong;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import org.atmosphere.cpr.BroadcasterFactory;
import org.atmosphere.cpr.DefaultBroadcaster;

/**
 *
 * @author ganjalf
 */
@Path("/bell")
@Produces(MediaType.APPLICATION_JSON)
public class DoorBellResource {

    private final DoorBell doorBell;
    private BroadcasterFactory broadcasterFactory;

    public DoorBellResource(DoorBell bell, BroadcasterFactory broadcasterFactory) {
        this.doorBell = bell;
        this.broadcasterFactory = broadcasterFactory;
    }

    @GET
    @Path("ring/")
    public void ringBell() {
        doorBell.ringIt();
    }

    @GET
    @Path("silent/")
    public boolean isSilent() {
        
        return doorBell.isSilentMode();
    }

    @POST
    @Path("silent/")
    public void setSilent(boolean silent) {
        doorBell.setSilentMode(silent);
        String silentUpdate=String.format("{\"silent\":%s}",silent);
        try {
            broadcasterFactory.lookup(DefaultBroadcaster.class, "round", true).broadcast(silentUpdate);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
