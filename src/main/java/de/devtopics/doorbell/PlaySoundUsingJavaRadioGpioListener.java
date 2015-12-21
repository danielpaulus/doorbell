/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.devtopics.doorbell;

import com.pi4j.io.gpio.PinState;
import com.pi4j.io.gpio.event.GpioPinDigitalStateChangeEvent;
import com.pi4j.io.gpio.event.GpioPinListenerDigital;

/**
 *
 * @author ganjalf
 */
public class PlaySoundUsingJavaRadioGpioListener implements GpioPinListenerDigital {

    @Override
    public void handleGpioPinDigitalStateChangeEvent(GpioPinDigitalStateChangeEvent event) {
        // display pin state on console
        System.out.println(" --> GPIO PIN STATE CHANGE-t: " + event.getPin() + " = " + event.getState());
        if (event.getState() == PinState.HIGH) {
            playSound();
        }
    }

    final static String file = "/home/pi/mp3s/scream.wav";
    final static MakeSound m = new MakeSound();

    private static void playSound() {
        try {
            m.playSound(file);
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }

    }

}
