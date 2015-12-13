/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kucingvspanda.server;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.ArrayList;
import kucingvspanda.packet.models.RoomInfo;
import kucingvspanda.packet.models.WinInfo;

/**
 *
 * @author ASUS X202E
 */
public class Room {
    private String roomName="**";
    private ArrayList<String> players = new ArrayList<>();
    private ArrayList<String> spectators = new ArrayList<>();
    private Board gameBoard = new Board();
    private String status="Waiting";
    private int turn=0;
    
    public Room() {
        roomName = "**";
    }
    
    public Room(String name) {
        this.roomName = name;
    }
    
    public Room(String roomName, ArrayList<String> players, ArrayList<String> spectators, Board gameBoard, String status) {
        this.roomName = roomName;
        this.players = players;
        this.spectators = spectators;
        this.gameBoard = gameBoard;
        this.status = status;
    }
    
    public String getName() {
        return getRoomName();
    }
    
    public void addPlayer(String pName) {
        if (getPlayers().size()<6 && !status.equals("Playing")&&!players.contains(pName)) {
            getPlayers().add(pName);
            //kirim pesan ke semua di room bahwa player bertambah pName
            //kirim pesan ke semua player yg lagi login bahwa players di roomName bertambah 1
        }
    }
    
    public void removePlayer(String name) {
        getPlayers().remove(name);
    }
    
    public void addSpectator(String name) {
        if (!getSpectators().contains(name)) getSpectators().add(name);
    }
    
    public void removeSpectator(String name) {
        getSpectators().remove(name);
    }
   
    
    public void setPlayerToBoard(int x, int y, String pName) {
        //kirim pesan ke semua di room bahwa pName memilih x,y
        getGameBoard().setPlayerAtCoordinate(x,y,pName);
        WinInfo win = getGameBoard().checkWin(x,y,pName);
        if (win!=null) {
            win.setWinner(pName);
            //kirim pesan berisi WinInfo win
            System.out.println("x: "+win.getX()+" - y: "+win.getY()+" - wintype: "+win.getWintype()+" - winner: "+win.getWinner());
            setStatus("Waiting");
        } else if (getGameBoard().boardFull()) {
            //kirim pesan board full ke semua di room
            endGame();
        }
        changeTurn();
    }
    
    public void startGame() {
        setStatus("Playing");
        //kirim pesan ke semua di board bahwa game dimulai
        //kirim pesan ke semua yg login bahwa status room ini lagi 
    }
    
    public void endGame() {
        setStatus("Waiting");
    }
    
    public String getStatus() {
        return status;
    }
    
    public void changeTurn() {
        setTurn((getTurn() + 1) % getPlayers().size());
    }

    /**
     * @return the roomName
     */
    public String getRoomName() {
        return roomName;
    }

    /**
     * @param roomName the roomName to set
     */
    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    /**
     * @return the players
     */
    public List<String> getPlayers() {
        return players;
    }

    /**
     * @param players the players to set
     */
    public void setPlayers(ArrayList<String> players) {
        this.players = players;
    }

    /**
     * @return the spectators
     */
    public List<String> getSpectators() {
        return spectators;
    }

    /**
     * @param spectators the spectators to set
     */
    public void setSpectators(ArrayList<String> spectators) {
        this.spectators = spectators;
    }

    /**
     * @return the gameBoard
     */
    public Board getGameBoard() {
        return gameBoard;
    }

    /**
     * @param gameBoard the gameBoard to set
     */
    public void setGameBoard(Board gameBoard) {
        this.gameBoard = gameBoard;
    }

    /**
     * @param status the status to set
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * @return the turn
     */
    public int getTurn() {
        System.out.println("turn:"+turn);
        return turn;
    }

    /**
     * @param turn the turn to set
     */
    public void setTurn(int turn) {
        this.turn = turn;
    }
    public RoomInfo toRoomInfo() {
        return new RoomInfo(roomName, players.size(), status);
    }
}
