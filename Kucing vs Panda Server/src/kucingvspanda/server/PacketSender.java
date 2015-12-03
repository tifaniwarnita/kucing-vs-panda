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
    
    // Reply: LoginPacket
    public static void sendLoginSuccessPacket(ObjectOutputStream outputStream, ArrayList<RoomInfo> roomInfoList) {
        ServerPacket loginSuccessPacket = new ServerPacket();
        loginSuccessPacket.buildLoginSuccessPacket(roomInfoList);
        try {
            outputStream.writeObject(loginSuccessPacket);
        } catch (Exception e) {
            System.out.println("Sending login success packet failed: " + e.toString());
        }
    }
    
    public static void sendLoginFailedPacket(ObjectOutputStream outputStream) {
        ServerPacket loginFailedPacket = new ServerPacket();
        loginFailedPacket.buildLoginFailedPacket();
        try {
            outputStream.writeObject(loginFailedPacket);
        } catch (Exception e) {
            System.out.println("Sending login failed packet failed: " + e.toString());
        }
    }
    
    
    public static void sendRoomInfoPacket()
}
