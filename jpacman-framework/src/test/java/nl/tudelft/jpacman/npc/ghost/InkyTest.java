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

class InkyTest {
    private PacManSprites pacManSprites = new PacManSprites();
    private PlayerFactory playerFactory = new PlayerFactory(pacManSprites);
    private GhostFactory ghostFactory = new GhostFactory(pacManSprites);
    private LevelFactory levelFactory = new LevelFactory(pacManSprites, ghostFactory);
    private BoardFactory boardFactory = new BoardFactory(pacManSprites);
    MapParser ghostMapParser = new GhostMapParser(levelFactory, boardFactory, ghostFactory);

    @Test
    void pathExistingFromInkyToDestinationTest() {
         List<String> map = Arrays.asList(
            "########",
            "#I B   #",
            "#      #",
            "#   P  #",
            "########"
        );
        Level level = ghostMapParser.parseMap(map);
        Player pacman = playerFactory.createPacMan();
        level.registerPlayer(pacman);
        
        Inky inky = Navigation.findUnitInBoard(Inky.class, level.getBoard());
        assertNotNull(inky, "Inky should be present on the map.");
        
        Blinky blinky = Navigation.findUnitInBoard(Blinky.class, level.getBoard());
        assertNotNull(blinky, "Blinky should be present on the map.");

        Direction inkyDirection = inky.nextMove();

        assertNotNull(inkyDirection, "Inky should have a valid move towards the player.");
    }

    @Test
    void pathBlockedFromInkyToBlinkyTest(){
        List<String> map = Arrays.asList(
            "########",
            "#I#B   #",
            "# #    #",
            "#   P  #",
            "########"
        );

        Level level = ghostMapParser.parseMap(map);
        Player pacman = playerFactory.createPacMan();
        level.registerPlayer(pacman);

        Inky inky = Navigation.findUnitInBoard(Inky.class, level.getBoard());
        assertNotNull(inky, "Inky should be present on the map.");

        Blinky blinky = Navigation.findUnitInBoard(Blinky.class, level.getBoard());
        assertNotNull(blinky, "Blinky should be present on the map.");

        Direction inkyDirection = inky.nextMove();

        assertNotNull(inkyDirection, "Inky should have a valid move as the path to Blinky is blocked.");
        assertTrue(inkyDirection == Direction.SOUTH,
            "Inky should move SOUTH to navigate around the obstacle.");
    }

    @Test
    void noBlinkyOnMapInkyBehaviorTest() {
         List<String> map = Arrays.asList(
            "########",
            "#I     #",
            "#      #",
            "#   P  #",
            "########"
        );
        Level level = ghostMapParser.parseMap(map);
        Player pacman = playerFactory.createPacMan();
        level.registerPlayer(pacman);

        Inky inky = Navigation.findUnitInBoard(Inky.class, level.getBoard());
        assertNotNull(inky, "Inky should be present on the map.");

        Blinky blinky = Navigation.findUnitInBoard(Blinky.class, level.getBoard());
        assertNull(blinky, "Blinky should not be present on the map.");

        Direction inkyDirection = inky.nextMove();
        assertNotNull(inkyDirection, "Inky should have a valid move towards the player even without Blinky.");
    }

    @Test
    void noPlayerOnMapInkyBehaviorTest() {
         List<String> map = Arrays.asList(
            "########",
            "#I  B  #",
            "#      #",
            "#      #",
            "########"
        );
        Level level = ghostMapParser.parseMap(map);

        Inky inky = Navigation.findUnitInBoard(Inky.class, level.getBoard());
        assertNotNull(inky, "Inky should be present on the map.");

        Blinky blinky = Navigation.findUnitInBoard(Blinky.class, level.getBoard());
        assertNotNull(blinky, "Blinky should be present on the map.");

        Direction inkyDirection = inky.nextMove();

        assertNotNull(inkyDirection, "Inky should have a valid move even without the player present.");
        assertTrue(inkyDirection == Direction.EAST, 
            "Inky should move EAST on this map when no player is present.");
    }

    @Test
    void pathBlockedFromInkyToDestinationTest() {
         List<String> map = Arrays.asList(
            "########",
            "#I#B   #",
            "###    #",
            "#   P  #",
            "########"
        );
        Level level = ghostMapParser.parseMap(map);
        Player pacman = playerFactory.createPacMan();
        level.registerPlayer(pacman);

        Inky inky = Navigation.findUnitInBoard(Inky.class, level.getBoard());
        assertNotNull(inky, "Inky should be present on the map.");

        Blinky blinky = Navigation.findUnitInBoard(Blinky.class, level.getBoard());
        assertNotNull(blinky, "Blinky should be present on the map.");

        Direction inkyDirection = inky.nextMove();

        assertNull(inkyDirection, "Inky should not have a valid move towards the player as the path is blocked.");
    }
}