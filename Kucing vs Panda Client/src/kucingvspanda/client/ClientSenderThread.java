/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kucingvspanda.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 *
 * @author FiqieUlya
 */
public class ClientSenderThread implements Runnable {
    private String name;
    private Socket socket;
    private Thread thread;
    //private BufferedReader in;
    private PrintWriter out;
    // boolean variable to check that client is running or not
    private volatile boolean isRunning = true;

    // opcode
    private int opcode;
    
    public ClientSenderThread(Socket socket, String name){
        try {
            this.socket = socket;
            this.name = name;

            //in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream(), true);

            thread = new Thread(this);
            thread.start();

        } catch (IOException e) {
            System.out.println(e);
        }
    }
    
    public void sendMessage() throws IOException {
        System.out.println("Enter your message :");
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String message = br.readLine();
        
        // sending opcode first then sending chatName to the server
        out.println(Opcode.CLIENT_MESSAGE);
        out.println(name);
        out.println(message);
    }
    
    @Override
    public void run() {
        try{
            while(isRunning){
                sendMessage();
            }
            // clsoe all connections
            out.close();
            socket.close();
        } catch (IOException e) {
            System.out.println(e);
        }
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
