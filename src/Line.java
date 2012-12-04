
import java.awt.Point;
import java.util.ArrayList;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Thomas Bennett
 */
public class Line {

    // 
    public ArrayList<Cell> cells = new ArrayList<Cell>();

    @Override
    public String toString() {
        String s="";
        s+="Line: "+cells.size()+"    ";
        for (Cell p: cells){
            s+=p+", ";
        }
        return s;


    }


}
