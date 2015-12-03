/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kucingvspanda.client.model;
import java.util.List;
import java.util.ArrayList;
import javax.swing.table.DefaultTableModel;
import java.util.Map;
import java.util.HashMap;
import kucing.vs.panda.packet.model.RoomInfo;

/**
 *
 * @author ASUS X202E
 */
public class MainMenuModel {
    private DefaultTableModel roomTableModel = new DefaultTableModel(new Object[]{"Name", "Players", "Status"},0);
    //private List<RoomInfo> rooms = new ArrayList<>();
    private Map<String,Integer> roomNumber = new HashMap<>();

    public MainMenuModel() {
        
    }
    
    public MainMenuModel(List<RoomInfo> rooms) {
        initModel(rooms);
    }
    
    public DefaultTableModel getTableModel() {
        return roomTableModel;
    }
    public void initModel(List<RoomInfo> rooms) {
        roomTableModel.setRowCount(0);
        int i = 0;
        for (RoomInfo room : rooms) {
            roomTableModel.addRow(buildObject(room));
            roomNumber.put(room.getName(),i);
            i++;
        }
    }
    
    public void addRoom(RoomInfo room) {
        int i = roomTableModel.getRowCount();
        roomTableModel.addRow(buildObject(room));
        roomNumber.put(room.getName(),i);
    }
    
    public void updateRoom(RoomInfo room) {
        updateRoomPlayerSize(room.getName(),room.getPlayerSize());
        updateRoomStatus(room.getName(),room.getStatus());
    }
    
    public void updateRoomPlayerSize(String roomName, int playerSize) {
        roomTableModel.setValueAt(playerSize,roomNumber.get(roomName),1);
    }
    
    public void updateRoomStatus(String roomName, String status) {
        roomTableModel.setValueAt(status,roomNumber.get(roomName),2);
    }
    
    public boolean roomExists(RoomInfo room) {
        return roomNumber.containsKey(room.getName());
    }
    
    public Object[] buildObject(RoomInfo room) {
        Object[] o = new Object[3];
        o[0] = room.getName();
        o[1] = room.getPlayerSize();
        o[2] = room.getStatus();
        return o;
    }
}
