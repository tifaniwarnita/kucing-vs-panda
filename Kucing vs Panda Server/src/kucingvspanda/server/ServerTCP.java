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
import java.util.Map;
import kucingvspanda.packet.ClientPacket;
import kucingvspanda.packet.models.RoomInfo;


public class ServerTCP {

    // Connection state info
    private static LinkedHashMap<String, ClientThread> clientInfo = new LinkedHashMap<String, ClientThread>();

    /**
     * @return the roomsInfo
     */
    public static ArrayList<RoomInfo> getRoomsInfo() {
        return roomsInfo;
    }

    /**
     * @param aRoomsInfo the roomsInfo to set
     */
    public static void setRoomsInfo(ArrayList<RoomInfo> aRoomsInfo) {
        roomsInfo = aRoomsInfo;
    }
    
    // TCP Components
    private ServerSocket serverSocket;
    private static Map<String,Room> rooms = new HashMap<>();
    private static ArrayList<RoomInfo> roomsInfo;
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
        String port = "1234";

        try {
            // in constractor we are passing port no, back log and bind address whick will be local
            // host
            // port no - the specified port, or 0 to use any free port.
            // backlog - the maximum length of the queue. use default if it is equal or less than 0
            // bindAddr - the local InetAddress the server will bind to

            int portNo = Integer.valueOf(port);
            serverSocket = new ServerSocket(portNo, 0, InetAddress.getLocalHost());
            System.out.println(serverSocket);

            
            //ObjectInputStream in = new ObjectInputStream(socket.getInputStream());

            while (true) {
            Socket socket = serverSocket.accept();
            System.out.println(serverSocket.getInetAddress().getHostName() + ":"
                    + serverSocket.getLocalPort());
            new ClientThread(socket);
                //ClientPacket cp = (ClientPacket) in.readObject();
                //System.out.println(cp.getIdentifier());
                
            }
        } catch (Exception e) {
            System.out.println("IO Exception:" + e);
            System.exit(1);
        }
        
    }

    public static HashMap<String, ClientThread> getClientInfo() {
        return clientInfo;
    }
    
    // To: All client with roomname = null
    public static void broadCastAddRoom(RoomInfo roomInfo){
        for(ClientThread client : clientInfo.values()){
            if(client.getRoomName().equals(null)){
                PacketSender.sendAddRoomSuccessPacket(client.getOut(), roomInfo);
            }
        }
    }
    // *********************************** Main Method ********************

    public static void main(String args[]) {
        new ServerTCP();
    }
}