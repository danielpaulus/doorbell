/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.devtopics.doorbell;

import de.devtopics.doorbell.bellcontrolls.MakeSound;
import com.pi4j.io.gpio.PinState;
import com.pi4j.io.gpio.event.GpioPinDigitalStateChangeEvent;
import com.pi4j.io.gpio.event.GpioPinListenerDigital;
import de.devtopics.doorbell.bellcontrolls.DoorBell;

/**
 *
 * @author ganjalf
 */
public class PlaySoundUsingJavaRadioGpioListener implements GpioPinListenerDigital {

    private final DoorBell doorBell;

    
    

    PlaySoundUsingJavaRadioGpioListener(DoorBell doorbell) {
        doorBell=doorbell;
    }
    
    @Override
    public void handleGpioPinDigitalStateChangeEvent(GpioPinDigitalStateChangeEvent event) {
        // display pin state on console
        System.out.println(" --> GPIO PIN STATE CHANGE-t: " + event.getPin() + " = " + event.getState());
        if (event.getState() == PinState.HIGH) {            
            doorBell.SomeonePressedTheButtonAtTheDoorEvent();
        }
    }

   

}
