import java.util.HashMap;
public class EntityTable extends HashMap<String, GameEntity>{
    private int roomCount, playerCount, treasureCount, levelTrapCount;
    public EntityTable(){
        this(false);
    }
    public EntityTable(boolean gameStarted){
        super();
        this.roomCount = 0;
        this.playerCount = 0;
        this.treasureCount = 0;
    }
    public int getRoomCount() {
        return roomCount;
    }
    public int getPlayerCount() {
        return playerCount;
    }
    public int getTreasureCount() {
        return this.treasureCount;
    }
    @Override
    public GameEntity put(String key, GameEntity value) {
        if (value instanceof Player) {
            playerCount++;
        } else if (value instanceof Room) {
            roomCount++;
        } else if (value instanceof Treasure) {
            treasureCount++;
        }
        return super.put(key, value);
    }
    @Override
    public GameEntity remove(Object key) {
        if(get(key) instanceof Player){
            playerCount--;
        }else if (get(key) instanceof Room){
            roomCount--;
        }
        else if (get(key) instanceof Treasure) {
            treasureCount--;
        }
        return super.remove(key);
    }
}
