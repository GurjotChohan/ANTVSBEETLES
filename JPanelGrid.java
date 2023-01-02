import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import javax.swing.*;

public class JPanelGrid extends JPanel {
    private static final int SML_SIDE = 10;
    private static final int SIDE = SML_SIDE * SML_SIDE;
    private static final int GAP = 3;
    private static final Color BG = Color.BLACK;
    private static final Dimension BTN_PREF_SIZE = new Dimension(100, 70);
    private JButton[][] buttons = new JButton[SIDE][SIDE];


    public JPanelGrid(Creature grid[][]) {
        setBackground(BG);
        setLayout(new GridLayout(SML_SIDE, SML_SIDE, GAP, GAP));
        setBorder(BorderFactory.createEmptyBorder(GAP, GAP, GAP, GAP));

        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {

                ImageIcon icon = new ImageIcon("empty.jpeg");
                
      
                if(grid[i][j] instanceof Beetle)
                {
                    icon = new ImageIcon("beetle.png");
                }
                else if(grid[i][j] instanceof Ant)
                {
                    icon = new ImageIcon("ant.png");
                }
                buttons[i][j] = new JButton();
                buttons[i][j].setIcon(icon);
                buttons[i][j].setDisabledIcon(icon); 
              
                
                buttons[i][j].setPreferredSize(BTN_PREF_SIZE);
                add(buttons[i][j]);
            }
        }
        
    }



}
