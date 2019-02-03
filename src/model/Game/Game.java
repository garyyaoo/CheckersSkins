package model.Game;

import model.Piece.Cell;
import model.Piece.CheckersPiece;
import model.Piece.Piece;
import ui.Renderer2;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public abstract class Game {
    protected Board board;
    private List<Cell> possiblemoves;
    private boolean p1turn;
    private Renderer2 renderer2;
    private boolean isstrictgame;

    protected Piece selected;

    protected boolean moveover;

    public int numberofblackpieces;
    public int numberofwhitepieces;

    public Game() {
        moveover = true;
        isstrictgame = false;
        board = new Board();
        renderer2 = new Renderer2(this);
        p1turn = true;
    }


    public Board getBoard() {
        return board;
    }

    public abstract void print();

    public void toggleIsStrictGame() {
        isstrictgame = !isstrictgame;
        if (isstrictgame) {
            System.out.println("Now Strict Game");
            p1turn = !p1turn;
        } else {
            System.out.println("Open Un-Strict Game");
        }
    }

    public void getPiecesCount() {
        int whitepieces = 0;
        int blackpieces = 0;
        for (Cell c : board.getAllCellsWithPieces()) {
            if (c.getValue().getColor().equals("WHITE")) {
                whitepieces++;
            }
            if (c.getValue().getColor().equals("BLACK")) {
                blackpieces++;
            }
        }
        this.numberofwhitepieces = whitepieces;
        this.numberofblackpieces = blackpieces;
    }

    public void redraw() {
        renderer2.redraw();
    }

    public boolean isStrictGame() {
        return isstrictgame;
    }

    public void nextturn() {
        if (isstrictgame) {
            p1turn = !p1turn;
        }
    }

    public boolean isp1turn() {
        return p1turn;
    }

    public Piece getSelected() {
        return selected;
    }

    public boolean isMoveover() {
        return moveover;
    }


    public void select(Cell c) {
        this.selected = c.getValue();
        if (this.selected == null ) {
            deSelect();
            System.out.println("blank square selected");
        }
        possiblemoves = selected.possibleMoves();
    }

    public void deSelect() {
        this.selected = null;
        System.out.println("unselected!");
    }

    public List<Cell> getHighlights() {
        List<Cell> highlighted = new ArrayList<>();
        if (selected == null) {
            return highlighted ;
        } else {
            highlighted.add(selected.getPosition());
            highlighted.addAll(selected.possibleMoves());
            return highlighted;
        }
    }

    public boolean isValidMove(Cell c) {
        return possiblemoves.contains(c);
    }

    public abstract void save();

    public abstract void specials();

    public abstract boolean is_over();

    public void load() {
        try {


            List<String> lines = Files.readAllLines(Paths.get("saved.txt"));
            board.clear();
            for (String line: lines) {
                ArrayList<String> partsOfLine = splitOnSpace(line);
                if (partsOfLine.get(0).equals("CheckersPiece")) {
                    String color = partsOfLine.get(1);
                    int row = Integer.parseInt(partsOfLine.get(2));
                    int column = Integer.parseInt(partsOfLine.get(3));
                    boolean is_op = partsOfLine.get(4).equals("true");
                    CheckersPiece piece = new CheckersPiece(color, row, column, is_op, this);
                    board.getAt(row, column).setValue(piece);
                    this.getPiecesCount();

                }
            }
            redraw();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static ArrayList<String> splitOnSpace(String line) {
        String[] splits = line.split(" ");
        return new ArrayList<>(Arrays.asList(splits));
    }

    public abstract boolean makeMove(Cell c);

//
//    public boolean enforcedMove() {
//        boolean enforcedmoveisover = selected.enforcedMove();
//        if (enforcedmoveisover) {
//            return true;
//        } else {
//            return enforcedMove();
//        }
//    }



}

