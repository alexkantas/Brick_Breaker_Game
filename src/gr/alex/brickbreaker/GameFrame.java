package gr.alex.brickbreaker;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author Alexandros Kantas
 */
public class GameFrame extends JFrame {
    
    
    //vasika panels
    private JPanel top = new JPanel();
    private JPanel bottom = new JPanel();
    private static final JPanel left = new JPanel();
    private static final JPanel right = new JPanel();
    //
    
    // components korifis
    private static final String EMPTRY_SPACE_STRING = " ";
    private static final String SCORE_STRING = "Score: ";
    private static final String LEVEL_SCORE_STRING = "Level Score: ";
    private static final String OF_STRING = " of ";
    private JPanel totalScorePanel = new JPanel();
    private JLabel scoreLabel = new JLabel(EMPTRY_SPACE_STRING + SCORE_STRING);
    private JPanel levelScorePanel = new JPanel();
    private static final JLabel levelScoreLabel = new JLabel(LEVEL_SCORE_STRING + EMPTRY_SPACE_STRING);
    //
    //components katw merous
    private static final String SELECTION_SCORE = "Selection Score: ";
    private JPanel statusPanel = new JPanel();
    private JLabel statusLabel = new JLabel(EMPTRY_SPACE_STRING + SELECTION_SCORE);
    //
    //kyriws paixnidi
    private BrickBreakerGame brickBreakerGame = new BrickBreakerGame(this);
    private JPanel center = new JPanel(new GridLayout(2,2));

    public GameFrame() {
        // topo8etish twn plaisiwn
        add(top, BorderLayout.PAGE_START);
        add(bottom, BorderLayout.PAGE_END);
        add(left, BorderLayout.LINE_START);
        add(right, BorderLayout.LINE_END);
        add(center, BorderLayout.CENTER);
        center.add(brickBreakerGame);
        center.add(new BrickBreakerGame(this));
        center.add(new BrickBreakerGame(this));
        center.add(new BrickBreakerGame(this));
        
        //

        //panw plaisio
        top.setLayout(new BorderLayout());
        top.add(totalScorePanel, BorderLayout.LINE_START);
        top.add(levelScorePanel, BorderLayout.LINE_END);

        totalScorePanel.add(scoreLabel);
        levelScorePanel.add(levelScoreLabel);
        //

        //katw plaisio
        bottom.setLayout(new BorderLayout());
        bottom.add(statusPanel, BorderLayout.LINE_START);

        statusPanel.add(statusLabel);
        //
        

        setTitle("Brick Breaker");
        pack();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    public void updateScores(int totalScore,int levelCurrentScore,int levelTargetScore) {
        scoreLabel.setText(EMPTRY_SPACE_STRING + SCORE_STRING + Integer.toString(totalScore));
        levelScoreLabel.setText(LEVEL_SCORE_STRING + Integer.toString(levelCurrentScore)
                + OF_STRING + Integer.toString(levelTargetScore) + EMPTRY_SPACE_STRING);
    }

    public void updateStatusBar(int selectionScore) {
        if (selectionScore >= 0) {
            statusLabel.setText(EMPTRY_SPACE_STRING + SELECTION_SCORE + Integer.toString(selectionScore));
        } else{
            switch(selectionScore){
                case -1:
                    statusLabel.setText(EMPTRY_SPACE_STRING + "Welcome to Brick Breaker Game !!!");
                    break;
                case -2:
                    try{
                    statusLabel.setText(EMPTRY_SPACE_STRING + "Congratulations , you passed to level " + Integer.toString(brickBreakerGame.getLevel() )+" !!!");}
                    catch(NullPointerException n){
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
