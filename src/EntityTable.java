import java.util.HashMap;

public class EntityTable extends HashMap<String, GameEntity>{
    private boolean gameStarted;
    public EntityTable(){
        this(false);
    }
    public EntityTable(boolean gameStarted){
        this.gameStarted = gameStarted;
    }
}
