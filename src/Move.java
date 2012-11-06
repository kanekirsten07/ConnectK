
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
	
	

}
