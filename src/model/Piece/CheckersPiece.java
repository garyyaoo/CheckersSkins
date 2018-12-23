package model.Piece;


import model.Game.Game;

import java.util.ArrayList;
import java.util.List;

public class CheckersPiece extends Piece     {
    private boolean is_op;


    public CheckersPiece(String color, Cell position, Game game) {
        super.color = color;
        super.position = position;
        super.game = game;
        super.board = game.getBoard();
        is_op = false;
    }

    public CheckersPiece(String color, int row, int column, boolean is_op, Game game) {
        super.color = color;
        super.position = game.getBoard().getAt(row, column);
        super.game = game;
        super.board = game.getBoard();
        this.is_op = is_op;

    }

    public boolean is_op() {
        return is_op;
    }

    public void setIs_op(boolean bool) {
        is_op = bool;
    }

    public String getFileName() {
        String name = "B";
        if (this.color.equals("WHITE")) {
            name = "W";
        }
        if (this.is_op) {
            name += "K";
        } else {
            name += "P";
        }
        return name;
    }

    public boolean deservesPromotion() {
        if (is_op) {
            return true;
        }
        if ((position.getRow() == 7 && color.equals("BLACK")) ||
                (position.getRow() == 0 && color.equals("WHITE"))) {
            setIs_op(true);
            return true;
        }
        return false;
    }

    @Override
    public String getPieceClass() {
        return "CheckersPiece";
    }

    @Override
    public List<Cell> possibleMoves() {
        List<Cell> moves = new ArrayList<>();
        List<Cell> validmoves = new ArrayList<>();
        int i = 1;
        if (color.equals("WHITE")) {
            i = -1;
        }
        if (position.inBound(position.getRow() + i, position.getColumn())) {
            if (position.getColumn() +1 < 8) {
                moves.add(board.getAt(position.getRow() + i, position.getColumn() +1));
            }
            if (position.getColumn() - 1 > -1) {
                moves.add(board.getAt(position.getRow() + i, position.getColumn() -1));
            }
        }

        if (is_op) {
            if (position.inBound(position.getRow() - i, position.getColumn())) {
                if (position.getColumn() + 1 < 8) {
                    moves.add(board.getAt(position.getRow() - i, position.getColumn() + 1));
                }
                if (position.getColumn() - 1 > -1) {
                    moves.add(board.getAt(position.getRow() - i, position.getColumn() -1));
                }
            }
        }
        if (game.isMoveover()) {
            for (Cell move:moves) {
                if (this.canMoveHere(move)) {
                    validmoves.add(move);
                }
            }
        } else {
            for (Cell move:moves) {
                if ( (!move.isEmpty()) && this.eatable(move.getValue())) {
                    validmoves.add(move);
                }
            }
        }
        return validmoves;
    }

    private boolean canMoveHere(Cell c) {
        return c.isEmpty() || this.eatable(c.getValue());
    }

    public Cell cellAfterEat(Piece food) {
        int newrow = 2*food.getPosition().getRow() - position.getRow();
        int newcolumn = 2*food.getPosition().getColumn() - position.getColumn();
        return board.getAt(newrow, newcolumn);
    }


    public boolean eatable(Piece p) {

        // checks if they are different colors
        if (this.color.equals(p.getColor())) {
            return false;
        }

        // checks if they are corner adjacent
        if (!this.isCornerAdjacent(p.getPosition())) {
            return false;
        }

        // position of new cell
        int newrow = 2*p.getPosition().getRow() - position.getRow();
        int newcolumn = 2*p.getPosition().getColumn() - position.getColumn();

        // checks if new cell is in bound
        if (!position.inBound(newrow, newcolumn)) {
            return false;
        }

        // checks if new cell is empty
        if (!board.getAt(newrow, newcolumn).isEmpty()) {
            return false;
        }
        return true;
    }


    public boolean canEatAny() {
        List<Cell> moves = possibleMoves();
        boolean yes = false;
        for (Cell c:possibleMoves()) {
            if (!c.isEmpty()) {
                if (eatable(c.getValue())) {
                    yes = true;
                }
            }
        }
        return yes;
    }

    public boolean isCornerAdjacent(Cell o) {
        return (
                (position.getRow() - o.getRow() == 1 ||
                        position.getRow() - o.getRow() == -1) &&
                        (position.getColumn() - o.getColumn() == 1 ||
                                position.getColumn() - o.getColumn() == -1)
        );
    }


//    public List<Cell> getAdjacentSquares() {
//        List<Cell> cells = new ArrayList<>();
//        for (int i = -1; i <2 ; i++) {
//            if (position.inBound(position.getRow() + 1, position.getColumn() + i)) {
//                cells.add(board.getAt(position.getRow() + 1, position.getColumn() + i));
//            }
//            if (position.inBound(position.getRow() - 1, position.getColumn() + i)) {
//                cells.add(board.getAt(position.getRow() -1, position.getColumn() + i));
//            }
//        }
//        if (position.inBound(position.getRow(), position.getColumn() + 1)) {
//            cells.add(board.getAt(position.getRow(), position.getColumn() + 1));
//        }
//        if (position.inBound(position.getRow(), position.getColumn() - 1 )) {
//            cells.add(board.getAt(position.getRow(), position.getColumn() - 1));
//        }
//        return cells;
//    }

    public boolean isValidMove(Cell destination) {
//            if (!destination.inBound(destination.getRow(), destination.getColumn())) {
//                return false;
//            }
//            if (!isCornerAdjacent(destination)) {
//                return false;
//            }
//            if (destination.isEmpty()) {
//                return true;
//            }
//            if (eatable(destination.getValue())) {
//                return true;
//            }
//            return false;
        return possibleMoves().contains(destination);

    }

//    public boolean makeMove(Cell c) {
//        boolean moveover = false;
//        boolean submoveisover = this.subMove(c);
//        game.redraw();
//        if (submoveisover) {
//            moveover = true;
//            return true;
//        } else {
//            // enforcedMove();
//            moveover = true;
//            return true;
//        }
//    }
//
//    public boolean subMove(Cell destination) {
//        if (destination.isEmpty()) {
//            destination.setValue(this);
//            position.setValue(null);
//            this.position = destination;
//            return true;
//        } else {
//            Cell cellaftereat = this.cellAfterEat(destination.getValue());
//            cellaftereat.setValue(this);
//            destination.setValue(null);
//            position.setValue(null);
//            this.position = cellaftereat;
//
//            if (canEatAny()) {
//                return false;
//            } else {
//                return true;
//            }
//
//        }
//    }

}


//public class CheckersPiece extends Piece     {
//    private boolean is_op;
//
//    public CheckersPiece(String color) {
//        super.color = color;
//        is_op = false;
//    }
//
//    public CheckersPiece(String color, Cell position, Board board) {
//        super.color = color;
//        super.position = position;
//        super.board = board;
//        is_op = false;
//    }
//
//    public boolean is_op() {
//        return is_op;
//    }
//
//    public void setIs_op(boolean bool) {
//        is_op = bool;
//    }
//
//    @Override
//    public List possibleMoves() {
//        List<Cell> moves = new ArrayList<>();
//        int i = 1;
//        if (color.equals("WHITE")) {
//            i = -1;
//        }
//        if (this.inBound(position.getRow() + i, position.getColumn())) {
//            if (position.getColumn() +1 < 8) {
//                moves.add(board.getAt(position.getRow() + i, position.getColumn() +1));
//            }
//            if (position.getColumn() - 1 > -1) {
//                moves.add(board.getAt(position.getRow() + i, position.getColumn() -1));
//            }
//        }
//
//        if (is_op) {
//            if (this.inBound(position.getRow() - 1, position.getColumn())) {
//                if (position.getColumn() + 1 < 8) {
//                    moves.add(board.getAt(position.getRow() - i, position.getColumn() + 1));
//                }
//                if (position.getColumn() - 1 > -1) {
//                    moves.add(board.getAt(position.getRow() - i, position.getColumn() -1));
//                }
//            }
//        }
//        return moves;
//    }
//
//    public boolean eatable(Cell c) {
//        if (this.color.equals( c.getValue().getColor())) {
//            return false;
//        }
//
//        int i = 1;
//        if (color.equals("WHITE")) {i = -1;}
//
//        if (c.getColumn() > position.getColumn() && c.getColumn()+1 <8) {
//            if (Game.board.getAt(
//                    c.getRow() + i, c.getColumn()+1).isEmpty()) {
//                return true;
//            }
//        }
//
//        if (c.getColumn() < position.getColumn() && c.getColumn() - 1 > -1) {
//            if (Game.board.getAt(
//                    c.getRow() + i, c.getColumn() -1).isEmpty()) {
//                return true;
//            }
//        }
//        return false;
//
//    }
//
//    public List getAdjacentSquares() {
//        List<Cell> cells = new ArrayList<>();
//        for (int i = -1; i <2 ; i++) {
//            if (this.inBound(position.getRow() + 1, position.getColumn() + i)) {
//                cells.add(board.getAt(position.getRow() + 1, position.getColumn() + i));
//            }
//            if (this.inBound(position.getRow() - 1, position.getColumn() + i)) {
//                cells.add(board.getAt(position.getRow() -1, position.getColumn() + i));
//            }
//        }
//        if (this.inBound(position.getRow(), position.getColumn() + 1)) {
//            cells.add(board.getAt(position.getRow(), position.getColumn() + 1));
//        }
//        if (this.inBound(position.getRow(), position.getColumn() - 1 )) {
//            cells.add(board.getAt(position.getRow(), position.getColumn() - 1));
//        }
//        return cells;
//    }
//
//    @Override
//    public boolean makeMove() {
//        return false;
//    }
//}
