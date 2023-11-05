import javax.swing.*;
import java.awt.*;
/**
 * @author Naomi Coakley
 */
public class GUI extends JPanel {
    JFrame frame;
    /**
     * Default zero-args constructor, passes default title to complete constructor
     * */
    public GUI() {
        this("Untitled Game");
    }
    /**
     * Complete constructor
     * @param name name of the window
     * */
    public GUI(String name){
        super(new GridBagLayout(), true);

        initializeGUI(name, 1280, 800);
    }

    public void initializeGUI(String name, int height, int width){
        frame = new JFrame(name);
        frame.setPreferredSize(new Dimension(height, width));
        this.setMinimumSize(new Dimension(height, width));
        this.setBackground(new Color(43, 43, 44, 255));
        frame.add(this);
        frame.setVisible(true);
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    public void showMenu(){}
    public void showMap(){}
}
