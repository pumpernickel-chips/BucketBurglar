import java.awt.*;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.*;
import java.util.List;
/**
 * An implementation of GameEntity that represents a Room and its contents, such as position, {@code Treasure}, and paths.
 * @author Yuliia Synytska
 * @author John Beaudry
 */
public class Room implements GameEntity{
    private Dimension roomSize;
    private int roomNumber;
    private Rectangle2D roomSprite;
    /** The absolute coordinates at the top-left corner of the {@code Room}*/
    private Point2D roomPosition;
    /** The absolute coordinates at the center of the {@code Room}, offset slightly to align with the entry corridor */
    private Point2D offsetOrigin;
    /** The absolute coordinates of the Room's entrance and its offsetOrigin */
    private Point2D[] pathNodes;
    private Deque<String> lootKeys;
    /**
     * Default zero-args constructor. Passes default position, dimensions, and node to complete constructor.
     * */
    public Room() {
        this(256, 256, 128, 128, 0);
    }
    /**
     * Parameterized constructor
     * @param posX x-axis positioning
     * @param posY y-axis positioning
     * @param width room width
     * @param height room height
     * @param node room number
     */
     public Room(int posX, int posY, int width, int height, int node) {
        this.roomSize = new Dimension(width, height);
        this.roomPosition = new Point2D.Double(posX, posY);
        this.roomSprite = new Rectangle2D.Double(roomPosition.getX(), roomPosition.getY(), roomSize.width, roomSize.height);
        this.offsetOrigin = new Point2D.Double(this.roomSprite.getCenterX()-12, this.roomSprite.getCenterY()-12);
        this.roomNumber = node;
        this.lootKeys = new ArrayDeque<>();
        /*
         * these conditionals determine whether to align the offsetOrigin by the x- or y-value of the entrance
         */
        boolean vertical = node == 0 || node == 1 || node == 2 || node == 5 || node == 6 || node == 7;
        boolean horizontal = node == 3 || node == 4 || node == 8 || node == 9;
        this.offsetOrigin = new Point2D.Double( vertical? FloorPlan.pathNodes[node].getX() : this.roomSprite.getCenterX()-12,
                                              horizontal? FloorPlan.pathNodes[node].getY() : this.roomSprite.getCenterY()-12);
        this.pathNodes = new Point2D[]{FloorPlan.pathNodes[node], offsetOrigin};
    }
    /**
     * Method to generate treasure in random quantity (max 12) with shuffled positions.
     */
    public void generateLoot() {
        Random rand = new Random();

        List<Point2D> lootPositions = new ArrayList<Point2D>(Arrays.asList(
                // corners
                new Point2D.Double(roomPosition.getX() + (12 * GUI.scale), roomPosition.getY() + (12 * GUI.scale)),
                new Point2D.Double(roomPosition.getX() + roomSize.getWidth() - (36 * GUI.scale), roomPosition.getY() + (12 * GUI.scale)),
                new Point2D.Double(roomPosition.getX() + (12 * GUI.scale), roomPosition.getY() + roomSize.getHeight() - (36 * GUI.scale)),
                new Point2D.Double(roomPosition.getX() + roomSize.getWidth() - (36 * GUI.scale), roomPosition.getY() + roomSize.getHeight() - (36 * GUI.scale)),
                // cardinals
                new Point2D.Double(roomPosition.getX() + roomSize.getWidth()*.5 - (12 * GUI.scale), roomPosition.getY() + (12 * GUI.scale)),
                new Point2D.Double(roomPosition.getX() + (12 * GUI.scale), roomPosition.getY() + roomSize.getHeight()*.5 - (12 * GUI.scale)),
                new Point2D.Double(roomPosition.getX() + roomSize.getWidth()*.5 - (12 * GUI.scale), roomPosition.getY() + roomSize.getHeight() - (36 * GUI.scale)),
                new Point2D.Double(roomPosition.getX() + roomSize.getWidth() - (36 * GUI.scale), roomPosition.getY() + roomSize.getHeight()*.5 - (12 * GUI.scale)),
                // inner corners
                new Point2D.Double(roomPosition.getX() + roomSize.getWidth()*.7 - (12 * GUI.scale), roomPosition.getY() + roomSize.getHeight()*.3 - (12 * GUI.scale)),
                new Point2D.Double(roomPosition.getX() + roomSize.getWidth()*.3 - (12 * GUI.scale), roomPosition.getY() + roomSize.getHeight()*.7 - (12 * GUI.scale)),
                new Point2D.Double(roomPosition.getX() + roomSize.getWidth()*.3 - (12 * GUI.scale), roomPosition.getY() + roomSize.getHeight()*.3 - (12 * GUI.scale)),
                new Point2D.Double(roomPosition.getX() + roomSize.getWidth()*.7 - (12 * GUI.scale), roomPosition.getY() + roomSize.getHeight()*.7 - (12 * GUI.scale))
        )
        );
        Collections.shuffle(lootPositions);
        int maxLoot = (int)(lootPositions.size()*.2) + rand.nextInt((int)(lootPositions.size()*.5));
        int keyIndex = 0;
        do {
            Treasure treasure = new Treasure((int) lootPositions.get(keyIndex).getX(), (int) lootPositions.get(keyIndex).getY(), keyIndex);
            String lootKey = "Room " + roomNumber + ", Treasure " + keyIndex;
            lootKeys.add(lootKey);
            HashGame.entities.put(lootKey, (Treasure) treasure);
            keyIndex++;
        } while (keyIndex < maxLoot);
    }
    /**
     * Accesses keys to all Treasures in this Room that are stored in the EntityTable
     * @return an ArrayDeque of String-type keys
     */
    public Deque<String> getLootKeys() {
        return lootKeys;
    }
    /**
     * Allows a Player "claim" a Treasure before collecting it to prevent other Players from accessing it (and throwing
     * errors). It either takes the first or last lootKey and puts it back if claimed before returning and empty String.
     * @return key to a random Treasure in the Room
     * */
    public String claimRandomLootKey(){
        if(!lootKeys.isEmpty()) {
            String key = ((new Random().nextBoolean()) ? lootKeys.pollLast() : lootKeys.poll());
            if(!((Treasure) (HashGame.entities.get(key))).isClaimed()){
                ((Treasure) (HashGame.entities.get(key))).setClaimed(true);
                return key;
            }else{
                lootKeys.push(key);
            }
        }
        return "";
    }
    public void removeLootKey(String keyToRemove){
        this.lootKeys.remove(keyToRemove);
    }
    public int getRoomNumber() {
        return roomNumber;
    }
    public Point2D[] getPathNodes() {
        return pathNodes;
    }
    @Override
    public boolean moveSprite(double stepInterval) {
        return false;
    }
    @Override
    public Rectangle2D getSprite() {
        return this.roomSprite;
    }
    /**
     * Overridden {@code equals()} method
     * @param o object to compare
     * @return returns true or false
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Room)) return false;
        Room room = (Room) o;
        return Objects.equals(roomSize, room.roomSize) && Objects.equals(offsetOrigin, room.offsetOrigin);
    }
    /**
     * Overridden {@code hashCode} method. Uses {@code roomSize}, {@code pathNodes}, and {@code offsetOrigin} since
     * these attributes are unique and will never change during gameplay.
     * @return {@code int} hash
     */
    @Override
    public int hashCode() {
        return Objects.hash(roomSize, Arrays.hashCode(pathNodes), offsetOrigin);
    }
}
