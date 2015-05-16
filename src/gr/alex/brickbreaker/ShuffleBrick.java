package gr.alex.brickbreaker;

import javax.swing.Icon;
import javax.swing.ImageIcon;

/**
 *
 * @author Alexandros Kantas
 */
public class ShuffleBrick extends Brick {
    
    Icon icon = new ImageIcon(getClass().getResource("icons/shuffle.png"));
    
    public ShuffleBrick() {
        super();
        setIcon(icon);
    }
}
