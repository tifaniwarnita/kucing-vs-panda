/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gomokuserver;
import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;

/**
 *
 * @author ASUS X202E
 */
public class MainMenu {
    private Map<String,Room> rooms = new HashMap<>();
    private Map<String,Player> allPlayers = new HashMap<>();
    private List<String> playersInGame = new ArrayList<>();
    
    public boolean enterPlayer(String name) {
        if (!playersInGame.contains(name)) {
            if (!allPlayers.containsKey(name)) {
                allPlayers.put(name,new Player(name));
            }
            playersInGame.add(name);
            return true;
        }
        return false;
    }
    
    public boolean addRoom(String roomName) {
        if (rooms.containsKey(roomName)) {
            return false;
        } else {
            rooms.put(roomName,new Room(roomName));
            return true;
        }
    }
    
    public void enterRoom(String roomName, String playerName) {
        rooms.get(roomName).addPlayer(playerName);
    }    
}
