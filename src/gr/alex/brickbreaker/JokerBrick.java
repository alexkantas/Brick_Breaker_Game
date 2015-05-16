package gr.alex.brickbreaker;

import java.awt.Color;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;

/**
 *
 * @author Alexandros Kantas
 */
public class JokerBrick extends Brick{
    
    private Icon icon = new ImageIcon(getClass().getResource("icons/joker.png"));
    private static final Color DEFAULT_COLOR = new JButton().getBackground();
    
    public JokerBrick(){
        super();
        setIcon(icon);
    }

    @Override
    public void setHighlight(boolean highlight) {
        super.setHighlight(highlight);
        if (highlight == false){
           setBackground(DEFAULT_COLOR); 
        }
    }
}
