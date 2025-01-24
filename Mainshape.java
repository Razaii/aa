import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public abstract class Mainshape {
    protected Rectangle rectangle;  // The shape (rectangle) associated with this object
    protected double dx;             // Horizontal speed
    protected double dy;    
    protected double height;  // Vertical speed
    
    // Constructor
    public Mainshape(double x, double y, double width, double height) {
        rectangle = new Rectangle(x, y, width, height);  // Initialize the rectangle with given position and dimensions
        rectangle.setFill(Color.PURPLE);  // Default fill color of  purple
    }

    // Abstract method to define behavior when colliding with another element
    public abstract boolean Colliding(Mainshape other);  
    
    // Abstract method to update position of the shape
    public abstract void update();  // Updates the position based on dx and dy
    
    // Getter for the rectangle
    public Rectangle getRectangle() {
        return rectangle;  // Return the rectangle object
    }
    
    // Getter and Setter for the speeds...
    public double getDx() {
        return dx;  
    }
  
    public void setDx(double dx) {
        this.dx = dx;  
    }

    
    public double getDy() {
        return dy;  
    }

    public void setDy(double dy) {
        this.dy = dy; 
    }
    
}
