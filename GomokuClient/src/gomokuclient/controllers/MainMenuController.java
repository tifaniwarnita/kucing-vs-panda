/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gomokuclient.controllers;
import gomokuclient.views.*;
import gomokuclient.models.*;

/**
 *
 * @author ASUS X202E
 */
public class MainMenuController {
    private GameFrame frame;
    private MainMenuModel model = new MainMenuModel();
    private final RoomController roomController;
    
    public MainMenuController(GameFrame frame) {
        this.frame = frame;
        roomController = new RoomController(frame);
    }
    
    public void setModel(MainMenuModel model) {
        this.model = model;
    }
    public void init() {
        frame.initRoomTable(model.getTableModel());
    }
    
    public void enterGame() {
        RoomInfo ri = new RoomInfo("Test",3,"Waiting");
        RoomInfo ri2 = new RoomInfo("Test2",5,"Playing");
        model.addRoom(ri);
        model.addRoom(ri2);
        frame.updateRoomButtons();
        frame.changeScreen("MainMenu");
    }
    
    public void test(String roomName) {
        RoomInfo ri = new RoomInfo(roomName,0,"Waiting");
        addRoom(ri);
    }
    
    public void play(int roomNo) {
        /* request RoomModel */
        roomController.setRoomModel(new RoomModel(model.getRoomInfo(roomNo)));
        roomController.buildRoomPageFromModel();
        frame.changeScreen("Room");
        
    }
    
    public void watch(int roomNo) {
        /* request RoomModel */
        roomController.setRoomModel(new RoomModel(model.getRoomInfo(roomNo)));
        roomController.getRoomModel().addSpectator("test");
        roomController.buildRoomPageFromModel();
        frame.changeScreen("Room");
    }
    
    public void createRoom(String roomName) {
        /*request add room to server */
    }
    
    public MainMenuModel getModel() {
        return model;
    }
    
    public void addRoom(RoomInfo room) {
        model.addRoom(room);
        frame.createRoomTableButtons(model.getTableModel().getRowCount());
    }

    public RoomController getRoomController() {
        return roomController;
    }
    
}
