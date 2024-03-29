/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gomokuclient.controllers;

import gomokuclient.models.*;
import gomokuclient.views.GameFrame;

/**
 *
 * @author ASUS X202E
 */
public class BoardController {
    private final GameFrame frame;
    private BoardModel model;
    
    
    public BoardController(GameFrame frame) {
        this.frame = frame;
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
            //kirim pesan x,y ke server
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
        /*if (wintype.equals("Horizontal")) {
            
        } else if (wintype.equals("Vertical")) {
            
        } else {
            
        }
        frame.showWinner();
        endGame();
        */
    }
    
    public void boardFull() {
        //open dialog "GAME OVER. Board is full. Restart game in this room?"
    }
    
    public void clearBoard() {
        model.getTableModel().setRowCount(0);
    }
    
}
