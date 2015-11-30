/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gomokuserver;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.ArrayList;

/**
 *
 * @author ASUS X202E
 */
public class Room {
    private String name;
    private Map<String,Player> players = new HashMap<>();
    private Board gameBoard;
    
    public Room() {
        name = "";
    }
    
    public Room(String name) {
        this.name = name;
    }
    
    public String getName() {
        return name;
    }
    
    public int addPlayer(Player p) {
        if (players.size()<5) {
            players.put(p.getNickname(),p);
            return players.size();
        } else {
            return 0;
        }
    }
    
    public Player getPlayer(String pName) {
        return players.get(pName);
    }
    
    public void setPlayerToBoard(int x, int y, String name) {
        MessageSender.send(x+" "+y+" "+name);
        if (gameBoard.checkWin(x,y,name)) {
            MessageSender.send(name+" win");
            getPlayer(name).win();
        }
    }
    
}
