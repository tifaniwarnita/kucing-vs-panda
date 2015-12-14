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
public class HighScoreInfo implements Serializable{
    private String playerName;
    private int score;

    public HighScoreInfo(){
        playerName="";
        score=0;
    }
    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
    
    
}
