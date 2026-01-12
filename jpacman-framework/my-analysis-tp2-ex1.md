# Specification-based Testing

For this exercise, I will design specification-based tests for the `nextAiMove` method of the `Clyde` ghost in the JPacman framework.

Here is the specification of the `nextAiMove` method:

```
/**
 * {@inheritDoc}
 *
 * <p>
 * Clyde has two basic AIs, one for when he's far from Pac-Man, and one for
 * when he is near to Pac-Man. 
 * When Clyde is far away from Pac-Man (beyond eight grid spaces),
 * Clyde behaves very much like Blinky, trying to move to Pac-Man's exact
 * location. However, when Clyde gets within eight grid spaces of Pac-Man,
 * he automatically changes his behavior and runs away
 * </p>
*/
```

## 1. Goal, inputs and outputs

- Goal: Move Clyde according to its AI rules.
- Input domain: No input parameters.
- Output domain: An `Optional<Direction>` representing Clyde's next move direction.

## 2. Explore the program (if needed)

## 3. Identify input and output partitions

### Input partitions

#### Individual inputs

Distance partitions:

- D1: Clyde is within 8 grid spaces
- D2: Clyde is beyond 8 grid spaces
- D3: Clyde is at exactly 8 grid spaces

Obstacle direction partitions:
- O1: Path of Clyde is free
- O2: Path of Clyde is blocked
- O3: Clyde has multiple valid moves

#### Combinations of input values

- Distance < 8 & path free
- Distance < 8 & path blocked
- Distance < 8 & multiple moves
- Distance > 8 & path free
- Distance > 8 & path blocked
- Distance > 8 & multiple moves
- Distance = 8 & path free
- Distance = 8 & path blocked
- Distance = 8 & multiple moves

### Output partitions

- Empty direction
- Direction towards Pac
- Direction away from Pac

## 4. Identify boundaries

- No Clyde on the Map
- Multiple Clydes on the Map

- No Pac-man on the Map
- Multiple Pac-men on the Map

- Clyde is on Pac-Man
- Clyde does not have a square partition
- Clyde is out of the board

- Pac-man does not have a square partition
- Pac-man is out of the board   
- Pac-man at the edge of the board

## 5. Select test cases

1. Distance < 8: DONE
   - T1: Path free => Direction away from Pac
   - T2: Path blocked => Empty direction
   - T3: Multiple moves => Direction away from Pac
2. Distance > 8: DONE
   - T4: Path free => Direction towards Pac
   - T5: Path blocked => Empty direction
   - T6: Multiple moves => Direction towards Pac
3. Distance = 8:
   - T7: Path free => Direction towards Pac
   - T8: Path blocked => Empty direction
   - T9: Multiple moves => Direction towards Pac
4. Boundary cases:
    - T10: No Clyde on the Map => Exception or Empty direction
    - T11: Multiple Clydes on the Map => Exception or Empty direction
    - T12: No Pac-man on the Map => Exception or Empty direction
    - ...

Some boundary cases are not implemented due to the complexity of setting up such scenarios in the JPacman framework. For example, testing "Clyde is on Pac-Man" would require manipulating the game state in a way that may not be straightforward with the existing classes and methods.