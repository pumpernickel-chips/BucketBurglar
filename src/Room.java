

import java.awt.Dimension;
/**
 * @author Yuliia Synytska
 */
public class Room implements GameEntity{

    private Dimension dimension;
    private boolean isEmpty;

    //default method for Room
    public Room() {
        isEmpty = false;
    }
    //parameterized method to set class attributes for Room
    public Room(Dimension dimension, boolean isEmpty) {
        this.dimension = dimension;
        this.isEmpty = isEmpty;
    }
    //method to generate loot in a Room so that players can grab it
    public void generateLoot(){
    }
    @Override
    public void store() {
    }
    @Override
    public void retrieve() {
    }
    @Override
    public void remove() {
    }
}
