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
    private String roomName;
    private List<String> players = new ArrayList<>();
    private List<String> spectators = new ArrayList<>();
    private Board gameBoard = new Board();
    private String status="Waiting";
    private int turn=0;
    
    public Room() {
        roomName = "";
    }
    
    public Room(String name) {
        this.roomName = name;
    }
    
    public Room(String roomName, List<String> players, List<String> spectators, Board gameBoard, String status) {
        this.roomName = roomName;
        this.players = players;
        this.spectators = spectators;
        this.gameBoard = gameBoard;
        this.status = status;
    }
    
    public String getName() {
        return roomName;
    }
    
    public void addPlayer(String pName) {
        if (players.size()<15 && !status.equals("Playing")) {
            players.add(pName);
            //kirim pesan ke semua di room bahwa player bertambah pName
            //kirim pesan ke semua player yg lagi login bahwa players di roomName bertambah 1
        }
    }
    
    public void removePlayer(String name) {
        players.remove(name);
    }
    
    public void addSpectator(String name) {
        spectators.add(name);
    }
    
    public void removeSpectator(String name) {
        spectators.remove(name);
    }
   
    
    public void setPlayerToBoard(int x, int y, String pName) {
        //kirim pesan ke semua di room bahwa pName memilih x,y
        if (gameBoard.checkWin(x,y,pName)) {
            //kirim pesan pName menang ke semua di room
            endGame();
        } else if (gameBoard.boardFull()) {
            //kirim pesan board full ke semua di room
            endGame();
        }
    }
    
    public void startGame() {
        status = "Playing";
        //kirim pesan ke semua di board bahwa game dimulai
        //kirim pesan ke semua yg login bahwa status room ini lagi 
    }
    
    public void endGame() {
        status = "Waiting";
    }
    
    public String getStatus() {
        return status;
    }
    
    public void changeTurn() {
        turn = (turn+1)%players.size();
    }

}
