/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kucingvspanda;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 *
 * @author Tifani
 */
public class Client {
    private Socket socket = null;
    private DataOutputStream os = null;
    private DataInputStream is = null;
    
    public Client() {
        
    }
    
    private void initializeSocket(String host, int port) {
        // Attempts to connect to the specified server at the specified port.
        // If this constructor does not throw an exception,
        // the connection is successful and the client is connected to the server.
        try {
            socket = new Socket(host, port);
            os = new DataOutputStream(socket.getOutputStream());
            is = new DataInputStream(socket.getInputStream());
        } catch (UnknownHostException e) {
            System.err.println("Don't know about host: " + host);
        } catch (IOException e) {
            System.err.println("Couldn't get I/O for the connection to: " + port);
        }
    }

    private void closeSocket() {
        // Clean up: close the output stream, close the input stream, close the socket
        try {
            os.close();
            is.close();
            socket.close();
        } catch (Exception e) {
            System.err.println("Error occured when attempting to close socket");
        }
        
    }
}
