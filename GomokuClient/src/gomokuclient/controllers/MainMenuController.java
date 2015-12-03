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
    private final GameFrame frame;
    private final MainMenuModel model = new MainMenuModel();
    private final RoomController roomController;
    
    public MainMenuController(GameFrame frame) {
        this.frame = frame;
        roomController = new RoomController(frame);
        frame.initRoomTable(model.getTableModel());
    }
    
    public MainMenuModel getModel() {
        return model;
    }

    public RoomController getRoomController() {
        return roomController;
    }
    
    /* REQUESTS */
    
    public void enterGame(String nickname) {
        //send message to server
        model.setCurrentPlayer(nickname);
        //anggap sukses yha, sebenernya successLogin bukan dipanggil di sini sih. ini tes doang oke
        java.util.List<RoomInfo> rooms = new java.util.ArrayList<>();
        rooms.add(new RoomInfo("Tes",3,"Playing"));
        successLogin(rooms);
    }
   
    
    public void play(int roomNo) {
        // request RoomModel
        //anggap sukses yha, sebenernya successPlay ga dipanggil di sini sih
        
        successPlay(model.getRooms().get(roomNo));
        
    }
    
    public void watch(int roomNo) {
        // request RoomModel
        //anggap sukses yha, sebenernya successWatch ga dipanggil di sini sih
        successWatch(model.getRooms().get(roomNo));
    }
    
    public void createRoom(String roomName) {
        /*request add room to server */
        //ini tes doang:
        RoomInfo ri = new RoomInfo(roomName,0,"Waiting");
        addRoom(ri);
    }
    
    /* RECEIVED */
    
    public void addRoom(RoomInfo room) {
        model.addRoom(room);
        frame.createRoomTableButtons(model.getTableModel().getRowCount()-1);
    }
    
    public void errorRoom(String name) {
        frame.errorMessage("Room already exists. Please enter a different name.");
        frame.addRoomDialog();
    }

    
    public void errorLogin() {
        frame.errorMessage("This name is already in use. Please input a different name.");
    }
    
    public void successLogin(java.util.List<RoomInfo> roomInfoList) {
        frame.setCurrentPlayer(model.getCurrentPlayer());
        buildMainMenu(roomInfoList);
    }
    
    public void successPlay(String roomName) {
        //ceritanya sih nanti masukannya bukan cuma roomname tapi ada playerlist dan spectatorlistjuga
        java.util.List<String> players = new java.util.ArrayList<>(java.util.Arrays.asList("Nilta","Tifa"));
        java.util.List<String> spectators = new java.util.ArrayList<>(java.util.Arrays.asList("Fiqie","Jess"));
        roomController.setRoomModel(new RoomModel(new RoomInfo(roomName),players,spectators));
        roomController.getRoomModel().setRole("Player");
        roomController.buildRoom();
        roomController.getBoardController().buildModel(new BoardModel());
        frame.changeScreen("Room");
    }
    
    public void successWatch(String roomName) {
        //ceritanya sih nanti masukannya bukan cuma roomname tapi ada playerlist, spectatorlist, dan String[20][20] juga
        java.util.List<String> players = new java.util.ArrayList<>(java.util.Arrays.asList("Nilta","Tifa"));
        java.util.List<String> spectators = new java.util.ArrayList<>(java.util.Arrays.asList("Fiqie","Jess"));
        String[][] test = new String[20][20];
        roomController.setRoomModel(new RoomModel(new RoomInfo(roomName),players,spectators));
        roomController.getRoomModel().setRole("Spectator");
        roomController.buildRoom();
        roomController.getBoardController().buildModel(new BoardModel(test,players));
        frame.disableBoard();
        frame.disableStartGameButton();
        frame.changeScreen("Room");
    }
    
    public void errorPlay() {
        frame.errorMessage("Sorry, room can't be entered. Please choose a different room.");
    }
    
    public void incPlayerCount(String roomName) {
        model.incRoomPlayerCount(roomName);
        if (model.getRoomPlayerCount(roomName)>=15) {
            frame.disablePlayButton(model.getRoomNumber(roomName));
        }
    }
    
    public void decPlayerCount(String roomName) {
        model.decRoomPlayerCount(roomName);
        if (model.getRoomPlayerCount(roomName)<=0) {
            
        }
    }
    
    public void buildMainMenu(java.util.List<RoomInfo> roomInfoList) {
        model.initModel(roomInfoList);
        frame.updateRoomButtons();
        for (int i=0;i<roomInfoList.size();i++) {
            RoomInfo room = roomInfoList.get(i);
            if (room.getStatus().equals("Playing") || room.getPlayerCount()>15) {
                frame.disablePlayButton(i);
            }
        }
        frame.changeScreen("MainMenu");
    }
    
}
