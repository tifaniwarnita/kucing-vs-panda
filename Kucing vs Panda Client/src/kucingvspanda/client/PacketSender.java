package kucingvspanda.client;

import com.sun.security.ntlm.Client;
import java.io.ObjectOutputStream;
import kucingvspanda.packet.ClientPacket;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Tifani
 */
public class PacketSender {
    
    public static void sendLoginPacket(ObjectOutputStream outputStream, String nickname) {
        ClientPacket loginPacket = new ClientPacket();
        loginPacket.buildLoginPacket(nickname);
        try {
            outputStream.writeObject(loginPacket);
        } catch (Exception e) {
            System.out.println("Sending login packet failed: " + e.toString());
        }
    }
    
    public static void sendAddRoomPacket(ObjectOutputStream outputStream, String roomName) {
        ClientPacket addRoomPacket = new ClientPacket();
        addRoomPacket.buildAddRoomPacket(roomName);
        try {
            outputStream.writeObject(addRoomPacket);
        } catch (Exception e) {
            System.out.println("Sending add room packet failed: " + e.toString());
        }
    }
    
    public static void sendPlayPacket(ObjectOutputStream outputStream, String roomName) {
        ClientPacket playPacket = new ClientPacket();
        playPacket.buildPlayPacket(roomName);
        try {
            outputStream.writeObject(playPacket);
        } catch (Exception e) {
            System.out.println("Sending play packet failed: " + e.toString());
        }
    }
    
    public static void sendSpectatorPacket(ObjectOutputStream outputStream, String roomName) {
        ClientPacket spectatorPacket = new ClientPacket();
        spectatorPacket.buildSpectatorPacket(roomName);
        try {
            outputStream.writeObject(spectatorPacket);
        } catch (Exception e) {
            System.out.println("Sending spectator packet failed: " + e.toString());
        }
    }
    
    public static void sendStartGamePacket(ObjectOutputStream outputStream) {
        ClientPacket startGamePacket = new ClientPacket();
        startGamePacket.buildStartGamePacket();
        try {
            outputStream.writeObject(startGamePacket);
        } catch (Exception e) {
            System.out.println("Sending start game packet failed: " + e.toString());
        }
    }
    
    public static void sendAddPawnPacket(ObjectOutputStream outputStream, int x, int y) {
        ClientPacket addPawnPacket = new ClientPacket();
        addPawnPacket.buildAddPawnPacket(x, y);
        try {
            outputStream.writeObject(addPawnPacket);
        } catch (Exception e) {
            System.out.println("Sending add pawn packet failed: " + e.toString());
        }
    }
    
    public static void sendExitGamePacket(ObjectOutputStream outputStream) {
        ClientPacket exitGamePacket = new ClientPacket();
        exitGamePacket.buildExitGamePacket();
        try {
            outputStream.writeObject(exitGamePacket);
        } catch (Exception e) {
            System.out.println("Sending exit game packet failed: " + e.toString());
        }
    }
    
    public static void sendViewHighscorePacket(ObjectOutputStream outputStream) {
        ClientPacket viewHighscorePacket = new ClientPacket();
        viewHighscorePacket.buildViewHighscorePacket();
        try {
            outputStream.writeObject(viewHighscorePacket);
        } catch (Exception e) {
            System.out.println("Sending view highscore packet failed: " + e.toString());
        }
    }
    
    public static void sendChatPacket(ObjectOutputStream outputStream, String message) {
        ClientPacket chatPacket = new ClientPacket();
        chatPacket.buildChatPacket(message);
        try {
            outputStream.writeObject(chatPacket);
        } catch (Exception e) {
            System.out.println("Sending chat packet failed: " + e.toString());
        }
    }
        
}
