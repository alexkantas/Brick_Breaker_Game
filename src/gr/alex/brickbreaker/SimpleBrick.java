package gr.alex.brickbreaker;

import java.awt.Color;

/**
 *
 * @author Alexandros Kantas
 */
public class SimpleBrick extends Brick {

    private Color color;

    public SimpleBrick(Color color) {
        super();
        this.color = color;
        setBackground(this.color);
    }
}