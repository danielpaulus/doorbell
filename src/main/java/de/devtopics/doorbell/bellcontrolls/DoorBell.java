/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.devtopics.doorbell.bellcontrolls;

/**
 *
 * @author ganjalf
 */
public class DoorBell {

    private final MakeSound makeSound;

    public DoorBell(MakeSound makeSound) {
        this.makeSound = makeSound;
       
    }
    
    final static String file = "/home/pi/mp3s/scream.wav";
    public void ringIt(){
        try{
        makeSound.playSound(file);
        } catch (Exception e){
        e.printStackTrace();
        }
    }
    
}
