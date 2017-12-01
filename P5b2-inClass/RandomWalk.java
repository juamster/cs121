import java.awt.Point;
import java.util.ArrayList;
import java.util.Random;


/**
 * This class sets up the random walking path.
 * 
 * @author Judy Amster
 * 
 */
public class RandomWalk {

/* Instance Variables (attributes) of the Walking Path
 *
 */
	private ArrayList<Point> path;  	// the path of points
	private Point currentPoint;  	// the current point
	private Boolean done;  			// are we done?
	private boolean atNorthBorder;	// Are we at the north border
	private boolean atEastBorder;	// Are we at the east border
	private int nBorder;				// The location of the north border
	private int eBorder;				// The location of the east border
	private Random rand;				// random number generator
	private String thePath = "";		// for printing the path

	
	/**
	 * Constructor - the walking grid with no random number generator
	 * @param size - the size of the walking grid 
	 * Initialize instance variables
	 */
	public RandomWalk(int size)
	{
		// initialize vars and random w/o seed
		rand = new Random();
		initializeRandomWalkVars(size);
	}
	
	/**
	 * Constructor - the walking grid plus a random number generator
	 * @param size - the size of the walking grid
	 * @param seed - for the random number generator
	 */
	public RandomWalk(int size, long seed)
	{
		// initialize vars and random w seed
		initializeRandomWalkVars(size);
		rand = new Random(seed);
		
	}
	
	/**
	 * Initialize all the instance variables
	 * @param size of the grid
	 */
	private void initializeRandomWalkVars(int size)
	{
		currentPoint = new Point();
		currentPoint.setLocation(0, size-1);
		path = new ArrayList<Point>();
		path.add(currentPoint);
		eBorder = size-1;
		nBorder = 0;
		done = false;
		atNorthBorder = false;
		atEastBorder = false;
	}
	
	
	/**
	 *  Take a step, figure out if we go north or east
	 */
	public void step()
	{
		// check to see if we are at the north or east border
		atNorthBorder = ((int) currentPoint.y == nBorder);
		atEastBorder = ((int) currentPoint.x == eBorder);
		
		// Are we done???  Return if we are.
		if (atNorthBorder && atEastBorder){
			done = true;
			return;
		}
		// see if we are at northern boarder and not at east, then we only go east
		if (atNorthBorder && !atEastBorder) {
			goDirection(1, 0);
		} else if (!atNorthBorder && atEastBorder) { // go north only, cause at east border
			goDirection(0, -1);
		} else { // can move either direction 
			if (rand.nextInt(2) == 1) {  // go north cause the random generator said to
				goDirection(0, -1); 
			} else { // go east
				goDirection(1, 0); 
			}
				
		}
		
	}
	
	/**
	 * This method will create a new point in the correct direction and add it to the path
	 * @param x - how far you move in the x direction
	 * @param y - how far you move in the y direction
	 */
	private void goDirection(int x, int y) {
		Point newPoint = new Point();
		newPoint.setLocation(currentPoint.x + x, currentPoint.y + y); 
		path.add(newPoint);
		currentPoint = newPoint;
		return;
	}
	
	/**
	 *  This simply creates the walk by calling step until isDone is true
	 */
	public void createWalk() {
		while (isDone() == false) 
		{
			step();
		}
		return;
	}
	
	/**
	 * Returns the done variable
	 * @return the value of done
	 */
	public boolean isDone() {
		return done;
		
	}
	/**
	 * Returns the path of the walk 
	 * @return the path of points
	 */
	public ArrayList<Point> getPath()
	{
		return path;
	}
	
	/**
	 *  Prints out the path in a pretty way
	 */
	public String toString() 
	{	
		for (Point p : path) {
			thePath = thePath + "[" + p.x + "," + p.y + "] ";		
		}
		return thePath;
	}
	
	
}
			



