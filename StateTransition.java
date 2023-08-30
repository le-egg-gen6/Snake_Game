package my_snake_game;

/**
 *
 * @author Admin
 */
public interface StateTransition {
    default void initGame() { };
    default void newGame() { };
    default void startGame() { };
    default void stopGame() { };
    default void pauseGame() { };
    default void resumeGame() { };
    default void destroyGame() { };
}
