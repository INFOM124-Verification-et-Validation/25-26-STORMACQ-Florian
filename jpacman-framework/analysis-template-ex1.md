# Specification-based Testing

**Program to be tested**

```java
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
@Override
public Optional<Direction> nextAiMove() {
assert hasSquare();

Unit nearest = Navigation.findNearest(Player.class, getSquare());
if (nearest == null) {
    return Optional.empty();
}
assert nearest.hasSquare();
Square target = nearest.getSquare();

List<Direction> path = Navigation.shortestPath(getSquare(), target, this);
if (path != null && !path.isEmpty()) {
    Direction direction = path.get(0);
    if (path.size() <= SHYNESS) {
        return Optional.ofNullable(OPPOSITES.get(direction));
    }
    return Optional.of(direction);
}
return Optional.empty();
}
```

## 1. Goal, inputs and outputs
- Goal: Determine Clyde's next move based on his distance from Pac-Man
- Input domain: No input, but internal state includes the map, the player's position, and Clyde's position
- Output domain: The direction Clyde should move in, optionally (Direction or empty)

## 2. Explore the program (if needed)

Clyde has two behaviors based on his distance from Pac-Man:

1. If Clyde is more than eight grid spaces away from Pac-Man, he behaves like Blinky and tries to move towards Pac-Man.
2. If Clyde is within eight grid spaces of Pac-Man, he runs away from him.

The method can return no direction if there is no path to Pac-Man or if no player is found.

## 3. Identify input and output partitions

> What's a partition?
>
> A partition is a set of values that are treated the same by the program. For example, if a program behaves the same for all positive integers, then the set of all positive integers is a partition. To test this program, we can select one representative value from this partition, such as 1 or 42. We should also consider negative integers and zero as separate partitions if the program behaves differently for these values.

### Input partitions

#### Individual inputs

1. Distance partitions:
   - Distance > 8 grid spaces (Clyde moves towards Pac-Man)
   - Distance < 8 grid spaces (Clyde moves away from Pac-Man)
   - Distance = 8 grid spaces (boundary case)

2. Pac-Man not on the board partition:

3. Pac-Man at the edge of the board partition:

4. Pac-man does not have a square partition:

5. Obstacle direction partition:
   - Path of Clyde is free (no obstacles)
   - Path of Clyde is blocked (obstacles present)
   - Clyde is on Pac-Man
   - Clyde has multiple valid moves

6. Not valid map partition:
    - Multiple clyde on the map
    - Multiple pac-man on the map

#### Combinations of input values

1. Distance < 8 & path blocked
2. Distance < 8 & path free
3. Distance < 8 & multiple moves

4. Distance > 8 & path blocked
5. Distance > 8 & path free
6. Distance > 8 & multiple moves

7. Distance = 8 & path blocked
8. Distance = 8 & path free
9. Distance = 8 & multiple moves

### Output partitions

1. Move towards Pac-Man
2. Move away from Pac-Man
3. No move (empty)
4. Multiple valid moves

## 4. Identify boundaries

Not valid cases:

- Multiple Clyde instances on the map
- Multiple Pac-Man instances on the map
- Clyde is on Pac-Man
- Pac-man not on the board partition
- Pac-Man does not have a square partition

- Pac-Man at the edge of the board partition
- Pac-Man goes from when edge of the board to another edge of the board

## 5. Select test cases

**Distance < 8**
1. Path free $\Rightarrow$ Move away
2. Path blocked $\Rightarrow$ Empty direction
3. Multiple moves $\Rightarrow$ Move away

**Distance > 8**
4. Path free $\Rightarrow$ Move towards
5. Path blocked $\Rightarrow$ Empty direction
6. Multiple moves $\Rightarrow$ Move towards

**Distance = 8**
7. Path free $\Rightarrow$ Move away
8. Path blocked $\Rightarrow$ Empty direction
9. Multiple moves $\Rightarrow$ Move away

**Boundary cases**

1.  Multiple Clyde instances on the map
2.  Multiple Pac-Man instances on the map
3.  Clyde is on Pac
4.  Pac-Man not on the board
5.  Pac-Man does not have a square
6.  Pac-Man at the edge of the board
7.  Pac-Man goes from one edge of the board to another