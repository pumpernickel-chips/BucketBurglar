

import java.util.HashMap;

public class EntityTable extends HashMap<Integer, GameEntity>{
    private boolean gameStarted;
    private int roomCount;
    private int playerCount;
    public EntityTable(){
        this(false);
    }
    public EntityTable(boolean gameStarted){
        super();
        this.gameStarted = gameStarted;
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
