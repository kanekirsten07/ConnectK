/******************************************************

   Class: ConnectK.java
   purpose: Return AI moves - a row and col number. 
   Author: Thuan Truong
   E-mail: tim@drimgmt.com
   Note: This is ONLY a sample

*******************************************************/

import java.util.Vector;
import java.awt.*;

public class connectK
{
/////////////////////////////////////////////////////////////////////////////////////////
// 
//	            Private variables definition
//
//              rows:	number of rows in the game board
//				cols:	number of columns in the game board
//				wins:	number of pieces required to win a game
//				computerMark:	this is a mark that the computer player has been assigned.
//						it will be either 'X' or 'O'.  It is not actually necessary to use
//						this variable, but you may find it useful to know which player will
//						move first, prior to nextMove() being called.
//              numOccupied: keep track how many pieces are currently occupied variable board array
//              cO:     character object
//              pB:     used to populate variables such as rows, cols, gravity, etc
//              depthLimit: determine where you cut off your search or how deep your search will be
//				board:   The board layout used by the GUI has the origin located at the top left corner
//				of the board.  For example:
//
//					N columns
//
//				  0  1  2..N-1
//               +-----------+
//              0|	|  |  |	 |
//               |--+--+--+--|
//				1|	|  |  |	 |
//     M rows    |--+--+--+--|
//				2|  |  |  |	 |
//             ..|--+--+--+--|
//			  M-1|  |  |  |  |
//               +-----------+
//

    private pBoard pB;          // Please do not remove these variables.
	private int depthLimit;     // You will need them to implement your
    private int numOccupied;    // alpah beta pruning cut off.
    private Vector[] board;
    private int rows;
    private int cols;
    private int wins;
    private char computerMark;
    private CharObj cO;
    
    connectK(pBoard b) //constructor
    {
        pB = b;        // use all of these public interfaces to get important
                       // variables: rows, cols,isGon,mark, board, etc.
        depthLimit = constant.MAXDEPTH;
        rows        = b.getRows();
        cols        = b.getCols();
        wins        = b.getWins();
        InitBoard();
    }
    
    public int heuristicEval(Vector[] board)
    {
    	int heuristic = rowEvaluation(board) + columnEvaluation(board);
    	
    	return heuristic;
    }
    //evaluate each row; sum up the number of icons of the same type (i.e. the computer player's icon, the cat) as long as there is no opposing player in between them.
    // The algorithm checks each for for all possible win scenarios adding up to wins within a row
    public int rowEvaluation(Vector[] board)
    {
    	int totalheuristic = 0;
    	int columnstart = 0;
    	for(int r=(rows-1); r>=0; r--)
    	{
    		do{
    		for(int i = columnstart; i <wins; i++)
    		{
    			cO = (CharObj)board[r].elementAt(i);
    			//if the current mark is the AI's, add one, otherwise subtract
    		}
    		columnstart++;
    		}while(cols-columnstart >= wins);
    	}
    	return totalheuristic;
    }
    
    public int columnEvaluation(Vector[] board)
    {
    	int totalheuristic = 0;
    	for(int c=0; c<cols; c++)
    	{
    		
    	}
    	return totalheuristic;
    }
        
    // This method is only a sample to show how you can make an AI move.
    // There are many required methods missing.  You have to implement these
    // to make a best AI move.
    public void nextMove()
    {
        copyBoard();
        boolean lbreak = false;
        for(int r=(rows-1); r>=0;r--)
        {
            for(int c=0; c < cols ;c++)
            {
                cO = (CharObj)board[r].elementAt(c);
                if(cO.mark==constant.BLANK)
                {
                    pB.setRow(r);   //return an AI row move.  This is how you return an AI row.  You must use this interface.
                    pB.setCol(c);   //return an AI col move.  This is how you return an AI col.  You must use this interface.
                    lbreak = true;
                    break;
                }
            }
            if(lbreak)
                break;
        }
    }  
    
        /*
	    method: InitBoard
	    purpose: Create a board array and set all elements to Blank:" "
	    visiblity: called ONLY by other methods in this class
	    paramters: NONE
	    return NONE
	*/  
    
    private void InitBoard()
    {
        board = new Vector[rows];
        for(int j=0; j< rows; j++)
        {
            board[j] = new Vector(cols);
        }
        
        for(int j=0; j<rows; j++)
        {
            for(int i=0; i<cols; i++)
            {
                board[j].addElement(new CharObj());
            }
        }
    }
            
    /*
	    method: copyBoard
	    purpose: Make a copy of board variable in pBoard class to variable board of this class
	    visiblity: called ONLY by other methods in this class
	    paramters: NONE
	    return NONE
	*/  
    
    private void copyBoard()
    {
        Vector[] temp = pB.getBoard();
        
        for(int j=0; j<rows; j++)
        {
            for(int i=0; i<cols; i++)
            {
                board[j].setElementAt(temp[j].elementAt(i), i);
            }
        }
    }    
}