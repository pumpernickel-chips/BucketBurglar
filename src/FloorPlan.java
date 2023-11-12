import javax.swing.*;
import java.awt.*;
import java.awt.geom.Point2D;
import java.util.Iterator;
import java.util.Map;
import java.util.Random;

public class FloorPlan extends JPanel {
    private boolean ready;
    private int minRooms;
    private EntityTable entities;
    private Iterator<Map.Entry<Integer, GameEntity>> iteRoom;
    public static Dimension hallCircuit;
    public FloorPlan(){
        this(5, new EntityTable());
    }
    public FloorPlan(int minRooms, EntityTable entities){
        super(true);
        this.ready = false;
        this.minRooms = minRooms;
        this.entities = entities;
    }
    public void buildLevel(){
        arrangeHallways();
        arrangeRooms();

        setReady(true);
    }
    public void arrangeRooms(){
        //create rooms
        Random rand = new Random();
        int need = minRooms + rand.nextInt(minRooms);

        while(entities.getRoomCount() < need) {
            Room rm = new Room(1,1, 64+rand.nextInt(256), 32+rand.nextInt(128), true);

            entities.put(((Room) rm).hashCode(), rm);
        }
        //position rooms
        while(iteRoom.hasNext()){
            GameEntity e = iteRoom.next().getValue();
            if(e instanceof Room){
                Room r = (Room) e;
                Dimension s = r.getRoomSize();
                //r.setOrigin();
            }
        }
    }
    public void arrangeHallways(){

    }

    public boolean isReady() {
        return ready;
    }

    public void setReady(boolean ready) {
        this.ready = ready;
    }

    public int getMinRooms() {
        return minRooms;
    }

    public void setMinRooms(int minRooms) {
        this.minRooms = minRooms;
    }

    public EntityTable getEntities() {
        return entities;
    }

    public void setEntities(EntityTable entities) {
        this.entities = entities;
    }

    public Iterator<Map.Entry<Integer, GameEntity>> getIteRoom() {
        return iteRoom;
    }

    public void setIteRoom(Iterator<Map.Entry<Integer, GameEntity>> iteRoom) {
        this.iteRoom = iteRoom;
    }
}
