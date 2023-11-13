import javax.swing.*;
import java.awt.*;
import java.awt.geom.Point2D;
import java.util.Iterator;
import java.util.Map;
import java.util.Random;

/**
 * The FloorPlan class represents the floor plan of the game, which includes
 * rooms and hallways. It extends JPanel for graphical rendering.
 */
public class FloorPlan extends JPanel {
    private boolean ready;
    private int minRooms;
    private EntityTable entities;
    private Iterator<Map.Entry<Integer, GameEntity>> iteRoom;
    public static Dimension hallCircuit;

    /**
     * Constructs a FloorPlan with the default minimum number of rooms (5) and
     * an empty EntityTable.
     */
    public FloorPlan(){
        this(5, new EntityTable());
    }

    /**
     * Constructs a FloorPlan with the specified minimum number of rooms and
     * EntityTable.
     *
     * @param minRooms The minimum number of rooms to generate in the floor
     * plan.
     * @param entities The EntityTable containing game entities (rooms and
     * players).
     */
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

    /**
     * Arranges the rooms in the floor plan.
     */
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

    /**
     * Arranges the hallways in the floor plan.
     */
    public void arrangeHallways(){

    }

    /**Checks if the floor plan is ready.
     * @return True if the floor plan is ready, false otherwise.
     */
    public boolean isReady() {
        return ready;
    }

    /**
     * Sets the readiness status of the floor plan.
     *
     * @param ready The new readiness status.
     */
    public void setReady(boolean ready) {
        this.ready = ready;
    }

    /**
     * Gets the minimum number of rooms in the floor plan.
     *
     * @return The minimum number of rooms.
     */
    public int getMinRooms() {
        return minRooms;
    }

    /**
     * Sets the minimum number of rooms in the floor plan.
     *
     * @param minRooms The new minimum number of rooms.
     */
    public void setMinRooms(int minRooms) {
        this.minRooms = minRooms;
    }

    /**
     * Gets the EntityTable containing game entities.
     *
     * @return The EntityTable.
     */
    public EntityTable getEntities() {
        return entities;
    }

    /**
     * Sets the EntityTable containing game entities.
     *
     * @param entities The new EntityTable.
     */
    public void setEntities(EntityTable entities) {
        this.entities = entities;
    }

    /**
     * Gets the iterator for rooms in the floor plan.
     *
     * @return The iterator for rooms.
     */
    public Iterator<Map.Entry<Integer, GameEntity>> getIteRoom() {
        return iteRoom;
    }

    /**
     * Sets the iterator for rooms in the floor plan.
     *
     * @param iteRoom The new iterator for rooms.
     */
    public void setIteRoom(Iterator<Map.Entry<Integer, GameEntity>> iteRoom) {
        this.iteRoom = iteRoom;
    }
}
