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
    private boolean blocked = false;
    private int blockedrow, blockedcol;
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

    public void searchForBlocking(int [][]board)
    {
    	
    	
    	
    	
    	//Search Rows
    	for(int r=(rows-1); r>=0; r--)
    	{
    		int columnstart = 0;
    		do{
    			int numAIpiecesseen = 0;
    			int numpersonplayersseen = 0;
    		for(int i = 0; i <wins; i++)
    		{
    			
    			if(board[r][columnstart+ i] == 2)
    			{
    				numpersonplayersseen = 0;
    				numAIpiecesseen++;
    				

    			}else if(board[r][columnstart + i] == 0)
    			{
    				numAIpiecesseen =0;
    				numpersonplayersseen = 0;
    			}else
    			{
    				numAIpiecesseen = 0;
    				numpersonplayersseen++;
    				if (numpersonplayersseen == wins -1 || numpersonplayersseen== wins -2)
    				{
    					
    					if( ((columnstart +i+1) < cols) && board[r][columnstart +i+1]== 0)
    					{
    						blocked = true;
    						blockedrow = r;
    						blockedcol = columnstart + i+1;
    					}else if((columnstart +i-(wins-1) >= 0) && board[r][columnstart +i-(wins-1)] == 0)
    					{
    						blocked = true;
    						blockedrow = r;
    						blockedcol = columnstart + i-(wins-1);
    					}
    				}
    			}

    		}
    		columnstart++;
    		}while(cols-columnstart > wins);
    	}
    	if(blocked)
    	{
    		return;
    	}
    	//search columns
    	for(int c=0; c<cols; c++)
    	{
    		int rowstart = 0;
    		do{
    			int numAIpiecesseen = 0;
    			int numpersonplayersseen = 0;
    			for(int i = 0; i <wins; i++)
        		{
        			if(board[rowstart + i][c] == 2)
        			{
        				numpersonplayersseen = 0;
        				numAIpiecesseen++;
        				
        			}else if(board[rowstart + i][c] == 0)
        			{
        				numAIpiecesseen =0;
        				numpersonplayersseen = 0;
        			}else
        			{
        				numAIpiecesseen = 0;
        				numpersonplayersseen++;
        				
        			
    				if (numpersonplayersseen == wins -1|| numpersonplayersseen == wins -2)
    				{
    					if((rowstart +i+1 <rows) && board[rowstart + i+1][c]== 0)
    					{
    						blocked = true;
    						blockedrow = rowstart + i+1;
    						blockedcol = c;
    					}else if((rowstart +i-(wins-1)>= 0 )&& board[rowstart + i-(wins-1)][c] == 0)
    					{
    						blocked = true;
    						blockedrow = rowstart +i -(wins-1);
    						blockedcol = c;
    					}
    				}
    			}

    		}
    		rowstart++;
    		}while(rows-rowstart >= wins);
    	}
    	if(blocked)
    	{
    		return;
    	}
    	//Search Diagonals left to right
    	for(int r = wins -1; r <= rows-1; r++)
    	{

    		if(r < 0 || r > rows)
    		{
    			break;
    		}
    		int columnstart = 0;
    		do{
    			int numAIpiecesseen = 0;
    			int numpersonplayersseen = 0;
    		for(int i = columnstart, j = 0, intr = r; j < wins; i++, intr--, j++)
    		{
    			
    			//System.out.println("Row: " + intr + "Column: " + i);
    				if(board[intr][i] == 2)
        			{
        				numpersonplayersseen = 0;
        				numAIpiecesseen++;
        				

        			}else if(board[intr][i] == 0)
        			{
        				numAIpiecesseen =0;
        				numpersonplayersseen = 0;
        			}else
        			{
        				numAIpiecesseen = 0;
        				numpersonplayersseen++;
        				if(numpersonplayersseen == (wins-1))
        				{
        					if((intr-1 >= 0) && (i+1 < cols) && board[intr-1][i+1] == 0)
        					{
        						blocked = true;
        						blockedrow = intr-1;
        						blockedcol = i+1;
        					}else if((intr+1 < rows)&& (i-(wins-1) >= 0) && board[intr+1][i-(wins-1)] ==0)
        					{
        						blocked = true;
        						blockedrow = intr+1;
        						blockedcol = i-(wins-1);
        					}
        					
        				}
        				
        			}
    				
    		}
    		columnstart++;
    		}while(columnstart <= cols-wins);
    		System.out.println("Break");
    	}
    	if(blocked)
    	{
    		System.out.println("True");
    	}
    	//Search Diagonals right to left
    	for(int r = wins -1; r <= rows-1; r++)
    	{

    		if(r < 0 || r > rows)
    		{
    			break;
    		}
    		int columnstart = cols-1;
    		do{
    			int numAIpiecesseen = 0;
    			int numpersonplayersseen = 0;
    		for(int i = columnstart, j = 0, intr = r; j < wins; i--, intr--, j++)
    		{
    			
    			System.out.println("Row: " + intr + "Column: " + i);
    				if(board[intr][i] == 2)
        			{
        				numpersonplayersseen = 0;
        				numAIpiecesseen++;
        				

        			}else if(board[intr][i] == 0)
        			{
        				numAIpiecesseen =0;
        				numpersonplayersseen = 0;
        			}else
        			{
        				numAIpiecesseen = 0;
        				numpersonplayersseen++;
        				if(numpersonplayersseen == (wins-1))
        				{
        					if((intr-1 >= 0) && (i-1 >= 0) && board[intr-1][i-1] == 0)
        					{
        						blocked = true;
        						blockedrow = intr-1;
        						blockedcol = i-1;
        					}else if((intr+1 < rows)&& (i+(wins-1) < cols) && board[intr+1][i+(wins-1)] ==0)
        					{
        						blocked = true;
        						blockedrow = intr+1;
        						blockedcol = i+(wins-1);
        					}
        					
        				}
        			}
    				
    		}
    		columnstart--;
    		}while(columnstart >= wins-1);
    		
    	}
    	
    }



    // Vector dot product of MaxWinPaths and Row/Column/Diagonal Evaluation
    public int heuristicEval(int[][] board)
    {
        int heuristic = 0;

    	heuristic = rowEvaluation(board) + columnEvaluation(board)+ diagonalEvaluation(board) * numWinPaths(nextMoveToEval.getRow(), nextMoveToEval.getCol());
    	//heuristic = rowEvaluation(board) + columnEvaluation(board)+ diagonalEvaluation(board); //+numWinPaths(nextMoveToEval.getRow(), nextMoveToEval.getCol());
//        heuristic = winPathsTest(board);
 //       heuristic = testLinesHeuristic(board)+centerPossessionHeuristic(board);
//        heuristic = testStupidHeuristic(board);
    	return heuristic;
    }
    //evaluate each row; sum up the number of icons of the same type (i.e. the computer player's icon, the cat) as long as there is no opposing player in between them.
    // The algorithm checks each for for all possible win scenarios adding up to wins within a row
    public int rowEvaluation(int[][] board)
    {
    	int totalheuristic = 0;
    	
    	for(int r=(rows-1); r>=0; r--)
    	{
    		int columnstart = 0;
    		do{
    			int numAIpiecesseen = 0;
    			int numpersonplayersseen = 0;
    		for(int i = 0; i <wins; i++)
    		{
    			//cO = (CharObj)board[r].elementAt(i);
    			/* In each row, iterate through each column up to the number of pieces are required to win (e.g. 4). If the new move plusotherse seen are equal to
    			 * 1, add 1. If 1 has been seen and the new move adds another next to it, add 4. If two have been spotted next to the new move, add 32. If whitespace breaks
    			 * any of the possible win scenarios, reset the AI piece counter. If the player piece blocks any of these, subtract 1 from the total heuristic
    			 *
    			 * */
    			//System.out.println("Row: " + r + "column : " + columnstart + i);
    			if(board[r][columnstart + i] == 2)
    			{
    				numpersonplayersseen = 0;
    				numAIpiecesseen++;
    				totalheuristic += 10*numAIpiecesseen;

    			}else if(board[r][columnstart + i] == 0)
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
    	
    	for(int c=0; c<cols; c++)
    	{
    		int rowstart = 0;
    		do{
    			int numAIpiecesseen = 0;
    			int numpersonplayersseen = 0;
    		for(int i = 0; i <wins; i++)
    		{
    			if(board[rowstart + i][c] == 2)
    			{
    				numpersonplayersseen = 0;
    				numAIpiecesseen++;
    				totalheuristic += 10*numAIpiecesseen;
    			}else if(board[rowstart + i][c] == 0)
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
    			int numAIpiecesseen = 0;
    			int numpersonplayersseen = 0;
    		for(int i = columnstart, j = 0, intr = r; j < wins; i++, intr--, j++)
    		{
    			
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
    			int numAIpiecesseen = 0;
    			int numpersonplayersseen = 0;
    		for(int i = columnstart, j = 0, intr = r; j < wins; i--, intr--, j++)
    		{
    			
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
    				
    		}
    		columnstart--;
    		}while(columnstart >= wins-1);
    		
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
    private MoveValuePair olddepthLimitedSearch(int[][] startingBoard, int depth, boolean gravityOn, boolean max){

        int minMaxMultiplier = 1;
        if (!max)
            minMaxMultiplier = -1; //this way min values will be chosen when selecting for min.
       // System.out.println("depth: "+depth);
        ArrayList<Move> moves;
        if (depth==0){
            return new MoveValuePair(null, minMaxMultiplier*heuristicEval(startingBoard));
        }
        if (gravityOn){
            moves = generateMovesGravityOn(startingBoard);
        } else{
            moves = generateMovesGravityOff(startingBoard);
        }
        int bestValueSoFar=Integer.MIN_VALUE;
        Move bestMoveSoFar = null;
        for (Move m: moves){
            int[][] state = m.createState(startingBoard, (max ? AI_MOVE : HUMAN_MOVE), rows, cols);
            nextMoveToEval=m;
            MoveValuePair pair= olddepthLimitedSearch(state, depth-1, gravityOn, !max);
            int value = pair.value*minMaxMultiplier;
            if (value>bestValueSoFar){
                bestValueSoFar=value;
                bestMoveSoFar=m;
            }
        }
        return new MoveValuePair(bestMoveSoFar, bestValueSoFar);
    }

    private Node depthLimitedSearch(Node node, int depth, boolean gravityOn, boolean max){
        if (depth <= 0){
            node.value=heuristicEval(node.board);
            return node;
        }
        int moveType=HUMAN_MOVE;
        if (max)
            moveType=AI_MOVE;
        ArrayList<Node> children = expand (node, moveType, gravityOn);
        for (Node n: children){
        	
            Node n1= depthLimitedSearch(n, depth-1, gravityOn, !max);
            if(!(n1== null)){
            int value = n1.value;
            n.value = value;
            }else {
            	n.value = 0;
            }
        } Node best = null;
        if (max){
                best = pickGreatest(children);
         }
         if (!max){
                best = pickLeast(children);
         }
        // System.out.println("the best value: "+best.value);
         return best;




    }











       //Renamed the old nextMove() nextMoveOriginal just in case we need to roll back to it.
    public void nextMove(){
        representationboard= new int [rows][cols];
        copyBoard();
        bestMove = null;
    //Search for wins-1 player pieces in a row, if it exists, forego the search and immediately block
        searchForBlocking(representationboard);
        
        if(blocked)
        {
        	
        	Move m = new Move(blockedrow, blockedcol);
        	blocked = false;
        	makeMove(m);
        }else
        {
       // test();
      /*  int branchingFactor;
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

        int bestHeuristicValue = pair.value;*/

        bestMove = iterativeDeepeningSearch(representationboard);
        if (bestMove==null)
            System.err.println("depthLimitedSearch() failed to find any moves. ");
        //System.out.println("Making move "+bestMove);
        makeMove(bestMove);
        }



    }

    private Move iterativeDeepeningSearch(int[][] board){
        final int startingDepth = 1;



        int branchingFactor;
        if (gravityOn){
            branchingFactor = branchingFactorGravityOn;
        }
        else{
            branchingFactor = branchingFactorGravityOff;
        }
        int depth = startingDepth;
        boolean keepLooking = true;
        Move moveToMake=null;
       long veryFirstStart = System.currentTimeMillis();
        long threshhold = NUMBER_OF_SECONDS_PER_MOVE*1000;
        long maxTime = veryFirstStart+threshhold;
        while (keepLooking){

            long timeStart = System.currentTimeMillis();
            moveToMake = depthLimitedSearchStart(board, depth);
            long timeEnd = System.currentTimeMillis();
            long timeTaken = timeEnd-timeStart;
            long timeNeededForOneMoreSearch = timeTaken * branchingFactor; //each successive search will be bigger by a factor of branchingFactor
            long timeAfterOneMoreSearch = timeNeededForOneMoreSearch+timeEnd;
            if (timeAfterOneMoreSearch > maxTime)
                keepLooking = false;
            else
                depth++;

        }
        //return depthLimitedSearchStart(board, 5);
        return moveToMake;
    }

    private Move depthLimitedSearchStart(int[][] board, int startingDepth){
        //returns the move that should be made.

        boolean gravityOn = this.gravityOn;
        boolean max = true;

        Node startNode = new Node();
        startNode.board=board;
        startNode.move=null;
        ArrayList<Node> children = expand(startNode, AI_MOVE, gravityOn);
        int bestValue= Integer.MIN_VALUE;
        Node candidate = null;
        for (Node n: children){
            nextMoveToEval = n.move;
            Node result = depthLimitedSearch (n, startingDepth -1, gravityOn, !max);
            if (result.value>bestValue){
                bestValue = result.value;
                candidate = result;
            }
        }
        //System.out.println("returning "+candidate);
        return candidate.move;


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

     private int testStupidHeuristic (int[][] board){
         int score = 0;

         if (board[5][3]==AI_MOVE)
             score++;

         return score;
     }



     private int testLinesHeuristic(int[][] board){
         ArrayList<Line> lines = new ArrayList<Line>();
         for (int i =0; i<cols; i++){
             for (int j= 0; j<rows; j++){
                 int col = i, row =j;
                 addAllValidLinesFromPoint (lines, row, col, board);
             }
         }
         int value =0;

         for (Line l: lines){
             if (contains(l, HUMAN_MOVE, board)&&contains(l, AI_MOVE, board)){
                 //value gets nothing because this line can be won by nobody.
             }
             else if (contains(l, HUMAN_MOVE, board)&&!contains(l, AI_MOVE, board)){
                 int c = containsHowMany(l, HUMAN_MOVE, board);
                 if (c==wins-2)
                     c-=15;
                 if (c==wins-1)
                     c-=25;
                 if (c == wins)
                     c-=1000; //if this would be a lose state, minus a bunch
                 //otherwise, minus however many pieces are in that line
                 //c*=2;
                 value-=c; //value decrements because human moves are bad.
             }
             else if (contains(l, AI_MOVE, board)&&!contains(l, HUMAN_MOVE, board)){
                 int c = containsHowMany(l, AI_MOVE, board);
                 if (c==wins)
                     value+=50;
                 value+=c; //this is a line we can win.
             }//note that this says nothing about lines with no pieces from either player.
         }
         return value;
     }
     private int containsHowMany(Line l, int moveType, int[][] board){
         int count =0;
         for (Cell c: l.cells){
             if (board[c.row][c.col]==moveType){
                 count ++;
             }
         }
         return count;
     }


      private boolean contains(Line l, int moveType, int[][] board) {
        return (containsHowMany(l, moveType, board)>=1);
    }

     /**
      * takes a starting location and makes a line starting at that location.
      * @param row
      * @param col
      * @param board
      * @return
      */
    private Line makeLine(int row, int col, int[][] board, boolean vert, boolean horiz, boolean backwardDiagonal) {
        if (backwardDiagonal && (!vert||!horiz)){
            throw new UnsupportedOperationException("for backwards diagonal to function, both very and horiz must be on. ");
        }

        int rMod, cMod;
        rMod = (vert? 1 : 0);
        cMod = (horiz? 1: 0);
        if (backwardDiagonal){
            cMod = -1;
        }

        Line l = new Line();
        for (int i =0; i<wins; i++){
            l.cells.add(new Cell (row+i*rMod, col+i*cMod));
        }
        return l;
    }
    private Line makeLineHorizontal (int row, int col, int[][] board){
        return makeLine (row, col, board, false, true, false);
    }
    private Line makeLineVertical(int row, int col, int[][] board){
        return makeLine (row, col, board, true, false, false);
    }
    private Line makeLineDiagonal(int row, int col, int[][] board){
        return makeLine (row, col, board, true, true, false);
    }
    private Line makeLineReverseDiagonal(int row, int col, int[][] board){
        return makeLine (row, col, board, true, true, true);
    }



    private void test() {
        for (int i=0; i<rows; i++){
            for (int j=0; j<cols; j++){
                if (representationboard[i][j]==HUMAN_MOVE)
                    System.out.println("human at: "+i+", "+j);
            }
        }
        System.out.println(representationboard[3][0]);
        Line l = makeLine(0, 4, this.representationboard, false, false, false);
        System.out.println(l);

        System.exit(-2);
    }

    private void addAllValidLinesFromPoint(ArrayList<Line> lines, int row, int col, int[][] board) {
        ArrayList<Line> ls = new ArrayList<Line>();
        Line l = makeLineHorizontal(row, col, board);
        ls.add(l);
        l=makeLineVertical(row, col, board);
        ls.add(l);
        l=makeLineDiagonal(row, col, board);
        ls.add(l);
        l=makeLineReverseDiagonal(row, col, board);
        ls.add(l);
        l=null;
        for (Line a: ls){
            if (isValidLine(a, board))
                lines.add(a);
        }




    }

    private boolean isValidLine(Line a, int[][] board) {
        for (Cell c: a.cells){
            if (c.col<0)
                return false;
            if (c.row<0)
                return false;
            if (c.col>=cols)
                return false;
            if (c.row>=rows)
                return false;
        }
        return true;
    }

    private ArrayList<Node> expand(Node node,int moveType,  boolean gravityOn) {
        ArrayList<Node> children = new ArrayList<Node>();
        ArrayList<Move> moves;
        if (gravityOn)
            moves = generateMovesGravityOn (node.board);
        else
            moves = generateMovesGravityOff(node.board);
        for (Move m: moves){
            Node n = new Node();
            n.move = m;
            n.board= m.createState(node.board, moveType, rows, cols);
            children.add(n);
        }
        return children;

    }

    private Node pickGreatest(ArrayList<Node> children) {
        int bestValue=Integer.MIN_VALUE;
        Node candidate=null;
        for (Node n: children){
            if (n.value>bestValue){
                bestValue=n.value;
                candidate = n;
            }
        }
        return candidate;
    }

    private Node pickLeast(ArrayList<Node> children) {
        int lowestValue = Integer.MAX_VALUE;
        Node candidate = null;
        for (Node n: children){
            if (n.value<lowestValue){
                candidate = n;
                lowestValue = n.value;
            }
        }
        return candidate;
    }

    private int centerPossessionHeuristic(int[][] board) {
        int score = 0;
        int col = cols-1;
        col/=2;
        int row = rows-1;;
        if (!gravityOn)
            row/=2;
        if (board[row][col]==AI_MOVE)
            score+=20;
        return score;

    }






}