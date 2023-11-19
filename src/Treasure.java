import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.Objects;
/**
 * An implementation of GameEntity that represents a Treasure and it's attributes, such as whether it is booby-trapped.
 * @author Yuliia Synytska
 * @author John Beaudry
 */
public class Treasure implements GameEntity{
    private Ellipse2D lootSprite;
    private Point2D targetPlayer;
    private boolean claimed;
    private final int keyNum, sideLength = (int) (24*(GUI.scale != null? GUI.scale : 1));
    public static final Color intelliYellow = new Color(244, 184, 98);
    /**
     * {@code true} if the Treasure is booby-trapped
     */
    private boolean boobyTrapped;
    /**
     * Default zero-args constructor, passes all zeroes to complete constructor
     * */
    public Treasure(){
        this(0,0,0);
    }
    /**
     * Complete constructor
     * @param posX position x-axis
     * @param posY position y-axis
     */
    public Treasure(int posX, int posY, int keyNum){
        this.boobyTrapped = false;
        this.lootSprite = new Ellipse2D.Double(posX, posY, sideLength, sideLength);
        this.keyNum = keyNum;
        this.claimed = false;
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


    public int getKeyNum() {
        return keyNum;
    }

    public boolean isClaimed() {
        return claimed;
    }

    public void setClaimed(boolean claimed) {
        this.claimed = claimed;
    }
    public Point2D getTargetPlayer() {
        return targetPlayer;
    }
    public void setTargetPlayer(Point2D targetPlayer) {
        this.targetPlayer = targetPlayer;
    }
    /**
     * @return {@code Ellipse2D}
     */
    @Override
    public Ellipse2D getSprite() {
        return this.lootSprite;
    }
    @Override
    public boolean moveSprite(double stepInterval) {
        double x1 = this.getSprite().getX();
        double y1 = this.getSprite().getY();
        double x2 = this.getTargetPlayer().getX();
        double y2 = this.getTargetPlayer().getY();

        double delta_x = x2 - x1;
        double delta_y = y2 - y1;

        double nextNodeDistance = Math.pow(Math.pow(delta_x, 2) + Math.pow(delta_y, 2), 0.5);

        boolean arrived = nextNodeDistance < 6;

        double proportion = stepInterval / nextNodeDistance;

        double destX = x1 + proportion * delta_x;
        double destY = y1 + proportion * delta_y;

        this.setLootSprite(new Ellipse2D.Double(destX, destY, sideLength, sideLength));

        return arrived;
    }
    /**
     * Sets {@code Ellipse2D.Double} lootSprite
     * @param ellipse {@code Ellipse2D.Double}
     */
    public void setLootSprite(Ellipse2D.Double ellipse) {
        this.lootSprite = ellipse;
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Treasure)) return false;
        Treasure treasure = (Treasure) o;
        return isClaimed() == treasure.isClaimed() && getKeyNum() == treasure.getKeyNum() && sideLength == treasure.sideLength && isBoobyTrapped() == treasure.isBoobyTrapped() && Objects.equals(lootSprite, treasure.lootSprite) && Objects.equals(getTargetPlayer(), treasure.getTargetPlayer());
    }
    /**
     * Overridden {@code hashCode} method. Uses {@code keyNum} and {@code isBoobyTrapped} since
     * these attributes are unique and will never change during gameplay.
     * @return {@code int} hash
     */
    @Override
    public int hashCode() {
        return Objects.hash(keyNum, boobyTrapped);
    }
}