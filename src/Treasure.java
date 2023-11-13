import java.awt.*;
import java.awt.geom.Ellipse2D;

/**
 *
 *
 * @author Yuliia Synytska
 * @author John Beaudry
 * @author Naomi Coakley
 *
 *
 */

public class Treasure{
    private Ellipse2D.Double lootSprite;
    public static final Color lootRGB = new Color(244, 184, 98);
    /**
     * {@code true} if the room contains a booby trap
     */
    private boolean boobyTrapped;
    /**
     * Default zero-args constructor, passes default player name, health, and an empty ArrayDeque
     * */
    public Treasure(){
        this(0,0);
    }

    /**
     * Complete constructor
     * @param posX position x-axis
     * @param posY position y-axis
     */
    public Treasure(int posX, int posY){
        this.boobyTrapped = false;
        this.lootSprite = new Ellipse2D.Double(posX-5, posY-5, 10, 10);
    }

    /**
     * Returns true if room contains a booby trap
     * @return boobyTrapped
     */
    public boolean isBoobyTrapped() {
        return boobyTrapped;
    }

    /**
     * Method to set {@code boobyTrapped} to true or false
     * @param boobyTrapped
     */
    public void setBoobyTrapped(boolean boobyTrapped) {
        this.boobyTrapped = boobyTrapped;
    }

    /**
     * Returns {@code Rectangle2D} lootBox
     * @return lootBox
     */
    public Ellipse2D.Double getSprite() {
        return this.lootSprite;
    }

    /**
     * Sets {@code Ellipse2D.Double} lootSprite
     * @param ellipse {@code Ellipse2D.Double}
     */
    public void setLootSprite(Ellipse2D.Double ellipse) {
        this.lootSprite = ellipse;
    }
}