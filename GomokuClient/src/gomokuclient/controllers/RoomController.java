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
public class RoomController {
    private RoomModel roomModel;
    private GameFrame frame;
    
    public RoomController(GameFrame frame) {
        this.frame = frame;
    }

    public RoomModel getRoomModel() {
        return roomModel;
    }

    public void setRoomModel(RoomModel roomModel) {
        this.roomModel = roomModel;
    }
    
    public void buildRoomPageFromModel() {
        
    }
    
    public void exit() {
        frame.changeScreen("MainMenu");
    }
    
    
}
