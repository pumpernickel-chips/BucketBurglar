import javax.swing.*;

public class FloorPlan extends JPanel {
    private EntityTable entities;
    public FloorPlan(){
        this(new EntityTable());
    }
    public FloorPlan(EntityTable entities){
        super(true);

    }
}
