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

import java.awt.BorderLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author Alexandros Kantas
 */
public class GameFrame extends JFrame {

    //basic panels
    private JPanel top = new JPanel();
    private JPanel bottom = new JPanel();
    private JPanel left = new JPanel();
    private JPanel right = new JPanel();
    //

    // top components 
    private static final String EMPTRY_SPACE_STRING = " ";
    private static final String SCORE_STRING = "Score: ";
    private static final String LEVEL_SCORE_STRING = "Level Score: ";
    private static final String OF_STRING = " of ";
    private JPanel totalScorePanel = new JPanel();
    private JLabel scoreLabel = new JLabel(EMPTRY_SPACE_STRING + SCORE_STRING);
    private JPanel levelScorePanel = new JPanel();
    private JLabel levelScoreLabel = new JLabel(LEVEL_SCORE_STRING + EMPTRY_SPACE_STRING);
    //
    //down components
    private static final String SELECTION_SCORE = "Selection Score: ";
    private JPanel statusPanel = new JPanel();
    private JLabel statusLabel = new JLabel(EMPTRY_SPACE_STRING + SELECTION_SCORE);
    //
    //main paixnidi
    private BrickBreakerGame brickBreakerGame = new BrickBreakerGame(this);
    

    public GameFrame() {

        //top Panel
        totalScorePanel.add(scoreLabel);
        levelScorePanel.add(levelScoreLabel);

        top.setLayout(new BorderLayout());
        top.add(totalScorePanel, BorderLayout.LINE_START);
        top.add(levelScorePanel, BorderLayout.LINE_END);
        //

        //bottom panel
        statusPanel.add(statusLabel);
        
        bottom.setLayout(new BorderLayout());
        bottom.add(statusPanel, BorderLayout.LINE_START);
        //
        
        
        // add frames
        add(top, BorderLayout.PAGE_START);
        add(bottom, BorderLayout.PAGE_END);
        add(left, BorderLayout.LINE_START);
        add(right, BorderLayout.LINE_END);
        add(brickBreakerGame, BorderLayout.CENTER);
        //

        setTitle("Brick Breaker");
        pack();
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
    }

    public void updateScores(int totalScore, int levelCurrentScore, int levelTargetScore) {
        scoreLabel.setText(EMPTRY_SPACE_STRING + SCORE_STRING + Integer.toString(totalScore));
        levelScoreLabel.setText(LEVEL_SCORE_STRING + Integer.toString(levelCurrentScore)
                + OF_STRING + Integer.toString(levelTargetScore) + EMPTRY_SPACE_STRING);
    }

    public void updateStatusBar(int selectionScore) {
        if (selectionScore >= 0) {
            statusLabel.setText(EMPTRY_SPACE_STRING + SELECTION_SCORE + Integer.toString(selectionScore));
        } else {
            switch (selectionScore) {
                case -1:
                    statusLabel.setText(EMPTRY_SPACE_STRING + "Welcome to Brick Breaker Game !!!");
                    break;
                case -2:
                    try {
                        statusLabel.setText(EMPTRY_SPACE_STRING + "Congratulations , you passed to level " + Integer.toString(brickBreakerGame.getLevel()) + " !!!");
                    } catch (NullPointerException n) {
                        statusLabel.setText(EMPTRY_SPACE_STRING + "Congratulations , you passed to the next level !!!");
                    }
                    break;
                case -3:
                    statusLabel.setText(EMPTRY_SPACE_STRING + "Game Over :-(");
                    break;
                case -4:
                    statusLabel.setText(EMPTRY_SPACE_STRING + "First line must be emptry");
                    break;
            }
        }

    }
}
