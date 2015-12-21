/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.devtopics.doorbell.resources;

import de.devtopics.doorbell.bellcontrolls.DoorBell;
import java.util.concurrent.atomic.AtomicLong;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 *
 * @author ganjalf
 */


@Path("/bell")
@Produces(MediaType.APPLICATION_JSON)
public class DoorBellResource {
    private final AtomicLong counter;
    private final DoorBell doorBell;

    public DoorBellResource(DoorBell bell) {
        this.doorBell=bell;
        this.counter = new AtomicLong();
    }

    @GET
    @Path("ring/")
    public void ringBell(){
        doorBell.ringIt();
    }
}