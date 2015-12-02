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
public class LoginPacket implements Packet {
    private static final int identifier = Identifier.LOGIN;
    private String message;
    
    public LoginPacket() {
        message = "";
    }
    
    public LoginPacket(String message) {
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
