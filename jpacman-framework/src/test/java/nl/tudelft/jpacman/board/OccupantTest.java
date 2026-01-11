package nl.tudelft.jpacman.board;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Test suite to confirm that {@link Unit}s correctly (de)occupy squares.
 *
 * @author Jeroen Roosen 
 *
 */
class OccupantTest {

    /**
     * The unit under test.
     */
    private Unit unit;

    /**
     * Resets the unit under test.
     */
    @BeforeEach
    void setUp() {
        unit = new BasicUnit();
    }

    /**
     * Asserts that a unit has no square to start with.
     */
    @Test
    void noStartSquare() {
        assertThat(unit.hasSquare()).isFalse();
    }

    /**
     * Tests that the unit indeed has the target square as its base after
     * occupation.
     */
    @Test
    void testOccupy() {
        // Arrange
        Square target = new BasicSquare();

        // Act
        unit.occupy(target);

        // Assert
        assertThat(unit.getSquare()).isEqualTo(target);
        assertThat(target.getOccupants()).contains(unit);
        assertThat(target.getOccupants().size()).isEqualTo(1);
    }

    /**
     * Test that the unit indeed has the target square as its base after
     * double occupation.
     */
    @Test
    void testReoccupy() {
        // Arrange
        Square first = new BasicSquare();
        Square second = new BasicSquare();

        // Act
        unit.occupy(first);
        unit.occupy(second);

        // Assert
        assertThat(unit.getSquare()).isEqualTo(second);

        assertThat(first.getOccupants()).doesNotContain(unit);
        assertThat(first.getOccupants().size()).isEqualTo(0);

        assertThat(second.getOccupants()).contains(unit);
        assertThat(second.getOccupants().size()).isEqualTo(1);
    }
}
