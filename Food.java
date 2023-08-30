package my_snake_game;

import java.awt.Color;
import java.awt.Graphics;
import java.util.Random;

/**
 *
 * @author Admin
 */
public class Food {
    public static final Color COLOR_FOOD = Color.CYAN;
    public static final int CELL_SIZE = My_snake_game.CELL_SIZE;
    
    int x;
    int y;
    
    int foodEaten = -1;
    public static Random rand = new Random();
    
    public void newFood() {
        x = rand.nextInt(My_snake_game.COLS - 4) + 2;
        y = rand.nextInt(My_snake_game.ROWS - 4) + 2;
        
        ++foodEaten;
    }
    
    public void paint(Graphics g) {
        g.setColor(COLOR_FOOD);
        int offset = 10;
        g.fill3DRect(x * CELL_SIZE - offset / 2, 
                y * CELL_SIZE - offset / 2, 
                CELL_SIZE + offset, 
                CELL_SIZE + offset, 
                true);
    } 
}
