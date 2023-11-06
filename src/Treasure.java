import java.awt.*;
import java.awt.geom.Rectangle2D;

public class Treasure {
    private Rectangle2D lootBox;
    public static final Color lootRGB = new Color(244, 184, 98);
    public Treasure(){
        this(0,0);
    }
    public Treasure(int posX, int posY){
        this.lootBox = new Rectangle2D.Double(posX, posY, 10, 10);
    }
}

