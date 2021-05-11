package breakout;



// The model represents all the actual content and functionality of the game
// For Breakout, it manages all the game objects that the View needs
// (the bat, ball, bricks, and the score), provides methods to allow the Controller
// to move the bat (and a couple of other functions - change the speed or stop 
// the game), and runs a background process (a 'thread') that moves the ball 
// every 20 milliseconds and checks for collisions 

import javafx.scene.paint.*;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import java.io.File;
import javafx.application.Platform;

public class Model 
{
	// First,a collection of useful values for calculating sizes and layouts etc.

	public byte B              = 6;      // Border round the edge of the panel
	public byte M              = 40;     // Height of menu bar space at the top

	public byte BALL_SIZE      = 30;     // Ball side

	public byte BRICK_R        = 15;
	public byte BRICK_C		  = 15;
	public byte BRICK_WIDTH    = 50;     // Brick size
	public byte BRICK_HEIGHT   = 30;

	public byte BAT_MOVE       = 5;      // Distance to move bat on each keypress
	public static byte BALL_MOVE      = 3;      // Units to move the ball on each step changed to static to reference globally

	public byte HIT_BRICK      = 50;     // Score for hitting a brick
	public short HIT_BOTTOM     = -200;   // Score (penalty) for hitting the bottom of the screen

	public int lives		  = 3;      //The lives the player has when starting the game

	private Color colors[] = {Color.RED, Color.YELLOW, Color.PINK, Color.GREEN, Color.ORANGE, Color.YELLOW, Color.BLUE}; // Colors for the random colors of the bricks

	// The other parts of the model-view-controller setup
	View view;
	Controller controller;

	// The game 'model' - these represent the state of the game
	// and are used by the View to display it
	public GameObj ball;                // The ball
	public GameObj[] bricks;            // The bricks
	public BatObj bat;                 // The bat
	public int score = 0;               // The score

	// variables that control the game 
	public String gameState = "running";// Set to "finished" to end the game
	public boolean fast = false;        // Set true to make the ball go faster

	// initialisation parameters for the model
	public int width;                   // Width of game
	public int height;                  // Height of game

	// CONSTRUCTOR - needs to know how big the window will be
	public Model( int w, int h )
	{
		Debug.trace("Model::<constructor>");  
		width = w; 
		height = h;


	}


	// Animating the game
	// The game is animated by using a 'thread'. Threads allow the program to do 
	// two (or more) things at the same time. In this case the main program is
	// doing the usual thing (View waits for input, sends it to Controller,
	// Controller sends to Model, Model updates), but a second thread runs in 
	// a loop, updating the position of the ball, checking if it hits anything
	// (and changing direction if it does) and then telling the View the Model // changed.

	// When we use more than one thread, we have to take care that they don't
	// interfere with each other (for example, one thread changing the value of 
	// a variable at the same time the other is reading it). We do this by 
	// SYNCHRONIZING methods. For any object, only one synchronized method can
	// be running at a time - if another thread tries to run the same or another
	// synchronized method on the same object, it will stop and wait for the
	// first one to finish.

	// Start the animation thread
	public void startGame()
	{
		
		initialiseGame();                           // set the initial game state
		Thread t = new Thread( this::runGame );     // create a thread running the runGame method
		t.setDaemon(true);                          // Tell system this thread can die when it finishes
		t.start();                                  // Start the thread running
	}   

	// Initialise the game - reset the score and create the game objects 
	public void initialiseGame()
	{      
		try {
			Thread.sleep(3000);				//delays game, add more comments. 
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		score = 0;
		ball   = new BallObj(width/2, height/2, Color.PURPLE );
		bat    = new BatObj(width/2-150, height - BRICK_HEIGHT * 2, Color.PALEGOLDENROD);


		int max_BRICK_COLUMN = width/BRICK_WIDTH;
		int max_BRICK_ROWS = (height/BRICK_HEIGHT)/8;
		Debug.trace("Max amount of colums is: %s", + max_BRICK_COLUMN);
		Debug.trace("Max amount of rows is: %s", + max_BRICK_ROWS);



		bricks = new BrickObj[max_BRICK_COLUMN*max_BRICK_ROWS];
		int posX = 70;
		int posY = 70;

		for (int j = 0; j < max_BRICK_ROWS; j++) {
			for (int i = 0; i < max_BRICK_COLUMN; i++) {

				int rand = (int)(Math.random() * (colors.length - 0));	
				bricks[i+(j*(max_BRICK_COLUMN))] = new BrickObj(posX*i,posY*(j+1), colors[rand]);
			}

		}

	}       	


	// The main animation loop
	public void runGame()
	{
		try
		{
			Debug.trace("Model::runGame: Game starting"); 
			// set game true - game will stop if it is set to "finished"
			setGameState("running");
			while (!getGameState().equals("finished"))
			{
				updateGame();                        // update the game state
				modelChanged();                      // Model changed - refresh screen
				Thread.sleep( getFast() ? 10 : 6); // wait a few milliseconds
			}
			Debug.trace("Model::runGame: Game finished"); 
		} catch (Exception e) 
		{ 
			Debug.error("Model::runAsSeparateThread error: " + e.getMessage() );
		}
	}

	// updating the game - this happens about 50 times a second to give the impression of movement
	public synchronized void updateGame()
	{
		// move the ball one step (the ball knows which direction it is moving in)
		ball.moveX(BALL_MOVE);                      
		ball.moveY(BALL_MOVE);
		// get the current ball possition (top left corner)
		int x = ball.topX;  
		//        Debug.trace("Position of Ball " + x); 
		int y = ball.topY;
		//        Debug.trace("Position of Ball " + y); 
		// Deal with possible edge of board hit-
		if (y <= 50) ball.changeDirectionY(); 
		if (x >= width - B - BALL_SIZE)  ball.changeDirectionX();
		if (x <= 0 + B)  ball.changeDirectionX();
		if (y >= height - B - BALL_SIZE)  // Bottom
		{ 
			//        	ball   = new GameObj(width/2, height/2, BALL_SIZE, BALL_SIZE, Color.RED );
			// subtractFromLives( HIT_BOTTOM );     // lose life once you hit bottom of screen

		}

		if (lives == 0) {

			gameState = "finished";
		}

		if (y >= height - B - BALL_SIZE)  // Bottom
		{ 
			ball.changeDirectionY(); 
			addToScore( HIT_BOTTOM );     // score penalty for hitting the bottom of the screen
		}
		if (y <= 0 + M)  ball.changeDirectionY();
		if (y <= 0 + M)  ball.changeDirectionY();

		// check whether ball has hit a (visible) brick


		for (GameObj brick : bricks) {
			if (brick.visible )
			{
				if (brick.hitSide(ball)) {
					ball.changeDirectionX();
					Debug.trace("HIT BRICK SIDE" );
					brick.visible = false;
					score =+ HIT_BRICK;
				}
				if (brick.hitTopBot(ball)) { 
					ball.changeDirectionY();
					Debug.trace("HIT BRICK Top" );
					brick.visible = false;
					score =+ HIT_BRICK;
				}
			}
		}
		// check whether ball has hit the bat
		if ( bat.hitSide(ball) ) {
			ball.changeDirectionX();

			Debug.trace("HIT bat SIDE" );
		}

		if (bat.hitTopBot(ball)) {
			ball.changeDirectionY();
			Debug.trace("HIT bat top" );
		}

	}

	// This is how the Model talks to the View
	// Whenever the Model changes, this method calls the update method in
	// the View. It needs to run in the JavaFX event thread, and Platform.runLater 
	// is a utility that makes sure this happens even if called from the
	// runGame thread
	public synchronized void modelChanged()
	{
		Platform.runLater(view::update);
	}


	// Methods for accessing and updating values
	// these are all synchronized so that the can be called by the main thread 
	// or the animation thread safely

	// Change game state - set to "running" or "finished"
	public synchronized void setGameState(String value)
	{  
		gameState = value;
	}

	// Return game running state
	public synchronized String getGameState()
	{  
		return gameState;
	}

	// Change game speed - false is normal speed, true is fast
	public synchronized void setFast(Boolean value)
	{  
		fast = value;
	}

	// Return game speed - false is normal speed, true is fast
	public synchronized Boolean getFast()
	{  
		return(fast);
	}

	// Return bat object
	public synchronized GameObj getBat()
	{
		return(bat);
	}

	// return ball object
	public synchronized GameObj getBall()
	{
		return(ball);
	}

	// return bricks
	public synchronized GameObj[] getBricks()
	{
		return(bricks);
	}

	// return score
	public synchronized int getScore()
	{
		return(score);
	}

	// update the score
	public synchronized void addToScore(int n)    
	{
		score += n;        
	}

	public synchronized void subtractFromLives(int L) {
		lives -= 1;
	}

	// move the bat one step - -1 is left, +1 is right
	public synchronized void moveBat( int direction )
	{        
		int dist = direction * BAT_MOVE;    // Actual distance to move
		Debug.trace( "Model::moveBat: Move bat = " + dist );
		bat.moveX(dist);
	}
}   
