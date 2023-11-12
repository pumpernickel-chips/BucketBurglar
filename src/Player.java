import java.util.*;

/**
 *
 *
 * @author Yuliia Synytska
 * @author John Beaudry
 * @author Naomi Coakley
 *
 *
 */
public class Player implements GameEntity{
    private int currentScore;
    private String name;
    /**
     * {@code int} represents how much health a player has
     */
    private int health;
    /**
     * {@code Deque} containing path for player to take
     */
    private Deque<Integer> roomKeys;
    private Room currentRoom;
    /**
     * {@code true} if player is out of the game
     */
    private boolean lost;
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
        this.currentScore = 0;// make final? maybe?
        this.name = name;
        this.health = health;
        this.roomKeys = roomKeys;
        this.currentRoom = null;
        this.lost = false;
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
    public void decidePath(int numberOfRooms, EntityTable allRooms){
        List<Integer> keysAsArray = new ArrayList<>(allRooms.keySet());
        Collections.shuffle(keysAsArray);
        roomKeys = new ArrayDeque<>();
        for (int i = 0; i < Math.min(numberOfRooms, keysAsArray.size()); i++) {
            roomKeys.add(keysAsArray.get(i));
        }
    }

    /**
     * Method to get next room from roomKeys {@code Deque<Integer>}
     * @return {@code roomKeys.poll}
     */
    public Integer getNextRoom() {
        return roomKeys.poll();
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
    public Deque<Integer> getRoomKeys() {
        return roomKeys;
    }

    /**
     * Method to set {@code Deque} roomKeys
     * @param roomKeys {@code Deque} roomKeys
     */
    public void setRoomKeys(Deque<Integer> roomKeys) {
        this.roomKeys = roomKeys;
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

