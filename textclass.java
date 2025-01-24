import javafx.scene.Parent;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

public class textclass {
    private Text text;  

    // Constructor  for the Text object with specific properties
    public textclass(String content, double layoutX, double layoutY, double sizeX, double sizeY, Color color) {
        text = new Text(content);  // Create a new Text object with the content
        text.setX(layoutX);        //  X position 
        text.setY(layoutY);        // Y position 
        text.setScaleX(sizeX);     // horizontal scale 
        text.setScaleY(sizeY);     // vertical scale 
        text.setFill(color);      // fill color 
    }

    // Getter method to retrieve the Text object
    public Text getText() {
        return text;
    }

    // Method to update the content of the Text object
    public void setText(String content) {
        text.setText(content);  // Set the new content for the Text object
    }
    public void removeFromParent() {
        Parent parent = text.getParent();
        if (parent instanceof javafx.scene.layout.Pane pane) {
            pane.getChildren().remove(text); // Remove the text from its parent Pane
        }
    }
}
