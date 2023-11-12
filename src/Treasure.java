import java.awt.*;
import java.awt.geom.Rectangle2D;

/**
 *
 *
 * @author Yuliia Synytska
 * @author John Beaudry
 * @author Naomi Coakley
 *
 *
 */

public class Treasure implements GameEntity{
    private Rectangle2D lootBox;
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
        this.lootBox = new Rectangle2D.Double(posX, posY, 10, 10);
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
    public Rectangle2D getLootBox() {
        return lootBox;
    }

    /**
     * Sets {@code Rectangle2D} lootBox
     * @param lootBox {@code Rectangle2D}
     */
    public void setLootBox(Rectangle2D lootBox) {
        this.lootBox = lootBox;
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