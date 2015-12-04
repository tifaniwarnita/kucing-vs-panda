/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kucingvspanda.client;

import java.net.*; //for socket
import java.io.*; //for IOException and Input/OutputStream
import java.util.Observable;
import java.util.Observer;
import java.util.logging.Level;
import java.util.logging.Logger;
import kucingvspanda.client.controllers.MainMenuController;
import kucingvspanda.packet.Identifier;
import kucingvspanda.packet.ServerPacket;
import kucingvspanda.packet.models.RoomInfo;

/**
 *
 * @author FiqieUlya
 */
public class TCPClient {
    private String name;
    //private ActionPacket actionPacket;
    private String serverAddress;
    private ServerPacket packet;
    // TCP Components
    private Socket socket;
    private ObjectInputStream inputStream = null;
    private ObjectOutputStream outputStream = null;
    private static RoomInfo roomsInfo;

    
    public TCPClient(String host, String port) {
        initHostName(host, port);
    }

    public void initHostName(String host, String port) {
        try {
            //replace host name with your computer name or IP address
            setServerAddress(host);
            if (getServerAddress() == null)
                System.exit(1);

            setServerAddress(getServerAddress().trim());
            if (getServerAddress().length() == 0)// empty field
            {
                System.out.println("Server IP Address or Name can't be blank.");
                initHostName(host,port);
                return;
            }
            System.out.println("Trying to connect with server...\nServer IP Address:"
                    + getServerAddress());

            // create socket
            InetAddress inetAddress = InetAddress.getByName(getServerAddress());
            if (!inetAddress.isReachable(60000))// 60 sec
            {
                System.out
                        .println("Error! Unable to connect with server.\nServer IP Address may be wrong.");
                System.exit(1);
            }

            initPortNo(port);
        } catch (SocketException e) {
            System.out.println("Socket Exception:\n" + e);
            initHostName(host,port);
            return;
        } catch (IOException e) {
            initHostName(host,port);
            return;
        }
    }

    public void initPortNo(String portNo) {
        try {
            portNo = portNo.trim();
            if (portNo.length() == 0)// empty field
            {
                System.out.println("Server port No can't be blank.");
                initPortNo(portNo);
                return;
            }
            System.out.println("Trying to connect with server...\nServer Port No:" + portNo);

            socket = new Socket(getServerAddress(), Integer.parseInt(portNo));
            outputStream = new ObjectOutputStream(socket.getOutputStream());
           
        } catch (IOException e) {
            System.out.println("IO Exception:\n" + e);
            initPortNo(portNo);
            return;
        }
    }
    
    public ObjectInputStream getInputStream() {
        return inputStream;
    }

    public ObjectOutputStream getOutputStream() {
        return outputStream;
    }

    public void runClient(MainMenuController firstObs){
        ClientListenerThread clientListener = new ClientListenerThread(socket, name, firstObs);
    }

    /**
     * @return the Name
     */
    public String getName() {
        return name;
    }

    /**
     * @param Name the Name to set
     */
    public void setName(String Name) {
        this.name = Name;
    }

    /**
     * @return the serverAddress
     */
    public String getServerAddress() {
        return serverAddress;
    }

    /**
     * @param serverAddress the serverAddress to set
     */
    public void setServerAddress(String serverAddress) {
        this.serverAddress = serverAddress;
    }

    /**
     * @return the packet
     */
    public ServerPacket getPacket() {
        return packet;
    }

    /**
     * @param packet the packet to set
     */
    public void setPacket(ServerPacket packet) {
        this.packet = packet;
    }

    
}
