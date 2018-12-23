package model.Piece;

public class Cell {
    private int row;
    private int column;
    private Piece value;

    public Cell(int row, int column) {
        this.row = row;
        this.column = column;
    }

    public int getColumn() {
        return column;
    }

    public int getRow() {
        return row;
    }

    public boolean isEmpty() {
        return value == null;
    }

    public void setValue(Piece value) {
        this.value = value;
    }

    public Piece getValue() {
        return value;
    }

    public boolean inBound(int row, int column) {
        return row > -1 && row < 8 && column > -1 && column <8;
    }

    @Override
    public String toString() {
        return "Cell( " + row + ", " + column + ")";
    }

    @Override
    public boolean equals(Object other) {
        return other instanceof Cell &&
                row == ((Cell) other).getRow() &&
                column == ((Cell) other).getColumn() &&
                value == ((Cell) other).getValue();
    }

}
