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
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import kucingvspanda.packet.ClientPacket;
import kucingvspanda.packet.models.RoomInfo;
import kucingvspanda.packet.models.WinInfo;


public class ServerTCP {

    // Connection state info
    public static LinkedHashMap<String, ClientThread> clientInfo = new LinkedHashMap<String, ClientThread>();

    /**
     * @return the roomsInfo
     */
    public static ArrayList<RoomInfo> getRoomsInfo() {
        ArrayList<RoomInfo> temp = new ArrayList<>(); 
        for(Room room : rooms.values()){
            temp.add(room.toRoomInfo());
        }
        return temp;
    }

    /**
     * @param aRoomsInfo the roomsInfo to set
     */
    public static void setRoomsInfo(Map<String,RoomInfo> aRoomsInfo) {
        roomsInfo = aRoomsInfo;
    }
    // TCP Components
    private ServerSocket serverSocket;
    private static Map<String,Room> rooms = new HashMap<>();
    private static Map<String,RoomInfo> roomsInfo= new HashMap<>();
    /**
     * @return the roomsInfo
     */
    public static ArrayList<String> getListPlayer(String roomName){
        Room room = rooms.get(roomName);
        return (ArrayList)room.getPlayers();
    }
    public static ArrayList<String> getListSpectator(String roomName){
        Room room = rooms.get(roomName);
        return (ArrayList)room.getSpectators();
    }
    public static ArrayList<String> getAllPlayerInRoom(Room room){
        ArrayList<String> people = new ArrayList<>();
        List<String> players = room.getPlayers();
        for(String player : players){
            people.add(player);
        }

        List<String> spectators = room.getSpectators();
        for(String spectator : spectators){
            people.add(spectator);
        }
        return people; 
    }
   

   
    
    
    // Main Constructor
    public ServerTCP() {

        startServer();// start the server
    }
    public static void createRoom(String roomName){
        rooms.put(roomName, new Room(roomName));
        
    }
    public static Map<String,Room> getRooms(){
        return rooms;
    } 
    public static Room getRoom(String roomName){
        return rooms.get(roomName);
    }
    public void startServer() {
        //String port = "1234";

        try {

            //int portNo = Integer.valueOf(port);
            serverSocket = new ServerSocket (2000, 0, InetAddress.getLocalHost());
            System.out.println(serverSocket);

            
            //ObjectInputStream in = new ObjectInputStream(socket.getInputStream());

            while (true) {
                Socket socket = serverSocket.accept();
                System.out.println(serverSocket.getInetAddress().getHostName() + ":"
                    + serverSocket.getLocalPort());
                new ClientThread(socket); 
            }

        } catch (Exception e) {
            
            System.out.println("IO Exception:" + e);
            System.exit(1);
        }
        
    }

    public static HashMap<String, ClientThread> getClientInfo() {
        return clientInfo;
    }
    
    /******BROADCAST METHOD******/
    
    // To: All client with roomname = null
    public static void broadCastAddRoom(String roomName){
        System.out.println(rooms.get(roomName).getName());
        RoomInfo roomInfo = rooms.get(roomName).toRoomInfo();
        System.out.println(roomInfo.getName());
        for(ClientThread client : clientInfo.values()){
                PacketSender.sendAddRoomSuccessPacket(client.getOut(), roomInfo);
            
        }
    }
    // To:Players in room 
    public static void broadCastNewPlayer(Room room, String name){
        ArrayList<String>players = getAllPlayerInRoom(room);
        for(String player : players){
            if(!player.equals(name)){
                ClientThread client = clientInfo.get(player);
                PacketSender.sendNewPlayerPacket(client.getOut(), name);
            }
        }
    }
    // To: All client (except players in room)
    public static void broadCastAddPlayerCount(String roomName){
        for(ClientThread client: clientInfo.values()){
            PacketSender.sendAddPlayerCountPacket(client.getOut(), roomName);
        }
    }
    // To: Players in room
    public static void broadCastNewSpectatorPacket(Room room, String name){
        ArrayList<String> players = getAllPlayerInRoom(room);
        for(String player : players){
            if(!player.equals(name)){
                System.out.println("player : "+ player+" dikirim");
                ClientThread client = clientInfo.get(player);
                PacketSender.sendNewSpectatorPacket(client.getOut(), name);
            }
        }
    }
    
    // To: Players in room
    public static void broadCastChat(Room room, String name, String message){
        ArrayList<String> players = getAllPlayerInRoom(room);
        for(String player : players){
                System.out.println("player : "+ player+" dikirim");
                ClientThread client = clientInfo.get(player);
                PacketSender.sendChatPacket(client.getOut(), name, message);
            
        }
    }
    
    // To: Packet sender + all connected player with roomname = null
    public static void broadCastStartGame(String roomName){
        for(ClientThread client: clientInfo.values()){
            PacketSender.sendStartGameSuccessPacket(client.getOut(), roomName);
        }
    }
   
    // To: All in room
    public static void broadCastaddPawn(Room room,String from, int x, int y){
        ArrayList<String> players = getAllPlayerInRoom(room);
        String to = players.get(room.getTurn());
        for(String player : players){
            ClientThread client = clientInfo.get(player);
            PacketSender.sendAddPawnPacketSuccess(client.getOut(), from, x, y, to);
            
        }
    }
    

    // To: All client with roomname = null
    public static void decPlayerCount(String roomName){
        for(ClientThread client: clientInfo.values()){
            PacketSender.sendDecPlayerCountPacket(client.getOut(), roomName);
        }
    }
    // To: All in room
    public static void winPacket(Room room, WinInfo win){
        for(ClientThread client: clientInfo.values()){
            PacketSender.sendWinPacket(client.getOut(), room.getRoomName(), win);
        }
    }
    //To: All in room
    public static void boardFull(Room room){
        ArrayList<String> players = getAllPlayerInRoom(room);
        for(String player : players){
            ClientThread client = clientInfo.get(player);
            PacketSender.sendBoardFullPacket(client.getOut());
            
        }
    }
    
    //To: All in room
    public static void chatPacket(Room room, String message, String name){
        ArrayList<String> players = getAllPlayerInRoom(room);
        for(String player : players){
            ClientThread client = clientInfo.get(player);
            PacketSender.sendChatPacket(client.getOut(), name, message); 
            
        }
    }
    
    public static boolean validateUser(String nama){
        ClientThread check = null;
        check = clientInfo.get(nama);
        if(check==null){
            return true;
        }else{
            return false;   
        }
    }
    public static boolean validateRoom(String name){
        Room room = null;
        room = rooms.get(name);
        if(room==null){
            return true;
        }else return false;
    }
    
    public static void broadCastLeavePlayer(Room room, String name){
        ArrayList<String> players = getAllPlayerInRoom(room);
        for(String player : players){
            ClientThread client = clientInfo.get(player);
            PacketSender.sendLeavePlayerPacket(client.getOut(), name); 
            
        }
    }
    // *********************************** Main Method ********************

    public static void main(String args[]) {
        new ServerTCP();
    }
}