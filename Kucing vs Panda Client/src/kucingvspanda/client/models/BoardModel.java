/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kucingvspanda.client.models;

/**
 *
 * @author ASUS X202E
 */
public class BoardModel {
   private javax.swing.table.DefaultTableModel boardTableModel = new javax.swing.table.DefaultTableModel(20,20);
   private java.util.List<String> players;
   
   public BoardModel() {
       
   }
   public BoardModel(java.util.List<String> players) {
       this.players = players;
   }
   public BoardModel(String[][] board, java.util.List<String> players) {
       this.players = players;
       for (int i=0;i<20;i++) {
           for (int j=0;j<20;j++) {
               if (board[i][j]!=null) {
                   boardTableModel.setValueAt(players.indexOf(board[i][j]), i, j);
               }
           }
       }
   }
   
   public javax.swing.table.DefaultTableModel getTableModel() {
       return boardTableModel;
   }
   
   public void setBoardCoordinate(int x, int y, String name) {
       System.out.println(players.indexOf(name));
       boardTableModel.setValueAt(players.indexOf(name), x, y);
   }
   
    public boolean isEmpty(int x, int y) {
        return (getTableModel().getValueAt(x,y) == null);
    }
}
