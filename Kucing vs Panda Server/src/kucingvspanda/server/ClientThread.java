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
    private String roomName = "";
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
                        if(ServerTCP.validateUser(clientPacket.getNickname())){
                            PacketSender.sendLoginSuccessPacket(getOut(), ServerTCP.getRoomsInfo());
                            setName(clientPacket.getNickname());
                            ServerTCP.clientInfo.put(clientPacket.getNickname(), this);
                            System.out.println(""+clientPacket.getNickname()+" berhasil login");
                        }
                        else{
                            System.out.println(""+clientPacket.getNickname()+" gagal login");
                            PacketSender.sendLoginFailedPacket(getOut());
                        }
                        break;
                        
                    case Identifier.ADD_ROOM: //roomname
                        if(ServerTCP.validateRoom(clientPacket.getRoomName())){
                            ServerTCP.createRoom(clientPacket.getRoomName());
                            System.out.println("Berhasil membuat room");
                            
                            //PacketSender.sendAddRoomSuccessPacket(out,ServerTCP.getRoomsInfo() );
                            ServerTCP.broadCastAddRoom(clientPacket.getRoomName());
                        }else{
                            System.out.println("Gagal membuat room");
                            PacketSender.sendAddRoomFailedPacket(out);
                        }
                        
                        //ServerTCP.addRoom(room); //HARUSNYA ROOM yang dikirim
                        break;
                        
                    case Identifier.PLAY: //roomname + player
                        setRoomName(clientPacket.getRoomName());
                        ServerTCP.getListPlayer(roomName).add(name);
                        PacketSender.sendPlaySuccessPacket(out, roomName, ServerTCP.getListPlayer(roomName), ServerTCP.getListSpectator(roomName));
                        ServerTCP.broadCastNewPlayer(ServerTCP.getRoom(roomName), name);
                        ServerTCP.broadCastAddPlayerCount(roomName);
                        break;
                    case Identifier.WATCH: //roomname + player
                        setRoomName(clientPacket.getRoomName());
                        ServerTCP.getListSpectator(roomName).add(name);
                        PacketSender.sendSpectatorSuccessPacket(out, roomName, ServerTCP.getListPlayer(roomName), ServerTCP.getListSpectator(roomName), ServerTCP.getRoom(roomName).getGameBoard().getBoard());
                        ServerTCP.broadCastNewSpectatorPacket(ServerTCP.getRoom(roomName), name);
                        break;
    
                    case Identifier.START_GAME: //roomname
                        ServerTCP.getRoom(roomName).startGame();
                        ServerTCP.broadCastStartGame(roomName);
                        //sending room
                        
                        break;
                    case Identifier.ADD_PAWN: //player + x + y + roomname
                        int x,y; 
                        x = clientPacket.getX();
                        y = clientPacket.getY();
                        //ServerTCP.addPawn(clientPacket.getRoomName(), name, x, y);
                        // broadcast ke semua (nama, x, y)
                        ServerTCP.getRooms().get(roomName).setPlayerToBoard(x, y, name);
                        //if (ServerTCP.getRooms().get(roomName).getGameBoard().checkWin(x, y, name)) {
                          //  ServerTCP.winPacket(ServerTCP.getRoom(roomName), "horizontal", x, y);
                        //}
                        if (ServerTCP.getRooms().get(roomName).getStatus().equals("Win")) {
                            ServerTCP.winPacket(ServerTCP.getRoom(roomName), "horizontal", x, y);
                            System.out.println("ADA YANG MENANG");
                        }
                        ServerTCP.broadCastaddPawn(ServerTCP.getRoom(roomName),name, x, y);
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
