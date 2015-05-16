package gr.alex.brickbreaker;

import java.awt.BorderLayout;
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
        this(null);
        gameFrameExist = false;
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
    }
}
