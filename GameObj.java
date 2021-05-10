package breakout;

 

// An object in the game, represented as a rectangle, with a position,
// a size, a colour and a direction of movement.

// Watch out for the different spellings of Color/colour - the class uses American
// spelling, but we have chosen to use British spelling for the instance variable!

// import Athe JavaFX Color class
import javafx.scene.paint.Color;

public class GameObj
{
    // state variables for a game object
    public boolean visible  = true;     // Can be seen on the screen (change to false when the brick gets hit)
    public int topX   = 0;              // Position - top left corner X
    public int topY   = 0;              // position - top left corner Y
    public int width  = 0;              // Width of object
    public int height = 0;              // Height of object
    public Color colour;                // Colour of object
    public int   dirX   = 1;            // Direction X (1, 0 or -1)
    public int   dirY   = 1;            // Direction Y (1, 0 or -1)


    public GameObj( int x, int y, int i, int j, Color c )
    {
        topX   = x;       
        topY = y;
        width  = i; 
        height = j; 
        colour = c;
    }

    // move in x axis
    public void moveX( int units )
    {
        topX += units * dirX;
    }

    // move in y axis
    public void moveY( int units )
    {
        topY += units * dirY;
    }

    // change direction of movement in x axis (-1, 0 or +1)
    public void changeDirectionX()
    {
        dirX = -dirX;
    }

    // change direction of movement in y axis (-1, 0 or +1)
    public void changeDirectionY()
    {
        dirY = -dirY;
    }

    // Detect collision between this object and the argument object
    // It's easiest to work out if they do NOT overlap, and then
    // return the opposite
   
    public boolean hitTopBot(GameObj obj) {
    		
    	boolean intersectingX =
    			
    			topX+width >= obj.topX &&
    			topX <= obj.topX +obj.width; //checking if it is CURRENTLY crossing X axis
//    			Debug.trace("interesecting X %s", intersectingX + "");
    			
    	if (intersectingX) Debug.trace("Intersecting X ");
    			
    	boolean abouttointersectY =
    			topY+height >= obj.topY - Model.BALL_MOVE &&  
    			topY <= obj.topY + obj.height + Model.BALL_MOVE; //checking if it is GOING TO  crossing X axis
    			
    	boolean hitvertical = 
    			intersectingX && abouttointersectY;
    			
    	return hitvertical;
    }
    
    
    public boolean hitSide(GameObj obj) {
    	
    	boolean intersectingY = 
    			topY+height >= obj.topY &&  
    			topY <= obj.topY + obj.height; //checking if it is CURRENTLY crossing X axis
//    			Debug.trace("interesecting Y %s", intersectingY + "");
    			
    			
    			
    			
    	if (intersectingY) Debug.trace("Intersecting y ");
    	
    	boolean abouttointersectX =
    			topX+width >= obj.topX - Model.BALL_MOVE&&
    			topX <= obj.topX +obj.width + Model.BALL_MOVE; //checking if it is GOING crossing X axis
    			
    	
    	boolean hitSide= 
    			intersectingY && abouttointersectX;
    	
    	
    			
    			
    	return hitSide;
    	
    	
    	
    }    
    

}
