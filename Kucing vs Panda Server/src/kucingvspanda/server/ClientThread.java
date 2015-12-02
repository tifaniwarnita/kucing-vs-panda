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
import java.io.PrintWriter;
import java.net.Socket;
import java.util.HashMap;

        
public class ClientThread implements Runnable {
    // TCP Components
    private Socket socket;
    private BufferedReader in;
    private PrintWriter out;
    private String Name;

    // seperate thread
    private Thread thread;

    // boolean variable to check that client is running or not
    private volatile boolean isRunning = true;

    // opcode
    private int opcode;
    private HashMap<String, ClientThread> clientInfo = new HashMap<String, ClientThread>();

    public ClientThread(Socket socket) {
        try {
            this.socket = socket;
            this.clientInfo = ServerTCP.getClientInfo();

            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream(), true);

            thread = new Thread(this);
            thread.start();

        } catch (IOException e) {
            System.out.println(e);
        }
    }
    public void sendListClient(){
        // tell other users about new added user and update their online users list
            for (ClientThread client : clientInfo.values()) {
                client.out.println(Opcode.CLIENT_CONNECTED);
                client.out.println(clientInfo.size());

                for (ClientThread client1 : clientInfo.values()) {
                    client.out.println(client1.Name);
                }
            }
    }
    public void sendBroadcastMessage(String name, String message){
        // tell other users about new added user and update their online users list
            for (ClientThread client : clientInfo.values()) {
                if(!client.Name.equalsIgnoreCase(name)){
                    client.out.println(Opcode.CLIENT_BROADCAST);
                    client.out.println(message);
                    System.out.println("kirim pesan ke "+ client.Name + " : "+message);
                }
            }
    }
    public void run() {
        try {
            while (isRunning) {
                if (!in.ready())
                    continue;

                opcode = Integer.parseInt(in.readLine());// getting opcode first from client
                switch (opcode) {
                    case Opcode.CLIENT_MESSAGE:
                        String name = in.readLine();
                        String Message = in.readLine();
                        sendBroadcastMessage(name,Message);
                        break;
                        
                    case Opcode.CLIENT_CONNECTEING:
                        Name = in.readLine();

                        boolean result = clientInfo.containsKey(Name);
                        out.println(Opcode.CLIENT_CONNECTEING);
                        out.println(result);
                        if (result)// wait for another chat name if already present
                            continue;

                        // send list of already online users to new online user
                        // for (Object user : clientInfo.keySet().toArray()) {
                        // out.println(Opcode.CLIENT_CONNECTED);
                        // out.println(user.toString());
                        // }

                        // put new entry in clientInfo hashmap
                        clientInfo.put(Name, this);

                        int i = 0;
                        for (String key : clientInfo.keySet()) {
                            if (key.equals(Name)) {
                                System.out.println(Name + " added at " + (i + 1) + " position");
                            }
                            i++;
                        }

                        sendListClient();

                        break;
                }
            }

            // clsoe all connections
            out.close();
            in.close();
            socket.close();
        } catch (IOException e) {
            System.out.println(e);
        }
    }
}
