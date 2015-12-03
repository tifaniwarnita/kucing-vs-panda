/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gomokuclient.controllers;
import gomokuclient.views.*;
import gomokuclient.models.*;
import java.util.List;
import java.util.ArrayList;

/**
 *
 * @author ASUS X202E
 */
public class RoomController {
    private RoomModel model;
    private final GameFrame frame;
    private final BoardController boardController;
    
    public RoomController(GameFrame frame) {
        this.frame = frame;
        boardController = new BoardController(frame);
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
    }
    
    /* RECEIVED */
    public void buildRoom() {
        frame.setRoomName(model.getName());
        frame.initPlayerTable(model.getPlayersTableModel());
        frame.initSpectatorList(model.getSpectatorList());
        if (model.isPlayer()) frame.disableBoard();
        if (model.getPlayerCount() < 3 ) frame.setTurnLabel("Waiting for other players to join...");
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
    
    public void startGame(List<String> updatedPlayers) {
        model.setPlayerList(updatedPlayers);
        boardController.clearBoard();
        model.setStatus("Playing");
        frame.enableBoard();
        frame.disableStartGameButton();
        frame.setTurnLabel("Waiting for "+updatedPlayers.get(0)+"...");
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
        frame.removeSpectatorList(name);
    }
    
    public void addSpectator(String name) {
        model.addSpectator(name);
        frame.addSpectatorList(name);
    }
    
}
