package model.Game;

import model.Piece.Cell;
import model.Piece.Piece;

import java.util.ArrayList;
import java.util.List;

public class Board {
    private Cell[][] board;
    public int boardsize = 64;

    public Board() {
        board = new Cell[8][8];
        for (int row = 0; row < 8; row++) {
            for (int column = 0; column < 8 ; column ++) {
                Cell c = new Cell(row, column);
                board[row][column] = c;
            }
        }
    }

    // REQUIRES: row, column > 0


    public Cell getAt(int row, int column) {
        return board[row][column];
    }

    public List<Cell> getAllCellsWithPieces() {
        List<Cell> list = new ArrayList<>();
        for (int i = 0 ; i < 8 ; i++) {
            for (int j = 0 ; j < 8 ; j++) {
                if (!board[i][j].isEmpty()) {
                    list.add(board[i][j]);
                }
            }
        }
        return list;
    }


    public Piece pieceAt(int row, int column) {
        return board[row][column].getValue();
    }

    // EFFECTS: checks if the Cell's value at the given position is empty
    public boolean isEmptyAt(int row, int column) {
        return board[row][column].getValue() == null ;
    }

    public void clear() {
        for (int i = 0 ; i < 8 ; i++) {
            for (int j = 0 ; j < 8 ; j++) {
                board[i][j].setValue(null);
            }
        }
    }

    public void makeMove(Cell cell1, Cell cell2) {
        boolean move_is_over = false;
        if (cell2.isEmpty()) {
            cell2.setValue(cell1.getValue());
            cell1.setValue(null);
            cell2.getValue().setPosition(cell2);
            move_is_over = true;
        }

    }

    public List<Cell> getEndRows() {
        List<Cell> endrows = new ArrayList<>();
        for (int i = 0 ; i < 8; i ++) {
            endrows.add(board[0][i]);
            endrows.add(board[7][i]);
        }
        return endrows;
    }
}




//public class Board {
//    private Cell[][] board;
//    public int boardsize = 64;
//
//    public Board() {
//        board = new Cell[8][8];
//        for (int row = 0; row < 8; row++) {
//            for (int column = 0; column < 8 ; column ++) {
//                Cell c = new Cell(row, column);
//                board[row][column] = c;
//            }
//        }
//    }
//
//    // REQUIRES: row, column > 0
//
//
//    public Cell getAt(int row, int column) {
//        return board[row][column];
//    }
//
//    public Piece pieceAt(int row, int column) {
//        return board[row][column].getValue();
//    }
//
//    // EFFECTS: checks if the Cell's value at the given position is empty
//    public boolean isEmptyAt(int row, int column) {
//        return board[row][column].getValue() == null ;
//    }
//
//    public void makeMove(Cell cell1, Cell cell2) {
//        cell2.setValue(cell1.getValue());
//        cell1.setValue(null);
//    }
//}
