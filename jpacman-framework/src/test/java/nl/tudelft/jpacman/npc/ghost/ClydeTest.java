package nl.tudelft.jpacman.npc.ghost;

import nl.tudelft.jpacman.board.BoardFactory;
import nl.tudelft.jpacman.board.Direction;
import nl.tudelft.jpacman.level.*;
import nl.tudelft.jpacman.sprite.PacManSprites;
import nl.tudelft.jpacman.board.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.image.DirectColorModel;
import java.beans.Transient;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class ClydeTest {

    /**
     * Test map parser with Clyde support. 
    */

    private PacManSprites pacManSprites = new PacManSprites();
    private PlayerFactory playerFactory = new PlayerFactory(pacManSprites);
    private GhostFactory ghostFactory = new GhostFactory(pacManSprites);
    private LevelFactory levelFactory = new LevelFactory(pacManSprites, ghostFactory);
    private BoardFactory boardFactory = new BoardFactory(pacManSprites);
    MapParser ghostMapParser = new GhostMapParser(levelFactory, boardFactory, ghostFactory);

    /**
     * Distance greater than 8 tests
    */

    @Test
    void distanceGreaterThan8AndPathFreeTest() {
        List<String> map = Arrays.asList(
            "#############",
            "#C         P#",
            "#############"
        );
        Level level = ghostMapParser.parseMap(map);
        Player pacman = playerFactory.createPacMan();
        level.registerPlayer(pacman);

        Clyde clyde = Navigation.findUnitInBoard(Clyde.class, level.getBoard());
        assertNotNull(clyde);
        Optional<Direction> direction = clyde.nextAiMove();
        assertEquals(Optional.of(Direction.EAST), direction);
    }

    @Test
    void distanceGreaterThan8AndMultiplePathsTest() {
        List<String> map = Arrays.asList(
            "#############",
            "#          P#",
            "#           #",
            "#C          #",
            "#############"
        );

        Level level = ghostMapParser.parseMap(map);
        Player pacman = playerFactory.createPacMan();
        level.registerPlayer(pacman);

        Clyde clyde = Navigation.findUnitInBoard(Clyde.class, level.getBoard());
        assertNotNull(clyde);
        Optional<Direction> direction = clyde.nextAiMove();
        assertTrue(direction.isPresent());
        assertTrue(direction.get() == Direction.EAST || direction.get() == Direction.NORTH);
    }

    @Test
    void distanceGreaterThan8AndPathBlockedTest() {
        List<String> map = Arrays.asList(
            "#############",
            "#C#        P#",
            "#############"
        );

        Level level = ghostMapParser.parseMap(map);
        Player pacman = playerFactory.createPacMan();
        level.registerPlayer(pacman);

        Clyde clyde = Navigation.findUnitInBoard(Clyde.class, level.getBoard());
        assertNotNull(clyde);
        Optional<Direction> direction = clyde.nextAiMove();
        assertEquals(Optional.empty(), direction);
    }
    /**
     * Distance less than 8 tests
    */

    @Test 
    void distanceLessThan8AndPathFreeTest() {
        List<String> map = Arrays.asList(
            "#############",
            "# C  P      #",
            "#############"
        );
        Level level = ghostMapParser.parseMap(map);
        Player pacman = playerFactory.createPacMan();
        level.registerPlayer(pacman);

        Clyde clyde = Navigation.findUnitInBoard(Clyde.class, level.getBoard());
        assertNotNull(clyde);
        Optional<Direction> direction = clyde.nextAiMove();
        assertEquals(Optional.of(Direction.WEST), direction);
    }

    @Test
    void distanceLessThan8AndPathBlockedTest(){
        List<String> map = Arrays.asList(
            "#############",
            "#C#P        #",
            "#############"
        );
        Level level = ghostMapParser.parseMap(map);
        Player pacman = playerFactory.createPacMan();
        level.registerPlayer(pacman);

        Clyde clyde = Navigation.findUnitInBoard(Clyde.class, level.getBoard());
        assertNotNull(clyde);
        Optional<Direction> direction = clyde.nextAiMove();
        assertEquals(Optional.empty(), direction);
    }

    @Test
    void distanceLessThan8AndMultiplePathsTest(){
        List<String> map = Arrays.asList(
            "#############",
            "#   P       #",
            "#   C       #",
            "#           #",
            "#############"
        );
        Level level = ghostMapParser.parseMap(map);
        Player pacman = playerFactory.createPacMan();
        level.registerPlayer(pacman);

        Clyde clyde = Navigation.findUnitInBoard(Clyde.class, level.getBoard());
        assertNotNull(clyde);
        Optional<Direction> direction = clyde.nextAiMove();
        assertTrue(direction.isPresent());
        assertTrue(direction.get() == Direction.WEST || direction.get() == Direction.EAST || direction.get() == Direction.SOUTH);
    }

    /**
     * Distance equals 8 tests
    */

    @Test
    void distanceEquals8AndPathFreeTest() {
        List<String> map = Arrays.asList(
            "#############",
            "# C       P #",
            "#############"
        );
        Level level = ghostMapParser.parseMap(map);
        Player pacman = playerFactory.createPacMan();
        level.registerPlayer(pacman);

        Clyde clyde = Navigation.findUnitInBoard(Clyde.class, level.getBoard());
        assertNotNull(clyde);

        Optional<Direction> direction = clyde.nextAiMove();
        assertEquals(Optional.of(Direction.WEST), direction);
    }

    @Test
    void distanceEquals8AndPathBlockedTest(){
        List<String> map = Arrays.asList(
            "#############",
            "#C#      P  #",
            "#############"
        );
        Level level = ghostMapParser.parseMap(map);
        Player pacman = playerFactory.createPacMan();
        level.registerPlayer(pacman);

        Clyde clyde = Navigation.findUnitInBoard(Clyde.class, level.getBoard());
        assertNotNull(clyde);

        Optional<Direction> direction = clyde.nextAiMove();
        assertEquals(Optional.empty(), direction);
    }

    @Test
    void distanceEquals8AndMultiplePathsTest(){
        List<String> map = Arrays.asList(
            "#############",
            "#           #",
            "# C       P #",
            "#           #",
            "#############"
        );
        Level level = ghostMapParser.parseMap(map);
        Player pacman = playerFactory.createPacMan();
        level.registerPlayer(pacman);

        Clyde clyde = Navigation.findUnitInBoard(Clyde.class, level.getBoard());
        assertNotNull(clyde);

        Optional<Direction> direction = clyde.nextAiMove();
        assertEquals(Optional.of(Direction.WEST), direction);
    }

    /**
     * Boundary test
    */

    @Test
    void noClydeOnBoardTest(){
        List<String> map = Arrays.asList(
            "#############",
            "#           #",
            "#     P     #",
            "#           #",
            "#############"
        );
        Level level = ghostMapParser.parseMap(map);
        Player pacman = playerFactory.createPacMan();
        level.registerPlayer(pacman);

        Clyde clyde = Navigation.findUnitInBoard(Clyde.class, level.getBoard());
        assertNull(clyde);
    }

    @Test
    void noPacmanOnBoardTest(){
        List<String> map = Arrays.asList(
            "#############",
            "#           #",
            "#     C     #",
            "#           #",
            "#############"
        );
        Level level = ghostMapParser.parseMap(map);
        Clyde clyde = Navigation.findUnitInBoard(Clyde.class, level.getBoard());
        assertNotNull(clyde);
        Optional<Direction> direction = clyde.nextAiMove();
        assertEquals(Optional.empty(), direction);
    }

    @Test 
    void multipleClydesOnBoardTest(){
        List<String> map = Arrays.asList(
            "#############",
            "# C   P   C #",
            "#############"
        );
        Level level = ghostMapParser.parseMap(map);
        Player pacman = playerFactory.createPacMan();
        level.registerPlayer(pacman);

        for (int i = 0; i < 2; i++) {
            Clyde clyde = Navigation.findUnitInBoard(Clyde.class, level.getBoard());
            assertNotNull(clyde);
            Optional<Direction> direction = clyde.nextAiMove();
            if (i == 0)
                assertEquals(Optional.of(Direction.WEST), direction);
            else
                assertEquals(Optional.of(Direction.EAST), direction);
            //Remove Clyde from board to find the next one
            clyde.leaveSquare();
        }
    }

    @Test
    void multiplePlayersOnBoardTest(){
        List<String> map = Arrays.asList(
            "#############",
            "# P  C   P  #",
            "#############"
        );
        Level level = ghostMapParser.parseMap(map);
        Player pacman1 = playerFactory.createPacMan();
        Player pacman2 = playerFactory.createPacMan();
        level.registerPlayer(pacman1);
        level.registerPlayer(pacman2);

        Clyde clyde = Navigation.findUnitInBoard(Clyde.class, level.getBoard());
        assertNotNull(clyde);
        Optional<Direction> direction = clyde.nextAiMove();
        assertEquals(Optional.of(Direction.EAST), direction);
    }

    // This boundary test generates a failure, as the nextAiMove tries to move Clyde outside the board.
    // @Test
    // void clydeAtTheEdgeOfTheBoard(){
    //     List<String> map = Arrays.asList(
    //         "#########",
    //         "#C      #",
    //         "#      P#",
    //         "#########"
    //     );
    //     Level level = ghostMapParser.parseMap(map);
    //     Player pacman = playerFactory.createPacMan();
    //     level.registerPlayer(pacman);

    //     Clyde clyde = Navigation.findUnitInBoard(Clyde.class, level.getBoard());
    //     assertNotNull(clyde);
    //     Optional<Direction> direction = clyde.nextAiMove();
    //     Optional<Direction> dir = clyde.nextAiMove();
        
    //     assertTrue(dir.isPresent());
    //     Square current = clyde.getSquare();
    //     Square next = current.getSquareAt(dir.get());
    //     assertNotNull(next);
    //     assertTrue(next.isAccessibleTo(clyde));
    // }
}