import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * @author Naomi Coakley
 */
public class HashGame {
    public HashGame(){
        GUI g = new GUI();
        EntityTable ents = new EntityTable();
        GameMap map = new GameMap(ents);
        Iterator<Map.Entry<Integer, GameEntity>> itEnt = ents.entrySet().iterator();
        while(itEnt.hasNext()){
            GameEntity p = itEnt.next().getValue();
            if(p instanceof Player){
                ((Player) p).decidePath(ents.getRoomCount(), ents);
                System.out.println(((Player) p).getRoomKeys());
            }
        }
    }
    public static void main(String[] args){
        HashGame test = new HashGame();
    }
    public void createPlayers(){}
    public void movePlayer(int posX, int posY){}

    public void initializeMap(){}
}
