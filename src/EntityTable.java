

import java.util.HashMap;
import java.util.Random;

/**
 * The EntityTable class represents a table of game entities, extending HashMap
 * with integer keys and GameEntity values. It includes methods to manage the
 * state of the game entities, such as generating treasure and booby trap maps.
 */
public class EntityTable extends HashMap<Integer, GameEntity>{
    private boolean gameStarted;
    private HashMap<Integer, Integer> treasureMap;
    private int roomCount, playerCount, treasureCount, baseTrapCount, levelTrapCount;

    /**
     * Constructs an EntityTable with default values for gameStarted (false) and
     * baseTrapCount (5).
     */
    public EntityTable(){
        this(false, 5);
    }

    /**
     * Constructs an EntityTable with the specified values for gameStarted and
     * baseTrapCount.
     *
     * @param gameStarted Indicates whether the game has started.
     * @param baseTrapCount The base count of traps in the game.
     */
    public EntityTable(boolean gameStarted, int baseTrapCount){
        super();
        this.gameStarted = gameStarted;
        this.baseTrapCount = baseTrapCount;
        this.treasureMap = generateTreasureMap();
    }

    /**
     * Generates a map of treasures for each room.
     *
     * @return A HashMap representing the mapping of room indices to treasure
     * values.
     */
    public HashMap<Integer, Integer> generateTreasureMap(){
        HashMap<Integer, Integer> map = new HashMap<>();
        int rooms = 20;
        int random = 0;
        Random r = new Random();
        for (int i = 0; i < rooms; i++){
            random = r.nextInt(5-0) + 0;
            map.put(i, random);
        }
        return map;
    }

    /**
     * Generates a map of booby traps for each room.
     *
     * @return A HashMap representing the mapping of room indices to booby trap
     * states.
     */
    public HashMap<Integer, Boolean> generateBoobyTrapsMap(){
        HashMap<Integer, Boolean> map = new HashMap<>();
        int rooms = 20;
        int random = 0;
        boolean boobyTrapped = false;
        Random r = new Random();
        for (int i = 0; i < rooms; i++){
            random = r.nextInt(1 - 0) + 0;
            if (random == 0) {
                boobyTrapped = false;
            } else {
                boobyTrapped = true;
            }
            map.put(i, boobyTrapped);
        }
        return map;
    }

    /**
     * Gets the treasure value for a specified room.
     *
     * @param room The index of the room.
     * @return The treasure value for the specified room.
     */
    public int getTreasureValue(int room){
        int val = treasureMap.get(room);
        return val;
    }

    /**
     * Checks if the game has started.
     *
     * @return True if the game has started, false otherwise.
     */
    public boolean isGameStarted() {
        return gameStarted;
    }

    /**
     * Sets the gameStarted flag to the specified value.
     *
     * @param gameStarted The new value for the gameStarted flag.
     */
    public void setGameStarted(boolean gameStarted) {
        this.gameStarted = gameStarted;
    }

    /**
     * Gets the total count of rooms in the game.
     *
     * @return The total count of rooms.
     */
    public int getRoomCount() {
        return roomCount;
    }

    /**
     * Sets the total count of rooms in the game.
     *
     * @param roomCount The new value for the total count of rooms.
     */
    public void setRoomCount(int roomCount) {
        this.roomCount = roomCount;
    }

    /**
     * Gets the total count of players in the game.
     *
     * @return The total count of players.
     */
    public int getPlayerCount() {
        return playerCount;
    }

    /**
     * Sets the total count of players in the game and updates the level trap
     * count.
     *
     * @param playerCount The new value for the total count of players.
     */
    public void setPlayerCount(int playerCount) {
        this.playerCount = playerCount;
        if(!isGameStarted()){
            setLevelTrapCount(baseTrapCount + ((baseTrapCount-2)*(playerCount-1)));
        }
    }

    /**
     * Gets the total count of treasures in the game.
     *
     * @return The total count of treasures.
     */
    public int getTreasureCount() {
        return treasureCount;
    }

    /**
     * Sets the total count of treasures in the game.
     *
     * @param treasureCount The new value for the total count of treasures.
     */
    public void setTreasureCount(int treasureCount) {
        this.treasureCount = treasureCount;
    }

    /**
     * Gets the base count of traps in the game.
     *
     * @return The base count of traps.
     */
    public int getBaseTrapCount() {
        return baseTrapCount;
    }

    /**
     * Sets the base count of traps in the game.
     *
     * @param baseTrapCount The new value for the base count of traps.
     */
    public void setBaseTrapCount(int baseTrapCount) {
        this.baseTrapCount = baseTrapCount;
    }

    /**
     * Gets the total count of traps in the game at the current level.
     *
     * @return The total count of traps at the current level.
     */
    public int getLevelTrapCount() {
        return levelTrapCount;
    }

    /**
     * Sets the total count of traps in the game at the current level.
     *
     * @param levelTrapCount The new value for the total count of traps at the
     * current level.
     */
    public void setLevelTrapCount(int levelTrapCount) {
        this.levelTrapCount = levelTrapCount;
    }

    /**
     * Overrides the put method to update counts when adding a Player or Room to
     * the EntityTable.
     *
     * @param key The key with which the specified value is to be associated.
     * @param value The value to be associated with the specified key.
     * @return The previous value associated with key, or null if there was no
     * mapping for key.
     */
    @Override
    public GameEntity put(Integer key, GameEntity value) {
        if(value instanceof Player){
            playerCount++;
        }else if (value instanceof Room){
            roomCount++;
        }
        return super.put(key, value);
    }
}
