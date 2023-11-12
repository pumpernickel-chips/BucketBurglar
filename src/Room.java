import java.awt.*;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Objects;

/**
 * @author Yuliia Synytska
 */

public class Room implements GameEntity{
    private Dimension roomSize;
    private boolean isEmpty;
    private Treasure loot;
    /** The absolute coordinates at the center of the {@code Room} */
    private Point2D origin;
    private ArrayList<Player> players;

    /**
     * Default zero-args constructor, passes default title to complete constructor
     * */
    public Room() {
        this(256, 256, 128, 128, true);
    }

    /**
     * Complete constructor
     * @param posX x-axis positioning
     * @param posY y-axis positioning
     * @param width room width
     * @param height room height
     * @param isEmpty does room have player in it
     */
     public Room(int posX, int posY, int width, int height, boolean isEmpty) {
        this.roomSize = new Dimension(width, height);
        this.isEmpty = isEmpty;
        this.origin = new Point(posX, posY);
        players = new ArrayList<Player>();
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
        int x = (int) ((Math.random() * (roomSize.width - origin.getX())) + origin.getX());
        int y = (int) ((Math.random() * (roomSize.height - origin.getY())) + origin.getY());
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
    public Point2D getOrigin() {
        return origin;
    }

    /**
     * Sets origin
     * @param origin {@code Point2D}
     */
    public void setOrigin(Point2D origin) {
        this.origin = origin;
    }

    /**
     * Sets {@code Arraylist<Player>} player
     * @param players
     */
    public void setPlayers(ArrayList<Player> players) {
        this.players = players;
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
        return isEmpty == room.isEmpty && Objects.equals(roomSize, room.roomSize) && Objects.equals(origin, room.origin);
    }

    /**
     * Overridden {@code hashCode} method
     * @return {@code int} hash
     */
    @Override
    public int hashCode() {
        return Objects.hash(roomSize, isEmpty, origin);
    }
}
