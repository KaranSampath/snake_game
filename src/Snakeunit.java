/**
 * CIS 120 Game HW
 * (c) University of Pennsylvania
 * @version 2.1, Apr 2017
 */

import java.awt.*;

/**
 * A basic object representing a snakeunit starting in the upper left corner of the game court. 
 * It is displayed as a square of a specified color.
 */
public class Snakeunit extends GameObj {
    public static final int SIZE = 7;
    public static final int INIT_VEL_X = 0;
    public static final int INIT_VEL_Y = 0;

    private Color color;

    public Snakeunit(int posx, int posy, int courtWidth, int courtHeight, Color color) {
        super(INIT_VEL_X, INIT_VEL_Y, posx, posy, SIZE, SIZE, courtWidth, courtHeight);
        this.color = color;
    }

    @Override
    public void draw(Graphics g) {
        g.setColor(this.color);
        g.fillRect(this.getPx(), this.getPy(), this.getWidth(), this.getHeight());
    }
}