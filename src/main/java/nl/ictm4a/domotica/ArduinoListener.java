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
    RaspberryPiListener raspberryPiListener;
    private int ldrValue = 0;


    static OutputStream outputStream;

    public ArduinoListener(User user, RaspberryPiListener raspberryPiListener) {
        this.user = user;
        this.raspberryPiListener = raspberryPiListener;

        Thread arduinoListener = new Thread(() -> { // this is a thread, it runs along other threads separately at the same time
            SerialPort port = setCommPort();
            if(port.openPort()) { // port is open (not in use)
                //System.out.println(port.getSystemPortName() + " port is open.");
                sleepFunction(5000); // a small sleep after opening the port, this seems to solve some issues for stuff not being ran
                scanArduino(port);
            } else {
                System.out.println(port.getSystemPortName() + " port is not open");
            }
        });
        arduinoListener.start(); // start the thread
    }

    private void scanArduino(SerialPort port) {
        port.setComPortTimeouts(SerialPort.TIMEOUT_READ_SEMI_BLOCKING, 0, 0); // In this mode, a call to any of the read() or readBytes() methods will block until the number of milliseconds specified by the newReadTimeout parameter has elapsed or at least 1 byte of data can be read.
        Scanner data = new Scanner(port.getInputStream()); // scanner is for incoming information (values from the sensors)
        outputStream = port.getOutputStream(); // outputstream is for outgoing information (to turn leds on/off)
        String lastSent = "Lighting";
        while(data.hasNextLine()) {
            boolean currentLightingStatus = MainScreenPanel.jlLightingStatus; // need to know current status to compare if incoming value from arduino's ldr sensor changes
            try { // try parsing the incoming data to a long (because sometimes u get a "_____________________________________<value)" returned...) and sometimes u get a string returned as well..?
                ldrValue = Integer.parseInt(data.nextLine());
                MainScreenPanel.jlLightingStatus = ldrValue < user.getLightingInputText();
            } catch(NumberFormatException ignored) {}
            // checks out what current status is, if it has changed, send the changes to led light and display
            MainScreenPanel.jlLighting.setText("Verlichting: " + MainScreenPanel.getLightingStatus());
            if(currentLightingStatus != MainScreenPanel.jlLightingStatus) {
                int sensorID = 1;
                insertLogging(sensorID, ldrValue, (MainScreenPanel.jlLightingStatus) ? 1 : 0); // status 1 = on, 0 = off
            }
            MainScreenPanel.jlHeating.setText("Verwarming: " + MainScreenPanel.getHeatingStatus());

            if(lastSent.equals("Lighting")) {
                sendToArduino((MainScreenPanel.jlHeatingStatus) ? "3" : "4"); // 3 = red on, 4 = red off
                lastSent = "Heating";
            } else {
                sendToArduino((MainScreenPanel.jlLightingStatus) ? "1" : "2"); // 1 = green on, 2 = green off
                lastSent = "Lighting";
            }
        }
    }

    private void sleepFunction(int ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    // Automatic port
    private SerialPort setCommPort() {
        SerialPort[] commPort = SerialPort.getCommPorts();
//        for (int i = 0; i < commPort.length; i++) {
//            System.out.println((i+1)+ ": " + commPort[i].getSystemPortName());
//        }
        int port = commPort.length - 1;
        return SerialPort.getCommPort(commPort[port].getSystemPortName()); // change this to what your port is
    }

    public void sendToArduino(String message) {
        try {
            outputStream.write(message.getBytes());
            outputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void insertLogging(int sensorID, int value, int status) {
        databaseFunction.insertLogging("logging","sensor_id","value","datetime","status", "user_id",sensorID, value, databaseFunction.getCurrentDateTime(),status, user.getUserID());
    }

}
