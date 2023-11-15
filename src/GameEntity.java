import java.awt.*;

public interface GameEntity {
    Shape getSprite();
    boolean positionSprite(int distX, int distY);
}
