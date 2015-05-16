package gr.alex.brickbreaker;

import java.awt.Color;
import javax.swing.JButton;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;

/**
 *
 * @author Alexandros Kantas
 */
/**
 * Perigrafei tis basikes idiotites kai me8odous enos Brick
 */
public abstract class Brick extends JButton {

    private boolean highlight = false;
    private static final Border BORDER = new  JButton().getBorder();
    private static final Border WHITE_BORDER = new LineBorder(Color.WHITE, 5);

    public Brick() {
        super();
    }

    //read - write access
    public boolean isHighlight() {
        return highlight;
    }

    public void setHighlight(boolean highlight) {
        this.highlight = highlight;
        if (highlight) {
            setBorder(WHITE_BORDER);
        } else {
            setBorder(BORDER);
        }
    }
    //
}