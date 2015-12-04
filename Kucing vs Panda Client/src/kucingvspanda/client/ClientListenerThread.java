/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kucingvspanda.client;


import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import java.util.logging.Level;
import java.util.logging.Logger;
import kucingvspanda.client.controllers.MainMenuController;
import kucingvspanda.packet.ServerPacket;
import kucingvspanda.packet.Identifier;
import kucingvspanda.packet.models.RoomInfo;

/**
 *
 * @author FiqieUlya
 */
public class ClientListenerThread extends Observable implements Runnable {
    private String name;
    private Socket socket;
    private Thread thread;
    private ObjectInputStream in;
    private boolean isRunning=true;
    private ServerPacket packet;
    ArrayList<Observer> observerList = new ArrayList<Observer>();
    
    public ClientListenerThread(Socket socket, String name, MainMenuController firstObs){
        this.socket = socket;
        this.name = name;
        this.observerList.add(firstObs);
        System.out.println("Sebelum in");
        System.out.println("Sebelum thread distart");
        thread = new Thread(this);
        thread.start();
        System.out.println("Thread jalan");
    }
    
    public void receivePacket(ServerPacket packet){
        notifyObservers(packet);
        setChanged();
    }
    
    public void stopClient()
    {
        try {
		this.socket.close();
        }catch(IOException ioe){ };
    }
    
    @Override
    public void run() {
        try{
            in = new ObjectInputStream(socket.getInputStream());
            while(isRunning){
              packet = (ServerPacket) in.readObject();
              receivePacket(packet);
            }
            // close all connections
            in.close();
            socket.close();
        } catch (IOException e) {
            System.out.println(e);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ClientListenerThread.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @Override
    public void notifyObservers(Object arg) {
        for (Observer obj : observerList) {
            obj.update(this, arg);
        }
        clearChanged();
    }
}
