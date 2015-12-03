/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kucingvspanda.server;

import java.io.ObjectOutputStream;
import java.util.ArrayList;
import kucingvspanda.packet.ClientPacket;
import kucingvspanda.packet.ServerPacket;
import kucingvspanda.packet.model.RoomInfo;

/**
 *
 * @author Tifani
 */
public class PacketSender {
    
    //----------------------------- REPLY: LOGINPACKET
    // To: Packet sender
    public static void sendLoginSuccessPacket(ObjectOutputStream outputStream, ArrayList<RoomInfo> roomInfoList) {
        ServerPacket loginSuccessPacket = new ServerPacket();
        loginSuccessPacket.buildLoginSuccessPacket(roomInfoList);
        try {
            outputStream.writeObject(loginSuccessPacket);
        } catch (Exception e) {
            System.out.println("Sending login-success packet failed: " + e.toString());
        }
    }
    
    // To: Packet sender
    public static void sendLoginFailedPacket(ObjectOutputStream outputStream) {
        ServerPacket loginFailedPacket = new ServerPacket();
        loginFailedPacket.buildLoginFailedPacket();
        try {
            outputStream.writeObject(loginFailedPacket);
        } catch (Exception e) {
            System.out.println("Sending login-failed packet failed: " + e.toString());
        }
    }
    
    
    //----------------------------- REPLY: ADDROOMPACKET
    // To: All client with roomname = null
    public static void sendAddRoomSuccessPacket(ObjectOutputStream outputStream, RoomInfo newRoomInfo){
        ServerPacket addRoomSuccessPacket = new ServerPacket();
        addRoomSuccessPacket.buildAddRoomSuccessPacket(newRoomInfo);
        try {
            outputStream.writeObject(addRoomSuccessPacket);
        } catch (Exception e) {
            System.out.println("Sending add-room-success packet failed: " + e.toString());
        }
    }
    
    // To: Packet sender
    public static void sendAddRoomFailedPacket(ObjectOutputStream outputStream) {
        ServerPacket addRoomFailedPacket = new ServerPacket();
        addRoomFailedPacket.buildAddRoomFailedPacket();
        try {
            outputStream.writeObject(addRoomFailedPacket);
        } catch (Exception e) {
            System.out.println("Sending add-room-failed packet failed: " + e.toString());
        }
    }
    
    
    //----------------------------- REPLY: PLAYPACKET
    // To: Packet sender
    public static void sendPlaySuccessPacket(ObjectOutputStream outputStream, String roomName, 
            ArrayList<String> players, ArrayList<String> spectators) {
        ServerPacket playSuccessPacket = new ServerPacket();
        playSuccessPacket.buildPlaySuccessPacket(roomName, players, spectators);
        try {
            outputStream.writeObject(playSuccessPacket);
        } catch (Exception e) {
            System.out.println("Sending play-success packet failed: " + e.toString());
        }
    }
    
    // To: Players in room
    public static void sendNewPlayerPacket(ObjectOutputStream outputStream, String playerName) {
        ServerPacket newPlayerPacket = new ServerPacket();
        newPlayerPacket.buildNewPlayerPacket(playerName);
        try {
            outputStream.writeObject(newPlayerPacket);
        } catch (Exception e) {
            System.out.println("Sending new-player packet failed: " + e.toString());
        }
    }
    
    // To: All client (except players in room)
    public static void sendAddPlayerCountPacket(ObjectOutputStream outputStream, String roomName) {
        ServerPacket updatePlayerCountPacket = new ServerPacket();
        updatePlayerCountPacket.buildAddPlayerCountPacket(roomName);
        try {
            outputStream.writeObject(updatePlayerCountPacket);
        } catch (Exception e) {
            System.out.println("Sending update-player-count packet failed: " + e.toString());
        }
    }
    
    // To: Packet sender
    public static void sendPlayFailedPacket(ObjectOutputStream outputStream) {
        ServerPacket playFailedPacket = new ServerPacket();
        playFailedPacket.buildPlayFailedPacket();
        try {
            outputStream.writeObject(playFailedPacket);
        } catch (Exception e) {
            System.out.println("Sending play-failed packet failed: " + e.toString());
        }
    }
    
    
    //----------------------------- REPLY: SPECTATOR PACKET
    // To: Packet sender
    public static void sendSpectatorSuccessPacket(ObjectOutputStream outputStream, String roomName, 
            ArrayList<String> players, ArrayList<String> spectators, String[][] board) {
        ServerPacket spectatorSuccessPacket = new ServerPacket();
        spectatorSuccessPacket.buildSpectatorSuccessPacket(roomName, players, spectators, board);
        try {
            outputStream.writeObject(spectatorSuccessPacket);
        } catch (Exception e) {
            System.out.println("Sending spectator-success packet failed: " + e.toString());
        }
    }
    
    // To: Players in room
    public static void sendNewSpectatatorPacket(ObjectOutputStream outputStream, String player) {
        ServerPacket newSpectatorPacket = new ServerPacket();
        newSpectatorPacket.buildNewSpectatorPacket(player);
        try {
            outputStream.writeObject(newSpectatorPacket);
        } catch (Exception e) {
            System.out.println("Sending new-spectator packet failed: " + e.toString());
        }
    }
    
    //----------------------------- REPLY: START GAME PACKET
    // To: Packet sender + all connected player with roomname = null
    public static void sendStartGameSuccessPacket(ObjectOutputStream outputStream, String roomName) {
        ServerPacket startGameSuccessPacket = new ServerPacket();
        startGameSuccessPacket.buildStartGameSuccessPacket(roomName);
        try {
            outputStream.writeObject(startGameSuccessPacket);
        } catch (Exception e) {
            System.out.println("Sending start-game-success packet failed: " + e.toString());
        }
    }
    
    //----------------------------- REPLY: ADD PAWN PACKET
    // To: All in room
    public static void sendAddPawnPacketSuccess(ObjectOutputStream outputStream, String player, int x, int y, String nextPlayer) {
        ServerPacket addPawnSuccessPacket = new ServerPacket();
        addPawnSuccessPacket.buildAddPawnSuccessPacket(player, x, y, nextPlayer);
        try {
            outputStream.writeObject(addPawnSuccessPacket);
        } catch (Exception e) {
            System.out.println("Sending add-pawn-success packet failed: " + e.toString());
        }
    }
    
    //----------------------------- REPLY: LEAVE GAME PACKET
    // To: Packet sender
    public static void sendLeaveGameSuccessPacket(ObjectOutputStream outputStream, ArrayList<RoomInfo> roomInfoList) {
        ServerPacket leaveGameSuccessPacket = new ServerPacket();
        leaveGameSuccessPacket.buildLeaveGameSuccessPacket(roomInfoList);
        try {
            outputStream.writeObject(leaveGameSuccessPacket);
        } catch (Exception e) {
            System.out.println("Sending leave-game-success packet failed: " + e.toString());
        }
    }
    
    // To: All in room
    public static void sendLeavePlayerPacket(ObjectOutputStream outputStream, String player) {
        ServerPacket leavePlayerPacket = new ServerPacket();
        leavePlayerPacket.buildLeavePlayerPacket(player);
        try {
            outputStream.writeObject(leavePlayerPacket);
        } catch (Exception e) {
            System.out.println("Sending leave-player packet failed: " + e.toString());
        }
    }
    
    // To: All client with roomname = null
    public static void sendDecPlayerCountPacket(ObjectOutputStream outputStream, String roomName) {
        ServerPacket leavePlayerCountPacket = new ServerPacket();
        leavePlayerCountPacket.buildDecPlayerCountPacket(roomName);
        try {
            outputStream.writeObject(leavePlayerCountPacket);
        } catch (Exception e) {
            System.out.println("Sending dec-player-count packet failed: " + e.toString());
        }
    }
    
    
    //----------------------------- REPLY: WIN PACKET
    // To: All in room
    public static void sendWinPacket(ObjectOutputStream outputStream, String winType, int x, int y) {
        ServerPacket winPacket = new ServerPacket();
        winPacket.buildWinPacket(winType, x, y);
        try {
            outputStream.writeObject(winPacket);
        } catch (Exception e) {
            System.out.println("Sending win-packet packet failed: " + e.toString());
        }
    }
    
    // To: All in room
    public static void sendBoardFullPacket(ObjectOutputStream outputStream) {
        ServerPacket boardFullPacket = new ServerPacket();
        boardFullPacket.buildBoardFullPacket();
        try {
            outputStream.writeObject(boardFullPacket);
        } catch (Exception e) {
            System.out.println("Sending board-full packet failed: " + e.toString());
        }
    }
    
    //----------------------------- REPLY: CHAT PACKET
    // To: All in room
    public static void sendChatPacket(ObjectOutputStream outputStream, String message) {
        ServerPacket chatPacket = new ServerPacket();
        chatPacket.buildChatPacket(message);
        try {
            outputStream.writeObject(chatPacket);
        } catch (Exception e) {
            System.out.println("Sending chat packet failed: " + e.toString());
        }
    }
    
    //----------------------------- REPLY: HIGHSCORE PACKET
    // To: Packet sender
    public static void sendHighscorePacket(ObjectOutputStream outputStream, ArrayList< ArrayList<String> > highscore) {
        ServerPacket highscorePacket = new ServerPacket();
        highscorePacket.buildHighScorePacket(highscore);
        try {
            outputStream.writeObject(highscorePacket);
        } catch (Exception e) {
            System.out.println("Sending highscore packet failed: " + e.toString());
        }
    }
    
   
}
