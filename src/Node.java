/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Thomas Bennett
 */
public class Node {
    public Move move;
    public int value;
    public int[][] board;

    @Override
    public String toString() {
        return "Node{" + "move=" + move + ", value=" + value + ", board=" + board + '}';
    }

    
}
