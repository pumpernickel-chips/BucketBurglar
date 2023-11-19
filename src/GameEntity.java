import java.awt.*;
/**
 * Allows hashing implementation to be demonstrated with larger quantity by unifying the game's assets as one interface
 * @author Naomi Coakley
 * */
public interface GameEntity {
    /**
     * Gets the sprite
     * @return the sprite's Shape (cast to Ellipse2D and Rectangle2D)
     * */
    Shape getSprite();
    /**
     * Moves the sprite
     * @return whether the sprite has arrived at it's next destination.
     * */
    boolean moveSprite(double stepInterval);
}
