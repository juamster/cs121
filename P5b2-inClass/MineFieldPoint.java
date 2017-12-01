import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.border.LineBorder;

/**
 * This implements a simple minefield button.  
 * @author judyconrad
 *
 */
public class MineFieldPoint extends JButton {

	 private int myX;
	 private int myY;
	 private boolean isMine;
	 private boolean isStart;
	 private int numMinesNearby;
	 final Color noStep = Color.gray;
	 final Color aMine = Color.black; 
	 final Color[] COLORS = {Color.green, Color.yellow, Color.orange, Color.red, Color.MAGENTA};
	 private final Color VALIDSTEP = new Color(82, 130, 206);

	 private ArrayList <MineFieldPoint> neighbors;
	 private ArrayList <MineFieldPoint> nextMove;
	 private int borderSize = 3;
	 
	 /**
	  * Minefield will create a point, and will know about it's neighbors. 
	  * @param x
	  * @param y
	  */

	 public MineFieldPoint(ActionListener listener, int x, int y)
	 {
		 // instance variables
		 this.myX = x;
		 this.myY = y;
		 this.isMine = false; 
		 this.isStart = false;
		 this.neighbors = new ArrayList<MineFieldPoint>();
		 this.nextMove = new ArrayList<MineFieldPoint>();
		
		 this.setBackground(noStep);
		 this.setPreferredSize(new Dimension(30, 30));
		 this.setOpaque(true); // mac fix
		 
		 this.setBorderPainted(true); // mac fix set to false
		 this.setBorder(new LineBorder(Color.white, borderSize));

		 this.addActionListener(listener);

	 }
	 /**
	  * Sets the starting button
	  */
	 public void setStart()
	 {
		 this.isStart = true;
	 }
	 
	 /**
	  * 
	  * @return the starting button
	  */
	 public boolean getStart()
	 {
		 return this.isStart;
	 }
	 
	 /**
	  * 
	  * @return the X coordinate of the button
	  */
	 public int getMyX()
	 {
		 return myX;
	 }
	 
	 /**
	  * 
	  * @return the Y coordinate of the button
	  */
	 public int getMyY()
	 {
		 return myY;
	 }
	 
	 
	/**
	 * @param n a mineFieldPoint.The neighbors are only mines
	 */
	 public void addNeighbor(MineFieldPoint n)
	 {
		 this.neighbors.add(n);
	 }
	 
	 /**
	  * 
	  * @param n  All possible next moves from a current point
	  */
	 public void addNextMove(MineFieldPoint n)
	 {
		 this.nextMove.add(n);
	 }
	 
	 public int numNeighbors()
	 {
		 return this.neighbors.size();
	 }
	 
	 public boolean isMine()
	 {
		 return isMine;
	 }
	 
	 public void setMine(boolean isMine)
	 {
		 this.isMine = isMine;
	 }
	 
	 public void colorValidPath()
	 {
		 setBackground(Color.pink);
		 this.setOpaque(true);
		 this.updateUI();
		 
	 }
	 
	 public void colorUserPath()
	 {
		 setBackground(Color.blue);
		 this.setOpaque(true);
		 this.updateUI();
		 
	 }
	 
	 public void hideValidPath()
	 {
		 setBackground(Color.gray);
		 this.setOpaque(true);
		 this.updateUI();
		 
	 }
	 
	 // check to see if the minepoint passed is in the neighbor list of the current point 
	 public boolean isNeighbor(MineFieldPoint minepoint)
	 { 
		 boolean isNeighbor = false;
		 if (minepoint.nextMove.size() != 0)
		 {
			 for(MineFieldPoint next : nextMove)
			 {
				 if (minepoint.equals(next))
				 {
					 isNeighbor = true;
					 System.out.println("This is an ok step");
				 } else {
					 System.out.println("This is not an ok step");
				 }
			 }
		 }
		 return isNeighbor;
	 }
	 
	 /**
	  * Show which places you can move to next
	  * @param minepoint
	  */
	 public void showNextPossibleSteps(MineFieldPoint minepoint)
	 { 
		 for(MineFieldPoint next : minepoint.nextMove)
		 {		 
			 next.setBorder(new LineBorder(VALIDSTEP, borderSize));
		 }
	 }
	 /**
	  * Resets the color of the boarder to the default 
	  */
	 public void resetBorderColor()
	 { 
		 for(MineFieldPoint next : this.nextMove)
		 {	
			 next.setBorder(new LineBorder(Color.white, borderSize));
//			 System.out.println("reseting the coloring border");
		 }
	 }
	 
	 public void resetPoint()
	 {
		 this.setBackground(noStep);
		 this.setOpaque(true);
		 this.updateUI();
		 this.isMine = false; 
		 this.neighbors = new ArrayList<MineFieldPoint>();
		 
	 }
	 
	 /**
	  * 
	  * @return a boolean that is true if this step is a mine
	  */
	 public Boolean doStep()
	 {
		 int minesNear;
		 
		 if (this.isMine())
		 {
			 System.out.println("It's a mine ");
			 setBackground(aMine);
		 } else 
		 {
			 minesNear = this.neighbors.size();
			
			 setBackground(COLORS[minesNear]);
			 System.out.println(minesNear + " mines near this point");
		 }
		 setText("");
		 return this.isMine();
//		 System.out.println("Took a step");
	 }
	 
	 
	 
	 public String toString()
	 {
		 if (isMine)
			 return "#";
		 else
			 return "0";
	 }
}

