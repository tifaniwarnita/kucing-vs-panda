/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kucingvspanda.packet;

import java.util.ArrayList;
import kucingvspanda.packet.model.RoomInfo;

/**
 *
 * @author Tifani
 */
public class RoomInfoPacket implements Packet {
    private static final int identifier = Identifier.ROOM_INFO;
    private ArrayList<RoomInfo> roomInfo;
    
    public RoomInfoPacket() {
        if (roomInfo==null)
            roomInfo = new ArrayList<>();
    }
    
    public RoomInfoPacket(ArrayList<RoomInfo> roomInfo) {
        this.roomInfo = roomInfo;
    }
    
    @Override
    public int getIdentifier() {
        return identifier;
    }

    @Override
    public Object getMessage() {
        return roomInfo;
    }
}
