/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kucingvspanda.packet.models;

/**
 *
 * @author ASUS X202E
 */
public class WinInfo {
    private int x;
    private int y;
    private String wintype;
    private String winner;

    public WinInfo(int x, int y, String wintype) {
        this.x = x;
        this.y = y;
        this.wintype = wintype;
    }
    
    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public String getWintype() {
        return wintype;
    }

    public void setWintype(String wintype) {
        this.wintype = wintype;
    }

    public String getWinner() {
        return winner;
    }

    public void setWinner(String winner) {
        this.winner = winner;
    }
    
    
}
