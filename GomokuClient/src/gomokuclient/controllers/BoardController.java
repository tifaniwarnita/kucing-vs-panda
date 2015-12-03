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
    private GameFrame frame;
    private BoardModel model;
    
    public BoardController(GameFrame frame) {
        this.frame = frame;
    }

    public BoardModel getModel() {
        return model;
    }

    public void setModel(BoardModel model) {
        this.model = model;
        frame.initBoard(model.getTableModel());
    }
    
    public void selectBoardCoordinate(int x, int y) {
        //if (model.getTableModel().getValueAt(x,y) == -1) kirim pesan ke server
        // else frame.errorMessage("Please select an empty square.");
    }
    
    public void updateBoardCoordinate(int x, int y, String name) {
        model.setBoardCoordinate(x,y,name);
    }
    
    public void showWinner(int x, int y, String wintype) {
        /*if (wintype.equals("Horizontal")) {
            
        } else if (wintype.equals("Vertical")) {
            
        } else {
            
        }
        frame.disableBoard();
        frame.showWinnerPopup();
        */
    }
}
