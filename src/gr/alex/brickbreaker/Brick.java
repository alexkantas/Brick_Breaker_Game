/*
 * Copyright (C) 2016 Alexandros Kantas 
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
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
 * Describes the basic properties of a Brick
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