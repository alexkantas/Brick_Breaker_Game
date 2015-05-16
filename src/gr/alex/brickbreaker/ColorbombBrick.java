package gr.alex.brickbreaker;

import java.awt.Color;
import javax.swing.Icon;
import javax.swing.ImageIcon;

/**
 *
 * @author Alexandros Kantas
 */
public class ColorbombBrick extends Brick {
    
    private Color color;
    Icon icon = new ImageIcon(getClass().getResource("icons/bomb.png"));
    
    public ColorbombBrick(Color color){
        super();
        this.color = color;
        setBackground(this.color);
        setIcon(icon);
    }

}
