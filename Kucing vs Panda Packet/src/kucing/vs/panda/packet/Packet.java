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
public interface Packet {
    public int getIdentifier();
    public Object getMessage();
}
