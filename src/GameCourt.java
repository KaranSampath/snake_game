/**
 * CIS 120 Game HW
 * (c) University of Pennsylvania
 * @version 2.1, Apr 2017
 */

import java.awt.*;
import java.awt.event.*;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import javax.swing.*;

/**
 * GameCourt
 * 
 * This class holds the primary game logic for how different objects interact with one another. 
 */
@SuppressWarnings("serial")
public class GameCourt extends JPanel {

    // the state of the game logic
    private Snakeunit head; 
    private List<Snakeunit> snake;
    private int[][] food;
    private JLabel score;
    private int scorecount = 0;
    private int snakelength;
    private boolean playing = false; // whether the game is running 
    private JLabel status; // Current status text, i.e. "Running..."
    private JLabel name;
    private boolean hitWall;
    private int highscore;
    private boolean first;

    // Game constants
    public static final int COURT_WIDTH = 400;
    public static final int COURT_HEIGHT = 400;
    public static final int SQUARE_VELOCITY = 7;
    public static final int SIZE = 7;

    // Update interval for timer, in milliseconds
    public static final int INTERVAL = 30;
    
    public GameCourt(JLabel status, JLabel score, JLabel name, int snakelength) {
        setBorder(BorderFactory.createLineBorder(Color.BLACK));

        Timer timer = new Timer(INTERVAL, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                tick();
            }
        });
        timer.start();

        setFocusable(true);

        // This key listener allows the square to move as long as an arrow key is pressed, by
        // changing the square's velocity accordingly. (The tick method below actually moves the
        // square.)
        addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_LEFT) {
                    if (head.getVx() == 0) {
                        head.setVx(-SQUARE_VELOCITY);
                        head.setVy(0); 
                    }
                } else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
                    if (head.getVx() == 0) {
                        head.setVx(SQUARE_VELOCITY);
                        head.setVy(0); 
                    }
                } else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
                    if (head.getVy() >= 0) {
                        head.setVy(SQUARE_VELOCITY);
                        head.setVx(0);
                    }
                    
                } else if (e.getKeyCode() == KeyEvent.VK_UP) {
                    if (head.getVy() <= 0) {
                        head.setVy(-SQUARE_VELOCITY);
                        head.setVx(0);
                    }
                } else if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
                        Game.PAUSE.doClick();
                    } 
                }

        });

        this.status = status;
        this.score = score;
        this.name = name;
        this.snakelength = snakelength;
    }
    
    /*** GETTERS **********************************************************************************/
    //For testing and game saving/loading purposes
    public int getSnakeLength() {
        return this.snakelength;
    }

    public int[][] getFood() {
        return this.food;
    }
    
    public List<Snakeunit> getSnake() {
        return this.snake;
    }
    
    public Snakeunit getHead() {
        return this.head;
    }
    
    public int getscore() {
        return this.scorecount;
    }
    
    public String getName() {
        String a = this.name.getText();
        return a;
    }
    
    public boolean getIntersect() {
        return hitWall;
    }
    public boolean getPlaying() {
        return this.playing;
    }
    /*** SETTERS **********************************************************************************/
  //For testing and game saving/loading purposes
    public void setSnakeLength(int snakelen) {
        this.snakelength = snakelen;
    }

    public void setFood(int[][] f) {
        this.food = f;
    }
    
    public void setSnake(List<Snakeunit> b) {
        this.snake = b;
    }
    
    public void setScore(int s) {
        this.scorecount = s;
        this.score.setText("Score: " + this.scorecount);
    }
    
    public void setName(String n) {
        this.name.setText(n);
    }
    public void setPlaying(boolean a) {
        this.playing = a;
    }
    public void setHead(Snakeunit sn) {
        this.head = sn;
    }
    public void setHighScore(int highscore) {
        this.highscore = highscore;
    }
    public void setFocus() {
        requestFocusInWindow();
    }

    /**
     * (Re-)set the game to its initial state.
     */
    public void reset() {
        hitWall = false;
        first = true;
        snakelength = 3;
        snake = new LinkedList<Snakeunit>();
        // y coordinate at one to allow for full visibility of the snake
        head = new Snakeunit(SIZE * snakelength, 1, COURT_WIDTH, COURT_HEIGHT, Color.BLUE);
        head.setVx(SQUARE_VELOCITY);
        food = createFood(3);
        snake.add(head);
        for (int a = 0; a < snakelength - 1; a++) {
            Snakeunit body = new Snakeunit(SIZE * (snakelength - a - 1) - 1, 1, 
                    COURT_WIDTH, COURT_HEIGHT, Color.GREEN);
            snake.add(body);            
        }                
        playing = true;
        status.setText("Running...");
        score.setText("Score: 0");
        scorecount = 0;
        // Make sure that this component has the keyboard focus
        requestFocusInWindow();
    }

    /**
     * This method is called every time the timer defined in the constructor triggers.
     */
    void tick() {
        if (playing) {
            // advance the square and snitch in their current direction.
            Iterator<Snakeunit> iter = snake.iterator();
            iter.next();
            int x = 0;
            int y = 0;
            for (Snakeunit a : snake) {
                if (first) {
                    x = a.getPx();
                    a.setPx(x + SQUARE_VELOCITY);
                } else {
                    if (a.equals(head)) {
                        x = a.getPx();
                        y = a.getPy();
                        a.move();
                    } else {
                        int t = a.getPx();
                        int v = a.getPy();
                        a.setPx(x);
                        a.setPy(y);
                        x = t;
                        y = v;
                    }
                }
            }
            first = false;

            for (int i = 0; i < food.length; i++) {
                Food a = new Food(food[i][0], food[i][1], COURT_WIDTH, COURT_HEIGHT);
                if (head.intersects(a)) {
                    food = createFood(3);
                    this.scorecount++;
                    if (scorecount > highscore) {
                        status.setText("New High Score!");
                    }
                    score.setText("Score: " + scorecount);
                    snakelength++;
                    int[] val = getDetails();
                    Snakeunit tail = new Snakeunit(val[0], val[1], 
                            COURT_WIDTH, COURT_HEIGHT, Color.GREEN);
                    snake.add(tail);      
                }
            }
            for (Snakeunit b : snake) {
                if (b.equals(head)) {
                    continue;
                }
                if (head.intersects(b)) {
                    hitWall = true;
                }
            }
            if (hitWall) {
                playing = false;
                Writer a = new Writer(name.getText(), scorecount);
                a.scoreWriter();
                int rs = JOptionPane.showInternalConfirmDialog(null,
                        "Game Over! Would you like to play again?", "Game Over",
                        JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE);                
                if (rs == JOptionPane.YES_OPTION) {
                    reset();
                } else {
                    System.exit(0);
                }
            }
            if (head.hitWall()) {
                hitWall = true;
            }
            // update the display
            repaint();
        }
    }
    
    // Creates 3 new random positions for food objects
    public int[][] createFood(int a) {
        int[][] h = new int[a][2];
        RandomNumberGenerator random = new RandomNumberGenerator();
        for (int i = 0; i < a; i++) {
            int x = random.next(COURT_WIDTH - Food.SIZE);
            int y = random.next(COURT_HEIGHT - Food.SIZE);
            h[i][0] = x;
            h[i][1] = y;
        }
        return h;        
    }
    // Gets details of the snake tail
    public int[] getDetails() {
        int[] h = new int[2];
        Snakeunit a = null;
        for (Snakeunit b : snake) {
            a = b;
        }
        h[0] = a.getPx();
        h[1] = a.getPy();
        return h;        
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        for (Snakeunit a : snake) {
            a.draw(g);
        }
        for (int i = 0; i < food.length; i++) {
            Food a = new Food(food[i][0], food[i][1], COURT_WIDTH, COURT_HEIGHT);
            a.draw(g);
        }
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(COURT_WIDTH, COURT_HEIGHT);
    }
}