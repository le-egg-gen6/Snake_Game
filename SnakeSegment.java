package my_snake_game;
import java.awt.Graphics;
/**
 *
 * @author Admin
 */
public class SnakeSegment {
    public static final int CELL_SIZE = My_snake_game.CELL_SIZE;
    
    int headX, headY;
    
    int length;
    
    Move direction;

    public SnakeSegment(int headX, int headY, int length, Move direction) {
        this.headX = headX;
        this.headY = headY;
        this.length = length;
        this.direction = direction;
    }
    
    public void grow() {
        ++length;
        
        switch(direction) {
            case LEFT -> { 
                --headX;
                break;
            }
            case RIGHT -> {
                ++headX;
                break;
            }
            case UP -> {
                --headY;
                break;
            }
            case DOWN -> {
                ++headY;
                break;
            }
        }
    }
    public void shrink() {
        --length;
    }
    public int getTaiX() {
        if (direction == Move.LEFT) {
            return headX + length - 1;
        } else if (direction == Move.RIGHT) {
            return headX - length + 1;
        } else {
            return headX;
        }
    }
    public int getTailY() {
        if (direction == Move.UP) {
            return headY + length - 1;
        } else if (direction == Move.DOWN) {
            return headY - length + 1;
        } else {
            return headY;
        }
    }
    public boolean contains(int x, int y) {
        switch(direction) {
            case LEFT: return (y == headY && x >= headX && x <= headX + length - 1);
            case RIGHT: return  (y == headY && x <= headX && x >= headX - length + 1);
            case UP: return (x == headX && y >= headY && y <= headY + length - 1);
            case DOWN: return (x == headX && y <= headY && y >= headY - length + 1);
            default: return false;
        }
    }
    
    public void paint(Graphics g) {
        int x = headX;
        int y = headY;
        
        int offset = 2;
        
        switch (direction) {
            case UP:
                for (int i = 0; i < length; ++i) {
                    g.fill3DRect(x * CELL_SIZE + offset / 2, 
                            y * CELL_SIZE + offset / 2, 
                            CELL_SIZE - offset, 
                            CELL_SIZE - offset, 
                            true);
                    ++y;
                }
                break;
            case DOWN:
                for (int i = 0; i < length; ++i) {
                    g.fill3DRect(x * CELL_SIZE + offset / 2, 
                            y * CELL_SIZE + offset / 2, 
                            CELL_SIZE - offset, 
                            CELL_SIZE - offset, 
                            true);
                    --y;
                }
                break;
            case LEFT:
                for (int i = 0; i < length; ++i) {
                    g.fill3DRect(x * CELL_SIZE + offset / 2, 
                            y * CELL_SIZE + offset / 2, 
                            CELL_SIZE - offset, 
                            CELL_SIZE - offset, 
                            true);
                    ++x;
                }
                break;
            case RIGHT:
                for (int i = 0; i < length; ++i) {
                    g.fill3DRect(x * CELL_SIZE + offset / 2, 
                            y * CELL_SIZE + offset / 2, 
                            CELL_SIZE - offset, 
                            CELL_SIZE - offset, 
                            true);
                    --x;
                }
                break;
        }
    }
}
