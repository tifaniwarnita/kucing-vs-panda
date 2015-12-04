/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kucingvspanda.server;

/**
 *
 * @author FiqieUlya
 */
/************************ Client Thread *******************/

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import kucingvspanda.packet.*;


        
public class ClientThread implements Runnable {
    // TCP Components
    private Socket socket;
    private ObjectInputStream in=null;
    private ObjectOutputStream out=null;
    private String name;
    private ClientPacket clientPacket;
    ///////////////private MessagePacket message;
    ///////////////private RoomInfoPacket room;
    private String roomName;
    // seperate thread
    private Thread thread;
    
    private HashMap<String, ClientThread> clientInfo = new HashMap<String, ClientThread>();

    // boolean variable to check that client is running or not
    private volatile boolean isRunning = true;

    // opcode
    private int opcode;
    
    public ClientThread(Socket socket) {
        try {
            this.socket = socket;
            ////////////this.clientInfo = ServerTCP.getClientInfo();
            
            in = new ObjectInputStream(socket.getInputStream());
            out = new ObjectOutputStream(socket.getOutputStream());

            thread = new Thread(this);
            thread.start();

        } catch (IOException e) {
            System.out.println(e);
        }
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    
    /*public void sendListClient(){
        // tell other users about new added user and update their online users list
            for (ClientThread client : clientInfo.values()) {
                client.out.println(Opcode.CLIENT_CONNECTED);
                client.out.println(clientInfo.size());

                for (ClientThread client1 : clientInfo.values()) {
                    client.out.println(client1.);
                }
            }
    }*/
    
    /*public void sendBroadcastMessage(String name, String message){
        // tell other users about new added user and update their online users list
            for (ClientThread client : clientInfo.values()) {
                if(!client.Name.equalsIgnoreCase(name)){
                    client.out.println(Opcode.CLIENT_BROADCAST);
                    client.out.println(message);
                    System.out.println("kirim pesan ke "+ client.Name + " : "+message);
                }
            }
    }*/
    public void run() {
        try {
            while (isRunning) {
                System.out.println("running");
                clientPacket= (ClientPacket) in.readObject();
                opcode = clientPacket.getIdentifier();// getting opcode first from client
                switch (opcode) {
//                    case Opcode.CLIENT_MESSAGE:
//                        String name = in.readLine();
//                        String Message = in.readLine();
//                        //sendBroadcastMessage(name,Message);
//                        break;
                    case Identifier.LOGIN:
                        //ngirim list
                        //put new entry in clientInfo hashmap
                        System.out.println("BERHASIL login");
                        System.out.println(""+clientPacket.getNickname()+" berhasil login");
                        setName(clientPacket.getNickname());
                        //////////////////////clientInfo.put(name, this);
                        //sending room list
                        PacketSender.sendLoginSuccessPacket(getOut(), ServerTCP.getRoomsInfo());
                        
                        //out.writeObject(ServerTCP.getRooms());
                        break;
                    case Identifier.ADD_ROOM: //roomname
                        ServerTCP.createRoom(clientPacket.getRoomName());
                        //broadcast room info list
                        
                        //ServerTCP.addRoom(room); //HARUSNYA ROOM yang dikirim
                        break;
                    case Identifier.PLAY: //roomname + player
                        setRoomName(clientPacket.getRoomName());
                        
                        //sending room
                        //out.writeObject(ServerTCP.getRoom(roomName));
                        break;
                    case Identifier.WATCH: //roomname + player
                        setRoomName(clientPacket.getRoomName());
                        
                        //sending room
                        //out.writeObject(ServerTCP.getRoom(roomName));
                        break;
    
                    case Identifier.START_GAME: //roomname
                        setRoomName(clientPacket.getRoomName());
                        
                        //sending room
                        
                        break;
                    case Identifier.ADD_PAWN: //player + x + y + roomname
                        int x,y; 
                        x = clientPacket.getX();
                        y = clientPacket.getY();
                        
                        // broadcast ke semua (nama, x, y)
                        
                        break;
                    case Identifier.LEAVE_GAME: //roomname + player
                        
                        break;
    
                    case Identifier.VIEW_HIGHSCORE: //roomname + 
                        
                        break;
                    case Identifier.CHAT:
                        
                        break;
//                        loginPacket= (LoginPacket) packet;
//
//                        boolean result = clientInfo.containsKey(loginPacket);
//                        out.writeObject(out);
//                        out.println(result);
//                        if (result)// wait for another chat name if already present
//                            continue;
//
//                        // send list of already online users to new online user
//                        // for (Object user : clientInfo.keySet().toArray()) {
//                        // out.println(Opcode.CLIENT_CONNECTED);
//                        // out.println(user.toString());
//                        // }
//
//                        // put new entry in clientInfo hashmap
//                        clientInfo.put(Name, this);
//
//                        int i = 0;
//                        for (String key : clientInfo.keySet()) {
//                            if (key.equals(Name)) {
//                                System.out.println(Name + " added at " + (i + 1) + " position");
//                            }
//                            i++;
//                        }
//
//                        sendListClient();
//
//                        break;
                }
            }

            // clsoe all connections
            getOut().close();
            in.close();
            socket.close();
        } catch (IOException e) {
            System.out.println(e);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ClientThread.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * @return the roomName
     */
    public String getRoomName() {
        return roomName;
    }

    /**
     * @param roomName the roomName to set
     */
    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    /**
     * @return the out
     */
    public ObjectOutputStream getOut() {
        return out;
    }

    /**
     * @param out the out to set
     */
    public void setOut(ObjectOutputStream out) {
        this.out = out;
    }
}
