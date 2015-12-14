/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kucingvspanda.client.controllers;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Observable;
import java.util.Observer;
import kucingvspanda.client.PacketSender;
import kucingvspanda.client.models.*;
import kucingvspanda.client.views.GameFrame;
import kucingvspanda.packet.ServerPacket;

/**
 *
 * @author ASUS X202E
 */
public class BoardController {
    private final GameFrame frame;
    private BoardModel model;
    private ObjectInputStream in;
    private ObjectOutputStream os;
    
    public BoardController(GameFrame frame) {
        this.frame = frame;
    }
    
    public void setInputOutputStream(ObjectInputStream in, ObjectOutputStream os) {
        this.in = in;
        this.os = os;
    }

    public BoardModel getModel() {
        return model;
    }

    public void setModel(BoardModel model) {
        this.model = model;
    }
    
    /* REQUESTS */
    
    public void sendMove(int x, int y) {
        if (model.isEmpty(x,y)) {
            System.out.println("Send move");
            //kirim pesan x,y ke server
            System.out.println(x+","+y);PacketSender.sendAddPawnPacket(os, x, y);
            frame.disableBoard();
        }
        else frame.errorMessage("Please select an empty square!");
    }
    
    /* RECEIVED */
    
    public void buildModel(BoardModel model) {
        setModel(model);
        frame.initBoard(model.getTableModel());
    }

    public void showWinner(int x, int y, String wintype) {
        switch (wintype) {
            case "Horizontal":
                setWinHorizontal(x,y);
                break;
            case "Vertical":
                setWinVertical(x,y);
                break;
            case "Diagonal Left":
                setWinDiagonalLeft(x,y);
                break;
            case "Diagonal Right":
                setWinDiagonalRight(x,y);
                break;
        }
        
    }
    
    public void setWinHorizontal(int x, int y) {
        int winner = (int)model.getTableModel().getValueAt(y,x);
        for (int i=x;i<x+5;i++) {
            model.getTableModel().setValueAt(winner+6, y, i);
        }
    }
    
    public void setWinVertical(int x, int y) {
        int winner = (int)model.getTableModel().getValueAt(y,x);
        for (int i=y;i<y+5;i++) {
            model.getTableModel().setValueAt(winner+6, i, x);
        }
    }
    
    public void setWinDiagonalLeft(int x, int y) {
        int winner = (int)model.getTableModel().getValueAt(y,x);
        int i=x;
        int j=y;
        while (i<x+5) {
            model.getTableModel().setValueAt(winner+6, j, i);
            i++; j++;
        }
    }
    
    public void setWinDiagonalRight(int x, int y) {
        int winner = (int)model.getTableModel().getValueAt(y,x);
        int i=x;
        int j=y;
        while (i>x-5) {
            model.getTableModel().setValueAt(winner+6, j, i);
            i--; j++;
        }
    }
    
    public void clearBoard() {
        model.getTableModel().setRowCount(0);
        model.getTableModel().setRowCount(20);
    }

    /*@Override
    public void update(Observable o, Object arg) { ServerPacket packet = (ServerPacket) arg;

    }*/
    
}
