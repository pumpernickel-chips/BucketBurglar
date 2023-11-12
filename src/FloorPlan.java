import javax.swing.*;
import java.awt.*;
import java.awt.geom.Point2D;
import java.util.Iterator;
import java.util.Map;
import java.util.Random;

public class FloorPlan extends JPanel {
    private int minRooms;
    private EntityTable entities;
    private Iterator<Map.Entry<Integer, GameEntity>> iteRoom;
    public FloorPlan(){
        this(5, new EntityTable());
    }
    public FloorPlan(int minRooms, EntityTable entities){
        super(true);
        this.minRooms = minRooms;
        this.entities = entities;
        //generateRooms();
        //arrangeRooms();
        //drawHallways();
    }
    public void generateRooms(){
        Random rand = new Random();
        int need = minRooms + rand.nextInt(minRooms+2);
        Room.maxSize = new Dimension (321, 161);
        while(entities.getRoomCount() < need || rand.nextBoolean()) {
            Room rm = new Room(1,1, 64+rand.nextInt(256), 32+rand.nextInt(128), true);

            entities.put(((Room) rm).hashCode(), rm);
        }
    }
    public void arrangeRooms(){
        while(iteRoom.hasNext()){
            GameEntity e = iteRoom.next().getValue();
            if(e instanceof Room){
                Room r = (Room) e;
                Dimension s = r.getRoomSize();
                Point2D o = r.getOrigin();

            }
        }
    }
    public void drawHallways(){

    }
}
