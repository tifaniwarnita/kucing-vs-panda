/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gomokuclient.models;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.ArrayList;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author ASUS X202E
 */
public class RoomModel {
    private RoomInfo info;
    private List<String> players = new ArrayList<>();
    private List<String> spectators = new ArrayList<>();
    private DefaultTableModel playersTableModel = new DefaultTableModel(new Object[]{"Name", "Icon"},0);
    private BoardModel board;

    public RoomModel() {
        
    }
    
    public RoomModel(RoomInfo info) {
        this.info = info;
    }
    public RoomModel(RoomInfo info, List<String> players, List<String> spectators) {
        this.info = info;
        this.players = players;
        this.spectators = spectators;
    }
    
    public void addPlayer(String name) {
        players.add(name);
        info.setPlayerSize(info.getPlayerSize()+1);
        Object[] o = new Object[2];
        o[0] = name;
        o[1] = players.size()-1;
        playersTableModel.addRow(o);
    }
    
    public void removePlayer(String name) {
        players.remove(name);
        info.setPlayerSize(info.getPlayerSize()-1);
    }
    
    public void addSpectator(String name) {
        spectators.add(name);
    }
    
    public void removeSpectator(String name) {
        spectators.remove(name);
    }

    public BoardModel getBoard() {
        return board;
    }

    public void setBoard(String[][] board) {
        this.board = new BoardModel(board,players);
    }

    public DefaultTableModel getPlayersTableModel() {
        return playersTableModel;
    }
    
    public List<String> getSpectatorList() {
        return spectators;
    }
    
    public List<String> getPlayerList() {
        return players;
    }
    
    public void initPlayersTableModel() {
        for (int i=0;i<players.size();i++) {
            Object[] o = new Object[2];
            o[0] = players.get(i);
            o[1] = i;
            playersTableModel.addRow(o);
        }
    }
}
