/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kucing.vs.panda.packet.model;

/**
 *
 * @author ASUS X202E
 */
public class RoomInfo {
    private String name;
    private String status;
    private int playerSize;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public RoomInfo(String name, String status, int playerSize) {
        this.name = name;
        this.status = status;
        this.playerSize = playerSize;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getPlayerSize() {
        return playerSize;
    }

    public void setPlayerSize(int playerSize) {
        this.playerSize = playerSize;
    }
    
    
}
