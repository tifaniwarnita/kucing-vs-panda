/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kucingvspanda.client;

import java.net.*; //for socket
import java.io.*; //for IOException and Input/OutputStream
/**
 *
 * @author FiqieUlya
 */
public class TCPClient {
    private String Name;
    private String serverAddress;

    // TCP Components
    private Socket socket;
    private BufferedReader in;
    private PrintWriter out;
    
    public TCPClient(String host, String port) {

        initHostName(host, port);
        runClient();// have fun
    }

    public void initHostName(String host, String port) {
        try {
            //replace host name with your computer name or IP address
            serverAddress = host;
            if (serverAddress == null)
                System.exit(1);

            serverAddress = serverAddress.trim();
            if (serverAddress.length() == 0)// empty field
            {
                System.out.println("Server IP Address or Name can't be blank.");
                initHostName(host,port);
                return;
            }
            System.out.println("Trying to connect with server...\nServer IP Address:"
                    + serverAddress);

            // create socket
            InetAddress inetAddress = InetAddress.getByName(serverAddress);
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

            //String portNo = "1234";

            portNo = portNo.trim();
            if (portNo.length() == 0)// empty field
            {
                System.out.println("Server port No can't be blank.");
                initPortNo(portNo);
                return;
            }
            System.out.println("Trying to connect with server...\nServer Port No:" + portNo);

            socket = new Socket(serverAddress, Integer.parseInt(portNo));
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream(), true);

        } catch (IOException e) {
            System.out.println("IO Exception:\n" + e);
            initPortNo(portNo);
            return;
        }
    }

    public void sendChatName() throws IOException {
        System.out.println("Enter your name:");
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String name = br.readLine();
        if (name == null)
            System.exit(1);

        // title case (get only first 9 chars of chat name)
        name = name.trim();

        if (name.equalsIgnoreCase("All")) {
            System.out.println("This name is already reserved. Try different one.");
            sendChatName();
            return;
        }
        if (name.length() == 0) {
            System.out.println("Please enter your chat name.");
            sendChatName();
            return;
        }
        if (name.length() == 1)
            Name = String.valueOf(name.charAt(0)).toUpperCase();
        if (name.length() > 1 && name.length() < 10)
            Name = String.valueOf(name.charAt(0)).toUpperCase()
                    + name.substring(1).toLowerCase();
        else if (name.length() > 9)
            Name = String.valueOf(name.charAt(0)).toUpperCase()
                    + name.substring(1, 10).toLowerCase();

        // sending opcode first then sending chatName to the server
        out.println(Opcode.CLIENT_CONNECTING);
        out.println(Name);
    }
    
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

    public void runClient() {
        try {
            sendChatName();
            new ClientSenderThread(socket,Name);
            while (true) {
                int opcode = Integer.parseInt(in.readLine());
                //clientListener(opcode);
                System.out.println("opcode : "+opcode);
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
            }
        } catch (IOException e) {
            System.out.println("Client is closed...");
        }
    }
    
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
}
