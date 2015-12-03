/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kucingvspanda.client.models;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.ArrayList;
import javax.swing.table.DefaultTableModel;
import kucingvspanda.packet.models.RoomInfo;

/**
 *
 * @author ASUS X202E
 */
public class RoomModel {
    private RoomInfo info;
    private List<String> players = new ArrayList<>();
    private List<String> spectators = new ArrayList<>();
    private DefaultTableModel playersTableModel = new DefaultTableModel(new Object[]{"Name", "Icon"},0);
    private String role;

    public RoomModel() {
        
    }
    
    public RoomModel(RoomInfo info) {
        this.info = info;
    }
    public RoomModel(RoomInfo info, List<String> players, List<String> spectators) {
        this.info = info;
        this.players = players;
        this.spectators = spectators;
        initPlayersTableModel();
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
    
    public boolean isPlayer() {
        return role.equals("Player");
    }
    
    public void addPlayer(String name) {
        players.add(name);
        info.setPlayerCount(getPlayerCount()+1);
        Object[] o = new Object[2];
        o[0] = name;
        o[1] = players.size()-1;
        playersTableModel.addRow(o);
    }
    
    public void removePlayer(String name) {
        playersTableModel.removeRow(players.indexOf(name));
        players.remove(name);
        info.setPlayerCount(getPlayerCount()-1);
    }
    
    public void addSpectator(String name) {
        spectators.add(name);
    }
    
    public void removeSpectator(String name) {
        spectators.remove(name);
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
    
    public void setPlayerList(List<String> players) {
        this.players = players;
        initPlayersTableModel();
    }
    
    public void initPlayersTableModel() {
        playersTableModel.setRowCount(0);
        for (int i=0;i<players.size();i++) {
            Object[] o = new Object[2];
            o[0] = players.get(i);
            o[1] = i;
            playersTableModel.addRow(o);
        }
    }
    
    public int getPlayerCount() {
        return info.getPlayerCount();
    }
    
    public String getStatus() {
        return info.getStatus();
    }
    
    public void setStatus(String status) {
        info.setStatus(status);
    }
    
    public String getName() {
        return info.getName();
    }
    
}
