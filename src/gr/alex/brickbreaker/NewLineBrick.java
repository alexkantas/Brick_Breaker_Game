package gr.alex.brickbreaker;

import javax.swing.Icon;
import javax.swing.ImageIcon;

/**
 *
 * @author Alexandros Kantas
 */
public class NewLineBrick extends Brick{
    
    Icon icon = new ImageIcon(getClass().getResource("icons/newLine.png"));
    
    public NewLineBrick(){
        super();
        setIcon(icon);
    }

}
