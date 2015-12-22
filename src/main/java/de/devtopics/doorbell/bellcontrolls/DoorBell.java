/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.devtopics.doorbell.bellcontrolls;

import de.devtopics.doorbell.DoorBellApplication;
import org.atmosphere.cpr.BroadcasterFactory;
import org.atmosphere.cpr.DefaultBroadcaster;

/**
 *
 * @author ganjalf
 */
public class DoorBell {

    private MakeSound makeSound;
private boolean silentMode=false;

    public boolean isSilentMode() {
        refresh();
        return silentMode;
    }

    public void setSilentMode(boolean silentMode) {
        this.silentMode = silentMode;
        DoorBellApplication.keyValueStore.put("silentMode", silentMode);
    }
    private BroadcasterFactory broadcasterFactory;
    
    private final String ringJson="{\"action\":\"ring\"}";
    
    public DoorBell(){
    
    }
    
    public DoorBell(MakeSound makeSound) {
        this.makeSound = makeSound;
       
    }
    
    private void refresh(){
            if (DoorBellApplication.keyValueStore.containsKey("silentMode")){
        silentMode=(boolean) DoorBellApplication.keyValueStore.get("silentMode");
        }
    }
    
    final static String file = "/home/pi/mp3s/scream.wav";
    public void ringIt(){
        try{
        makeSound.playSound(file);
        } catch (Exception e){
        e.printStackTrace();
        }
    }

    public void SomeonePressedTheButtonAtTheDoorEvent() {
        if (!silentMode)
            ringIt();
        notifyWebClients();
    }

    public void setMakeSound(MakeSound makeSound) {
       this.makeSound=makeSound;
    }

    public void setBroadcasterFactory(BroadcasterFactory broadcasterFactory) {
    this.broadcasterFactory= broadcasterFactory;
    }

    private void notifyWebClients() {
       try{
            broadcasterFactory.lookup(DefaultBroadcaster.class, "round", true).broadcast(ringJson);
       }
       catch (Exception e){
           e.printStackTrace();
       }
    }
    
}
