package de.devtopics.doorbell;

// START SNIPPET: listen-gpio-snippet
/*
 * #%L
 * **********************************************************************
 * ORGANIZATION  :  Pi4J
 * PROJECT       :  Pi4J :: Java Examples
 * FILENAME      :  ListenGpioExample.java  
 * 
 * This file is part of the Pi4J project. More information about 
 * this project can be found here:  http://www.pi4j.com/
 * **********************************************************************
 * %%
 * Copyright (C) 2012 - 2015 Pi4J
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Lesser Public License for more details.
 * 
 * You should have received a copy of the GNU General Lesser Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/lgpl-3.0.html>.
 * #L%
 */
import de.devtopics.doorbell.bellcontrolls.MakeSound;
import com.pi4j.io.gpio.GpioController;
import com.pi4j.io.gpio.GpioFactory;
import com.pi4j.io.gpio.GpioPinDigitalInput;
import com.pi4j.io.gpio.PinPullResistance;
import com.pi4j.io.gpio.RaspiPin;
import de.devtopics.doorbell.bellcontrolls.DoorBell;

/**
 * This example code demonstrates how to setup a listener for GPIO pin state
 * changes on the Raspberry Pi.
 *
 * @author Robert Savage
 */
public class ListenGpioExample {

    
    public static DoorBell doorbell;
    public static boolean debug=false;
    // amixer cset numid=1 400
    public static void main(String args[]) throws Exception {
        
        for (String s: args){
            if ("-d".equals(s))
                debug=true;
        }
        doorbell= new DoorBell();
        
        if(!debug)
            startGpio();
        
        
        try{
        new DoorBellApplication().run(new String[]{args[0],args[1]});
        } catch (Exception e){
        e.printStackTrace();
        }
        
        for (;;) {
            Thread.sleep(500);
        }

        // stop all GPIO activity/threads by shutting down the GPIO controller
        // (this method will forcefully shutdown all GPIO monitoring threads and scheduled tasks)
        // gpio.shutdown();   <--- implement this method call if you wish to terminate the Pi4J GPIO controller        
    }

    private static void startGpio(){
    System.out.println("<--Pi4J--> GPIO Listen Example ... started.");
        // playSound2();
        // create gpio controller
        final GpioController gpio = GpioFactory.getInstance();

        // provision gpio pin #02 as an input pin with its internal pull down resistor enabled
        final GpioPinDigitalInput myButton = gpio.provisionDigitalInputPin(RaspiPin.GPIO_00, PinPullResistance.PULL_DOWN);
        final MakeSound makeSound = new MakeSound();
        System.out.println(doorbell);
        doorbell.setMakeSound(makeSound);
        // create and register gpio pin listener
        myButton.addListener(new PlaySoundUsingJavaRadioGpioListener(doorbell));

        System.out.println(" ... complete the GPIO #02 circuit and see the listener feedback here in the console.");

        // keep program running until user aborts (CTRL-C)
        
    }
    
}

 

// END SNIPPET: listen-gpio-snippet
