/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kucingvspanda.client.controllers;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;
import kucingvspanda.client.PacketSender;
import kucingvspanda.client.views.*;
import kucingvspanda.client.models.*;
import kucingvspanda.packet.Identifier;
import kucingvspanda.packet.ServerPacket;
import kucingvspanda.packet.models.RoomInfo;

/**
 *
 * @author ASUS X202E
 */
public class MainMenuController implements Observer {
    private final GameFrame frame;
    private final MainMenuModel model = new MainMenuModel();
    private final RoomController roomController;
    private ObjectInputStream in;
    private ObjectOutputStream os;
    
    public MainMenuController(GameFrame frame) {
        this.frame = frame;
        roomController = new RoomController(frame);
        frame.initRoomTable(model.getTableModel());
    }
    
    public void setInputOutputStream(ObjectInputStream in, ObjectOutputStream os) {
        this.in = in;
        this.os = os;
    }
    
    public MainMenuModel getModel() {
        return model;
    }

    public RoomController getRoomController() {
        return roomController;
    }
    
    /* REQUESTS */
    
    public void enterGame(String nickname) {
        // Send LoginPacket to server
        System.out.println("Sending login packet");
        PacketSender.sendLoginPacket(os, nickname);
        model.setCurrentPlayer(nickname);
    }
   
    
    public void play(int roomNo) {
        // Sending PlayPacket to server
        System.out.println("Sending play packet");
        String roomName = model.getRooms().get(roomNo);
        PacketSender.sendPlayPacket(os, roomName);
    }
    
    public void watch(int roomNo) {
        // Sending SpectatorPacket to server
        System.out.println("Sending spectator packet");
        String roomName = model.getRooms().get(roomNo);
        PacketSender.sendSpectatorPacket(os, roomName);
    }
    
    public void createRoom(String roomName) {
        // Sending AddRoomPacket to server
        System.out.println("Sending add room2 packet");
        PacketSender.sendAddRoomPacket(os, roomName);
    }
    
    /* RECEIVED */
    
    public void addRoom(RoomInfo room) {
        model.addRoom(room);
        frame.createRoomTableButtons(model.getTableModel().getRowCount()-1);
    }
    
    public void errorRoom() {
        frame.errorMessage("Room already exists. Please enter a different name.");
        frame.addRoomDialog();
    }

    
    public void errorLogin() {
        frame.errorMessage("This name is already in use. Please input a different name.");
    }
    
    public void successLogin(java.util.List<RoomInfo> roomInfoList) {
        frame.setCurrentPlayer(MainMenuModel.getCurrentPlayer());
        buildMainMenu(roomInfoList);
    }
    
    public void successPlay(String roomName, ArrayList<String> players, ArrayList<String> spectators) {
        //ceritanya sih nanti masukannya bukan cuma roomname tapi ada playerlist dan spectatorlistjuga
        String[][] empty = new String[20][20];
        roomController.setRoomModel(new RoomModel(new RoomInfo(roomName), players, spectators));
        roomController.getRoomModel().setRole("Player");
        roomController.buildRoom();
        roomController.getBoardController().buildModel(new BoardModel(empty,players));
        frame.changeScreen("Room");
    }
    
    public void successWatch(String roomName, ArrayList<String> players, ArrayList<String> spectators, String[][] board) {
        //ceritanya sih nanti masukannya bukan cuma roomname tapi ada playerlist, spectatorlist, dan String[20][20] juga
        roomController.setRoomModel(new RoomModel(new RoomInfo(roomName), players, spectators));
        roomController.getRoomModel().setRole("Spectator");
        roomController.buildRoom();
        roomController.getBoardController().buildModel(new BoardModel(board, players));
        frame.disableBoard();
        frame.disableStartGameButton();
        frame.hideStartGameLabel();
        frame.changeScreen("Room");
    }
    
    public void errorPlay() {
        frame.errorMessage("Sorry, room can't be entered. Please choose a different room.");
    }
    
    public void incPlayerCount(String roomName) {
        model.incRoomPlayerCount(roomName);
        if (model.getRoomPlayerCount(roomName)>=6) {
            frame.disablePlayButton(model.getRoomNumber(roomName));
        }
    }
    
    public void decPlayerCount(String roomName) {
        model.decRoomPlayerCount(roomName);
        if (model.getRoomPlayerCount(roomName)<3) {
            model.updateRoomStatus(roomName,"Waiting");
            frame.enablePlayButton(model.getRoomNumber(roomName));
        }
    }
    
    public void buildMainMenu(java.util.List<RoomInfo> roomInfoList) {
        model.initModel(roomInfoList);
        frame.updateRoomButtons();
        for (int i=0;i<roomInfoList.size();i++) {
            RoomInfo room = roomInfoList.get(i);
            if (room.getStatus().equals("Playing") || room.getPlayerCount()>=6) {
                frame.disablePlayButton(i);
            }
        }
        frame.changeScreen("MainMenu");
    }

    @Override
    public void update(Observable o, Object arg) {
        ServerPacket packet = (ServerPacket) arg;
        int identifier = (int) packet.getIdentifier();
        String roomName;
        ArrayList<String> players;
        ArrayList<String> spectators;
        String[][] board;
        
        switch(identifier){
            case Identifier.LOGIN_SUCCESS : //list<roominfo>
                ArrayList<RoomInfo> rooms = packet.getRoomList();
                successLogin(rooms);
                break;
            case Identifier.LOGIN_FAILED: //message
                MainMenuModel.setCurrentPlayer("");
                errorLogin();
                break;
            case Identifier.ADD_ROOM_SUCCESS: //roominfo
                RoomInfo room = packet.getNewRoomInfo();
                addRoom(room);
                break;
            case Identifier.ADD_ROOM_FAILED: //message
                errorRoom();
                break;
            case Identifier.PLAY_SUCCESS: //roomname + players + spectators
                roomName = packet.getActiveRoomName();
                players = packet.getPlayerList();
                spectators = packet.getSpectatorList();
                successPlay(roomName, players, spectators);
                System.out.println("play success");
                System.out.println("player 1:" + players.get(0));
                break;
            case Identifier.NEW_PLAYER: //playername
                // Implemented in RoomModel
                break;
            case Identifier.ADD_PLAYER_COUNT: //roomname
                roomName = packet.getUpdatedRoomName();
                incPlayerCount(roomName);
                break;
            case Identifier.PLAY_FAILED: //message
                errorPlay();
                break;
            case Identifier.SPECTATOR_SUCCESS: //room + players + spectators + board (matrix string)
                roomName = packet.getActiveRoomName();
                players = packet.getPlayerList();
                spectators = packet.getSpectatorList();
                board = packet.getBoard();
                successWatch(roomName, players, spectators, board);
                break;
            case Identifier.NEW_SPECTATOR: //playername
                // Implemented in RoomModel
                break;
            case Identifier.START_GAME_SUCCESS: //roomname
                System.out.println("START GAME SUCCESS");
                roomName = packet.getUpdatedRoomName();
                model.updateRoomStatus(roomName, "Playing");
                frame.disablePlayButton(model.getRoomNumber(roomName));
                break;
            case Identifier.PAWN_PLACED: //roomname + player + x + y
                // Implemented in RoomController
                break;
            case Identifier.LEAVE_GAME_SUCCESS: //roomname + player
                // MASIH BINGUNG PERLU GA
                break;
            case Identifier.PLAYER_LEAVE: //playername
                // Implemented in RoomModel
                break;
            case Identifier.DEC_PLAYER_COUNT: //roomname 
                roomName = packet.getUpdatedRoomName();
                decPlayerCount(roomName);
                break;
            case Identifier.WIN: //roomname + winner
                roomName = packet.getUpdatedRoomName();
                model.updateRoomStatus(roomName, "Waiting");
                break;
            case Identifier.BOARD_FULL:
                roomName = packet.getUpdatedRoomName();
                model.updateRoomStatus(roomName, "Waiting");
                break;
            case Identifier.HIGHSCORE:
                break;
        }
    }
}