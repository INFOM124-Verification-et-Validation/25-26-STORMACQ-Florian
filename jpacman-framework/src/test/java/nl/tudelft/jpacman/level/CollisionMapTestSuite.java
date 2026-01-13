package nl.tudelft.jpacman.level;

import java.util.Collections;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import nl.tudelft.jpacman.board.Board;
import nl.tudelft.jpacman.board.Unit;
import nl.tudelft.jpacman.npc.Ghost;
import nl.tudelft.jpacman.board.Square;
import nl.tudelft.jpacman.board.BoardFactory;
import nl.tudelft.jpacman.board.Direction;
import nl.tudelft.jpacman.sprite.PacManSprites;
import nl.tudelft.jpacman.npc.ghost.*;
import com.google.common.collect.Lists;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

abstract class CollisionMapTestSuite {
    
    protected abstract CollisionMap createCollisionMap();
    
    protected CollisionMap collisionMap;
    protected PacManSprites sprites;
    protected BoardFactory boardFactory;
    protected PlayerFactory playerFactory;
    protected GhostFactory ghostFactory;
    
    // Mocks pour tests unitaires
    private Player playerMock;
    private Pellet pelletMock;
    private Ghost ghostMock;
    
    @BeforeEach
    void setUp() {
        // ========== INITIALISER collisionMap ==========
        collisionMap = createCollisionMap();
        
        sprites = new PacManSprites();
        boardFactory = new BoardFactory(sprites);
        playerFactory = new PlayerFactory(sprites);
        ghostFactory = new GhostFactory(sprites);
        
        playerMock = mock(Player.class);
        pelletMock = mock(Pellet.class);
        ghostMock = mock(Ghost.class);
        when(pelletMock.getValue()).thenReturn(10);
    }
    
    @Test
    void playerCollidesWithPelletTest() {
        collisionMap.collide(playerMock, pelletMock);
        
        verify(playerMock, times(1)).addPoints(pelletMock.getValue());
        verify(pelletMock, times(1)).leaveSquare();
        verify(playerMock, times(0)).setAlive(false);
    }
    
    @Test
    void playerCollidesWithNothingTest() {
        collisionMap.collide(playerMock, mock(Unit.class));
        
        verify(playerMock, times(0)).addPoints(org.mockito.Mockito.anyInt());
        verify(playerMock, times(0)).setAlive(false);
    }
    
    @Test
    void playerCollidesWithGhostTest() {
        collisionMap.collide(playerMock, ghostMock);
        
        verify(playerMock, times(1)).setAlive(false);
        verify(ghostMock, times(0)).leaveSquare();
    }
    
    @Test
    void playerWinsWhenCollectingLastPellet() {
        // Setup board
        Square[][] grid = new Square[2][1];
        grid[0][0] = boardFactory.createGround();
        grid[1][0] = boardFactory.createGround();
        Board board = boardFactory.createBoard(grid);
        
        // Place pellet
        Pellet pellet = new Pellet(10, sprites.getPelletSprite());
        pellet.occupy(grid[1][0]);
        
        // Create player
        Player player = playerFactory.createPacMan();
        
        Level level = new Level(
            board, 
            Lists.newArrayList(), 
            Lists.newArrayList(grid[0][0]), 
            createCollisionMap()
        );
        
        level.registerPlayer(player);
        Level.LevelObserver observer = mock(Level.LevelObserver.class);
        level.addObserver(observer);
        level.start();
        
        // Act
        level.move(player, Direction.EAST);
        
        // Assert
        verify(observer, times(1)).levelWon();
    }
    
    @Test
    void ghostCollidesWithPellet() {
        // Setup board
        Square[][] grid = new Square[2][1];
        grid[0][0] = boardFactory.createGround();
        grid[1][0] = boardFactory.createGround();
        Board board = boardFactory.createBoard(grid);
        
        // Place pellet
        Pellet pellet = new Pellet(10, sprites.getPelletSprite());
        pellet.occupy(grid[1][0]);
        
        // Create ghost
        Ghost ghost = ghostFactory.createBlinky();
        ghost.occupy(grid[0][0]);
        
        Level level = new Level(
            board, 
            Lists.newArrayList(ghost), 
            Collections.emptyList(), 
            createCollisionMap()
        );
        level.start();
        
        // Act
        level.move(ghost, Direction.EAST);
        
        // Assert
        assertThat(grid[1][0].getOccupants()).contains(ghost, pellet);
        assertThat(pellet.getSquare()).isEqualTo(grid[1][0]);
    }
}
