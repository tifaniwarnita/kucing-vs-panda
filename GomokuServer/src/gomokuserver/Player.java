/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gomokuserver;

/**
 *
 * @author ASUS X202E
 */
public class Player {
    private String nickname;
    private int totalscore;
    
    public Player() {
        nickname = "";
        totalscore = 0;
    }
    
    public Player(String nickname) {
        this.nickname = nickname;
    }
    
    public String getNickname() {
        return nickname;
    }
    
    public int getTotalScore() {
        return totalscore;
    }
    
    public void setNickname(String nickname) {
        this.nickname = nickname;
    }
    
    public void setTotalScore(int totalscore) {
        this.totalscore = totalscore;
    }
    
    public void win() {
        this.totalscore++;
    }
}
