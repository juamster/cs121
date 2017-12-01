import java.awt.Color;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.JPanel;


/**
 * This class creates and deals with the mine field part of the game
 * @author judyconrad
 *
 */
public class MineFieldPanel extends JPanel {

	private int size;
	private int numMines;
	private MineFieldPoint[][] grid;
	private double percent;
	private JPanel minefieldPanel;
	private RandomWalk validPath;
	private ActionListener listener;
	private MineFieldPoint currentPoint;
	private MineFieldPoint lastPoint;
	private ArrayList <MineFieldPoint> userPath;
	
	public MineFieldPanel(ActionListener listener, int size, double percent)
	{
		this.listener = listener;
		this.size = size;
		this.numMines = (int) (size * size * percent);
		this.grid = new MineFieldPoint[size][size];
		this.percent = percent;
		userPath = new ArrayList<MineFieldPoint>();

		this.setLayout(new GridLayout(size, size));

		// Initialize the points on the grid;
		createPoints();
		
		//  set the mines on the board in a random fashion, avoiding 
		//  the "valid" path
		getValidPath();
		setRandomMines();
		addMinesAndNeighbors();
		currentPoint = grid[size-1][0];
		lastPoint = grid[size-1][0];
		
	}
	
	public void addUserStep(MineFieldPoint p)
	{
		userPath.add(p);
	}
	
	public void showUserSteps()
	{
		for (MineFieldPoint next : userPath) {

			next.colorUserPath();
		}
	}
	
	public void addMinesAndNeighbors()
	{
	//  add the neighboring mines to each point & possible moves to each point
		for (int i = 0; i < grid.length; i++) {
			for (int j = 0; j < grid[i].length; j++) {
				getNearbyMinesAndNeighbors(i,j);
			}
		}
	}
	/**
	 *  This just adds all the points to the grid
	 **/
	public void createPoints()
	{
		// later figure out how to make the minefield point a component
		// why do I want to do that?
		for (int i = 0; i < grid.length; i++) {
			for (int j = 0; j < grid[i].length; j++) {
				grid[i][j] = new MineFieldPoint(listener, i,j);
				this.add(grid[i][j]);

			}
		}
		//Set start and Destination Squares 
		grid[size-1][0].setBackground(Color.CYAN);
		grid[size-1][0].setText("@");
		grid[size-1][0].setStart();
		
		grid[0][size-1].setBackground(Color.WHITE);
		grid[0][size-1].setText("$$");
				
	}
	
	public void resetMineFieldPanel()
	{
		// Initialize the points on the grid;
		for (int i = 0; i < grid.length; i++) {
			for (int j = 0; j < grid[i].length; j++) {
				grid[i][j].resetPoint();
			}
		}
		
		//  set the mines on the board in a random fashion, avoiding
		//  the "valid" path
		userPath = new ArrayList<MineFieldPoint>();
		getValidPath();
		setRandomMines();
		addMinesAndNeighbors();
		
		
		//  set current point to the starting point 
		grid[size-1][0].setBackground(Color.CYAN);
		grid[size-1][0].setText("@");
		grid[size-1][0].setStart();
		
		grid[0][size-1].setBackground(Color.WHITE);
		grid[0][size-1].setText("$$");
		currentPoint = grid[size-1][0];
		lastPoint=grid[size-1][0];
	}
	
	public MineFieldPoint getCurrentPoint()
	{
		return currentPoint;
	}
	
	public void setCurrentPoint(MineFieldPoint p)
	{
		currentPoint = p;
	}
	
	public MineFieldPoint getLastPoint()
	{
		return lastPoint;
	}
	
	public void setLastPoint(MineFieldPoint p)
	{
		lastPoint = p;
	}
	
	public MineFieldPoint getStartPoint()
	{
		return grid[size-1][0];
	}
	
	public void getNearbyMinesAndNeighbors(int x, int y)
	{
		// Check point to the north
		if (y > 0) // we are not at the top
		{
			grid[x][y].addNextMove(grid[x][y-1]);
			if (grid[x][y-1].isMine())
			{
				grid[x][y].addNeighbor(grid[x][y-1]);
			}
		}
		// Check point to the south
		if (y < size-1) // we are not at the bottom
		{
			grid[x][y].addNextMove(grid[x][y+1]);
			if (grid[x][y+1].isMine())
			{
				grid[x][y].addNeighbor(grid[x][y+1]);
			}
		}
		// Check point to the east
		if (x > 0) // we are not at the left side
		{
			grid[x][y].addNextMove(grid[x-1][y]);
			if (grid[x-1][y].isMine())
			{
				grid[x][y].addNeighbor(grid[x-1][y]);
			}
		}
		// Check point to the west
		if (x < size-1) // we are not at the right side
		{
			grid[x][y].addNextMove(grid[x+1][y]);
			if (grid[x+1][y].isMine())
			{
				grid[x][y].addNeighbor(grid[x+1][y]);
			}
		}
		
//		System.out.println("we added " + grid[x][y].numNeighbors());
		
	}
	/**
	 * This creates a validPath to use.  We won't set any mines 
	 * on this path.
	 */
	public void getValidPath()
	{
		validPath = new RandomWalk(size);
		validPath.createWalk();
	}
	
	/**
	 * Shows the path that has no mines
	 */
	public void showValidPath()
	{

//		System.out.println("size of valid path is " + validPath.getPath().size());
		for (Point next : validPath.getPath()) {
			grid[(int)next.getX()][(int)next.getY()].colorValidPath();
		}

	}
	
	/**
	 * Shows the path that has no mines
	 */
	public void hideValidPath()
	{

		System.out.println("size of valid path is " + validPath.getPath().size());
		for (Point next : validPath.getPath()) {

			grid[(int)next.getX()][(int)next.getY()].hideValidPath();
		}

	}
	/**
	 * Shows where all the mines are located
	 */
	public void showMines()
	{
		for (int i = 0; i < grid.length; i++) {
			for (int j = 0; j < grid[i].length; j++) {
				if (grid[i][j].isMine())
					grid[i][j].setBackground(Color.black);;
			}
		}
		
	}
	
	public void hideMines()
	{
		for (int i = 0; i < grid.length; i++) {
			for (int j = 0; j < grid[i].length; j++) {
				if (grid[i][j].isMine())
					grid[i][j].setBackground(Color.gray);;
			}
		}
		
	}
	
	/**
	 * check to see if this location is on the valid pat
	 */
	private Boolean isOnValidPath (MineFieldPoint p)
	{
		ArrayList<Point> path = validPath.getPath();
		Point thisPoint;
		
		Boolean foundIt = false;
		int i = 0;
		
		while (!foundIt && i < path.size())
		{
			thisPoint = path.get(i);
			
			if ( ((int) thisPoint.getX() == p.getMyX()) && ((int) thisPoint.getY() == p.getMyY()) )
			{
				foundIt = true;
//				System.out.println("Mine on Path");
			} else
			{
				i++;
//				System.out.println("Looking for point in the path");
			}
		}
		
		return foundIt;
	}
	/**
	 * 
	 * generate numMines mines
	 * @return the number of mines created 
	 */
	private void setRandomMines()
	{
		Random rand = new Random();
		int randX;
		int randY;
		int numMinesCounter = 0;
		boolean setMine;
		
		while (numMinesCounter <  numMines )
		{
			
			randX = rand.nextInt(size);
			randY = rand.nextInt(size);
			setMine = true;
			
			// Don't set a mine at the beginning or end space
			if (randX == grid.length - 1 && randY == 0)
			{
				setMine = false;
			}
			if (randX == 0 && randY == grid.length - 1 )
			{
				setMine = false;
			}
					
			// if we this place is not already set to be a mine and we want to set it to be a mine
			// and we haven't made too many mines already
			// each one of these must evaluate to TRUE
					
			if (!grid[randX][randY].isMine() && (numMinesCounter != numMines) 
							&& !isOnValidPath(grid[randX][randY]) && setMine)
			{
				grid[randX][randY].setMine(setMine);
				numMinesCounter ++;	
//				System.out.println("Setting a mine");
			}
								
		}
		System.out.println("We have created :" + numMinesCounter);
		
	}
	
    public JPanel getJPanel() {
		
		return minefieldPanel;
	}
    
	
	@Override
	public String toString()
	{
		String result = "";
		// loop through and append each point 
		for (int i = 0; i < grid.length; i++) {
			for (int j = 0; j < grid[i].length; j++) {
				result += grid[i][j] + " ";
			}
			result += "\n";
		}
		return result;
	}

	
}

