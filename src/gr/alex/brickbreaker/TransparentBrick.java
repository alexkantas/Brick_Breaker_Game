package gr.alex.brickbreaker;

/**
 *
 * @author Alexandros Kantas
 */
public class TransparentBrick extends Brick {
    
    public TransparentBrick(){
        super();
        setOpaque(false);
        setContentAreaFilled(false);
        setBorderPainted(false);
    }

    @Override
    public void setHighlight(boolean highlight) {
        
    }
    
    
}
