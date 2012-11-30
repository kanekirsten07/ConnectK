/******************************************************

   Class: ConnectK.java
   purpose: Return AI moves - a row and col number.
   Author: Thuan Truong
   E-mail: tim@drimgmt.com
   Note: This is ONLY a sample

*******************************************************/

import java.util.ArrayList;
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
    //2d array, which is a reconstruction of the current board state. To be used for heuristic evaluation. 0=whitespace, 1= player, 2= AI
    private int [][] representationboard;

    public static final int AI_MOVE=2, HUMAN_MOVE=1, EMPTY_SPACE=0;

    private Move bestMove=null; //this is the best move selected by whichever algorithm is called.
    private Move nextMoveToEval;
    private boolean gravityOn;
    private static final  int NUMBER_OF_SECONDS_PER_MOVE=5;
    private int branchingFactorGravityOn, branchingFactorGravityOff;


    connectK(pBoard b) //constructor
    {
        pB = b;        // use all of these public interfaces to get important
                       // variables: rows, cols,isGon,mark, board, etc.
        gravityOn=b.getG();
        depthLimit = constant.MAXDEPTH;
        rows        = b.getRows();
        cols        = b.getCols();
        wins        = b.getWins();
        branchingFactorGravityOn = cols;
        branchingFactorGravityOff=cols*rows;
        InitBoard();
    }



    // Vector dot product of MaxWinPaths and Row/Column/Diagonal Evaluation
    public int heuristicEval(int[][] board)
    {

    	int heuristic = rowEvaluation(board) + columnEvaluation(board)+ diagonalEvaluation(board)+numWinPaths(nextMoveToEval.getRow(), nextMoveToEval.getCol());
//        heuristic = winPathsTest(board);
    	return heuristic;
    }
    //evaluate each row; sum up the number of icons of the same type (i.e. the computer player's icon, the cat) as long as there is no opposing player in between them.
    // The algorithm checks each for for all possible win scenarios adding up to wins within a row
    public int rowEvaluation(int[][] board)
    {
    	int totalheuristic = 0;
    	int columnstart = 0;
    	for(int r=(rows-1); r>=0; r--)
    	{
    		do{
    			int numAIpiecesseen = 0;
    			int numpersonplayersseen = 0;
    		for(int i = columnstart; i <wins; i++)
    		{
    			//cO = (CharObj)board[r].elementAt(i);
    			/* In each row, iterate through each column up to the number of pieces are required to win (e.g. 4). If the new move plusotherse seen are equal to
    			 * 1, add 1. If 1 has been seen and the new move adds another next to it, add 4. If two have been spotted next to the new move, add 32. If whitespace breaks
    			 * any of the possible win scenarios, reset the AI piece counter. If the player piece blocks any of these, subtract 1 from the total heuristic
    			 *
    			 * */
    			if(board[r][i] == 2)
    			{
    				numpersonplayersseen = 0;
    				numAIpiecesseen++;
    				totalheuristic += 10*numAIpiecesseen;

    			}else if(board[r][i] == 0)
    			{
    				numAIpiecesseen =0;
    				numpersonplayersseen = 0;
    			}else
    			{
    				numAIpiecesseen = 0;
    				numpersonplayersseen++;
    				totalheuristic-= 10*numpersonplayersseen;
    			}

    		}
    		columnstart++;
    		}while(cols-columnstart >= wins);
    	}
    	return totalheuristic;
    }

    public ArrayList<Move> generateMovesGravityOn(int[][]board)
    {
    	ArrayList<Move> temp = new ArrayList<Move>();
    	for(int c=0; c<cols; c++)
    	{

    		for(int r=(rows-1); r>=0; r--)
    		{
    			if(board[r][c]== 0)
    			{
    				Move m = new Move( r, c);
    				temp.add(m);
    				break;
    			}
    		}
    	}
    	return temp;
    }

    public ArrayList<Move> generateMovesGravityOff(int[][]board)
    {
    	ArrayList<Move> temp = new ArrayList<Move>();
    	for(int c=0; c<cols; c++)
    	{

    		for(int r=(rows-1); r>=0; r--)
    		{
    			if(board[r][c]== 0)
    			{
    				Move m = new Move( r, c);
    				temp.add(m);

    			}
    		}
    	}
    	return temp;
    }

    public int columnEvaluation(int[][] board)
    {
    	int totalheuristic = 0;
    	int rowstart = 0;
    	for(int c=0; c<cols; c++)
    	{
    		do{
    			int numAIpiecesseen = 0;
    		for(int i = rowstart; i <wins; i++)
    		{
    			if(board[i][c] == 2)
    			{
    				switch(numAIpiecesseen){
    				case(0): totalheuristic += 1;
    				case(1): totalheuristic += 4;
    				case(3): totalheuristic += 32;
    				}
    				numAIpiecesseen++;
    			}else if(board[i][c] == 0)
    			{
    				numAIpiecesseen =0;
    			}else
    			{
    				numAIpiecesseen = 0;
    				totalheuristic-= 10;
    			}

    		}
    		rowstart++;
    		}while(rows-rowstart >= wins);
    	}
    	return totalheuristic;
    }
    //Alternate heuristic to evaluate the board state. Can be used with the heuristic above to obtain the vector product heuristic
    public int numWinPaths(int moverow, int movecolumn)
    {
    	return winPathsHorizontal(moverow,movecolumn) +
    	winPathVertical(moverow,movecolumn) +
    	winPathsDiagonal(moverow,movecolumn);
    }

    public int winPathsHorizontal(int row, int column)
    {
    	int totalheuristic = 0;
    	int tempheuristic = 0;
    	for(int i = 1; i<= wins-1; i++)
    	{
    		if(column+i >= cols){
    			tempheuristic =0;
    			break;
    		}else if(representationboard[row][column+i] != 1)
    		{
    			tempheuristic++;
    		}else {
    			tempheuristic = 0;
    			break;
    		}
    	}
    	totalheuristic += tempheuristic;
    	tempheuristic = 0;
    	for(int i = 1; i<= wins-1; i++)
    	{
    		if(column-i < 0){
    			tempheuristic =0;
    			break;
    		}else if(representationboard[row][column-i] != 1)
    		{
    			tempheuristic++;
    		}else
    			{
    			tempheuristic = 0;
    			break;
    			}
    	}
    	totalheuristic += tempheuristic;
    	return totalheuristic;
    }
    public int winPathVertical(int row, int column)
    {
    	int totalheuristic = 0;
    	int tempheuristic = 0;
    	for(int i = 1; i<= wins-1; i++)
    	{

    		if(row+i >= rows){
    			tempheuristic =0;
    			break;
    		}else if(representationboard[row+i][column] != 1)
    		{
    			tempheuristic++;
    		}else {
    			tempheuristic = 0;
    			break;
    		}
    	}
    	totalheuristic += tempheuristic;
    	tempheuristic = 0;
    	for(int i = 1; i<= wins-1; i++)
    	{

    		if(row-i < 0){
    			tempheuristic =0;
    			break;
    		}else if(representationboard[row-i][column] != 1)
    		{
    			tempheuristic++;
    		}else{
    			tempheuristic = 0;
    			break;}
    	}
    	totalheuristic += tempheuristic;
    	return totalheuristic;
    }
    public int winPathsDiagonal(int row, int column)
    {
    	int totalheuristic = 0;
    	int tempheuristic = 0;

    		for(int i = 1; i<=wins-1; i++)
    		{
    			if((row-i < 0) || column -i < 0 )
    			{
    				tempheuristic =0;
    				break;
    			}else if(representationboard[row-i][column-i] != 1 )
    			{
    				tempheuristic++;
    			}else {
    				tempheuristic = 0;
    				break;
    			}
    		}
    		totalheuristic += tempheuristic;
    		tempheuristic = 0;
    		for(int i = 1; i<=wins-1; i++)
    		{
    			if((row-i < 0) || column +i >= cols )
    			{
    				tempheuristic =0;
    				break;
    			}else if(representationboard[row-i][column+i] != 1 )
    			{
    				tempheuristic++;
    			}else {
    				tempheuristic = 0;
    				break;
    			}
    		}
    		totalheuristic += tempheuristic;
    		tempheuristic = 0;
    		for(int i = 1; i<=wins-1; i++)
    		{
    			if((row+i >= rows) || column -i < 0 )
    			{
    				tempheuristic =0;
    				break;
    			}else if(representationboard[row+i][column-i] != 1 )
    			{
    				tempheuristic++;
    			}else {
    				tempheuristic = 0;
    				break;
    			}
    		}
    		totalheuristic += tempheuristic;
    		tempheuristic = 0;

    		for(int i = 1; i<=wins-1; i++)
    		{
    			if((row+i >= rows) || column +i >= cols )
    			{
    				tempheuristic =0;
    				break;
    			}else if(representationboard[row+i][column+i] != 1 )
    			{
    				tempheuristic++;
    			}else {
    				tempheuristic = 0;
    				break;
    			}
    		}
    		totalheuristic += tempheuristic;
    		return totalheuristic;


    }
    public int diagonalEvaluation(int[][] board)
    {
    	int totalheuristic = 0;
    	int r1 = 0, c1 = 0;

    	for(int r = wins -1; r <= rows-1; r++)
    	{

    		if(r < 0 || r > rows)
    		{
    			break;
    		}
    		int columnstart = 0;
    		do{

    		for(int i = columnstart, j = 0, intr = r; j < wins; i++, intr--, j++)
    		{
    			int numAIpiecesseen = 0;
    			int numpersonplayersseen = 0;
    			//System.out.println("Row: " + intr + "Column: " + i);
    				if(board[intr][i] == 2)
        			{
        				numpersonplayersseen = 0;
        				numAIpiecesseen++;
        				totalheuristic += 10*numAIpiecesseen;

        			}else if(board[intr][i] == 0)
        			{
        				numAIpiecesseen =0;
        				numpersonplayersseen = 0;
        			}else
        			{
        				numAIpiecesseen = 0;
        				numpersonplayersseen++;
        				totalheuristic-= 10*numpersonplayersseen;
        			}
    				/*
    				if((j+1 == wins) && (intr > 0))
    				{
    					j = 0;
    					i = columnstart;
    					intr =r ;
    				}
    				*/
    		}
    		columnstart++;
    		}while(columnstart <= cols-wins);
    		//System.out.println("Break");
    	}

    	for(int r = wins -1; r <= rows-1; r++)
    	{

    		if(r < 0 || r > rows)
    		{
    			break;
    		}
    		int columnstart = cols-1;
    		do{

    		for(int i = columnstart, j = 0, intr = r; j < wins; i--, intr--, j++)
    		{
    			int numAIpiecesseen = 0;
    			int numpersonplayersseen = 0;
    			//System.out.println("Row: " + intr + "Column: " + i);
    				if(board[intr][i] == 2)
        			{
        				numpersonplayersseen = 0;
        				numAIpiecesseen++;
        				totalheuristic += 10*numAIpiecesseen;

        			}else if(board[intr][i] == 0)
        			{
        				numAIpiecesseen =0;
        				numpersonplayersseen = 0;
        			}else
        			{
        				numAIpiecesseen = 0;
        				numpersonplayersseen++;
        				totalheuristic-= 10*numpersonplayersseen;
        			}
    				/*
    				if((j+1 == wins) && (intr > 0))
    				{
    					j = 0;
    					i = columnstart;
    					intr =r ;
    				}
    				*/
    		}
    		columnstart--;
    		}while(columnstart >= wins-1);
    		//System.out.println("Break");
    	}

    	return totalheuristic;
    }


        /**
     * this will perform depth first search over all the possible moves from a given starting state
     * It will only search to the given depth.
     * This will set bestMove to the bestMove determined by depth-limited search.
     *
     * @param startingBoard
     * @param depth
     * @return returns the heuristic value for the best leaf node found.
     */
    private MoveValuePair depthLimitedSearch(int[][] startingBoard, int depth, boolean gravityOn, boolean max){
        System.out.println("depth: "+depth);
        ArrayList<Move> moves;
        if (depth==0){
            return new MoveValuePair(null, heuristicEval(startingBoard));
        }
        if (gravityOn){
            moves = generateMovesGravityOn(startingBoard);
        } else{
            moves = generateMovesGravityOff(startingBoard);
        }
        int bestValueSoFar=Integer.MIN_VALUE;
        Move bestMoveSoFar = null;
        int minMaxMultiplier = 1;
        if (!max)
            minMaxMultiplier = -1; //this way min values will be chosen when selecting for min.
        for (Move m: moves){
            int[][] state = m.createState(startingBoard, (minMaxMultiplier==1 ? AI_MOVE : HUMAN_MOVE), rows, cols);
            nextMoveToEval=m;
            MoveValuePair pair= depthLimitedSearch(state, depth-1, gravityOn, !max);
            int value = pair.value*minMaxMultiplier;
            if (value>bestValueSoFar){
                bestValueSoFar=value;
                bestMoveSoFar=m;
            }
        }

        return new MoveValuePair(bestMoveSoFar, bestValueSoFar);



    }

       //Renamed the old nextMove() nextMoveOriginal just in case we need to roll back to it.
    public void nextMove(){
        representationboard= new int [rows][cols];
        copyBoard();
        bestMove = null;
        int branchingFactor;
        if (gravityOn){
            branchingFactor = branchingFactorGravityOn;
        }
        else{
            branchingFactor = branchingFactorGravityOff;
        }
        int depth = 1;
        boolean keepSearching = true;
        MoveValuePair pair=null;
        long veryFirstStart = System.currentTimeMillis();
        long threshhold = NUMBER_OF_SECONDS_PER_MOVE*1000;
        long maxTime = veryFirstStart+threshhold;
        while (keepSearching){
            long timeStart = System.currentTimeMillis();
            pair = depthLimitedSearch(representationboard, depth, true, true);
            long timeEnd = System.currentTimeMillis();
            long timeTaken = timeEnd-timeStart;
            long timeNeededForOneMoreSearch = timeTaken * branchingFactor; //each successive search will be bigger by a factor of branchingFactor
            long timeAfterOneMoreSearch = timeNeededForOneMoreSearch+timeEnd;
            if (timeAfterOneMoreSearch > maxTime)
                keepSearching = false;
            else
                depth++;
        }

        int bestHeuristicValue = pair.value;
        bestMove = pair.move;
        if (bestMove==null)
            System.err.println("depthLimitedSearch() failed to find any moves. ");
        System.out.println("Making move "+bestMove+"  with eventual value "+bestHeuristicValue+" searched with depth "+depth);
        makeMove(bestMove);




    }



    // This method is only a sample to show how you can make an AI move.
    // There are many required methods missing.  You have to implement these
    // to make a best AI move.
    public void nextMoveOriginal()
    {
    	representationboard= new int [rows][cols];
        copyBoard();
        ArrayList<Move> temp = generateMovesGravityOn(representationboard);
        for(int i = 0; i < temp.size(); i++)
        {
        	System.out.println(temp.get(i).toString());
        }
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
                    lbreak=true;

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
                cO= (CharObj) board[j].elementAt(i);
                if(cO.mark == constant.X)
                {
                	representationboard[j][i]= 1;
                	//System.out.print("1");
                }else if(cO.mark == constant.O)
                {
                	representationboard[j][i]= 2;
                	//System.out.print("2");
                }else
                {
                	representationboard[j][i]= 0;
                	//System.out.print("0");
                }


            }
            System.out.println("");
        }
    }

     private void makeMove(Move move) {
        int col, row;
        col = move.getCol();
        row = move.getRow();
        pB.setRow(row);
        pB.setCol(col);
    }



}