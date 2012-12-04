/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Thomas Bennett
 */
class Cell {
    public int row, col;
    public Cell(){}
    public Cell(int row, int col){
        this.row=row;
        this.col=col;
    }

    @Override
    public String toString() {
        return "Cell{" + "row=" + row + ", col=" + col + '}';
    }

    
}
