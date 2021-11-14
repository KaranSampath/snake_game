import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.LinkedList;
import java.util.List;

import javax.swing.JLabel;

/** 
 *  File to test my implementation.
 */

public class GameTest {
    
    private GameCourt court;
    
    @BeforeEach
    public void setUp() {
        final JLabel a = new JLabel("yo");
        final JLabel b = new JLabel("yo");
        final JLabel c = new JLabel("yo");
        court = new GameCourt(a, b, c, 3);
    }
    /** Due to .equals checking for reference equality, these functions help check for structural
     * equality
    */
    public boolean checkEquality(Snakeunit a, Snakeunit b) {
        return (a.getPx() == b.getPx() && a.getPy() == b.getPy() && a.getVx() == b.getVx() 
                && a.getVy() == b.getVy());
    }
    
    public boolean checkEquality(int px1, int py1, int vx1, int vy1, Snakeunit b) {
        return (px1 == b.getPx() && py1 == b.getPy() && vx1 == b.getVx() 
                && vy1 == b.getVy());
    }
    
    @Test
    public void testSnakeSaveCheck() {
        court.reset();
        court.tick();
        court.setPlaying(false);
        List<Snakeunit> a = court.getSnake();
        Snakeunit head = court.getHead();
        Writer w = new Writer(court);
        w.gameWriter();
        Reader r = new Reader(false);
        r.snake();
        List<Snakeunit> b = r.getSnake();
        Snakeunit head2 = r.getHead();
        assertTrue(checkEquality(head, head2));
        for (int l = 0; l < a.size(); l++) {
            Snakeunit temp1 = a.get(l);
            Snakeunit temp2 = b.get(l);
            assertTrue(checkEquality(temp1, temp2));
        }
    }
    
    @Test
    public void testFoodSaveCheck() {
        court.reset();
        court.tick();
        court.setPlaying(false);
        int[][] a = court.getFood();
        Writer w = new Writer(court);
        w.gameWriter();
        Reader r = new Reader(false);
        r.food();
        int[][] b = r.getFood();
        assertArrayEquals(a, b);
    }
    @Test
    public void testNameScoreSaveCheck() {
        court.reset();
        court.tick();
        court.tick();
        court.setPlaying(false);
        String a = court.getName();
        int v = court.getscore();
        Writer w = new Writer(court);
        w.gameWriter();
        Reader r = new Reader(false);
        r.food();
        String b = r.getName();
        int d = r.getScore();
        assertEquals(a, b);
        assertEquals(d, v);
    }

    @Test
    public void testHighScoreCheck() {
        court.reset();
        court.tick();
        court.setPlaying(false);
        String t1 = "Hello";
        String t2 = "Adam";
        String t3 = "Jake";
        Writer w = new Writer(t1, 1212);
        Writer c = new Writer(t2, 1231);
        Writer b = new Writer(t3, 12231);
        w.setAppend(false);
        w.scoreWriter();
        c.scoreWriter();
        b.scoreWriter();
        Reader r = new Reader(true);
        r.highScore();
        String [][] high = r.highestscores();
        assertEquals(1212, Integer.parseInt(high[2][1]));
        assertEquals(1231, Integer.parseInt(high[1][1]));
        assertEquals(12231, Integer.parseInt(high[0][1]));
        assertEquals(t1, high[2][0]);
        assertEquals(t2, high[1][0]);
        assertEquals(t3, high[0][0]);
    }

    @Test
    public void testNullGameCheck() {
        // Check for null entries/file
        court.reset();
        court.setPlaying(false);
        Writer w = new Writer(court);
        w.gameWriter();
        Reader r = new Reader(false);
        r.snake();
        List<Snakeunit> v = r.getSnake();
        Writer c = new Writer(null);    
        c.gameWriter();
        Reader r2 = new Reader(false);
        r2.snake();
        List<Snakeunit> b = r2.getSnake();
        for (int l = 0; l < v.size(); l++) {
            Snakeunit temp1 = v.get(l);
            Snakeunit temp2 = b.get(l);
            assertTrue(checkEquality(temp1, temp2));
        }
    }
    @Test
    public void testNullHighScoreCheck() {
        Reader r = new Reader(true);
        r.highScore();
        String [][] high = r.highestscores();
        Writer w = new Writer(null, 12);
        w.scoreWriter();
        Writer c = new Writer("", 1);
        c.scoreWriter();
        Reader r2 = new Reader(true);
        r2.highScore();
        String [][] high2 = r2.highestscores();
        assertArrayEquals(high, high2);
    }

    
    @Test
    public void testSnakeLengthIncrease() {
        court.reset();
        court.tick();
        court.setPlaying(false);
        int[][] a = court.getFood();
        int x = a[0][0];
        int y = a[0][1];
        List<Snakeunit> n1 = court.getSnake();
        List<Snakeunit> n = new LinkedList<Snakeunit>();
        n.addAll(n1);
        Snakeunit h = court.getHead();
        int len = court.getSnakeLength();
        h.setPx(x - 5);
        h.setPy(y - 5);
        court.setHead(h);
        court.setPlaying(true);
        court.tick();
        court.setPlaying(false);
        assertEquals(len, court.getSnakeLength() - 1);
        assertFalse(n.equals(court.getSnake()));
    }
    @Test
    public void testSnakeHeadMovement() {
        court.reset();
        Snakeunit h = court.getHead();
        int px = h.getPx();
        int py = h.getPy();
        int vx = h.getVx();
        int vy = h.getVy();
        court.tick();
        court.setPlaying(false);
        Snakeunit h2 = court.getHead();
        assertFalse(checkEquality(px, py, vx, vy, h2));
    }
    @Test
    public void testSnakeMovement() {
        /** Due to the issues of reference equality, this test uses file reading and writing to 
         * check snake movement.
        */
        court.reset();
        court.tick();
        court.setPlaying(false);
        Writer w = new Writer(court);
        w.gameWriter();
        Reader r = new Reader(false);
        r.snake();
        List<Snakeunit> n = r.getSnake();
        int len = n.size();
        court.setPlaying(true);
        court.tick();
        court.tick();
        court.setPlaying(false);
        assertEquals(len, court.getSnakeLength());
        List<Snakeunit> v = court.getSnake();
        for (int l = 0; l < n.size(); l++) {
            Snakeunit temp1 = n.get(l);
            Snakeunit temp2 = v.get(l);
            assertFalse(checkEquality(temp1, temp2));   
        }
    }
    @Test
    public void testSnakeBoundingBoxWidthGameOver() {
        /** Since game will end on intersection, this test checks if the score and name 
         * have been written into the file, which will happen when the game ends
        */
        court.reset();
        court.tick();
        court.setPlaying(false);
        Snakeunit h = court.getHead();
        int x = GameCourt.COURT_WIDTH - h.getWidth();
        h.setPx(x);
        court.setHead(h);
        String a = court.getName();
        int score = court.getscore();
        court.setPlaying(true);
        court.tick();
        court.tick();
        Reader r = new Reader(true);
        r.highScore();
        String [][] s = r.highestscores();
        boolean check = false;
        for (int l = 0; l < s.length; l++) {
            if (s[l][0].equals(a) && Integer.parseInt(s[l][1]) == score) {
                check = true;
            }
        }
        assertTrue(check);
    }
    @Test
    public void testSnakeBoundingBoxHeightGameOver() {
        /** Since game will end on intersection, this test checks if the score and name 
         * have been written into the file, which will happen when the game ends
        */
        court.reset();
        court.tick();
        court.setPlaying(false);
        Snakeunit h2 = court.getHead();
        int y = GameCourt.COURT_HEIGHT - h2.getHeight();
        h2.setPy(y + 1);
        court.setHead(h2);
        String a = court.getName();
        int score = court.getscore();
        System.out.println(a);
        System.out.println(score);
        court.setPlaying(true);
        court.tick();
        court.tick();
        Reader r = new Reader(true);
        r.highScore();
        String [][] s = r.highestscores();
        boolean check = false;
        for (int l = 0; l < s.length; l++) {
            if (s[l][0].equals(a) && Integer.parseInt(s[l][1]) == score) {
                check = true;
            }
        }
        assertTrue(check);
    }
    
    @Test
    public void testSnakeBodyIntersectionGameOver() {
        /** Since game will end on intersection, this test checks if the score and name 
         * have been written into the file, which will happen when the game ends
        */
        court.reset();
        court.tick();
        court.setPlaying(false);
        List<Snakeunit> n = court.getSnake();
        int x = n.get(2).getPx();
        int y = n.get(2).getPy();
        Snakeunit h = court.getHead();
        h.setPx(x + 2);
        h.setPy(y);
        court.setHead(h);
        String a = court.getName();
        int score = court.getscore();
        court.setPlaying(true);
        court.tick();
        Reader r = new Reader(true);
        r.highScore();
        String [][] s = r.highestscores();
        boolean check = false;
        for (int l = 0; l < s.length; l++) {
            if (s[l][0].equals(a) && Integer.parseInt(s[l][1]) == score) {
                check = true;
            }
        }
        assertTrue(check);
    }
    
    @Test
    public void testFoodRandomChange() {
        court.reset();
        court.tick();
        court.setPlaying(false);
        int[][] a = court.getFood();
        int[][] b = court.createFood(3);
        assertFalse(a.equals(b));
    }
    @Test
    public void testSnakeTailDetails() {
        court.reset();
        court.tick();
        court.setPlaying(false);
        List<Snakeunit> a = court.getSnake();
        Snakeunit b = a.get(a.size() - 1);
        int[] det = court.getDetails();
        assertEquals(b.getPx(), det[0]);
        assertEquals(b.getPy(), det[1]);
    }
    

}
