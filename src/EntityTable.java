

import java.util.HashMap;
import java.util.Random;

public class EntityTable extends HashMap<String, GameEntity>{
    private boolean gameStarted;
    private HashMap<Integer, Integer> treasureMap;
    private int roomCount, playerCount, treasureCount, baseTrapCount, levelTrapCount;
    public EntityTable(){
        this(false, 5);
    }
    public EntityTable(boolean gameStarted, int baseTrapCount){
        super();
        this.gameStarted = gameStarted;
        this.baseTrapCount = baseTrapCount;
        this.treasureMap = generateTreasureMap();
    }
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
    public int getTreasureValue(int room){
        int val = treasureMap.get(room);
        return val;
    }
    public boolean isGameStarted() {
        return gameStarted;
    }
    public void setGameStarted(boolean gameStarted) {
        this.gameStarted = gameStarted;
    }
    public int getRoomCount() {
        return roomCount;
    }
    public void setRoomCount(int roomCount) {
        this.roomCount = roomCount;
    }

    public int getPlayerCount() {
        return playerCount;
    }

    public void setPlayerCount(int playerCount) {
        this.playerCount = playerCount;
        if(!isGameStarted()){
            setLevelTrapCount(baseTrapCount + ((baseTrapCount-2)*(playerCount-1)));
        }
    }
    public int getTreasureCount() {
        return treasureCount;
    }
    public void setTreasureCount(int treasureCount) {
        this.treasureCount = treasureCount;
    }

    public int getBaseTrapCount() {
        return baseTrapCount;
    }

    public void setBaseTrapCount(int baseTrapCount) {
        this.baseTrapCount = baseTrapCount;
    }

    public int getLevelTrapCount() {
        return levelTrapCount;
    }

    public void setLevelTrapCount(int levelTrapCount) {
        this.levelTrapCount = levelTrapCount;
    }
    //TODO: fix probably?
    @Override
    public GameEntity put(String key, GameEntity value) {
        if(value instanceof Player){
            playerCount++;
        }else if (value instanceof Room){
            roomCount++;
        }
        return super.put(key, value);
    }
}
