/**
 * CIS 120 Game HW
 * (c) University of Pennsylvania
 * @version 2.1, Apr 2017
 */

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import javax.swing.JOptionPane;

/**
 * File to save the game and high scores and write text into the program.
 */
public class Writer {

    static final String PATH_TO_SNAKE = "files/snake.txt";
    static final String PATH_TO_FOOD = "files/food.txt";
    static final String PATH_TO_HIGH_SCORES = "files/highscores.txt";
    private List<Snakeunit> snake = new LinkedList<Snakeunit>();
    private String name;
    private int[][]  food;
    private int score;
    private boolean write = false;
    private boolean append;


    public Writer(GameCourt a) {
        if (a != null) {
            this.snake = a.getSnake();
            this.food = a.getFood();
            this.score = a.getscore();
            this.name = a.getName(); 
            write = true;
            this.append = false;
        }
    }
    
    public Writer(String name, int score) {
        if (name != null && !name.isEmpty()) {
            this.score = score;
            this.name = name;
            write = true;
            this.append = true;
        }
    }
    /**
     * writes the score strings into the file 
     */
    public void scoreWriter() {
        if (write) {
            List<String> a = new LinkedList<String>();
            String b = (name + ";" + score);
            a.add(b);
            writeStringsToFile(a, PATH_TO_HIGH_SCORES, append); 
        }
    }
    /**
     * writes the snake and food strings into the file 
     */
    public void gameWriter() {
        if (write) {
            List<String> a = snakecleaner();
            List<String> b = foodcleaner();
            writeStringsToFile(a, PATH_TO_SNAKE, append); 
            writeStringsToFile(b, PATH_TO_FOOD, append);
        }
    }
    /**
     * writes a list of strings into a file
     */
    public void writeStringsToFile(List<String> stringsToWrite, String filePath, boolean append) {
        File file = Paths.get(filePath).toFile();
        FileWriter w;
        BufferedWriter bw;
        try {
            w = new FileWriter(file, append);
            bw = new BufferedWriter(w);
            Iterator<String> temp = stringsToWrite.iterator();
            while (temp.hasNext()) {
                String m = temp.next();
                bw.write(m + "\n");
            }
            bw.flush();
            bw.close();
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "No File to Save",
                    "Error", JOptionPane.ERROR_MESSAGE);   
        } 
    }
    /**
     * Converts the Snake LinkedList's data values into a list of strings
     */
    public List<String> snakecleaner() {
        List<String> b = new LinkedList<String>();
        for (Snakeunit a : snake) {
            int x = a.getPx();
            int y = a.getPy();
            int vx = a.getVx();
            int vy = a.getVy();
            b.add(x + ";" + y + ";" + vx + ";" + vy);
        }
        return b;
    }
    /**
     * Converts the Food LinkedList's data values into a list of strings
     */
    public List<String> foodcleaner() {
        List<String> b = new LinkedList<String>();
        b.add(name + ";" + score); 
        for (int a = 0; a < food.length; a++) {  
            int x = food[a][0];
            int y = food[a][1];
            b.add(x + ";" + y);
        }
        return b;
    }
    /**
     * Changes append functionality to help with testing
     */
    public void setAppend(boolean append) {
        this.append = append;
    }
    
    

}