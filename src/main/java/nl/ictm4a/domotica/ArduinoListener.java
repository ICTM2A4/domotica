package nl.ictm4a.domotica;

import com.fazecast.jSerialComm.SerialPort;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

/**
 * ArduinoListener and actually sender as well.
 * Reads the LDR value that's sent from the Arduino to the Java application, if there's a change to the status of the lighting,
 * it will send a 1 (turn green light on (is the lighting led)) to the Arduino, 2 for turning it off.
 *
 * For logging it will save the changes of the status of the lighting, not on each LDR value.
 * We believe this is better to prevent a lot of inserts each #milliseconds and that it's only needed to log the changes.
 */
public class ArduinoListener {
    DatabaseFunction databaseFunction = new DatabaseFunction();
    User user;
    private int ldrValue = 0;

    static OutputStream outputStream;

    public ArduinoListener(User user) {
        this.user = user;
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
                    ldrValue = Integer.parseInt(data.nextLine());
                    MainScreenPanel.jlLightingStatus = ldrValue < user.getLightingInputText();
                } catch(NumberFormatException nfe) {
                    //System.out.println("Couldn't parse");
                }

                // checks out what current status is, if it has changed, send the changes to led light and display
                if(currentLightingStatus != MainScreenPanel.jlLightingStatus) {
                    MainScreenPanel.jlLighting.setText("Verlichting: " + MainScreenPanel.getLightingStatus());
                    if (MainScreenPanel.jlLightingStatus) { // if lightingstatus is true = on
                        // TODO: can clean this up I guess.
                        try {
                            outputStream.write("1".getBytes()); // 1 is turn green light on
                            outputStream.flush();

                            Date date = new Date();
                            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss") ;
                            String currentDateTime = format.format(date);
                            databaseFunction.insertRow("INSERT INTO `logging`(`sensor_id`, `value`, `datetime`, `status`) VALUES (1, "+ldrValue+", '"+currentDateTime+"', 1)");
                            //.out.println("sent 1");
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    } else { // if lightingstatus is false = off
                        try {
                            outputStream.write("2".getBytes()); // 2 is turn green light off
                            outputStream.flush();

                            Date date = new Date();
                            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss") ;
                            String currentDateTime = format.format(date);
                            databaseFunction.insertRow("INSERT INTO `logging`(`sensor_id`, `value`, `datetime`, `status`) VALUES (1, "+ldrValue+", '"+currentDateTime+"', 0)");
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
