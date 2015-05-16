package gr.alex.brickbreaker;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JPanel;

/**
 *
 * @author Alexandros Kantas
 */
/**
 * Creates a grid with Bricks
 */
public class BrickGrid extends JPanel implements ActionListener {

    // <editor-fold defaultstate="collapsed" desc="Fields">
    private int level;
    private BrickBreakerGame brickBreakerGame;
    private int rows;
    private int columns;
    private int maxColorsNumber;
    private int speciaBricksNumber;
    private int removedBricks = 0;
    private Brick[][] brick;
    private static final Color[] color = new Color[]{Color.BLUE, Color.RED, Color.GREEN,
        Color.ORANGE, Color.PINK, Color.YELLOW, Color.DARK_GRAY, Color.CYAN,
        Color.MAGENTA, Color.BLACK, Color.GRAY};
    // </editor-fold>

    // <editor-fold defaultstate="uncollapsed" desc="Constructor">
    public BrickGrid(int level, BrickBreakerGame bbg) {
        this.brickBreakerGame = bbg;
        this.level = level;
        calculateColums();
        calculateRows();
        calculateMaxColorsNumber();
        calculateSpecialBricksNumber();
        initializeBrickArray();
        setLayout(new GridLayout(rows, columns));
        fillGrid();
        setVisible(true);
    }
    // </editor-fold>

    // <editor-fold defaultstate="uncollapsed" desc="Action Listener">
    public void actionPerformed(ActionEvent e) {
        updateGame((Brick) e.getSource());
        checkGame();
    }
    // </editor-fold>

    // <editor-fold defaultstate="uncollapsed" desc="Update & CheckGame Methods">
    private void updateGame(Brick brick) {
        if (brick.isHighlight()) {
            brickBreakerGame.updateLevelCurrentScore();
            updateBrickArray();
            brickBreakerGame.updateSelectionScore(0);
        } else {
            unHighlightGrid();
            if (brick instanceof ShuffleBrick) {
                suffleBrickAction(brick);
                brickBreakerGame.updateSelectionScore(0);
            } else if (brick instanceof SimpleBrick) {
                simpleBrickAction(brick);
                brickBreakerGame.updateSelectionScore(calculateSelectionScore());
                removedBricks = 0;
            } else if (brick instanceof NewLineBrick) {
                if (firstLineEmptry()) {
                    newLineBrickAction(brick);
                    brickBreakerGame.updateSelectionScore(0);
                } else {
                    brickBreakerGame.updateSelectionScore(-4);
                }
            } else if (brick instanceof BombBrick) {
                bombBrickAction(brick);
                brickBreakerGame.updateSelectionScore(calculateSelectionScore());
                removedBricks = 0;
            } else if (brick instanceof ColorbombBrick) {
                colorBombBrickAction(brick);
                brickBreakerGame.updateSelectionScore(calculateSelectionScore());
                removedBricks = 0;
            } else {
                brickBreakerGame.updateSelectionScore(0);
            }
        }
        updateGrid();
        revalidate();
    }

    private void checkGame() {
        if (checkAvailableActions() == false) {
            if (brickBreakerGame.getLevelCurrentScore() >= brickBreakerGame.getLevelTargetScore()) {
                brickBreakerGame.increaseLevel();
            } else {
                brickBreakerGame.updateSelectionScore(-3);
            }
        }
    }
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Update Game Methods">
    private int[] getBrickPosition(Brick brick) {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                if (this.brick[i][j] == brick) {
                    int[] x = {i, j};
                    return x;
                }
            }
        }
        return null;
    }

    private boolean checkNearSame(Brick brick) {
        int[] a = getBrickPosition(brick);
        final int R, C;
        R = a[0];
        C = a[1];

        if ((C - 1) >= 0) { //panw
            if ((this.brick[R][C - 1] instanceof JokerBrick)
                    || ((this.brick[R][C - 1] instanceof SimpleBrick)
                    && (this.brick[R][C - 1].getBackground() == brick.getBackground()))) {
                return true;
            }
        }

        if (((R - 1) >= 0)) { //aristera
            if ((this.brick[R - 1][C] instanceof JokerBrick)
                    || ((this.brick[R - 1][C] instanceof SimpleBrick)
                    && (this.brick[R - 1][C].getBackground() == brick.getBackground()))) {
                return true;
            }
        }

        if (((R + 1) < rows)) {//de3ia
            if ((this.brick[R + 1][C] instanceof JokerBrick)
                    || ((this.brick[R + 1][C] instanceof SimpleBrick)
                    && (this.brick[R + 1][C].getBackground() == brick.getBackground()))) {
                return true;
            }
        }
        if ((C + 1) < columns) {//katw
            if ((this.brick[R][C + 1] instanceof JokerBrick)
                    || ((this.brick[R][C + 1] instanceof SimpleBrick)
                    && (this.brick[R][C + 1].getBackground() == brick.getBackground()))) {
                return true;
            }
        }

        return false;
    }

    private void checkAllSame(Brick brick) {
        brick.setHighlight(true);
        removedBricks++;
        int[] a = getBrickPosition(brick);
        final int R, C;
        R = a[0];
        C = a[1];

        if ((C - 1) >= 0) { //panw
            if ((this.brick[R][C - 1].isHighlight() == false)
                    && ((this.brick[R][C - 1] instanceof JokerBrick)
                    || ((this.brick[R][C - 1] instanceof SimpleBrick)
                    && (this.brick[R][C - 1].getBackground() == brick.getBackground())))) {
                if (this.brick[R][C - 1] instanceof JokerBrick) {
                    this.brick[R][C - 1].setBackground(brick.getBackground());
                }
                checkAllSame(this.brick[R][C - 1]);
            }
        }

        if (((R - 1) >= 0)) { //aristera
            if ((this.brick[R - 1][C].isHighlight() == false)
                    && ((this.brick[R - 1][C] instanceof JokerBrick)
                    || ((this.brick[R - 1][C] instanceof SimpleBrick)
                    && (this.brick[R - 1][C].getBackground() == brick.getBackground())))) {
                if (this.brick[R - 1][C] instanceof JokerBrick) {
                    this.brick[R - 1][C].setBackground(brick.getBackground());
                }
                checkAllSame(this.brick[R - 1][C]);
            }
        }

        if (((R + 1) < rows)) {//de3ia
            if ((this.brick[R + 1][C].isHighlight() == false)
                    && ((this.brick[R + 1][C] instanceof JokerBrick)
                    || ((this.brick[R + 1][C] instanceof SimpleBrick)
                    && (this.brick[R + 1][C].getBackground() == brick.getBackground())))) {
                if (this.brick[R + 1][C] instanceof JokerBrick) {
                    this.brick[R + 1][C].setBackground(brick.getBackground());
                }
                checkAllSame(this.brick[R + 1][C]);
            }
        }

        if ((C + 1) < columns) {//katw
            if ((this.brick[R][C + 1].isHighlight() == false)
                    && ((this.brick[R][C + 1] instanceof JokerBrick)
                    || ((this.brick[R][C + 1] instanceof SimpleBrick)
                    && (this.brick[R][C + 1].getBackground() == brick.getBackground())))) {
                if (this.brick[R][C + 1] instanceof JokerBrick) {
                    this.brick[R][C + 1].setBackground(brick.getBackground());
                }
                checkAllSame(this.brick[R][C + 1]);
            }
        }

    }

    private void updateScores() {
    }

    private void moveVertically(final int R, final int C) {
        if (R == 0) {
            removeAll();
            brick[R][C] = new TransparentBrick();
        } else {
            int nr = R - 1;
            removeAll();
            brick[R][C] = brick[nr][C];
            moveVertically(nr, C);
        }
    }

    private void moveHorizontally(final int C) {
        if (C == (columns - 1)) {
            removeAll();
            for (int r = 0; r < rows; r++) {
                brick[r][C] = new TransparentBrick();
            }
        } else {
            for (int r = 0; r < rows; r++) {
                int nc = C + 1;
                brick[r][C] = brick[r][nc];
            }
            moveHorizontally(C + 1);
        }
    }

    private void destroyBrick(final int R, final int C) {
        moveVertically(R, C);
        if ((this.brick[R][C] instanceof TransparentBrick) && (R == (rows - 1))) {
            moveHorizontally(C);
        }
    }

    private void unHighlightGrid() {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                brick[i][j].setHighlight(false);

            }
        }
    }

    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Bricks Actions">
    private void suffleBrickAction(Brick brick) {
        int[] a = getBrickPosition(brick);
        int x = (int) (Math.random() * maxColorsNumber);
        removeAll();
        this.brick[a[0]][a[1]] = new SimpleBrick(color[x]);
        this.brick[a[0]][a[1]].addActionListener(this);
    }

    private void simpleBrickAction(Brick brick) {
        if (checkNearSame(brick)) {
            checkAllSame(brick);
        } else {
            brickBreakerGame.updateSelectionScore(0);
        }
    }

    private boolean firstLineEmptry() { // tsekarei an h prwth grammh einai adeia gia xrisi apo to NewLineBrick
        for (int i = 0; i < columns; i++) {
            if (!(brick[0][i] instanceof TransparentBrick)) {
                return false;
            }

        }
        return true;
    }

    private void newLineBrickAction(Brick brick) {
        int[] a = getBrickPosition(brick);
        for (int i = 0; i < (rows - 1); i++) {
            for (int j = 0; j < columns; j++) {
                if ((this.brick[i][j] instanceof TransparentBrick)
                        && !(this.brick[i + 1][j] instanceof TransparentBrick)) {
                    removeAll();
                    int x = (int) (Math.random() * maxColorsNumber);
                    this.brick[i][j] = new SimpleBrick(color[x]);
                    this.brick[i][j].addActionListener(this);
                } else if (i == (rows - 2)) {
                    if ((this.brick[i][j] instanceof TransparentBrick)
                            && (this.brick[i + 1][j] instanceof TransparentBrick)) {
                        removeAll();
                        int x = (int) (Math.random() * maxColorsNumber);
                        this.brick[i + 1][j] = new SimpleBrick(color[x]);
                        this.brick[i + 1][j].addActionListener(this);
                    }
                }
            }
        }
        destroyBrick(a[0], a[1]);
    }

    private void bombBrickAction(Brick brick) {
        int[] a = getBrickPosition(brick);
        final int R, C;
        R = a[0];
        C = a[1];


        if (((R + 1) < rows) && ((C + 1) < columns)) {//katw de3ia
            this.brick[R + 1][C + 1].setHighlight(true);
            if (!(this.brick[R + 1][C + 1] instanceof TransparentBrick)) {
                removedBricks++;
            }
        }

        if ((C + 1) < columns) {//katw
            this.brick[R][C + 1].setHighlight(true);
            if (!(this.brick[R][C + 1] instanceof TransparentBrick)) {
                removedBricks++;
            }
        }

        if (((R - 1) >= 0) && ((C + 1) < columns)) {//katw aristera
            this.brick[R - 1][C + 1].setHighlight(true);
            if (!(this.brick[R - 1][C + 1] instanceof TransparentBrick)) {
                removedBricks++;
            }
        }

        if (((R + 1) < rows)) {//de3ia
            this.brick[R + 1][C].setHighlight(true);
            if (!(this.brick[R + 1][C] instanceof TransparentBrick)) {
                removedBricks++;
            }
        }

        this.brick[R][C].setHighlight(true); //kentro
        removedBricks++;

        if (((R - 1) >= 0)) { //aristera
            this.brick[R - 1][C].setHighlight(true);
            if (!(this.brick[R - 1 ][C] instanceof TransparentBrick)) {
                removedBricks++;
            }
        }

        if (((R + 1) < rows) && ((C - 1) >= 0)) {//panw de3ia
            this.brick[R + 1][C - 1].setHighlight(true);
            if (!(this.brick[R + 1][C - 1] instanceof TransparentBrick)) {
                removedBricks++;
            }
        }

        if ((C - 1) >= 0) { //panw
            this.brick[R][C - 1].setHighlight(true);
            if (!(this.brick[R][C - 1] instanceof TransparentBrick)) {
                removedBricks++;
            }
        }

        if (((R - 1) >= 0) && ((C - 1) >= 0)) { //panw aristera
            this.brick[R - 1][C - 1].setHighlight(true);
            if (!(this.brick[R -1 ][C - 1] instanceof TransparentBrick)){
                removedBricks++;
            }
        }

    }

    private void colorBombBrickAction(Brick brick) {
        int[] a = getBrickPosition(brick);
        final int R, C;
        R = a[0];
        C = a[1];

        if (((R + 1) < rows) && ((C + 1) < columns)) {//katw de3ia
            if ((this.brick[R + 1][C + 1].getBackground() == brick.getBackground())
                    && (this.brick[R + 1][C + 1] instanceof SimpleBrick)) {
                this.brick[R + 1][C + 1].setHighlight(true);
                removedBricks++;
            }
        }

        if ((C + 1) < columns) {//katw
            if ((this.brick[R][C + 1].getBackground() == brick.getBackground())
                    && (this.brick[R][C + 1] instanceof SimpleBrick)) {
                this.brick[R][C + 1].setHighlight(true);
                removedBricks++;
            }
        }

        if (((R - 1) >= 0) && ((C + 1) < columns)) {//katw aristera
            if ((this.brick[R - 1][C + 1].getBackground() == brick.getBackground())
                    && (this.brick[R - 1][C + 1] instanceof SimpleBrick)) {
                this.brick[R - 1][C + 1].setHighlight(true);
                removedBricks++;
            }
        }

        if (((R + 1) < rows)) {//de3ia
            if ((this.brick[R + 1][C].getBackground() == brick.getBackground())
                    && (this.brick[R + 1][C] instanceof SimpleBrick)) {
                this.brick[R + 1][C].setHighlight(true);
                removedBricks++;
            }
        }

        if (this.brick[R][C].getBackground() == brick.getBackground()) {//kentro
            this.brick[R][C].setHighlight(true);
            removedBricks++;
        }

        if (((R - 1) >= 0)) { //aristera
            if ((this.brick[R - 1][C].getBackground() == brick.getBackground())
                    && (this.brick[R - 1][C] instanceof SimpleBrick)) {
                this.brick[R - 1][C].setHighlight(true);
                removedBricks++;
            }
        }

        if (((R + 1) < rows) && ((C - 1) >= 0)) {//panw de3ia
            if ((this.brick[R + 1][C - 1].getBackground() == brick.getBackground())
                    && (this.brick[R + 1][C - 1] instanceof SimpleBrick)) {
                this.brick[R + 1][C - 1].setHighlight(true);
                removedBricks++;
            }
        }

        if ((C - 1) >= 0) { //panw
            if ((this.brick[R][C - 1].getBackground() == brick.getBackground())
                    && (this.brick[R][C - 1] instanceof SimpleBrick)) {
                this.brick[R][C - 1].setHighlight(true);
                removedBricks++;
            }
        }

        if (((R - 1) >= 0) && ((C - 1) >= 0)) { //panw aristera
            if ((this.brick[R - 1][C - 1].getBackground() == brick.getBackground())
                    && (this.brick[R - 1][C - 1] instanceof SimpleBrick)) {
                this.brick[R - 1][C - 1].setHighlight(true);
                removedBricks++;
            }
        }

    }

// </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Check Game Methods">
    private boolean checkAvailableActions() {
        for (int i = (rows - 1); i >= 0; i--) {
            for (int j = 0; j < columns; j++) {
                if (!(brick[i][j] instanceof TransparentBrick)) {
                    if (!((brick[i][j] instanceof SimpleBrick) || (brick[i][j] instanceof JokerBrick))) {
                        return true;
                    } else if ((brick[i][j] instanceof SimpleBrick)) {
                        if (checkNearSame(brick[i][j])) {
                            return true;
                        }
                    } else if(brick[i][j] instanceof NewLineBrick){
                        if(firstLineEmptry()){
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Initialize and Update Brick Array">
    /**
     * Arxikopoiei ton pinaka brick
     */
    private void initializeBrickArray() {
        brick = new Brick[rows][columns];
        for (int i = 0; i < rows; i++) { //initialize simple Bricks
            for (int j = 0; j < columns; j++) {
                int x = (int) (Math.random() * maxColorsNumber);
                brick[i][j] = new SimpleBrick(color[x]);
            }
        }

        for (int i = 0; i < speciaBricksNumber; i++) {//initialize special Bricks
            final int r, c, x;
            x = (int) (Math.random() * 5);
            r = (int) (Math.random() * rows);
            c = (int) (Math.random() * columns);
            if (brick[r][c] instanceof SimpleBrick) {
                switch (x) {
                    case 0:
                        brick[r][c] = new JokerBrick();
                        break;
                    case 1:
                        brick[r][c] = new BombBrick();
                        break;
                    case 2:
                        brick[r][c] = new ColorbombBrick(color[(int) (Math.random() * maxColorsNumber)]);
                        break;
                    case 3:
                        brick[r][c] = new ShuffleBrick();
                        break;
                    case 4:
                        brick[r][c] = new NewLineBrick();
                        break;
                }

            } else {
                i--;
            }
        }
    }

    private void updateBrickArray() {
        for (int j = (columns - 1); j >= 0; j--) {
            for (int i = (rows - 1); i >= 0; i--) {
                while (brick[i][j].isHighlight()) {
                    destroyBrick(i, j);
                }
            }
        }

    }

    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Grid Methods">
    /**
     * Analambanei na gemisei to plegma me bricks.
     */
    private void fillGrid() {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                brick[i][j].addActionListener(this);
                add(brick[i][j]);
            }
        }
    }

    /*
     * Enimeronei to plegma
     */
    private void updateGrid() {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                add(brick[i][j]);
            }
        }
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Read Access">
    public int getColumns() {
        return columns;
    }

    public int getRows() {
        return rows;
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Calculate Methods">
    private void calculateRows() {
        rows = (int) (12 + level / 2);
    }

    private void calculateColums() {
        columns = (int) (14 + (level - 1) / 2);
    }

    private void calculateMaxColorsNumber() {
        maxColorsNumber = (int) (4 + (level - 1) / 2);
        if (maxColorsNumber > color.length) {
            maxColorsNumber = color.length;
        }
    }

    private void calculateSpecialBricksNumber() {
        speciaBricksNumber = (int) ((rows * columns) * 5 / 100);
    }

    private int calculateSelectionScore() {
        if (removedBricks <= 4) {
            return removedBricks;
        } else if ((removedBricks >= 5) && (removedBricks <= 12)) {
            return (int)(1.5 * removedBricks);
        } else {
            return 2*removedBricks;
        }
    }
    // </editor-fold>
}