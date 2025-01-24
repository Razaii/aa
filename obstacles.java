import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class obstacles extends Mainshape {

    // Constructor
    public obstacles(double x, double y) {
        super(x, y, 40, 40);  // Call to Mainshape constructor 
        rectangle.setFill(Color.RED);  //fill color of  orange
        dx = 2.5;  //  random horizontal speed
        dy = 2.5;  //  random vertical speed
    }
    
    @Override
    public boolean Colliding(Mainshape other) {
        if (other instanceof obstacles) {
        	
            obstacles otherObstacle = (obstacles) other; 
            return this.rectangle.getBoundsInLocal().intersects(otherObstacle.getRectangle().getBoundsInLocal());  // Check for intersection
            
        }
        return false; 
    }
    @Override
    public void update() {
        rectangle.setX(rectangle.getX() + dx);  // Update the X position based on the horizontal speed
        rectangle.setY(rectangle.getY() + dy);  // Update the Y position based on the vertical speed
    }
    
    public void setobstaclecords(double x, double y) {
        rectangle.setX(x);  // Update the rectangle's width
        rectangle.setY(y);  // Update the rectangle's height
    }
    
    public void setobstaclesize(double width, double height) {
        rectangle.setWidth(width);  // Update the rectangle's width
        rectangle.setHeight(height);  // Update the rectangle's height
    }
    
    public void setSpeed(double speedX, double speedY) {
        this.dx = speedX;
        this.dy = speedY;
    }



}
