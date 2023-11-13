import javax.swing.*;
import java.awt.*;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.*;
import java.util.List;

public class FloorPlan extends JPanel {

    public static final int rooms = 10;
    private static Dimension hall;
    private static Point2D cornerNE, cornerNW, cornerSW, cornerSE;
    private static Point2D[] roomOffsets;
    private boolean ready;
    private EntityTable entities;
    private List<String> roomKeys;
    private Rectangle2D[] hallSprites;
    public FloorPlan(){
        this(new EntityTable());
    }
    public FloorPlan(EntityTable entities){
        super(null, true);
        this.setBackground(GUI.intelliJGray);
        this.ready = false;
        this.entities = entities;
        this.roomKeys = new LinkedList<>();
    }
    public void buildLevel(){
        arrangeRooms();
        arrangeHallways();
        setReady(true);
    }
    public void arrangeRooms(){
        //create rooms
        Random rand = new Random();
        for(int i = 0; i < rooms; i++) {
            Room room = new Room((int) roomOffsets[i].getX(),(int) roomOffsets[i].getY(),
                    256-rand.nextInt(64), 200-rand.nextInt(64), true);
            String key = "Room " + i;
            entities.put(key, (Room) room);
            roomKeys.add(key);
        }
    }
    //TODO: CORRIDORS CONNECTING MAIN HALLWAY CIRCUIT TO ROOMS
    public void arrangeHallways(){
        hallSprites = new Rectangle2D.Double[]{
                //main circuit
                new Rectangle2D.Double(cornerNW.getX(),cornerNW.getY(),hall.width,64),
                new Rectangle2D.Double(cornerNW.getX(),cornerNW.getY(),64,hall.height),
                new Rectangle2D.Double(cornerSW.getX(),cornerSW.getY()-64,hall.width,64),
                new Rectangle2D.Double(cornerNE.getX()-64,cornerNE.getY(),64,hall.height)
                //top room corridors
        };
    }
    public static Dimension getHall() {
        return hall;
    }

    public static void setHall(Dimension hall) {
        FloorPlan.hall = hall;
        if(GUI.screenSize != null){
            cornerNE = new Point2D.Double(GUI.origin.getX()+(hall.width/2.), GUI.origin.getY()-(hall.height/2.));
            cornerNW = new Point2D.Double(GUI.origin.getX()-(hall.width/2.), GUI.origin.getY()-(hall.height/2.));
            cornerSW = new Point2D.Double(GUI.origin.getX()-(hall.width/2.), GUI.origin.getY()+(hall.height/2.));
            cornerSE = new Point2D.Double(GUI.origin.getX()+(hall.width/2.), GUI.origin.getY()+(hall.height/2.));
            roomOffsets = new Point2D[]{
                    //top
                    new Point2D.Double(cornerNE.getX(), cornerNE.getY()-256),
                    new Point2D.Double(cornerNE.getX()-(hall.width*.5)-128, cornerNE.getY()-256),
                    new Point2D.Double(cornerNW.getX()-256, cornerNW.getY()-256),
                    //left
                    new Point2D.Double(cornerNW.getX()-300, cornerSW.getY()-(hall.height*.9)),
                    new Point2D.Double(cornerNW.getX()-300, cornerSW.getY()-(hall.height*.4)),
                    //bottom
                    new Point2D.Double(cornerSW.getX()-256, cornerSW.getY()+64),
                    new Point2D.Double(cornerSE.getX()-(hall.width*.5)-128, cornerSE.getY()+64),
                    new Point2D.Double(cornerSE.getX()-64, cornerSE.getY()+64),
                    //right
                    new Point2D.Double(cornerNE.getX()+64, cornerSE.getY()-(hall.height*.4)),
                    new Point2D.Double(cornerNE.getX()+64, cornerSE.getY()-(hall.height*.9))
            };
            for(Point2D pt : roomOffsets){
                System.out.println("(" + (int) pt.getX() + ", " + (int)pt.getY() + ")");
            }
        }
    }
    public static Point2D getCorner(int corner) {
        switch(corner){
            case 0:
                return cornerNE;
            case 1:
                return cornerNW;
            case 2:
                return cornerSW;
            case 3:
                return cornerSE;
        }
        return null;
    }
    public List<String> getRoomKeys() {
        return roomKeys;
    }
    public void setRoomKeys(List<String> roomKeys) {
        this.roomKeys = roomKeys;
    }
    public boolean isReady() {
        return ready;
    }
    public void setReady(boolean ready) {
        this.ready = ready;
    }
    public EntityTable getEntities() {
        return entities;
    }
    public void setEntities(EntityTable entities) {
        this.entities = entities;
    }
    @Override
    public void paintComponent(Graphics g){
        Graphics2D gx = (Graphics2D) g;
        super.paintComponent(gx);
        gx.setColor(Color.LIGHT_GRAY);
        gx.setStroke(new BasicStroke(64, BasicStroke.CAP_SQUARE, BasicStroke.JOIN_MITER));
        for(String key : roomKeys){
            gx.fill(((Room)(entities.get(key))).getRoomSprite());
        }
        for(Rectangle2D corridor : hallSprites) gx.fill(corridor);
    }
}
