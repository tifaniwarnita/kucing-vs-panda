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
public class ActionPacket implements Packet {
    private final int identifier;
    private String payload;
    
      
    public ActionPacket(int identifier, String payload) {
        this.identifier = identifier;
        this.payload = payload;
    }

    @Override
    public int getIdentifier() {
        return identifier;
    }

    @Override
    public Object getMessage() {
        return payload;
    }
    
}
