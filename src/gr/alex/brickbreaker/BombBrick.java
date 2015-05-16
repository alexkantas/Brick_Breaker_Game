package gr.alex.brickbreaker;

import javax.swing.Icon;
import javax.swing.ImageIcon;

/**
 *
 * @author Alexandros Kantas
 */
public class BombBrick extends Brick {
    
    Icon icon = new ImageIcon(getClass().getResource("icons/bomb.png"));
    
    public BombBrick(){
        super();
        setIcon(icon);
    }
}