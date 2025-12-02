
/**
 * Interface for Tennis Game implementations.
 * Defines the basic operations for a tennis scoring system.
 */
public interface TennisGame {
    
    /**
     * Records a point won by the specified player.
     * @param playerName Name of the player who won the point
     */
    void wonPoint(String playerName);
    
    /**
     * Gets the current score of the tennis game.
     * @return String representation of the current score
     */
    String getScore();
}