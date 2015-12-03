/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kucingvspanda.packet.models;

import java.io.Serializable;

/**
 *
 * @author ASUS X202E
 */
public class RoomInfo implements Serializable {
    private String name;
    private String status="Waiting";
    private int playerCount=0;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public RoomInfo(String name) {
        this.name = name;
    }
    public RoomInfo(String name, int playerCount, String status) {
        this.name = name;
        this.status = status;
        this.playerCount = playerCount;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getPlayerCount() {
        return playerCount;
    }

    public void setPlayerCount(int playerCount) {
        this.playerCount = playerCount;
    }
    
    
}
