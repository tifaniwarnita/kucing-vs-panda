/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kucingvspanda.client.controllers;
import java.io.IOException;
import kucingvspanda.client.TCPClient;
import kucingvspanda.client.views.*;
import kucingvspanda.client.models.*;

/**
 *
 * @author ASUS X202E
 */
public class MainController {
    private static final GameFrame frame = new GameFrame();
    
    public static void main(String[] args) throws IOException{
        if((args.length< 2)||(args.length>3))
            throw new IllegalArgumentException("Parameter(s): <Server> [<Port>] ");
        String server = args[0]; //IP address
        String port = args[1];
        
        TCPClient tcp = new TCPClient(server, port);
        
        
        
        
        
        frame.setVisible(true);
        frame.setExtendedState(frame.getExtendedState() | javax.swing.JFrame.MAXIMIZED_BOTH);    
    }
    
}
