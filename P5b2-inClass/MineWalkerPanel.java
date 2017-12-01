import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;


public class MineWalkerPanel extends JPanel{
	
	// creates the main JFrame panel and places the rest of the panels on it

	private JPanel gamePanel;
	private JPanel keyPanel;
	private JPanel messagePanel;
	private MineFieldPanel minePanel;
	private JPanel scorePanel;
	private JPanel subPanel;
	private JButton resetButton;
	private JButton pathButton;
	private JButton minesButton;
	private JLabel noMinesLabel;
	private JLabel oneMinesLabel;
	private JLabel twoMinesLabel;
	private JLabel threeMinesLabel;
	private JLabel startLabel;
	private JLabel endLabel;
	private JLabel lives;
	private JLabel score;
	private JLabel aMessage;
	private JLabel gridSize;
	private JTextField gridSizeField;
	private int theScore;
	private int theLives;
	private int lifeValue;
	private boolean gameOver;
	private double percent;
	private int size;
	private MineFieldPoint currentPoint;
	private MineFieldPoint lastPoint;
	
	
	public MineWalkerPanel(int size, double percent)
	{
		this.percent = percent;
		this.size = size;
		
		theScore = size*10;
		theLives = size/4 + (int)(size * percent);
		lifeValue = theScore/theLives;
		gameOver = false;
		
		setLayout(new BorderLayout());
		
		//  setup the game panel and then call the createGamePanel method to 
		// put all the things on it.
		gamePanel = new JPanel();
		gamePanel.setBackground(Color.orange);
		
		add(gamePanel, BorderLayout.EAST);
		
		createGamePanel();
		
		//  setup the key panel and then call the createKeyPanel method to 
		// put all the things on it.

		keyPanel = new JPanel();
		keyPanel.setBackground(Color.orange);
		add(keyPanel, BorderLayout.WEST);
		
		createKeyPanel();
		
		//  setup the score panel and then call the createScorePanel method to 
		// put all the things on it.
		
		scorePanel = new JPanel();
		scorePanel.setBackground(Color.orange);
		add(scorePanel, BorderLayout.SOUTH);
		
		createScorePanel();
		
		//  setup the message panel and then call the messagePanel method to 
		// put all the things on it.
			
		messagePanel = new JPanel();
		messagePanel.setBackground(Color.orange);
		add(messagePanel, BorderLayout.NORTH);
			
		createMessagePanel();

		//  get the mineFieldPanel and add it to the center
		
		minePanel = new MineFieldPanel(new StepListener(), size, percent);
		lastPoint = minePanel.getStartPoint();
		currentPoint = minePanel.getStartPoint();
		
		add(minePanel, BorderLayout.CENTER);
		
	}
	
	private void resetGame()
	{
		remove(minePanel);
		theScore = size*10;
		theLives = size/4 + (int)(size * percent);
		lifeValue = theScore/theLives;
		gameOver = false;
		
		minePanel = new MineFieldPanel(new StepListener(), size, percent);
		minePanel.resetMineFieldPanel();
		add(minePanel, BorderLayout.CENTER);
		minePanel.revalidate();
		lastPoint = minePanel.getStartPoint();
		currentPoint = minePanel.getStartPoint();
		lives.setText("Number of Lives: " + theLives);
		score.setText("Score: " + theScore);
		System.out.println("Reseting the game");
	}
	
	private void createGamePanel()
	{
		// Create the Game Panel with button controls
		subPanel = new JPanel();
		
		gamePanel.setLayout(new BoxLayout (gamePanel, BoxLayout.Y_AXIS));
		
		// Make a button and put it on the gamePanel
		resetButton = new JButton("New Game");
		resetButton.addActionListener(new ResetButtonListener());
		
		// Make button to show path
		pathButton = new JButton("Show Path");
		pathButton.addActionListener(new PathButtonListener());
		
		// Make button to show the mines
		minesButton = new JButton ("Show Mines");
		minesButton.addActionListener(new minesButtonListener());
		
		//  Make Grid Size Field label and text field

		gridSize = new JLabel("Grid Size: ");
		gridSizeField = new JTextField(5);
		subPanel.setBackground(Color.orange);
		subPanel.add(gridSize);
		subPanel.add(gridSizeField);

		gridSizeField.addActionListener(new gridSizeFieldListener());
	
		gamePanel.add(Box.createVerticalGlue());
		gamePanel.add(resetButton);
		gamePanel.add(pathButton);
		gamePanel.add(minesButton);
		gamePanel.add(Box.createVerticalGlue());
		gamePanel.add(subPanel);
		gamePanel.add(Box.createVerticalGlue());
	}
	
	private void createKeyPanel()
	{
		// Create the Key to the colors panel 
		
		keyPanel.setLayout(new BoxLayout (keyPanel, BoxLayout.Y_AXIS));
		
		// Make a label and put it on the keyPanel
		noMinesLabel = new JLabel("0 mines nearby");
		noMinesLabel.setBackground(Color.green);
		noMinesLabel.setOpaque(true);
		
		oneMinesLabel = new JLabel("1 mine nearby ");
		oneMinesLabel.setBackground(Color.yellow);
		oneMinesLabel.setOpaque(true);
		
		twoMinesLabel = new JLabel("2 mines nearby");
		twoMinesLabel.setBackground(Color.orange);
		twoMinesLabel.setOpaque(true);
		
		threeMinesLabel = new JLabel("3 mines nearby");
		threeMinesLabel.setBackground(Color.red);
		threeMinesLabel.setOpaque(true);
		
		startLabel = new JLabel("Start Here");
		startLabel.setBackground(Color.CYAN);
		startLabel.setOpaque(true);
		
		endLabel = new JLabel("End Here");
		endLabel.setBackground(Color.WHITE);
		endLabel.setOpaque(true);
		
		keyPanel.add(Box.createVerticalGlue());
		keyPanel.add(noMinesLabel);
		keyPanel.add(oneMinesLabel);
		keyPanel.add(twoMinesLabel);
		keyPanel.add(threeMinesLabel);
		keyPanel.add(Box.createVerticalGlue());
		keyPanel.add(startLabel);
		keyPanel.add(endLabel);
	}
	
	private void createScorePanel()
	{
	
		// Create the Score Panel with labels telling score and lives remaining
		
		scorePanel.setLayout(new BoxLayout (scorePanel, BoxLayout.X_AXIS));
		
		// Make a label and put it on the keyPanel
		lives = new JLabel("Number of Lives: " + theLives);
		score = new JLabel("Score: " + theScore);
		lives.setBackground(Color.yellow);
		lives.setOpaque(true);
		score.setBackground(Color.yellow);
		score.setOpaque(true);
		
		scorePanel.add(Box.createHorizontalGlue());
		scorePanel.add(lives);
		scorePanel.add(Box.createHorizontalGlue());
		scorePanel.add(score);
		scorePanel.add(Box.createHorizontalGlue());
	}
	
	private void createMessagePanel()
	{
		// Create the Score Panel with labels telling score and lives remaining
		
		messagePanel.setLayout(new BoxLayout (messagePanel, BoxLayout.X_AXIS));
		
		// Make a label and put it on the keyPanel
		
		aMessage = new JLabel("Good Luck!  Whatch out for BOMBS!");
		aMessage.setBackground(Color.white);
		aMessage.setOpaque(true);
		
		messagePanel.add(Box.createVerticalGlue());
		messagePanel.add(Box.createHorizontalGlue());
		messagePanel.add(aMessage);
		messagePanel.add(Box.createHorizontalGlue());
	}
	
	
	/**
	 * Deals with each step on the MineField Panel
	 * @author judyconrad
	 *
	 */
	private class StepListener implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent e)
		{
			Boolean hitMine;
			MineFieldPoint minePoint = (MineFieldPoint) e.getSource();
			minePanel.addUserStep(minePoint);
			
			if (!gameOver)
			{
				lastPoint = currentPoint;
				lastPoint.resetBorderColor();
				currentPoint = minePoint;
		
				if(currentPoint.isNeighbor(lastPoint) && currentPoint.getStart() == false)
				{
					hitMine = minePoint.doStep();
					if (hitMine)
					{
						aMessage.setText("OMG!!!! You stepped on a BOMB!  BOOM!"); 
						theLives--;
						theScore = theScore - lifeValue;
						lives.setText("Number of Lives: " + theLives);
						score.setText("Score: " + theScore);
					} else {
						aMessage.setText("That was close!  Keep going"); 
					}
					// When the score hits zero, bring up a popup that quits the game 
					// TODO: Bring up a popup
					if (theScore <= 0) {
						gameOver = true;
						JOptionPane.showMessageDialog(null, "Game Over");
						aMessage.setText("GAME OVER, select New Game to play again"); 
						minePanel.showUserSteps();
						minePanel.showMines();
						lastPoint.resetBorderColor();
					}
					// Keep track of possible next steps, and show what they are to the user
					minePoint.showNextPossibleSteps(minePoint);
				} else {
					aMessage.setText("Not A Valid Step");
				}
	
			}
			else
			{
				aMessage.setText("GAME OVER - JUDY BRING UP A POPUP AND QUIT");
//				JOptionPane.showMessageDialog(null, "Game Over");
			}
		}
	}
	
	
	private class gridSizeFieldListener implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent e)
		{
			 String text = gridSizeField.getText();
			 size = Integer.parseInt(text);
			 System.out.println("new size of grid: " + size );
//			 remove(minePanel);
//			 minePanel = new MineFieldPanel(new StepListener(), size, percent);
//			 minePanel.resetMineFieldPanel();
//			 add(minePanel, BorderLayout.CENTER);
//			 minePanel.revalidate();
			 resetGame();
		}
	}
	
	private class ResetButtonListener implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent e)
		{
			resetGame();
			minePanel.resetMineFieldPanel();
			 minePanel.revalidate();
			 
		}
	}
	
	private class PathButtonListener implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent e)
		{
			String text = pathButton.getText();
			
			if (text.equals("Show Path")) 
			{
				minePanel.showValidPath();
				pathButton.setText("Hide Path");
			} 
			else {
				minePanel.hideValidPath();
				pathButton.setText("Show Path");
			}
		}
	}
	
	private class minesButtonListener implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent e)
		{
			String text = minesButton.getText();
			
			if (text.equals("Show Mines")) 
			{
				minePanel.showMines();
				minesButton.setText("Hide Mines");
			} 
			else {
				minePanel.hideMines();
				minesButton.setText("Show Mines");
			} 
			
		}
	}
	
}
