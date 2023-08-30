package my_snake_game;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.JPanel;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.Timer;
/**
 *
 * @author Admin
 */
public class My_snake_game extends JPanel implements StateTransition{
    private static final long serialVersionUID = 1L;
    
    public static int COLS = 40;
    public static int ROWS = 40;
    public static int CELL_SIZE = 15;
    
    public static final String TITLE = "Snake";
    public static final int PIT_WIDTH = COLS * CELL_SIZE;
    public static final int PIT_HEIGHT = ROWS * CELL_SIZE;
    
    public static final Color COLOR_PIT = Color.DARK_GRAY;
    public static final Color COLOR_GAMEOVER = Color.RED;
    public static final Font FONT_GAMEOVER = new Font("Verdana", Font.BOLD, 30);
    public static final Color COLOR_INSTRUCTION = Color.RED;
    public static final Font FONT_INSTRUCTION = new Font("Dialog", Font.PLAIN, 26);
    public static final Color COLOR_DATA = Color.WHITE;
    public static final Font FONT_DATA = new Font(Font.MONOSPACED, Font.PLAIN, 16);
    
    private Snake snake;
    private Food food;
    private GamePanel pit;
    
    private State currentState;
    
    private Timer stepTimer;
    
    public static final int STEPS_PER_SEC = 5;
    public static final int STEP_IN_MSEC = 1000 / STEPS_PER_SEC;
    
    public My_snake_game() {
        initGUI();
        initGame();
        newGame();
    }
    
    public void initGUI() {
        pit = new GamePanel();
        pit.setPreferredSize(new Dimension(PIT_WIDTH, PIT_HEIGHT));
        pit.setFocusable(true); // to receive key-events
        pit.requestFocus();
        super.add(pit);
    }
    
    @Override
    public void initGame() {
        snake = new Snake();
        food = new Food();
        
        stepTimer = new Timer(STEP_IN_MSEC, e -> stepGame());
        pit.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent evt) {
                int key = evt.getKeyCode();
                if (currentState == State.READY) {
                    startGame();
                } else if (currentState == State.PLAYING) {
                    switch (key) {
                        case KeyEvent.VK_UP:
                            snake.changeDirection(Move.UP);
                            break;
                        case KeyEvent.VK_DOWN:
                            snake.changeDirection(Move.DOWN);
                            break;
                        case KeyEvent.VK_LEFT:
                            snake.changeDirection(Move.LEFT);
                            break;
                        case KeyEvent.VK_RIGHT:
                            snake.changeDirection(Move.RIGHT);
                            break;
                    }
                } else if (currentState == State.GAMEOVER) {
                    newGame();
                    startGame();
                }
            }
        });
        currentState = State.INITIALIZED;
    }
    
    @Override
    public void newGame() {
        if (currentState != State.GAMEOVER && currentState != State.INITIALIZED) {
            throw new IllegalStateException("Cannot create Game in State " + currentState);
        }
        snake.newGame();
        do {            
            food.newFood();
        } while (snake.contains(food.x, food.y));
        food.foodEaten = 0;
        currentState = State.READY;
        repaint();
    }
    
    @Override
    public void startGame() {
        if (currentState != State.READY) {
            throw  new IllegalStateException("Cannot start game in State " + currentState);
        }
        stepTimer.start();
        currentState = State.PLAYING;
        repaint();
    }
    
    @Override
    public void stopGame() {
        if (currentState != State.PLAYING) {
            throw  new IllegalStateException("Cannot Stop game in state " + currentState);
        }
        stepTimer.stop();
        currentState = State.GAMEOVER;
        repaint();
    }
    
    public  void stepGame() {
        if (currentState != State.PLAYING) {
            throw new IllegalStateException("Error");
        }
        snake.grow();
        
        int headX = snake.getHeadX();
        int headY = snake.getHeadY();
        
        if (headX == food.x && headY == food.y) {
            do {
                food.newFood();
            } while (snake.contains(food.x, food.y));
        } else {
            snake.shrink();
        }
        
        if (!pit.contains(headX, headY)) {
            stopGame();
            return;
        }
        if (snake.eatItself()) {
            stopGame();
            return;
        }
        repaint();
    }
    
    public class GamePanel extends JPanel {
        private static final long serialVersionUID = 1L;
        public void paintComponent(Graphics g) {
            super.paintComponent(g);
            setBackground(COLOR_PIT);
            
            snake.paint(g);
            food.paint(g);
            
            g.setFont(FONT_DATA);
            g.setColor(COLOR_DATA);
            g.drawString("Snake Head: (" + snake.getHeadX() + "," + snake.getHeadY() + ")", 10, 25);
            g.drawString("Snake Length: " + snake.getLength(), 10, 45);
            g.drawString("Food: (" + food.x + "," + food.y + ")", 10, 65);
            g.drawString("Eaten: " + food.foodEaten, 10, 85);
            
            if (currentState == State.READY) {
                g.setFont(FONT_INSTRUCTION);
                g.setColor(COLOR_INSTRUCTION);
                g.drawString("Push any key to start the game ...", 100, PIT_HEIGHT / 4);
            }
            
            if (currentState == State.GAMEOVER) {
                g.setFont(FONT_GAMEOVER);
                g.setColor(COLOR_GAMEOVER);
                g.drawString("GAME OVER!", 200, PIT_HEIGHT / 2);
                g.setFont(FONT_INSTRUCTION);
                g.drawString("Push any key to start the game ...", 120, PIT_HEIGHT / 2 + 40);
            }
        }
        public boolean contains(int x, int y) {
            if ((x < 0) || (x >= ROWS)) {
                return false;
            }
            if ((y < 0) || (y >= COLS)) {
                return false;
            }
            return true;
        }
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                My_snake_game game = new My_snake_game();
                JFrame frame = new JFrame(TITLE);
                frame.setContentPane(game);  // main JPanel as content pane
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.pack();
                frame.setLocationRelativeTo(null); // center the application window
                frame.setVisible(true);            // show it
            }
        });
    }
}
