/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kucingvspanda.client.controllers;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import kucingvspanda.client.views.*;
import kucingvspanda.client.models.*;
import java.util.List;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

/**
 *
 * @author ASUS X202E
 */
public class RoomController implements Observer {
    private RoomModel model;
    private final GameFrame frame;
    private final BoardController boardController;
    private ObjectInputStream in;
    private ObjectOutputStream os;
    
    public RoomController(GameFrame frame) {
        this.frame = frame;
        boardController = new BoardController(frame);
    }
    
    public void setInputOutputStream(ObjectInputStream in, ObjectOutputStream os) {
        this.in = in;
        this.os = os;
    }

    public RoomModel getRoomModel() {
        return model;
    }

    public void setRoomModel(RoomModel model) {
        this.model = model;
    }

    /* REQUESTS */
    public void exitGame() {
        //send message exit game
        frame.changeScreen("MainMenu");
    }
    
    public void requestStartGame() {
        //send message start game
        startGame();
    }
    
    /* RECEIVED */
    public void buildRoom() {
        frame.setRoomName(model.getName());
        frame.initPlayerTable(model.getPlayersTableModel());
        frame.initSpectatorList(model.getSpectatorList());
        frame.disableStartGameButton();
        if (model.isPlayer()) frame.disableBoard();
        if (model.getPlayerCount() < 3 ) {
            frame.setTurnLabel("Waiting for other players to join...");
        }
        else {
            if (model.isPlayer()) {
                frame.enableStartGameButton();
                frame.setTurnLabel("Click Start Game to begin new game!");
            } else frame.setTurnLabel("Waiting for game to start...");
        }
    }

    public BoardController getBoardController() {
        return boardController;
    }
    
    public void addPlayer(String playerName) {
        model.addPlayer(playerName);
        if (model.getPlayerCount()>=3) {
            if (model.isPlayer()) {
                frame.enableStartGameButton();
                frame.setTurnLabel("Click Start Game to begin new game!");
            } else frame.setTurnLabel("Waiting for game to start...");
        }
    }
    
    public void removePlayer(String playerName) {
        model.removePlayer(playerName);
        if (model.getPlayerCount() < 3 && model.getStatus().equals("Playing")) {
            endGame();
        }
    }
    
    public void startGame() {
        //model.setPlayerList(updatedPlayers);
        boardController.clearBoard();
        model.setStatus("Playing");
        frame.disableStartGameButton();
        frame.hideStartGameLabel();
        if (model.getPlayerList().get(0).equals(MainMenuModel.getCurrentPlayer())) {
            frame.enableBoard();
            frame.setTurnLabel("Your turn!");
        }
        else frame.setTurnLabel("Waiting for "+model.getPlayerList().get(0)+"...");
    }
    
    public void endGame() {
        model.setStatus("Waiting");
        frame.disableBoard();
        if (model.getPlayerCount() < 3 ) frame.setTurnLabel("Waiting for other players to join...");
        else {
            if (model.isPlayer()) frame.setTurnLabel("Click Start Game to begin new game!");
            else frame.setTurnLabel("Waiting for game to start...");
        }
    }
    
    public void receiveMove(int x, int y, String player, String nextPlayer) {
        boardController.getModel().setBoardCoordinate(x,y,player);
        if (!nextPlayer.equals(MainMenuModel.getCurrentPlayer())) frame.setTurnLabel("Waiting for "+nextPlayer+"...");
        else {
            frame.setTurnLabel("Your turn!");
            frame.enableBoard();
        }
    }
   
    public void removeSpectator(String name) {
        model.removeSpectator(name);
    }
    
    public void addSpectator(String name) {
        model.addSpectator(name);
    }

    @Override
    public void update(Observable o, Object arg) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
