import java.awt.*;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.Objects;
/**
 * @author Yuliia Synytska
 * @author John Beaudry
 */
public class Room implements GameEntity{
    public static Dimension maxSize;
    private Dimension roomSize;
    private boolean isEmpty;
    private Treasure loot;
    private Rectangle2D roomSprite;
    private Point2D[] pathNodes;
    /** The absolute coordinates at the center of the {@code Room} */
    private Point2D offsetOrigin;
    private Point2D position;
    private ArrayList<Player> players;
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
        this.position = new Point2D.Double(posX, posY);
        this.players = new ArrayList<Player>();
        this.roomSprite = new Rectangle2D.Double(position.getX(), position.getY(), roomSize.width, roomSize.height);
        this.offsetOrigin = new Point2D.Double(this.roomSprite.getCenterX()-12, this.roomSprite.getCenterY()-12);
        boolean vertical = node == 0 || node == 1 || node == 2 || node == 5 || node == 6 || node == 7;
        boolean horizontal = node == 3 || node == 4 || node == 8 || node == 9;
        this.offsetOrigin = new Point2D.Double( vertical? FloorPlan.pathNodes[node].getX() : this.roomSprite.getCenterX()-12,
                                              horizontal? FloorPlan.pathNodes[node].getY() : this.roomSprite.getCenterY()-12);
        this.pathNodes = new Point2D[]{FloorPlan.pathNodes[node], offsetOrigin};
    }
    /**
     * Method to add player to room
     * @param p
     */
    public void addPlayer(Player p){
        players.add(p);
    }
    /**
     * Remove player from room
     * @param name name of player to remove
     */
    public void deletePlayer(String name){
        for (int a = 0; a < players.size(); a++) {
            if(players.get(a).getName().equalsIgnoreCase(name)){
                players.remove(a);
                return;
            }
        }
    }
    /**
     * Returns {@code ArrayList} players which contains list of players in rooms
     * @return
     */
    public ArrayList<Player> getPlayers(){
        return players;
    }
    /**
     * Method to generate treasure
     */
    public void generateLoot() {
        int x = (int) ((Math.random() * (roomSize.width - offsetOrigin.getX())) + offsetOrigin.getX());
        int y = (int) ((Math.random() * (roomSize.height - offsetOrigin.getY())) + offsetOrigin.getY());
        loot = new Treasure(x, y);
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
    public Treasure getLoot() {
        return loot;
    }
    /**
     * Sets treasure
     * @param loot {@link Treasure}
     */
    public void setLoot(Treasure loot) {
        this.loot = loot;
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
    /**
     * Sets {@code Arraylist<Player>} player
     * @param players
     */

    public void setPlayers(ArrayList<Player> players) {
        this.players = players;
    }
    public Rectangle2D getRoomSprite() {
        return roomSprite;
    }
    public void setRoomSprite(Rectangle2D roomSprite) {
        this.roomSprite = roomSprite;
    }
    @Override
    public void store() {
    }
    @Override
    public Rectangle2D getSprite() {
        return this.roomSprite;
    }
    @Override
    public void remove() {
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
