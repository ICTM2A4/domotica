
//https://stackoverflow.com/questions/48266026/socket-java-client-python-server

package nl.ictm4a.domotica;

import java.io.*;
import java.net.*;
import java.lang.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class RaspberryPiListener {
    DatabaseFunction databaseFunction = new DatabaseFunction();
    private String lastMessage;
    private double tempValue, humValue, airPressValue;
    private final boolean debugging = false;
    private DataOutputStream dout1;
    private User user;

    private String ipAdress = "192.168.1.14";

    public RaspberryPiListener(User user){
        this.user = user;

        //TODO: in losse thread klasse zetten
        Thread raspberryPiListener = new Thread(() -> {
            try{
            Socket socket = new Socket (ipAdress, 8000);
            DataOutputStream dout = new DataOutputStream(socket.getOutputStream());
            DataInputStream din = new DataInputStream(socket.getInputStream());
                while(receiveMessage(din) != null){
                    String[] data = receiveMessage(din).split(", ");
                    for (int i = 0; i < data.length; i++) {
                        if(i == 0){
                            this.airPressValue = Double.parseDouble(data[i]);
                            MainScreenPanel.jlAirPressure.setText("Luchtdruk: " + (int)this.airPressValue + " hPa");
                            databaseFunction.insertLogging("logging","sensor_id","value","datetime", "user_id", 3, airPressValue, databaseFunction.getCurrentDateTime(),user.getUserID());
                        }
                        else if(i == 1){
                            this.humValue = Double.parseDouble(data[i]);
                            MainScreenPanel.jlAirMoisture.setText("Luchtvochtigheid: " + (int)this.humValue + "%");
                            databaseFunction.insertLogging("logging","sensor_id","value","datetime", "user_id", 4, humValue, databaseFunction.getCurrentDateTime(),user.getUserID());
                        }
                        else{
                            this.tempValue = Double.parseDouble(data[i])-7;
                            MainScreenPanel.jlTemperature.setText("Temperatuur: " + ((int)this.tempValue) +  "℃");

                            MainScreenPanel.jlHeatingStatus = (int)tempValue < user.getHeatingInputText();
                            MainScreenPanel.jlHeating.setText("Verwarming: " + MainScreenPanel.getHeatingStatus());
                            if(MainScreenPanel.getHeatingStatus().equals("aan")) {
                                databaseFunction.insertLogging("logging", "sensor_id", "value", "datetime", "user_id", "status", 2, tempValue, databaseFunction.getCurrentDateTime(), user.getUserID(), 1);
                            } else {
                                databaseFunction.insertLogging("logging", "sensor_id", "value", "datetime", "user_id", "status", 2, tempValue, databaseFunction.getCurrentDateTime(), user.getUserID(), 0);
                            }
                        }
                    }

                }
            } catch (Exception e){
                if(debugging)System.out.println(e.getMessage());
            }
        });
        raspberryPiListener.start();

        Thread raspberryPiMusicListener = new Thread(() -> {
            try{
                Socket socket1 = new Socket (ipAdress, 8001);
                dout1=new DataOutputStream(socket1.getOutputStream());
                DataInputStream din1 =new DataInputStream(socket1.getInputStream());

                while(receiveMessage(din1) != null) {
                    String data = receiveMessage(din1);
                }
            }
            catch (Exception e){
                if(debugging)System.out.println(e.getMessage());
            }
        });
        raspberryPiMusicListener.start();
    }

    public void sendMessage(String message) {
        try {
            dout1.writeUTF(message);
            dout1.flush();
        } catch (Exception e) {
            if (debugging) System.out.println(e.getMessage());
        }
    }

    public String receiveMessage(DataInputStream din){
        try{
            return din.readUTF();
        }
        catch(Exception e){
            if(debugging) System.out.println(e.getMessage() + " on receiveMessage");
            return null;
        }
    }
    public void closeSocket(Socket socket){
        try {
            socket.close();
        }
        catch (Exception e){
            if(debugging) System.out.println(e.getMessage());
        }
    }
    public double getTempValue() {
        return tempValue;
    }
}

