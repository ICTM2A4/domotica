package nl.ictm4a.domotica;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;

public class RaspberryPiThread {

    private static int clientCount;
    private String ipAdress;
    private Socket socket;
    private DataInputStream din;
    private DataOutputStream dout;


    public RaspberryPiThread(String ipAdress){

        createSocket(ipAdress);

        Thread raspberryPiListener = new Thread(){


        };
    }

    private void createSocket(String ipAdress) {

        try{
            Socket socket = new Socket(ipAdress, clientCount);
            clientCount++;

        } catch (Exception e){
            System.out.println("");
        }
    }
}
