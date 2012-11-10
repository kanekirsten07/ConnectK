
public class Move {



	private int newpiecerow;
	private int newpiececolumn;
	private int heuristicvalue;

	// Moves will be represented solely as the coordinates of their piece, so save memory space
	public Move ( int row, int column)
	{

		this.newpiecerow = row;
		this.newpiececolumn = column;

	}
	public int getHeuristic()
	{
		return heuristicvalue;
	}

	public int getRow()
	{
		return newpiecerow;
	}

	public int getCol()
	{
		return newpiececolumn;
	}

    public int[][] createState(int[][] startingBoard, int moveType, int rows, int cols) {
        int[][] returnBoard = new int[rows][cols];
        for (int i =0; i<rows; i++){
            for (int j=0; j<cols; j++){
                returnBoard[i][j]=startingBoard[i][j];
            }
        }
        returnBoard[getRow()][getCol()]=moveType;
        return returnBoard;


    }



}
