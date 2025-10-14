package nl.tudelft.jpacman.npc.ghost;

import nl.tudelft.jpacman.board.Board;
import nl.tudelft.jpacman.board.BoardFactory;
import nl.tudelft.jpacman.board.Direction;
import nl.tudelft.jpacman.board.Square;
import nl.tudelft.jpacman.board.Unit;
import nl.tudelft.jpacman.level.LevelFactory;
import nl.tudelft.jpacman.level.MapParser;
import nl.tudelft.jpacman.level.Player;
import nl.tudelft.jpacman.level.PlayerFactory;
import nl.tudelft.jpacman.npc.Ghost;
import nl.tudelft.jpacman.sprite.PacManSprites;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.google.common.collect.Lists;
import java.util.List;
import java.util.Optional;

// TEST CASE
// ---------

// **Distance < 8**
// 1. Path free $\Rightarrow$ Move away
// 2. Path blocked $\Rightarrow$ Empty direction
// 3. Multiple moves $\Rightarrow$ Move away

// **Distance > 8**
// 4. Path free $\Rightarrow$ Move towards
// 5. Path blocked $\Rightarrow$ Empty direction
// 6. Multiple moves $\Rightarrow$ Move towards

// **Distance = 8**
// 7. Path free $\Rightarrow$ Move away
// 8. Path blocked $\Rightarrow$ Empty direction
// 9. Multiple moves $\Rightarrow$ Move away

// **Boundary cases**

// 1.  Multiple Clyde instances on the map
// 2.  Multiple Pac-Man instances on the map
// 3.  Clyde is on Pac
// 4.  Pac-Man not on the board
// 5.  Pac-Man does not have a square
// 6.  Pac-Man at the edge of the board
// 7.  Pac-Man goes from one edge of the board to another

public class ClydeTest {


    // The Clyde ghost under test.
    private Clyde clyde;

    // The player (Pac-Man).
    private Player player;

    // The square where Clyde is located.
    private Square clydeSquare;

    // The square where the player is located.
    private Square playerSquare;

    // The board for the test.
    private Board board;

    // Map parser used to construct boards.
    private MapParser parser;

    // Sets up the test environment with Clyde and a player on a simple board.

    @BeforeEach
    private void setUp(String[] map) {
        // TODO
    }

    // Simple test to check that setup works
    @Test
    void testSetup() {
        // TODO
    }
}
