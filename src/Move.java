
public class Move {
	
	
	private int [][]state ;
	private int newpiecerow;
	private int newpiececolumn;
	public Move (int [][]boardstate, int row, int column)
	{
		state = new int [boardstate.length][boardstate[0].length];
		for(int r=boardstate.length-1; r>=0; r--)
    	{
    		
    		for(int c=0; c<boardstate[0].length; c++)
    		{
    			state[r][c] = boardstate[r][c];
    		}
    	}
		this.newpiecerow = row;
		this.newpiececolumn = column;
		this.state[row][column] = 2;
	}
	
	public int[][] getState()
	{
		return this.state;
	}
	
	

}
