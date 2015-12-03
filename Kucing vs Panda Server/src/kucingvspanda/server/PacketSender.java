/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kucingvspanda.server;

import java.io.ObjectOutputStream;
import java.util.ArrayList;
import kucingvspanda.packet.ClientPacket;
import kucingvspanda.packet.ServerPacket;
import kucingvspanda.packet.model.RoomInfo;

/**
 *
 * @author Tifani
 */
public class PacketSender {
    
    // REPLY: LOGINPACKET
    // To: Packet sender
    public static void sendLoginSuccessPacket(ObjectOutputStream outputStream, ArrayList<RoomInfo> roomInfoList) {
        ServerPacket loginSuccessPacket = new ServerPacket();
        loginSuccessPacket.buildLoginSuccessPacket(roomInfoList);
        try {
            outputStream.writeObject(loginSuccessPacket);
        } catch (Exception e) {
            System.out.println("Sending login-success packet failed: " + e.toString());
        }
    }
    
    // To: Packet sender
    public static void sendLoginFailedPacket(ObjectOutputStream outputStream) {
        ServerPacket loginFailedPacket = new ServerPacket();
        loginFailedPacket.buildLoginFailedPacket();
        try {
            outputStream.writeObject(loginFailedPacket);
        } catch (Exception e) {
            System.out.println("Sending login-failed packet failed: " + e.toString());
        }
    }
    
    
    // REPLY: ADDROOMPACKET
    // To: Packet sender + all client with roomname = null
    public static void sendAddRoomSuccessPacket(ObjectOutputStream outputStream, RoomInfo newRoomInfo){
        ServerPacket addRoomSuccessPacket = new ServerPacket();
        addRoomSuccessPacket.buildAddRoomSuccessPacket(newRoomInfo);
        try {
            outputStream.writeObject(addRoomSuccessPacket);
        } catch (Exception e) {
            System.out.println("Sending add-room-success packet failed: " + e.toString());
        }
    }
    
    // To: Packet sender
    public static void sendAddRoomFailedPacket(ObjectOutputStream outputStream) {
        ServerPacket addRoomFailedPacket = new ServerPacket();
        addRoomFailedPacket.buildAddRoomFailedPacket();
        try {
            outputStream.writeObject(addRoomFailedPacket);
        } catch (Exception e) {
            System.out.println("Sending add-room-failed packet failed: " + e.toString());
        }
    }
    
    
    // REPLY: PLAYPACKET
    // To: Packet sender
    public static void sendPlaySuccessPacket(ObjectOutputStream outputStream, String roomName, 
            ArrayList<String> players, ArrayList<String> spectators) {
        ServerPacket playSuccessPacket = new ServerPacket();
        playSuccessPacket.buildPlaySuccessPacket(roomName, players, spectators);
        try {
            outputStream.writeObject(playSuccessPacket);
        } catch (Exception e) {
            System.out.println("Sending play-success packet failed: " + e.toString());
        }
    }
    
    // To: Players in room
    public static void sendNewPlayerPacket(ObjectOutputStream outputStream, String playerName) {
        ServerPacket newPlayerPacket = new ServerPacket();
        newPlayerPacket.buildNewPlayerPacket(playerName);
        try {
            outputStream.writeObject(newPlayerPacket);
        } catch (Exception e) {
            System.out.println("Sending new-player packet failed: " + e.toString());
        }
    }
    
    // To: All client (except players in room)
    
    
}
