package nl.ictm4a.domotica;

import com.fazecast.jSerialComm.SerialPort;
import java.io.IOException;
import java.io.OutputStream;
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
    private DatabaseFunction databaseFunction = new DatabaseFunction();
    private User user;
    private RaspberryPiListener raspberryPiListener;
    private int ldrValue = 0;
    private static OutputStream outputStream;

    /**
     * constructor, also includes a thread to repaint the temperature line graph
     * @param user user
     * @param raspberryPiListener information from the raspberrypilistener
     */
    public ArduinoListener(User user, RaspberryPiListener raspberryPiListener) {
        this.user = user;
        this.raspberryPiListener = raspberryPiListener;

        Thread arduinoListener = new Thread(() -> { // this is a thread, it runs along other threads separately at the same time
            SerialPort port = setCommPort();
            if(port.openPort()) { // port is open (not in use)
                sleepFunction(5000); // a small sleep after opening the port, this seems to solve some issues for stuff not being ran
                scanArduino(port);
            } else {
                System.out.println(port.getSystemPortName() + " port is not open");
            }
        });
        arduinoListener.start(); // start the thread
    }

    /**
     * Turns the LEDs (red = heating, green = lighting) on or off depending on the status.
     * Also reads for incoming message (LDR-sensor value) from the Arduino
     * @param port the port of the PC where the Arduino is plugged into
     */
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

    /**
     * sleep function
     * @param ms milliseconds how long the sleep should be
     */
    private void sleepFunction(int ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * setCommPort sets the com port, making this automaticly was a feature but did not have enough time
     * @return commPort the port the arduino sends to/from
     */
    private SerialPort setCommPort() {
        return SerialPort.getCommPort("COM4"); // change this to what your port is
    }

    /**
     * Sends messages to the Arduino (to turn LEDs on/off)
     * 1 = turn green on
     * 2 = turn green off
     * 3 = turn red on
     * 4 = turn red off
     * @param message message to be sent (1, 2, 3 or 4)
     */
    public void sendToArduino(String message) {
        try {
            outputStream.write(message.getBytes());
            outputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * inserts the logging of the heating and its status (is it turned on or not)
     * @param sensorID 1 = heating sensor
     * @param value measured temperature value from the raspberry pi
     * @param status is it on (1) or off (0)?
     */
    public void insertLogging(int sensorID, int value, int status) {
        databaseFunction.insertLogging("logging","sensor_id","value","datetime","status", "user_id",sensorID, value, databaseFunction.getCurrentDateTime(),status, user.getUserID());
    }

}
