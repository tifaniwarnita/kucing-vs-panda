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
import kucingvspanda.client.PacketSender;
import kucingvspanda.packet.Identifier;
import kucingvspanda.packet.ServerPacket;
import kucingvspanda.packet.models.RoomInfo;

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
        //Send message exit game
        System.out.println("Sending leave packet");
        PacketSender.sendLeaveGamePacket(os);
        frame.changeScreen("MainMenu");
    }
    
    public void requestStartGame() {
        //send message start game
        System.out.println("Sending leave packet");
        PacketSender.sendStartGamePacket(os);
        //startGame();
    }
    
    public void sendChat(String message) {
        PacketSender.sendChatPacket(os,message);
    }
    
    /* RECEIVED */
    public void buildRoom() {
        frame.setRoomName(model.getName());
        frame.initPlayerTable(model.getPlayersTableModel());
        frame.initSpectatorList(model.getSpectatorList());
        frame.disableStartGameButton();
        frame.clearChat();
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
            frame.endGameDialog("Uh oh", "There must a minimum of 3 people in the game :( Would you like to play a new game in this room?");
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
            if (model.isPlayer()) {
                frame.setTurnLabel("Click Start Game to begin new game!");
                frame.enableStartGameButton();
            }
            else frame.setTurnLabel("Waiting for game to start...");
        }
    }
    
    public void receiveMove(String player, int x, int y, String nextPlayer) {
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
    
    public void win(String winner) {
        frame.setTurnLabel(winner+" has won the game!");
        if (MainMenuModel.getCurrentPlayer().equals(winner)) {
            frame.endGameDialog("YOU WIN!","Congratulations, you have won the game! Would you like to play in this room again?");
        } else {
            frame.endGameDialog("GAME OVER",winner+" has won the game! Would you like to play in this room again?");
        }
        endGame();
    }
    
    public void boardFull() {
        frame.endGameDialog("Game Over","Board is full. Want to play in this room again?");
        endGame();
    }

    public void receiveChat(String chat) {
        frame.appendChat(chat);
    }
    
    @Override
    public void update(Observable o, Object arg) {
        ServerPacket packet = (ServerPacket) arg;
        int identifier = (int) packet.getIdentifier();
        String player, roomName;
        int x, y;
        
        switch(identifier){
            case Identifier.LOGIN_SUCCESS : //list<roominfo>
                // Implemented in MainMenuController
                break;
            case Identifier.LOGIN_FAILED: //message
                // Implemented in MainMenuController
                break;
            case Identifier.ADD_ROOM_SUCCESS: //roominfo
                // Implemented in MainMenuController
                break;
            case Identifier.ADD_ROOM_FAILED: //message
                // Implemented in MainMenuController
                break;
            case Identifier.PLAY_SUCCESS: //roomname + players + spectators
                // Implemented in MainMenuController
                break;
            case Identifier.NEW_PLAYER: //playername
                player = packet.getNewPlayerName();
                addPlayer(player);
                break;
            case Identifier.ADD_PLAYER_COUNT: //roomname
                // Implemented in MainMenuController
                break;
            case Identifier.PLAY_FAILED: //message
                // Implemented in MainMenuController
                break;
            case Identifier.SPECTATOR_SUCCESS: //room + players + spectators + board (matrix string)
                // Implemented in MainMenuController
                break;
            case Identifier.NEW_SPECTATOR: //playername
                player = packet.getNewSpectatorName();
                addSpectator(player);
                break;
            case Identifier.START_GAME_SUCCESS: //roomname
                if (model.getName().equals(packet.getUpdatedRoomName()))
                    startGame();
                break;
            case Identifier.PAWN_PLACED: //player + x + y + nextplayer
                player = packet.getPlayer();
                x = packet.getX();
                y = packet.getY();
                String nextPlayer = packet.getNextPlayer();
                System.out.println(player+" "+nextPlayer);
                receiveMove(player, x, y, nextPlayer);
                break;
            case Identifier.LEAVE_GAME_SUCCESS: //roomname + player
                break;
            case Identifier.PLAYER_LEAVE: //playername
                String name = packet.getLogoutPlayer();
                if (model.getPlayerList().contains(name)) removePlayer(name);
                else removeSpectator(name);
                break;
            case Identifier.DEC_PLAYER_COUNT: //roomname 
                // Implemented in RoomModel
                break;
            case Identifier.WIN: //wintype + x + y
                String winType = packet.getWinInfo().getWintype();
                x = packet.getWinInfo().getX();
                y = packet.getWinInfo().getY();
                boardController.showWinner(x, y, winType);
                win(packet.getWinInfo().getWinner());
                break;
            case Identifier.BOARD_FULL:
                boardFull();
                break;
            case Identifier.HIGHSCORE:
                break;
            case Identifier.CHAT_SUCCESS:
                receiveChat(packet.getChatMessage());
                
        }
    }
    
}