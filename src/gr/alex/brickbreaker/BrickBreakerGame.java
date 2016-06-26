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
import javax.swing.JPanel;

/**
 *
 * @author Alexandros Kantas
 */
public class BrickBreakerGame extends JPanel {

    private int level = 0;
    private int totalScore = 0;
    private int selectionScore = 0;
    private int levelCurrentScore = 0;
    private int levelTargetScore = 0;
    private BrickGrid brickGrid;
    private GameFrame gameFrame;
    private boolean gameFrameExist = false;
    private BorderLayout borderLayout = new BorderLayout();

    public BrickBreakerGame() {
        gameFrameExist = false;
        setLayout(borderLayout);
        increaseLevel();
    }

    public BrickBreakerGame(GameFrame gameFrame) {
        this.gameFrame = gameFrame;
        gameFrameExist = true;
        setLayout(borderLayout);
        increaseLevel();
    }

    //Read - Write Access
    public int getLevelCurrentScore() {
        return levelCurrentScore;
    }
    
    public int getLevelTargetScore() {
        return levelTargetScore;
    }

    public int getSelectionScore() {
        return selectionScore;
    }

    

    public int getLevel() {
        return level;
    }
    
    

    public int getTotalScore() {
        return totalScore;
    }
    //
    
    public void updateLevelCurrentScore() {
        levelCurrentScore += selectionScore;
        updateTotalScore();
        selectionScore = 0;
    }

    
    public void updateTotalScore() {
        totalScore += selectionScore;
        if (gameFrameExist) {
            gameFrame.updateScores(totalScore, levelCurrentScore, levelTargetScore);
        }
    }
    
    public void updateSelectionScore(int selectionScore) {
        this.selectionScore = selectionScore;
        if (gameFrameExist) {
            gameFrame.updateStatusBar(selectionScore);
        }
        if (selectionScore < 0) {
            this.selectionScore = 0;
        }
        
    }

    /**
     * Increases the current level by 1
     */
    public final void increaseLevel() {

        removeAll();
        level++;
        levelCurrentScore = 0;
        calculateLevelTargetScore();
        if (level == 1) {
            updateSelectionScore(-1);
        } else {
            updateSelectionScore(-2);
        }
        updateTotalScore();
        brickGrid = new BrickGrid(level, this);
        add(brickGrid, BorderLayout.CENTER);
        revalidate();

    }

    private void calculateLevelTargetScore() {
        levelTargetScore = 80 + (level * 20);
    }

    public static void main(String[] args) {
       GameFrame gameFrame = new GameFrame();
//        JFrame j = new JFrame();
//        j.add(new BrickBreakerGame());
//        j.setVisible(true);
//        j.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }
}
