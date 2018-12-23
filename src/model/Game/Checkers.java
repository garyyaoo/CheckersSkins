package model.Game;

import model.Piece.Cell;
import model.Piece.CheckersPiece;
import model.Piece.Piece;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

public class Checkers extends Game {

    public Checkers() {
        super();

        // black pieces
        for (int row = 0 ; row < 3 ; row +=2) {
            for (int column = 0 ; column < 4 ; column ++ ) {
                Cell c = board.getAt(row, 2*column+1);
                Piece p = new CheckersPiece("BLACK", c, this);
                c.setValue(p);
            }
        }
        for (int column = 0 ; column < 4 ; column ++) {
            Cell c = board.getAt(1, 2*column);
            Piece p = new CheckersPiece("BLACK", c, this);
            c.setValue(p);
        }

        // white pieces
        for (int row = 7 ; row > 4 ; row -=2) {
            for (int column = 0 ; column < 4 ; column ++ ) {
                Cell c = board.getAt(row, 2*column);
                Piece p = new CheckersPiece("WHITE", c, this);
                c.setValue(p);
            }
        }
        for (int column = 0 ; column < 4 ; column ++) {
            Cell c = board.getAt(6, 2*column+1);
            Piece p = new CheckersPiece("WHITE", c, this);
            c.setValue(p);
        }
        redraw();
        getPiecesCount();

    }


//    public void print() {
//        String line = "---------------------------------\n";
//        String result = line ;
//        for (int row = 0; row < 8 ; row ++) {
//            result += "|";
//            for (int column =0 ; column < 8; column ++) {
//                Piece value = board.getAt(row, column).getValue();
//                if (value == null) {
//                    result += "   |";
//                } else if (value.getColor() == "BLACK") {
//                    result += " X |";
//                } else {result += " O |";}
//            }
//            result += "\n" + line;
//        }
//        System.out.println(result);
//    }


        @Override
        public boolean makeMove(Cell c) {
            moveover = false;
            boolean submoveisover = this.subMove(c);
            if (submoveisover) {
                moveover = true;
                return true;
            } else {
                enforcedMove();
                return true;
            }
        }

        @Override
        public void specials() {
            for (Cell c : board.getEndRows()) {
                if (!c.isEmpty()) {
                    ((CheckersPiece) c.getValue()).deservesPromotion();
                }
            }
        }

        private boolean subMove(Cell destination) {
            CheckersPiece piece = ((CheckersPiece) selected);
            if (destination.isEmpty()) {
                destination.setValue(piece);
                piece.getPosition().setValue(null);
                piece.setPosition( destination);
                this.redraw();
                return true;
            } else {
                Cell cellaftereat = piece.cellAfterEat(destination.getValue());
                cellaftereat.setValue(piece);
                if (destination.getValue().getColor().equals("WHITE")) {
                    numberofwhitepieces -=1;
                } else {
                    numberofblackpieces -=1;
                }
                System.out.println("Number of black pieces left: " + numberofblackpieces);
                System.out.println("Number of white pieces left: " + numberofwhitepieces);
                destination.setValue(null);
                piece.getPosition().setValue(null);
                piece.setPosition(cellaftereat);
                this.redraw();
                if (piece.canEatAny()) {
                    return false;
                } else {
                    return true;
                }
            }
        }

        public void enforcedMove() {
            moveover = false;
        }

    @Override
    public void save() {
        PrintWriter writer = null;
        try {
            writer = new PrintWriter("saved.txt", "UTF-8");
            writer.println("Checkers");
            for (int i = 0 ; i < 8 ; i++) {
                for (int j = 0 ; j < 8 ; j++) {
                    if (!board.getAt(i,j).isEmpty()) {
                        StringBuilder line = new StringBuilder();
                        Piece p = board.getAt(i,j).getValue();
                        line.append(p.getPieceClass());
                        line.append(" ");
                        line.append(p.getColor());
                        line.append(" ");
                        line.append(p.getPosition().getRow());
                        line.append(" ");
                        line.append(p.getPosition().getColumn());
                        line.append(" ");
                        line.append(((CheckersPiece) p).is_op());
                        writer.println(line.toString());

                    }
                }
            }
            writer.close();
            System.out.println("SAVED");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

    }

    @Override
    public boolean is_over() {
        return (numberofblackpieces == 0 || numberofwhitepieces == 0);
    }

    @Override
    public void print() {
        StringBuilder str = new StringBuilder();
        String line = "---------------------------------\n";
        str.append(line);
        for (int row = 0; row < 8 ; row ++) {
            str.append("|");
            for (int column =0 ; column < 8; column ++) {
                Piece value = board.getAt(row, column).getValue();
                if (value == null) {
                    str.append("   |");
                } else if (value.getColor() == "BLACK") {
                    str.append(" X |");
                } else {str.append(" O |");}
            }
            str.append("\n");
            str.append(line);
        }
        System.out.println(str);
    }
}
