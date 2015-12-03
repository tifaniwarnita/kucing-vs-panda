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
    private BoardController boardController;
    
    public RoomController(GameFrame frame) {
        this.frame = frame;
        boardController = new BoardController(frame);
    }

    public RoomModel getRoomModel() {
        return roomModel;
    }

    public void setRoomModel(RoomModel roomModel) {
        this.roomModel = roomModel;
    }
    
    public void buildRoomPageFromModel() {
        String[][] test = new String[20][20];
        for (int i=0;i<20;i++) {
            for (int j=0;j<20;j++) {
                if (i==j) test[i][j] = "Tifa";
                else test[i][j] = "Nilta";
            }
        }
        roomModel.addPlayer("Nilta");
        roomModel.addPlayer("Tifa");
        frame.initPlayerTable(roomModel.getPlayersTableModel());
        frame.initSpectatorList(roomModel.getSpectatorList());
        boardController.setModel(new BoardModel(test,roomModel.getPlayerList()));
    }
    
    public void exitGame() {
        frame.changeScreen("MainMenu");
    }
    
    public void requestStartGame() {
        //send message start game
    }
    
    public void startGame() {
        frame.enableBoard();
    }
    
    public void endGame() {
        frame.disableBoard();
    }

    public BoardController getBoardController() {
        return boardController;
    }
    
    
}
