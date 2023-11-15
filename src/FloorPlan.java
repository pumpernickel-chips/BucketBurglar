import javax.swing.*;
import java.awt.*;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.*;
import java.util.List;

public class FloorPlan extends JPanel {

    public static final int rooms = 10;
    private static Double hWidth;
    private static Double hOffset;
    private static Dimension hall;
    public static Point2D[] hallNodes;
    public static Point2D[] pathNodes;
    public static int NEc = 0, Nv = 1, NWc = 2, NWh = 3, SWh = 4, SWc = 5, Sv = 6, SEc = 7, SEh = 8, NEh = 9;
    private static Point2D[] roomPositions;
    private boolean ready;
    private List<String> roomKeys;
    private Deque<String> playerKeys;
    private Rectangle2D[] hallSprites;
    private boolean simulating;

    /*public FloorPlan(){
        this();
    }*/
    //TODO: come up with a parameter
    public FloorPlan(){
        super(null, true);
        hWidth = 48*GUI.scale;
        hOffset = 128*GUI.scale;
        setHall(new Dimension((int)(.5*GUI.h), (int)(.4*GUI.h)));
        this.setBackground(GUI.intelliJGray);
        this.ready = false;
        this.roomKeys = new LinkedList<>();
        this.playerKeys = new ArrayDeque<String>();
        this.simulating = false;
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
                    (int) ((256-rand.nextInt(64))*GUI.scale), (int) ((200-rand.nextInt(64))* GUI.scale), i, true);
            String key = "Room " + i;
            HashGame.entities.put(key, (Room) room);
            roomKeys.add(key);
            System.out.println(key + ": " + room.getRoomSize().width +"x"+ room.getRoomSize().height);

        }
    }
    public void arrangeHallways(){
        hallSprites = new Rectangle2D.Double[]{
            // N main circuit
            new Rectangle2D.Double(hallNodes[NWc].getX(), hallNodes[NWc].getY(),hall.width,hWidth),
            // E main circuit
            new Rectangle2D.Double(hallNodes[NWc].getX(), hallNodes[NWc].getY(),hWidth,hall.height),
            // S main circuit
            new Rectangle2D.Double(hallNodes[SWc].getX(), hallNodes[SWc].getY()-hWidth,hall.width,hWidth),
            // W main circuit
            new Rectangle2D.Double(hallNodes[NEc].getX()-hWidth, hallNodes[NEc].getY(),hWidth,hall.height),

            //the following are small corridors connecting rooms to the main circuit

            // NE vertical
            new Rectangle2D.Double(hallNodes[NEc].getX()-hWidth, hallNodes[NEc].getY()-hOffset,hWidth,hOffset),
            // N vertical
            new Rectangle2D.Double(hallNodes[NEc].getX()-(hall.width*.5)-hWidth*.5, hallNodes[NEc].getY()-hOffset,hWidth,hOffset),
            // NW vertical
            new Rectangle2D.Double(hallNodes[NWc].getX(), hallNodes[NEc].getY()-hOffset,hWidth,hOffset),

            // NW horizontal
            new Rectangle2D.Double(hallNodes[NWc].getX()-hOffset, hallNodes[NWc].getY()+(136*GUI.scale),hOffset,hWidth),
            // SW horizontal
            new Rectangle2D.Double(hallNodes[SWc].getX()-hOffset, hallNodes[SWc].getY()-(184*GUI.scale),hOffset,hWidth),

            // SW vertical
            new Rectangle2D.Double(hallNodes[SEc].getX()-hWidth, hallNodes[SEc].getY(),hWidth,hOffset),
            // S vertical
            new Rectangle2D.Double(hallNodes[SEc].getX()-(hall.width*.5)-(hWidth*.5), hallNodes[SEc].getY(),hWidth,hOffset),
            // SE vertical
            new Rectangle2D.Double(hallNodes[SWc].getX(), hallNodes[SEc].getY(),hWidth,hOffset),

            // SE horizontal
            new Rectangle2D.Double(hallNodes[SEc].getX(), hallNodes[SEc].getY()-(184*GUI.scale),hOffset,hWidth),
            // NE horizontal
            new Rectangle2D.Double(hallNodes[NEc].getX(), hallNodes[NEc].getY()+(136*GUI.scale),hOffset,hWidth)
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
                new Point2D.Double(GUI.windowOrigin.getX()+(hall.width*.5), GUI.windowOrigin.getY()-(hall.height*.5)),
                //Nv
                new Point2D.Double(GUI.windowOrigin.getX(), GUI.windowOrigin.getY()-(hall.height*.5)),
                //NWc
                new Point2D.Double(GUI.windowOrigin.getX()-(hall.width*.5), GUI.windowOrigin.getY()-(hall.height*.5)),
                //NWh
                new Point2D.Double(GUI.windowOrigin.getX()-(hall.width*.5), GUI.windowOrigin.getY()-(hall.height*.5)+(136*GUI.scale)),
                //SWh
                new Point2D.Double(GUI.windowOrigin.getX()-(hall.width*.5), GUI.windowOrigin.getY()+(hall.height*.5)-(184*GUI.scale)),
                //SWc
                new Point2D.Double(GUI.windowOrigin.getX()-(hall.width*.5), GUI.windowOrigin.getY()+(hall.height*.5)),
                //Sv
                new Point2D.Double(GUI.windowOrigin.getX(), GUI.windowOrigin.getY()+(hall.height*.5)),
                //SEc
                new Point2D.Double(GUI.windowOrigin.getX()+(hall.width*.5), GUI.windowOrigin.getY()+(hall.height*.5)),
                //SEh
                new Point2D.Double(GUI.windowOrigin.getX()+(hall.width*.5), GUI.windowOrigin.getY()+(hall.height*.5)-(184*GUI.scale)),
                //NEh
                new Point2D.Double(GUI.windowOrigin.getX()+(hall.width*.5), GUI.windowOrigin.getY()-(hall.height*.5)+136*(GUI.scale))
            };
            FloorPlan.pathNodes = new Point2D[]{
                    //NEc
                    new Point2D.Double(hallNodes[NEc].getX()-(hWidth*.75), hallNodes[NEc].getY()+(hWidth*.25)),
                    //Nv
                    new Point2D.Double(hallNodes[Nv].getX()-(hWidth*.25), hallNodes[Nv].getY()+(hWidth*.25)),
                    //NWc
                    new Point2D.Double(hallNodes[NWc].getX()+(hWidth*.25), hallNodes[NWc].getY()+(hWidth*.25)),
                    //NWh
                    new Point2D.Double(hallNodes[NWh].getX()+(hWidth*.25), hallNodes[NWh].getY()+(hWidth*.25)),
                    //SWh
                    new Point2D.Double(hallNodes[SWh].getX()+(hWidth*.25), hallNodes[SWh].getY()+(hWidth*.25)),
                    //SWc
                    new Point2D.Double(hallNodes[SWc].getX()+(hWidth*.25), hallNodes[SWc].getY()-(hWidth*.75)),
                    //Sv
                    new Point2D.Double(hallNodes[Sv].getX()-(hWidth*.25), hallNodes[Sv].getY()-(hWidth*.75)),
                    //SEc
                    new Point2D.Double(hallNodes[SEc].getX()-(hWidth*.75), hallNodes[SEc].getY()-(hWidth*.75)),
                    //SEh
                    new Point2D.Double(hallNodes[SEh].getX()-(hWidth*.75), hallNodes[SEh].getY()+(hWidth*.25)),
                    //NEh
                    new Point2D.Double(hallNodes[NEh].getX()-(hWidth*.75), hallNodes[NEh].getY()+(hWidth*.25))
            };
            roomPositions = new Point2D[]{
                    //top
                    new Point2D.Double(hallNodes[NEc].getX()-hOffset, hallNodes[NEc].getY()-(hOffset/.5)),
                    new Point2D.Double(hallNodes[NEc].getX()-(hall.width*.5)-(hOffset*0.78125), hallNodes[NEc].getY()-(hOffset/.5)),
                    new Point2D.Double(hallNodes[NWc].getX()-(hOffset*.5), hallNodes[NWc].getY()-(hOffset/.5)),
                    //left
                    new Point2D.Double(hallNodes[NWc].getX()-(hOffset*2.34375), hallNodes[SWc].getY()-(hall.height*.88)),
                    new Point2D.Double(hallNodes[NWc].getX()-(hOffset*2.34375), hallNodes[SWc].getY()-(hall.height*.44)),
                    //bottom
                    new Point2D.Double(hallNodes[SWc].getX()-(hOffset*0.78125), hallNodes[SWc].getY()+(hOffset*.5)),
                    new Point2D.Double(hallNodes[SEc].getX()-(hall.width*.5)-hOffset, hallNodes[SEc].getY()+(hOffset*.5)),
                    new Point2D.Double(hallNodes[SEc].getX()-hOffset, hallNodes[SEc].getY()+(hOffset*.5)),
                    //right
                    new Point2D.Double(hallNodes[NEc].getX()+(hOffset*.5), hallNodes[SEc].getY()-(hall.height*.44)),
                    new Point2D.Double(hallNodes[NEc].getX()+(hOffset*.5), hallNodes[SEc].getY()-(hall.height*.88))
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
    public Deque<String> getPlayerKeys() {
        return playerKeys;
    }
    public void setPlayerKeys(Deque<String> playerKeys) {
        this.playerKeys = playerKeys;
    }
    public boolean isReady() {
        return ready;
    }
    public void setReady(boolean ready) {
        this.ready = ready;
    }

    public boolean isSimulating() {
        return simulating;
    }
    public void setSimulating(boolean simulating) {
        this.simulating = simulating;
    }
    @Override
    public void paintComponent(Graphics g){
        Graphics2D gx = (Graphics2D) g;
        super.paintComponent(gx);
        gx.setColor(Color.LIGHT_GRAY);//don't remove
        for(Rectangle2D corridor : hallSprites) gx.fill(corridor);
        for(String key : roomKeys){
            gx.fill(((Room)(HashGame.entities.get(key))).getSprite());
            /*gx.setColor(Color.GREEN);
            gx.fill(new Rectangle2D.Double((((Room)(entities.get(key))).getPathNodes()[0].getX()), ((Room)(entities.get(key))).getPathNodes()[0].getY(), 24, 24));
            gx.setColor(Color.BLUE);
            gx.fill(new Rectangle2D.Double(((Room)(entities.get(key))).getPathNodes()[1].getX(), ((Room)(entities.get(key))).getPathNodes()[1].getY(), 24, 24));
            gx.setColor(Color.LIGHT_GRAY);//temp*/
        }
        /*gx.setColor(Color.RED);

        for(Point2D node : hallNodes){

            gx.fill(new Rectangle2D.Doub
            le(node.getX(),node.getY(), 4,4));
        }*/

        if(isReady() && isSimulating()){
            for(String key : getPlayerKeys()){
                Player sprite = (Player) HashGame.entities.get(key);
                gx.setColor(sprite.getPlayerColor());
                gx.fill(sprite.getSprite());
            }
        }

    }
}
