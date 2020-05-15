
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
    private Socket socket;
    private final boolean debugging = true;
    private DataInputStream din;
    private DataOutputStream dout;
    private User user;

    public RaspberryPiListener(User user){

        this.user = user;

        Thread raspberryPiListener = new Thread(() -> {

            if(setupSocket("192.168.0.101", 8000)){

                while(receiveMessage() != null){
                    String[] data = receiveMessage().split(", ");
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
                        }
                    }
                    MainScreenPanel.jlHeatingStatus = (int)tempValue < user.getHeatingInputText();
                    MainScreenPanel.jlHeating.setText("Verwarming: " + MainScreenPanel.getHeatingStatus());
                }
            }
        });

        raspberryPiListener.start();
    }



    public boolean setupSocket(String serverIp, int serverPort){

        try{
            this.socket = new Socket(serverIp, serverPort);
            this.dout=new DataOutputStream(socket.getOutputStream());
            this.din=new DataInputStream(socket.getInputStream());
            return true;
        } catch (Exception e){
            if(debugging)System.out.println(e.getMessage());
            return false;
        }
    }

    public void sendMessage(String message){
        try{
        dout.writeUTF(message);
        dout.flush();

        }
        catch (Exception e){
            if(debugging) System.out.println(e.getMessage());

        }
    }

    public String receiveMessage(){
        try{
            return din.readUTF();
        }
        catch(Exception e){
            if(debugging) System.out.println(e.getMessage() + " on receiveMessage");
            return null;
        }
    }

    public void closeSocket(){
        try {
            this.socket.close();
        }
        catch (Exception e){
            if(debugging) System.out.println(e.getMessage());
        }
    }

    public double getTempValue() {
        return tempValue;
    }
}

