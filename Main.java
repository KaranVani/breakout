package breakout;

 

import java.nio.file.Paths;

// breakout game Main class - use this class to start the game

// We need to access some JavaFX classes so we list ('import') them here
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;

public class Main extends Application
{
	
	Stage window;
	Scene startscene, endscene;
    // The 'main' method - this is only used when launching from the command line.
    public static void main( String args[] )
    {
        // 'launch' initialises the system and then calls 'start'
        // (When running in BlueJ, the menu option 'Run JavaFX Application'
        // calls 'start' itself)
        launch(args);
    }

    // the 'start' method - this creates the Model, View and Controller objects and
    // makes them talk to each other, it then sets up the user interface (in the View 
    // object) and starts the game running (in the Model object)
    public void start(Stage window) throws Exception  
    {
    	
        int H = 1000;         // Height of game window (in pixels)
        int W = 900;         // Width  of game window (in pixels)

        // set up debugging and print initial debugging message
        Debug.set(true);    // change this to 'false' to stop breakout printing messages         
        Debug.trace("Main::start: Breakout starting"); 

        // Create the Model, View and Controller objects
        Model model = new Model(W,H);
        View  view  = new View(W,H);
        Controller controller  = new Controller();

        // Link them together so they can talk to each other
        // Each one has instance variables for the other two
        music();  //Let there be music! 
        model.view = view;
        model.controller = controller;
        
        controller.model = model;
        controller.view = view;
        
        view.model = model;
        view.controller = controller;

        // start up the game interface (the View object, passing it the window
        // object that JavaFX passed to this method, and then tell the model to 
        // start the game
        view.start(window); 				   
        model.startGame();
       

        // application is now running - print a debug message to say so
        Debug.trace("Main::start: Breakout running"); 
    }
    
    // used MediaPlayer for the game
    MediaPlayer mediaPlayer;
	public void music() {
		String s = "res/music.mp3";
		Media h = new Media(Paths.get(s).toUri().toString());
		mediaPlayer = new MediaPlayer(h);
		mediaPlayer.play();
		
	}
}
