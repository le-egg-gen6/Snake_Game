package my_snake_game;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Admin
 */
public class Snake implements StateTransition{
    public static final int INIT_LENGTH = 5;
    
    public static final int CELL_SIZE = My_snake_game.CELL_SIZE;
    public static final Color SNAKE_HEAD = Color.YELLOW;
    public static final Color SNAKE_BODY = Color.RED;
    
    List<SnakeSegment> segments = new ArrayList<>();
    
    Move direction;
    
    public Snake() { }
    
    @Override
    public void newGame() {
        segments.clear();
        int headX = My_snake_game.COLS / 2;
        int headY = My_snake_game.ROWS / 2;
        
        segments.add(new SnakeSegment(headX, headY, INIT_LENGTH, Move.UP));
    }
    
    public void changeDirection(Move newDir) {
        if (newDir != direction) {
            if ((newDir == Move.UP && direction != Move.DOWN) 
                    || (newDir == Move.DOWN && direction != Move.UP) 
                    || (newDir == Move.LEFT && direction != Move.RIGHT)
                    || (newDir == Move.RIGHT && direction != Move.LEFT)) {
                int x_ = segments.get(0).headX;
                int y_ = segments.get(0).headY;
                segments.add(0, new SnakeSegment(x_, y_, 0, newDir));
                direction = newDir;
            }
        }
    }
    
    public void grow() {
        segments.get(0).grow();
    }
    
    public void shrink() {
        segments.get(segments.size() - 1).shrink();
        if (segments.get(segments.size() - 1).length == 0) {
            segments.remove(segments.size() - 1);
        }
    } 
    
    public int getHeadX() {
        return segments.get(0).headX;
    }
    
    public int getHeadY() {
        return segments.get(0).headY;
    }
    public int getLength() {
        int length = 0;
        for (SnakeSegment segm : segments) {
            length += segm.length;
        }
        return length;
    }
    
    public boolean contains(int x, int y) {
        for (SnakeSegment segm : segments) {
            if (segm.contains(x, y)) {
                return true;
            }
        }
        return false;
    }
    
    public boolean eatItself() {
        int headX = this.getHeadX();
        int headY = this.getHeadY();
        
        for (int i = 1; i < segments.size(); ++i) {
            if (segments.get(i).contains(headX, headY)) {
                return true;
            }
        }
        return false;
    }
    
    public void paint(Graphics g) {
        g.setColor(SNAKE_BODY);
        for (SnakeSegment segm : segments) {
            segm.paint(g);
        }
        int offset = 2;
        if (!segments.isEmpty()) {
            g.setColor(SNAKE_HEAD);
            g.fill3DRect(this.getHeadX() * CELL_SIZE - offset / 2, 
                    this.getHeadY()* CELL_SIZE - offset / 2, 
                    CELL_SIZE + offset, 
                    CELL_SIZE + offset,
                    true);
        }
    }
}
