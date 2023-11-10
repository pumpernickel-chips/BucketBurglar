import java.util.Iterator;
import java.util.Map;

/**
 * @author Naomi Coakley
 */
public class HashGame {
    public static final int STAY = 0, NORTH = 1, EAST = 2, SOUTH = 3, WEST = 4, N_EAST = 5, N_WEST = 6, S_EAST = 7, S_WEST = 8;
    public static final int[] DIRECTIONS = new int[]{0, 1 ,2 , 3, 4, 5, 6, 7, 8};

    private EntityTable entities;
    private FloorPlan level;
    private Iterator<Map.Entry<Integer, GameEntity>> iterEnt;
    public HashGame(){
        GUI g = new GUI();
        this.entities = new EntityTable();
        level = new FloorPlan(entities);
        iterEnt = entities.entrySet().iterator();
        while(iterEnt.hasNext()){
            GameEntity p = iterEnt.next().getValue();
            if(p instanceof Player){
                ((Player) p).decidePath(entities.getRoomCount(), entities);
                System.out.println(((Player) p).getRoomKeys());
            }
        }
    }
    public static void main(String[] args){
        HashGame test = new HashGame();
    }
    public void trapTreasures(){
        while(iterEnt.hasNext()){
            GameEntity t = iterEnt.next().getValue();
            if(t instanceof Treasure){
                ((Treasure) t).setBoobyTrapped(true);//method to be written in Treasure
            }
        }
    }
    public void createPlayers(){}
    public void movePlayer(int posX, int posY, int direction){

    }

    public void initializeMap(){}
}
