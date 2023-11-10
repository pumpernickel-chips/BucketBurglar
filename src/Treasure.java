import java.awt.*;
import java.awt.geom.Rectangle2D;

public class Treasure implements GameEntity{
    private Rectangle2D lootBox;
    public static final Color lootRGB = new Color(244, 184, 98);
    private boolean boobyTrapped;
    public Treasure(){
        this(0,0);
    }
    public Treasure(int posX, int posY){
        this.boobyTrapped = false;
        this.lootBox = new Rectangle2D.Double(posX, posY, 10, 10);
    }
    @Override
    public void store() {
    }
    @Override
    public void retrieve() {
    }
    @Override
    public void remove() {
    }
}