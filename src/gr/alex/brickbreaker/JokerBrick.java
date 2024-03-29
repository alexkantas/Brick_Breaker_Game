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
