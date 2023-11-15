import java.awt.*;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.*;
import java.util.List;

/**
 * @author Yuliia Synytska
 * @author John Beaudry
 * @author Naomi Coakley
 */
public class Player implements GameEntity{
    private final static int out = 0, in = 1;
    private int currentScore;
    private final int sideLength = (int) (24*(GUI.scale != null? GUI.scale : 1));
    private String name;
    private Rectangle2D playerSprite;
    private Color playerColor;
    /**
     * {@code int} represents how much health a player has
     */
    private int health;
    /**
     * {@code Deque} containing path for player to take
     */
    private Deque<String> pathKeys;
    private Deque<Point2D> nextRoomNodes;
    private Room currentRoom;
    /**
     * {@code true} if player is out of the game
     */
    private boolean lost;
    private boolean advancing, entering, looting, exiting;
    /**
     * Default zero-args constructor, passes default player name, health, and an empty ArrayDeque
     * */
    public Player() {
        this("Player1", 3);
    }
    /**
     * Complete constructor
     * @param name player name
     * @param health player health
     */
    public Player(String name, int health) {
        this.playerSprite = new Rectangle2D.Double(0, 0, sideLength, sideLength);
        this.playerColor = new Color(142,140,140);
        this.currentScore = 0;// make final? maybe?
        this.name = name;
        this.health = health;
        this.pathKeys = new ArrayDeque<String>();
        this.nextRoomNodes = new ArrayDeque<Point2D>();
        this.currentRoom = null;
        this.lost = false;
        this.advancing = false;
        this.entering = false;
        this.looting = false;
        this.exiting = false;
    }
    /**
     * Method for player to join game
     */
    public void joinGame(){}
    /**
     * Method for player tp leave game at any time
     */
    public void leaveGame(){}
    /**
     * Method to get name of Player
     * @return name
     */
    public String getName(){
        return name;
    }
    /**
     * Method for the player to decide a path to move in/around the castle/rooms
     */
    public void decidePath(){
        int targetNumRooms = 0;
        switch(HashGame.entities.getPlayerCount()){
            case 1 :
                targetNumRooms = 8;
                break;
            case 2 :
                targetNumRooms = 7;
                break;
            case 3 :
                targetNumRooms = 5;
                break;
            case 4 :
                targetNumRooms = 4;
                break;
        }
        Random rand = new Random();
        System.out.println(HashGame.entities.getRoomCount());
        List<Integer> keys = new LinkedList<Integer>();
        for(int k = 0; k < HashGame.entities.getRoomCount(); k++) keys.add(k);
        for(int i = 0; i < HashGame.entities.getRoomCount()-targetNumRooms; i++){
            System.out.println(keys.size());
            keys.remove(rand.nextInt(keys.size()));
        }
        Collections.sort(keys);
        pathKeys = new ArrayDeque<>();
        for (int i = 0; i < Math.min(HashGame.entities.getRoomCount(), keys.size()); i++) {
            pathKeys.add("Room " + keys.get(i));

        }
    }
    /**
     * Method to get next room from roomKeys {@code Deque<Integer>}
     * @return {@code roomKeys.poll}
     */
    public Point2D getNextRoomNode() {
        if(nextRoomNodes.isEmpty() && !pathKeys.isEmpty()){
            setNextRoomNodes();
        }else{
            System.out.println("parsed int: " + Integer.parseInt(String.valueOf(this.getName().charAt(7))));
            return new Point2D.Double(GUI.windowOrigin.getX()-(4*sideLength)+(sideLength*(1.5*Integer.parseInt(String.valueOf(this.getName().charAt(7))))), GUI.windowOrigin.getY()-(2*sideLength));
        }
        return nextRoomNodes.pop();
    }
    public void setNextRoomNodes(){
        Room nextRoom = (Room) HashGame.entities.get(pathKeys.poll());
        nextRoomNodes.push(nextRoom.getPathNodes()[0]);
        nextRoomNodes.push(nextRoom.getPathNodes()[1]);
        nextRoomNodes.push(nextRoom.getPathNodes()[0]);
    }
    /**
     * Method to assess damage to player based on value passed to {@code damage}
     * @param damage damage to be assessed
     * @return {@code lost} if {@code boolean} lost is returned false, player is still in the game
     */
    public boolean takeDamage(int damage) {
        health -= damage;
        if (health <= 0) {
            lost = true;
        }
        return lost;
    }
    /**
     * Method to collect loot from {@link Room}
     */
    public void collectLoot() {
        if (currentRoom != null) {
            //method body tbd
        }
    }
    /**
     * Returns this players current score
     * @return currentScore current score
     */
    public int getCurrentScore() {
        return currentScore;
    }
    /**
     * Method to set current score
     * @param currentScore current score
     */
    public void setCurrentScore(int currentScore) {
        this.currentScore = currentScore;
    }
    /**
     * Method to set name of player
     * @param name player name
     */
    public void setName(String name) {
        this.name = name;
    }
    /**
     * Method returns remaining health of player
     * @return health remaining health
     */
    public int getHealth() {
        return health;
    }
    /**
     * Method to set players health
     * @param health remaining health
     */
    public void setHealth(int health) {
        this.health = health;
    }
    /**
     * Method returns {@code Deque} roomKeys containing the rooms
     * @return roomKeys
     */
    public Deque<String> getPathKeys() {
        return pathKeys;
    }
    /**
     * Method to set {@code Deque} roomKeys
     * @param pathKeys {@code Deque} roomKeys
     */
    public void setPathKeys(Deque<String> pathKeys) {
        this.pathKeys = pathKeys;
    }
    /**
     * Method to get room player in currently
     * @return currentRoom
     */
    public Room getCurrentRoom() {
        return currentRoom;
    }
    /**
     * Method to set room player in currently
     * @param currentRoom current room
     */
    public void setCurrentRoom(Room currentRoom) {
        this.currentRoom = currentRoom;
    }
    /**
     * Returns player sprite color
     * @return {@code playerColor}
     */
    public Color getPlayerColor(){
        return playerColor;
    }
    /**
     * Set player sprite color
     * @param c {@code Color}
     */
    public void setPlayerColor(Color c){
        playerColor = c;
    }
    /**
     * Returns player sprite
     * @return playerSprite
     */
    @Override
    public Rectangle2D getSprite() {
        return playerSprite;
    }
    /**
     * Sets {@code playerSprite}
     * @param playerSprite
     */
    public void setPlayerSprite(Rectangle2D playerSprite) {
        this.playerSprite = playerSprite;
    }
    /**
     * Returns if player is still in game
     * @return lost
     */
    public boolean isLost() {
        return lost;
    }
    /**
     * Sets whether player is still in game
     * @param lost {@code boolean}
     */
    public void setLost(boolean lost) {
        this.lost = lost;
    }
    @Override
    public boolean positionSprite(int distX, int distY) {
        /*Point2D startingPoint = new Point2D.Double(this.getSprite().getX(), this.getSprite().getY());

        if(startingPoint.distance(GUI.windowOrigin) == 0){

        }
        //below line just repositions the sprite 10 down and 10 right
        this.setPlayerSprite(new Rectangle2D.Double(this.getSprite().getX()+distX, this.getSprite().getY()+distY, sideLength*GUI.scale, sideLength*GUI.scale));
        */
        this.setPlayerSprite(new Rectangle2D.Double(this.getNextRoomNode().getX(), this.getNextRoomNode().getY(), sideLength, sideLength));
        return false;
    }
    public String getStatus(){
        if(advancing){
            return "advancing";
        }else if(entering){
            return "entering";
        }else if(looting){
            return "looting";
        }else if(exiting){
            return "exiting";
        }
        return "";
    }
}

