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
import kucingvspanda.packet.Identifier;
import kucingvspanda.packet.ServerPacket;
import kucingvspanda.packet.models.RoomInfo;

/**
 *
 * @author FiqieUlya
 */
public class TCPClient implements Observer {
    private String Name;
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
        runClient();// have fun
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
    
    /**
     * @return the roomsInfo
     */
    public static RoomInfo getRoomsInfo() {
        return roomsInfo;
    }

    /**
     * @param aRoomsInfo the roomsInfo to set
     */
    public static void setRoomsInfo(RoomInfo aRoomsInfo) {
        roomsInfo = aRoomsInfo;
    }

   /* public void sendChatName() throws IOException {
        System.out.println("Enter your name:");
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        
        String name = br.readLine();
        if (name == null)
            System.exit(1);

        // title case (get only first 9 chars of chat name)
        name = name.trim();

        if (name.length() == 0) {
            System.out.println("Please enter your chat name.");
            sendChatName();
            return;
        }
        actionPacket= new ActionPacket(Identifier.LOGIN,name);
        // sending login packet to the server
        outputStream.writeObject(actionPacket);                
    }*/
    
    
    /*public void clientListener(int opcode){
        switch (opcode) {
                        //a new broadcast message
                    case Opcode.CLIENT_BROADCAST:
                        String name = in.readLine();
                        String message = in.readLine();
                        System.out.println("Pesan dari "+name+" : " + message);
                        break;
                        
                    case Opcode.CLIENT_CONNECTING:
                        // this client is connecting
                        boolean result = Boolean.valueOf(in.readLine());
                        if (result) {
                            System.out.println(Name + " is already present. Try different one.");
                            runClient();
                        }

                        break;

                    case Opcode.CLIENT_CONNECTED:
                        // a new client is connected
                        Integer totalClient = Integer.valueOf(in.readLine());
                        System.out.println("Total Client:" + totalClient);

                        for (int i = 0; i < totalClient; i++) {
                            String client = in.readLine();
                            System.out.println((i + 1) + ":" + client);
                        }
                        
                        break;

                }
    }*/
public void runClient(){
    
}
//    public void runClient() {
//        try {
//            sendChatName();
//            new ClientSenderThread(socket,Name);
//            while (true) {
//                packet =  inputStream.readObject();
//                int opcode = packet.getIdentifier();
//                //clientListener(opcode);
//                switch (opcode) {
//                        //a new broadcast message
//                    case Opcode.CLIENT_BROADCAST:
//                        System.out.println("Pesan dari ");
//                        break;
//                    //KONDISI PACKET YANG DI TERIMA DARI SERVER
////                        
////                    case Opcode.CLIENT_CONNECTING:
////                        // this client is connecting
////                        boolean result = Boolean.valueOf(in.readLine());
////                        if (result) {
////                            System.out.println(Name + " is already present. Try different one.");
////                            runClient();
////                        }
////
////                        break;
////
////                    case Opcode.CLIENT_CONNECTED:
////                        // a new client is connected
////                        Integer totalClient = Integer.valueOf(in.readLine());
////                        System.out.println("Total Client:" + totalClient);
////
////                        for (int i = 0; i < totalClient; i++) {
////                            String client = in.readLine();
////                            System.out.println((i + 1) + ":" + client);
////                        }
////                        
////                        break;
//
//                }
//            //}
//        } catch (IOException e) {
//            System.out.println("Client is closed...");
//        } catch (ClassNotFoundException ex) {
//            Logger.getLogger(TCPClient.class.getName()).log(Level.SEVERE, null, ex);
//        }
//    }
    
    public static void main(String[] args) throws IOException{
        if((args.length< 2)||(args.length>3))
            throw new IllegalArgumentException("Parameter(s): <Server> [<Port>] ");
        String server = args[0]; //IP address
        //Convert input String to bytes using the default character encoding
    //byte[] byteBuffer = args[2].getBytes();
        
        //int servPort = (args.length==2)? Integer.parseInt((args[1])) : 7;
        
        TCPClient tcp = new TCPClient(server,args[1]); 
        /*
        //Create socket that is connected to server on specified port
        Socket socket = new Socket(server, servPort);
        System.out.println("Connected to server... sending echo string");
        
        InputStream in = socket.getInputStream();
        OutputStream out = socket.getOutputStream();
        
        out.write(byteBuffer); //mengirim string hasil encode ke server
        
        //Receive the same string back from the server
        int totalBytesRcvd = 0; //Total bytes received so far
        int bytesRcvd;           //Bytes received in last read
        while(totalBytesRcvd < byteBuffer.length){
            if((bytesRcvd = in.read(byteBuffer, totalBytesRcvd, byteBuffer.length - totalBytesRcvd))==-1)
                throw new SocketException("Connection closed prematurely");
                totalBytesRcvd +=bytesRcvd;
        }
        
        System.out.println("Received: " + new String(byteBuffer));
        
        socket.close(); // Close the socket and its streams
        */
    }

    @Override
    public void update(Observable o, Object arg) {
        int identifier = (int) arg;
        switch(identifier){
            case Identifier.LOGIN_SUCCESS : //list<roominfo>
                //tidak melakukan apapun
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
            case Identifier.ADD_PLAYER_COUNT: //roomname 
                break;
            case Identifier.DEC_PLAYER_COUNT: //roomname 
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
            case Identifier.WIN: //roomname + winner
                break;
            case Identifier.HIGHSCORE: //highscore list
                break;
            case Identifier.BOARD_FULL:
                break;
        }
    }

    /**
     * @return the Name
     */
    public String getName() {
        return Name;
    }

    /**
     * @param Name the Name to set
     */
    public void setName(String Name) {
        this.Name = Name;
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
