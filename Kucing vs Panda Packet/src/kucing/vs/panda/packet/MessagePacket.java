/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kucing.vs.panda.packet;

/**
 *
 * @author Tifani
 */
public class MessagePacket implements Packet {
    private static final int identifier = Identifier.CHAT;
    private String message;
    
    public MessagePacket() {
        message = "";
    }
    
    public MessagePacket(String message) {
        this.message = message;
    }
    
    @Override
    public int getIdentifier() {
        return identifier;
    }

    @Override
    public Object getMessage() {
        return message;
    }
    
}
