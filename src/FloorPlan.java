import javax.swing.*;
import java.awt.*;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.*;
import java.util.List;

public class FloorPlan extends JPanel {

    public static final int rooms = 10;
    private static Dimension hall;
    public static Point2D[] hallNodes;
    public static Point2D[] pathNodes;
    public static int NEc = 0, Nv = 1, NWc = 2, NWh = 3, SWh = 4, SWc = 5, Sv = 6, SEc = 7, SEh = 8, NEh = 9;
    private static Point2D[] roomPositions;
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
        arrangeHallways();
        arrangeRooms();
        setReady(true);
    }
    public void arrangeRooms(){
        //create rooms
        Random rand = new Random();
        for(int i = 0; i < rooms; i++) {
            Room room = new Room((int) roomPositions[i].getX(),(int) roomPositions[i].getY(),
                    256-rand.nextInt(64), 200-rand.nextInt(64), i, true);
            String key = "Room " + i;
            entities.put(key, (Room) room);
            roomKeys.add(key);
        }
    }
    public void arrangeHallways(){
        hallSprites = new Rectangle2D.Double[]{
            // N main circuit
            new Rectangle2D.Double(hallNodes[NWc].getX(), hallNodes[NWc].getY(),hall.width,48),
            // E main circuit
            new Rectangle2D.Double(hallNodes[NWc].getX(), hallNodes[NWc].getY(),48,hall.height),
            // S main circuit
            new Rectangle2D.Double(hallNodes[SWc].getX(), hallNodes[SWc].getY()-48,hall.width,48),
            // W main circuit
            new Rectangle2D.Double(hallNodes[NEc].getX()-48, hallNodes[NEc].getY(),48,hall.height),

            //the following are small corridors connecting rooms to the main circuit

            // NE vertical
            new Rectangle2D.Double(hallNodes[NEc].getX()-48, hallNodes[NEc].getY()-128,48,128),
            // N vertical
            new Rectangle2D.Double(hallNodes[NEc].getX()-(hall.width*.5)-24, hallNodes[NEc].getY()-128,48,128),
            // NW vertical
            new Rectangle2D.Double(hallNodes[NWc].getX(), hallNodes[NEc].getY()-128,48,128),

            // NW horizontal
            new Rectangle2D.Double(hallNodes[NWc].getX()-128, hallNodes[NWc].getY()+136,128,48),
            // SW horizontal
            new Rectangle2D.Double(hallNodes[SWc].getX()-128, hallNodes[SWc].getY()-184,128,48),

            // SW vertical
            new Rectangle2D.Double(hallNodes[SEc].getX()-48, hallNodes[SEc].getY(),48,128),
            // S vertical
            new Rectangle2D.Double(hallNodes[SEc].getX()-(hall.width*.5)-24, hallNodes[SEc].getY(),48,128),
            // SE vertical
            new Rectangle2D.Double(hallNodes[SWc].getX(), hallNodes[SEc].getY(),48,128),

            // SE horizontal
            new Rectangle2D.Double(hallNodes[SEc].getX(), hallNodes[SEc].getY()-184,128,48),
            // NE horizontal
            new Rectangle2D.Double(hallNodes[NEc].getX(), hallNodes[NEc].getY()+136,128,48)
        };
    }
    public static Dimension getHall() {
        return hall;
    }
    public static void setHall(Dimension hall) {
        FloorPlan.hall = hall;
        if(GUI.screenSize != null){
            FloorPlan.hallNodes = new Point2D[]{
                //NEc
                new Point2D.Double(GUI.origin.getX()+(hall.width*.5), GUI.origin.getY()-(hall.height*.5)),
                //Nv
                new Point2D.Double(GUI.origin.getX(), GUI.origin.getY()-(hall.height*.5)),
                //NWc
                new Point2D.Double(GUI.origin.getX()-(hall.width*.5), GUI.origin.getY()-(hall.height*.5)),
                //NWh
                new Point2D.Double(GUI.origin.getX()-(hall.width*.5), GUI.origin.getY()-(hall.height*.5)+136),
                //SWh
                new Point2D.Double(GUI.origin.getX()-(hall.width*.5), GUI.origin.getY()+(hall.height*.5)-184),
                //SWc
                new Point2D.Double(GUI.origin.getX()-(hall.width*.5), GUI.origin.getY()+(hall.height*.5)),
                //Sv
                new Point2D.Double(GUI.origin.getX(), GUI.origin.getY()+(hall.height*.5)),
                //SEc
                new Point2D.Double(GUI.origin.getX()+(hall.width*.5), GUI.origin.getY()+(hall.height*.5)),
                //SEh
                new Point2D.Double(GUI.origin.getX()+(hall.width*.5), GUI.origin.getY()+(hall.height*.5)-184),
                //NEh
                new Point2D.Double(GUI.origin.getX()+(hall.width*.5), GUI.origin.getY()-(hall.height*.5)+136)
            };
            FloorPlan.pathNodes = new Point2D[]{
                    //NEc
                    new Point2D.Double(hallNodes[NEc].getX()-36, hallNodes[NEc].getY()+12),
                    //Nv
                    new Point2D.Double(hallNodes[Nv].getX()-12, hallNodes[Nv].getY()+12),
                    //NWc
                    new Point2D.Double(hallNodes[NWc].getX()+12, hallNodes[NWc].getY()+12),
                    //NWh
                    new Point2D.Double(hallNodes[NWh].getX()+12, hallNodes[NWh].getY()+12),
                    //SWh
                    new Point2D.Double(hallNodes[SWh].getX()+12, hallNodes[SWh].getY()+12),
                    //SWc
                    new Point2D.Double(hallNodes[SWc].getX()+12, hallNodes[SWc].getY()-36),
                    //Sv
                    new Point2D.Double(hallNodes[Sv].getX()-12, hallNodes[Sv].getY()-36),
                    //SEc
                    new Point2D.Double(hallNodes[SEc].getX()-36, hallNodes[SEc].getY()-36),
                    //SEh
                    new Point2D.Double(hallNodes[SEh].getX()-36, hallNodes[SEh].getY()+12),
                    //NEh
                    new Point2D.Double(hallNodes[NEh].getX()-36, hallNodes[NEh].getY()+12)
            };
            roomPositions = new Point2D[]{
                    //top
                    new Point2D.Double(hallNodes[NEc].getX()-128, hallNodes[NEc].getY()-256),
                    new Point2D.Double(hallNodes[NEc].getX()-(hall.width*.5)-100, hallNodes[NEc].getY()-256),
                    new Point2D.Double(hallNodes[NWc].getX()-64, hallNodes[NWc].getY()-256),
                    //left
                    new Point2D.Double(hallNodes[NWc].getX()-300, hallNodes[SWc].getY()-(hall.height*.88)),
                    new Point2D.Double(hallNodes[NWc].getX()-300, hallNodes[SWc].getY()-(hall.height*.44)),
                    //bottom
                    new Point2D.Double(hallNodes[SWc].getX()-100, hallNodes[SWc].getY()+64),
                    new Point2D.Double(hallNodes[SEc].getX()-(hall.width*.5)-128, hallNodes[SEc].getY()+64),
                    new Point2D.Double(hallNodes[SEc].getX()-128, hallNodes[SEc].getY()+64),
                    //right
                    new Point2D.Double(hallNodes[NEc].getX()+64, hallNodes[SEc].getY()-(hall.height*.44)),
                    new Point2D.Double(hallNodes[NEc].getX()+64, hallNodes[SEc].getY()-(hall.height*.88))
            };
            /*for(Point2D pt : roomOffsets){
                System.out.println("(" + (int) pt.getX() + ", " + (int)pt.getY() + ")");
            }*/
        }
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
        gx.setColor(Color.LIGHT_GRAY);//don't remove
        for(Rectangle2D corridor : hallSprites) gx.fill(corridor);
        for(String key : roomKeys){
            gx.fill(((Room)(entities.get(key))).getRoomSprite());
            gx.setColor(Color.GREEN);
            gx.fill(new Rectangle2D.Double((((Room)(entities.get(key))).getPathNodes()[0].getX()), ((Room)(entities.get(key))).getPathNodes()[0].getY(), 24, 24));
            gx.setColor(Color.BLUE);
            gx.fill(new Rectangle2D.Double(((Room)(entities.get(key))).getPathNodes()[1].getX(), ((Room)(entities.get(key))).getPathNodes()[1].getY(), 24, 24));
            gx.setColor(Color.LIGHT_GRAY);//temp
        }
        gx.setColor(Color.RED);
        for(Point2D node : hallNodes){
            gx.fill(new Rectangle2D.Double(node.getX(),node.getY(), 4,4));
        }
    }
}
