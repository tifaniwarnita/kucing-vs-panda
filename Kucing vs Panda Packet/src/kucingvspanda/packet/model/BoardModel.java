/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kucingvspanda.packet.model;

/**
 *
 * @author ASUS X202E
 */
public class BoardModel {
   private javax.swing.table.DefaultTableModel boardTableModel = new javax.swing.table.DefaultTableModel(20,20);
   
   public BoardModel(String[][] board, java.util.List<String> players) {
       for (int i=0;i<19;i++) {
           for (int j=0;j<19;j++) {
               if (board[i][j]!=null) {
                   boardTableModel.setValueAt(players.indexOf(board[i][j]), i, j);
               }
           }
       }
   }
   
   public BoardModel(int test) {
       /*for (int i=0;i<19;i++) {
            Object[] o = new Object[20];
           for (int j=0;j<19;j++) {
                o[j] = -1;
           }
           boardTableModel.addRow(o);
       }*/
        for (int i=0;i<20;i++) {
           for (int j=0;j<20;j++) {
                   boardTableModel.setValueAt(1, i, j);
           }
       }
   }
   
   public javax.swing.table.DefaultTableModel getTableModel() {
       return boardTableModel;
   }
   
   
}
