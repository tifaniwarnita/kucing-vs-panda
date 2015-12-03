/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kucingvspanda.client.models;
import java.util.List;
import java.util.ArrayList;
import javax.swing.table.DefaultTableModel;
import kucingvspanda.packet.models.RoomInfo;

/**
 *
 * @author ASUS X202E
 */
public class MainMenuModel {
    private DefaultTableModel roomTableModel = new DefaultTableModel(new Object[]{"Name", "Players", "Status"},0);
    private List<String> rooms = new ArrayList<>();
    private static String currentPlayer;

    public MainMenuModel() {
        
    }
    
    public MainMenuModel(List<RoomInfo> roomInfoList) {
        initModel(roomInfoList);
    }
    
    public DefaultTableModel getTableModel() {
        return roomTableModel;
    }
    public void initModel(List<RoomInfo> roomInfoList) {
        roomTableModel.setRowCount(0);
        rooms.clear();
        for (RoomInfo room : roomInfoList) {
            rooms.add(room.getName());
            roomTableModel.addRow(buildObject(room));
        }
    }
    
    public void addRoom(RoomInfo room) {
        roomTableModel.addRow(buildObject(room));
        rooms.add(room.getName());
    }
    
    public void delRoom(String roomName) {
        roomTableModel.removeRow(getRoomNumber(roomName));
        rooms.remove(roomName);
    }
    
    public void incRoomPlayerCount(String roomName) {
        roomTableModel.setValueAt(getRoomPlayerCount(roomName)+1,getRoomNumber(roomName),1);
    }
    
    public void decRoomPlayerCount(String roomName) {
        roomTableModel.setValueAt(getRoomPlayerCount(roomName)-1,getRoomNumber(roomName),1);
    }
    
    public void updateRoomStatus(String roomName, String status) {
        roomTableModel.setValueAt(status,getRoomNumber(roomName),2);
    }
    
    public Object[] buildObject(RoomInfo room) {
        Object[] o = new Object[3];
        o[0] = room.getName();
        o[1] = room.getPlayerCount();
        o[2] = room.getStatus();
        return o;
    }
    
    public int getRoomPlayerCount(String name) {
        return ((int)roomTableModel.getValueAt(getRoomNumber(name),1));
    }

    public int getRoomNumber(String roomName) {
        return rooms.indexOf(roomName);
    }
    
    
    public List<String> getRooms() {
        return rooms;
    }
    
    public static String getCurrentPlayer() {
        return currentPlayer;
    }
    
    public static void setCurrentPlayer(String name) {
        currentPlayer = name;
    }
}
