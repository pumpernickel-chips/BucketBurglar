

import java.util.HashMap;

public class EntityTable extends HashMap<Integer, GameEntity>{
    private boolean gameStarted;
    private int roomCount, playerCount, treasureCount, baseTrapCount, levelTrapCount;
    public EntityTable(){
        this(false, 5);
    }
    public EntityTable(boolean gameStarted, int baseTrapCount){
        super();
        this.gameStarted = gameStarted;
        this.baseTrapCount = baseTrapCount;
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
