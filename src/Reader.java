import java.awt.Color;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import javax.swing.JOptionPane;

/**
 * The Reader class is meant to read from the file and return a value in its appropriate format.
 * Any IOExceptions thrown by readers are caught and handled by returning an error message.
 */
public class Reader {

    private BufferedReader fr;
    private BufferedReader cr;
    private BufferedReader sr;
    private List<Snakeunit> snake;
    private Snakeunit head;
    private int[][] food;
    private List<Integer> scores;
    private List<String> names;
    private String name;
    private int score;
    private boolean check = true;
    static final String PATH_TO_SNAKE = "files/snake.txt";
    static final String PATH_TO_FOOD = "files/food.txt";
    static final String PATH_TO_HIGH_SCORES = "files/highscores.txt";

    /**
     * Initializes the Buffered Readers
     */
    public Reader(boolean a) {
        if (a) {
            try {
                this.sr  = new BufferedReader(new FileReader(PATH_TO_HIGH_SCORES));
            } catch (FileNotFoundException e) {
                check = false;
            }
        } else {
            try {
                this.fr  = new BufferedReader(new FileReader(PATH_TO_SNAKE));
                this.cr  = new BufferedReader(new FileReader(PATH_TO_FOOD));
                this.sr  = new BufferedReader(new FileReader(PATH_TO_HIGH_SCORES));
            } catch (FileNotFoundException e) {
                check = false;
            }
        }
    }

    /**
     * parses the HighScore file into Linked Lists of names and scores
     */
    public void highScore() {
        String s = null;
        this.names = new LinkedList<String>();
        this.scores = new LinkedList<Integer>();
        if (check) {
            try {
                do {
                    s = sr.readLine();
                    // Checking if line is blank in case of buggy writing
                    if (s == null || s.isEmpty()) {
                        break;
                    }
                    String[] high = s.split(";");
                    if (high.length != 2 || high == null) {
                        break;
                    }
                    this.names.add(high[0]);
                    this.scores.add(Integer.parseInt(high[1]));
                } while (s != null);           
            } catch (IOException e) {
            }
        }
    }
    /**
     * Returns a two-dimensional string array
     */
    public String[][] highestscores() {
        if (this.names == null || this.scores == null) {
            return null;
        }
        Object[] temp = this.names.toArray();
        String[] a = new String[temp.length];
        for (int i = 0; i < a.length; i++) {
            a[i] = (String) temp[i];
        }
        Object[] temp1 = this.scores.toArray();
        Integer[] arr = new Integer[temp1.length];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = (Integer) temp1[i];
        }
        String[][] s = new String[names.size()][2];
        if (a.length == 0 || a == null || arr.length == 0 || arr == null || 
                a.length != arr.length) {
            return null;
        }
        for (int i = 0; i < arr.length; i++) {     
            for (int j = i + 1; j < arr.length; j++) {     
                if (arr[i] < arr[j]) {    
                    Integer tempo = arr[i];    
                    arr[i] = arr[j];    
                    arr[j] = tempo;
                    String tempi = a[i];
                    a[i] = a[j];
                    a[j] = tempi;
                }     
            }     
        }           
        for (int i = 0; i < names.size(); i++) {
            s[i][0] = a[i];
            s[i][1] = String.valueOf(arr[i]);
        }
        return s;
    }

    /**
     * parses the Snake file into a Linked List of Snakeunits
     */
    public void snake() {
        String s = null;
        try {
            s = fr.readLine();
        } catch (IOException e1) {
        }
        this.snake = new LinkedList<Snakeunit>();
        String[] head = s.split(";");
        if (head.length == 4 && head != null) {
            Snakeunit h = new Snakeunit(Integer.parseInt(head[0]), Integer.parseInt(head[1]), 
                    GameCourt.COURT_WIDTH, GameCourt.COURT_HEIGHT, Color.BLUE);
            h.setVx(Integer.parseInt(head[2]));
            h.setVy(Integer.parseInt(head[3]));
            this.head = h;
            this.snake.add(h);
        }
        try {
            s = fr.readLine();
            while (s != null && !s.isEmpty()) {
                String[] high = s.split(";");
                if (high.length != 4 || high == null) {
                    break;
                }
                Snakeunit body = 
                        new Snakeunit(Integer.parseInt(high[0]), Integer.parseInt(high[1]), 
                                GameCourt.COURT_WIDTH, GameCourt.COURT_HEIGHT, Color.GREEN);
                body.setVx(Integer.parseInt(high[2]));
                body.setVy(Integer.parseInt(high[3]));
                this.snake.add(body);
                s = fr.readLine();
            }            
        } catch (IOException e) {
        }
    }
    /**
     * parses the Food file into an Array of Foods
     */
    public void food() {
        this.food = new int[3][2];
        
        try {
            String w = cr.readLine();
            if (w == null || w.isEmpty()) {
                JOptionPane.showMessageDialog(null, "No Saved Game",
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
            String[] tempy = w.split(";");
            if (tempy.length == 2) {
                this.name = tempy[0];
                this.score = Integer.parseInt(tempy[1]);
            }
            for (int v = 0; v < food.length; v++) {
                String b = cr.readLine();
                if (b.isEmpty() || b == null) {
                    break;
                } else {
                    String[] high = b.split(";");
                    if (high.length != 2 || high == null) {
                        break;
                    }
                    food[v][0] = Integer.parseInt(high[0]);
                    food[v][1] = Integer.parseInt(high[1]);
                }
            }           
        } catch (IOException e) {
        }
    }
    /*** GETTERS **********************************************************************************/
    
    public List<Snakeunit> getSnake() {
        return this.snake;
    }
    
    public int[][] getFood() {
        return this.food;
    }
    public Snakeunit getHead() {
        return this.head;
    }
    public String getName() {
        return this.name;
    }
    public int getScore() {
        return this.score;
    }
    public boolean getCheck() {
        return this.check;
    }

}
