package nl.ictm4a.domotica;

import com.fazecast.jSerialComm.SerialPort;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Scanner;


public class ArduinoListener {
    private int lightingThreshold = 40;

    static OutputStream outputStream;

    public ArduinoListener() {

        Thread arduinoListener = new Thread(() -> { // this is a thread, it runs along other threads separately at the same time
            SerialPort port = SerialPort.getCommPort("COM4"); // change this to what youre port is TODO automatic change ports?
            if(port.openPort()) { // port is open (not in use)
                System.out.println(port.getSystemPortName() + " port is open.");
            } else {
                System.out.println(port.getSystemPortName() + " port is not open");
            }

            try {
                Thread.sleep(5000); // a small sleep after opening the port, this seems to solve some issues for stuff not being ran
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            port.setComPortTimeouts(SerialPort.TIMEOUT_READ_SEMI_BLOCKING, 0, 0);
            Scanner data = new Scanner(port.getInputStream()); // scanner is for incoming information (values from the sensors)
            outputStream = port.getOutputStream(); // outputstream is for outgoing information (to turn leds on/off)
            while(data.hasNextLine()) {
                //System.out.println(data.nextLine());
                boolean currentLightingStatus = MainScreenPanel.jlLightingStatus; // need to know current status to compare if incoming value from arduino's ldr sensor changes
                try { // try parsing the incoming data to a long (because sometimes u get a "_____________________________________<value)" returned...) and sometimes u get a string returned as well..?
                    MainScreenPanel.jlLightingStatus = Long.parseLong(data.nextLine()) < lightingThreshold; // TODO when is it dark enough to turn the lights on
                } catch(NumberFormatException nfe) {
                    System.out.println("Couldn't parse to long");
                }

                // checks out what current status is, if it has changed, send the changes to led light and display
                if(currentLightingStatus != MainScreenPanel.jlLightingStatus) {
                    MainScreenPanel.jlLighting.setText("Verlichting: " + MainScreenPanel.getLightingStatus());
                    if (MainScreenPanel.jlLightingStatus) { // if lightingstatus is true = on
                        try {
                            outputStream.write("1".getBytes()); // 1 is turn green light on
                            outputStream.flush();
                            //.out.println("sent 1");
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    } else { // if lightingstatus is false = off
                        try {
                            outputStream.write("2".getBytes()); // 2 is turn green light off
                            outputStream.flush();
                            //System.out.println("sent 2");
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }


            }
        });

        arduinoListener.start(); // start the thread

    }

}
