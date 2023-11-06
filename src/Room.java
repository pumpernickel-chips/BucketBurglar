

import java.awt.*;
import java.awt.geom.Point2D;
import java.util.Objects;

/**
 * @author Yuliia Synytska
 * TODO: Yuliia - take note of the new Point2D in the attributes.
 *       Notice that the parameters are now two sets of x and y values to define the Dimension and Point2D.
 *       Using this info, write the generateLoot() method to create Treasure objects (see class) in a forloop using
 *       randomized x and y parameters that use the roomSize and origin to only place them inside the bounds of the Room.
 *       TIP:
 *       You can choose to make these values relative to the whole window or just the room itself (treating the top-left
 *       corner of the room as point 0x0). There will be a method in HashGame to test this out in the GUI soon.
 *
 * TODO: (low priority) convert line comments to formatted JavaDoc comments
 */
public class Room implements GameEntity{
    private Dimension roomSize;
    private boolean isEmpty;
    /** The absolute coordinates at the center of the {@code Room} */
    private Point2D origin;

    /**
     * Default zero-args constructor, passes default title to complete constructor
     * */
    public Room() {
        this(256, 256, 128, 128, true);
    }
    /**
     * Parameterized constructor
     */
     public Room(int posX, int posY, int width, int height, boolean isEmpty) {
        this.roomSize = new Dimension(width, height);
        this.isEmpty = isEmpty;
        this.origin = new Point(posX, posY);
    }
    //method to generate loot in a Room so that players can grab it
    public void generateLoot(){
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Room)) return false;
        Room room = (Room) o;
        return isEmpty == room.isEmpty && Objects.equals(roomSize, room.roomSize) && Objects.equals(origin, room.origin);
    }
    @Override
    public int hashCode() {
        return Objects.hash(roomSize, isEmpty, origin);
    }
}
