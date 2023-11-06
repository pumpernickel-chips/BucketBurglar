import java.awt.geom.Point2D;
/**
 * @author Naomi Coakley
 */
public class HashGame {
    public HashGame(){
        GUI g = new GUI();
        EntityTable ents = new EntityTable();
        GameMap map = new GameMap(ents);
    }
    public static void main(String[] args){
        HashGame test = new HashGame();
    }
    public void createPlayers(){}
    public void movePlayer(int posX, int posY){}

    public void initializeMap(){}
}
