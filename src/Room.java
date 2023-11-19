import java.awt.*;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.*;
import java.util.List;

/**
 * @author Yuliia Synytska
 * @author John Beaudry
 */
public class Room implements GameEntity{
    private Dimension roomSize;
    private boolean isEmpty;
    private int roomNumber;

    private Rectangle2D roomSprite;
    private Point2D[] pathNodes;
    /** The absolute coordinates at the center of the {@code Room} */
    private Point2D offsetOrigin;
    private Point2D roomPosition;
    private Deque<String> lootKeys;
    /**
     * Default zero-args constructor, passes default title to complete constructor
     * */
    public Room() {
        this(256, 256, 128, 128, 0, true);
    }
    /**
     * Complete constructor
     * @param posX x-axis positioning
     * @param posY y-axis positioning
     * @param width room width
     * @param height room height
     * @param isEmpty does room have player in it
     */
     public Room(int posX, int posY, int width, int height, int node, boolean isEmpty) {
        this.roomSize = new Dimension(width, height);
        this.isEmpty = isEmpty;
        this.roomPosition = new Point2D.Double(posX, posY);
        this.roomSprite = new Rectangle2D.Double(roomPosition.getX(), roomPosition.getY(), roomSize.width, roomSize.height);
        this.offsetOrigin = new Point2D.Double(this.roomSprite.getCenterX()-12, this.roomSprite.getCenterY()-12);
        this.roomNumber = node;
        this.lootKeys = new ArrayDeque<>();
        boolean vertical = node == 0 || node == 1 || node == 2 || node == 5 || node == 6 || node == 7;
        boolean horizontal = node == 3 || node == 4 || node == 8 || node == 9;
        this.offsetOrigin = new Point2D.Double( vertical? FloorPlan.pathNodes[node].getX() : this.roomSprite.getCenterX()-12,
                                              horizontal? FloorPlan.pathNodes[node].getY() : this.roomSprite.getCenterY()-12);
        this.pathNodes = new Point2D[]{FloorPlan.pathNodes[node], offsetOrigin};
    }
    /**
     * Method to generate treasure
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
     * Returns size of room
     * @return roomSize
     */
    public Dimension getRoomSize() {
        return roomSize;
    }
    /**
     * Sets size of room
     * @param roomSize size of room
     */
    public void setRoomSize(Dimension roomSize) {
        this.roomSize = roomSize;
    }
    /**
     * Returns if room is empty
     * @return
     */
    public boolean isEmpty() {
        return isEmpty;
    }
    /**
     * Sets whether room is empty
     * @param empty
     */
    public void setEmpty(boolean empty) {
        isEmpty = empty;
    }
    /**
     * Returns treasure
     * @return {@link Treasure}
     */
    public Deque<String> getLootKeys() {
        return lootKeys;
    }
    public String claimRandomLootKey(){
        if(!lootKeys.isEmpty()) {
            String key = ((new Random().nextBoolean()) ? lootKeys.pollLast() : lootKeys.poll());
            ((Treasure) (HashGame.entities.get(key))).setClaimed(true);
            return key;
        }
        return "";
    }
    public void removeLootKey(String keyToRemove){
        this.lootKeys.remove(keyToRemove);
    }
    public int getRoomNumber() {
        return roomNumber;
    }
    /**
     * Returns origin
     * @return origin
     */
    public Point2D getOffsetOrigin() {
        return offsetOrigin;
    }
    /**
     * Sets origin
     * @param offsetOrigin {@code Point2D}
     */
    public void setOffsetOrigin(Point2D offsetOrigin) {
        this.offsetOrigin = offsetOrigin;
    }
    public Point2D[] getPathNodes() {
        return pathNodes;
    }
    public void setPathNodes(Point2D[] pathNodes) {
        this.pathNodes = pathNodes;
    }
    public void setRoomSprite(Rectangle2D roomSprite) {
        this.roomSprite = roomSprite;
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
     * @return returns result true or false
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Room)) return false;
        Room room = (Room) o;
        return isEmpty == room.isEmpty && Objects.equals(roomSize, room.roomSize) && Objects.equals(offsetOrigin, room.offsetOrigin);
    }
    /**
     * Overridden {@code hashCode} method
     * @return {@code int} hash
     */
    @Override
    public int hashCode() {
        return Objects.hash(roomSize, isEmpty, offsetOrigin);
    }
}
