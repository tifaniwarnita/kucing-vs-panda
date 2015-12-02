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
    
    public MainMenuController(GameFrame frame) {
        this.frame = frame;
    }
    
    public void setModel(MainMenuModel model) {
        this.model = model;
    }
    public void enterGame() {
        RoomInfo ri = new RoomInfo("Test","Waiting",3);
        RoomInfo ri2 = new RoomInfo("Test2","Playing",5);
        model.addRoom(ri);
        model.addRoom(ri2);
        frame.initRoomTable(model.getTableModel());
        frame.changeScreen("MainMenu");
    }
    
    public void test(String roomName) {
        RoomInfo ri = new RoomInfo(roomName,"Waiting",0);
        addRoom(ri);
    }
    
    public void play(int roomNo) {
        /* request RoomModel */
        frame.changeScreen("Room");
        frame.test();
    }
    
    public void watch(int roomNo) {
        /* request RoomModel */
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
    
}
