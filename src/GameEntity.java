import java.awt.*;

public interface GameEntity {
    Shape getSprite();
    boolean moveSprite(double stepInterval);
}
