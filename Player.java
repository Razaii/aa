import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Player extends Mainshape {
    private double speed = 5;  // Movement speed of the player
    private double xDirection = 0;  // Movement direction (left, right, etc.)
    private double yDirection = 0;  // Movement direction (up, down)
    private double width = 0;  // Movement direction (left, right, etc.)
    private double height = 0; 
    
    public Player(double x, double y, double width, double height) {
        super(x, y, width, height);  //  constructor of Mainshapw
        rectangle.setFill(Color.GREEN);  //fil the color green
        this.rectangle = new Rectangle(x, y, width, height);  // Initialize the rectangle with position and size
    }
    

    @Override
    public void update() {
        // Update the player's position based on the direction
        double speedX = rectangle.getX() + xDirection * speed;  // Calculate new X position
        double speedY = rectangle.getY() + yDirection * speed;  // Calculate new Y position
        
        // Check for wall collisions and reverse direction if necessary
        if (speedX <= 0 || speedX >= 800 - rectangle.getWidth()) {
            xDirection = -xDirection;  
        }
        if (speedY <= 0 || speedY >= 600 - rectangle.getHeight()) {
            yDirection = -yDirection;  
        }
        
        rectangle.setX(rectangle.getX() + xDirection * speed);  // Update X position
        rectangle.setY(rectangle.getY() + yDirection * speed);  // Update Y position
    }

    // Set the direction of movement based on keyboard input
    public void setDirection(double dx, double dy) {
        this.xDirection = dx;  //  horizontal  direction
        this.yDirection = dy;  //  vertical  direction
    }

    @Override
    public boolean Colliding(Mainshape other) {
        // Simple collision check using rectangle bounds
        return this.rectangle.getBoundsInParent().intersects(other.getRectangle().getBoundsInParent());
    }
    
    public void setsize(double width, double height) {
        rectangle.setWidth(width);  // Update the rectangle's width
        rectangle.setHeight(height);  // Update the rectangle's height
    }
    public void setplayercords(double x, double y) {
        rectangle.setX(x);  // Update the rectangle's width
        rectangle.setY(y);  // Update the rectangle's height
    }

}
