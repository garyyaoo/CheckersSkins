package model.Piece;

import model.Game.Board;
import model.Game.Game;

import java.util.List;

public abstract class Piece {
    protected String color;
    protected Cell position;
    protected Board board;
    protected Game game;


    public String getColor() {
        return color;
    }

    public Cell getPosition() {
        return position;
    }

    public Board getBoard() {
        return board;
    }

    public void setPosition(Cell position) {
        this.position = position;
    }

    public boolean inBound(int row, int column) {
        return row > -1 && row < 8 && column > -1 && column <8;
    }

    public abstract List<Cell> possibleMoves();

    public abstract boolean isValidMove(Cell destination);

    public boolean equals(Piece other) {
        return color == other.color &&
                position.equals(other.getPosition()) &&
                board == other.getBoard();
    }

    public abstract String getFileName();

    public abstract String getPieceClass();


}
