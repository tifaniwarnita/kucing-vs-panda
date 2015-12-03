/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kucingvspanda.client;


import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.util.Observable;
import java.util.logging.Level;
import java.util.logging.Logger;
import kucingvspanda.packet.ServerPacket;
import kucingvspanda.packet.Identifier;

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
    
    public ClientListenerThread(Socket socket, String name){
        try {
            this.socket = socket;
            this.name = name;

            in = new ObjectInputStream(socket.getInputStream());

            thread = new Thread(this);
            thread.start();
        } catch (IOException e) {
            System.out.println(e);
        }
    }
    
    public void receivePacket(ServerPacket packet){
        int identifier = packet.getIdentifier();
        setChanged();
        switch(identifier){
            case Identifier.LOGIN_SUCCESS : //list<roominfo>
                
                break;
            case Identifier.LOGIN_FAILED: //message
                break;
            case Identifier.ADD_ROOM_SUCCESS: //roominfo
                break;
            case Identifier.ADD_ROOM_FAILED: //message
                break;
            case Identifier.PLAY_SUCCESS: //roomname + players + spectators
                break;
            case Identifier.NEW_PLAYER: //playername
                break;
            case Identifier.UPDATE_PLAYER_COUNT: //roomname 
                break;
            case Identifier.PLAY_FAILED: //message
                break;
            case Identifier.NEW_SPECTATOR: //playername
                break;
            case Identifier.START_GAME: //roomname
                break;
            case Identifier.PAWN_PLACED: //roomname + player + x + y
                break;
            case Identifier.PLAYER_LEAVE: //roomname + player
                break;
            case Identifier.BOARD_FULL: //roomname + winner
                break;
            case Identifier.HIGHSCORE: //highscore list
                break;
        }
        // trigger notification
        notifyObservers(identifier);
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
    
}
