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
        
        
        
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(GameFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(GameFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(GameFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(GameFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        
        frame.setVisible(true);
            
    }
    
}
