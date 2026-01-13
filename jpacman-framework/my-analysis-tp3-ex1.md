# Specification-based Testing

## 1. Goal, inputs and outputs
- Goal: Determine the effect of collisions between entities in JPacman.
- Input domain: 
  - Collider: {Player, Ghost}
  - Collidee: {Pellet, Wall, Ghost, last pellet, /}
- Output domain: void (state changes in the game)

## 2. Explore the program (if needed)

## 3. Identify input and output partitions

### Input partitions

#### Individual inputs

| Collider | Collidee | Consequence |
|----------|----------|-------------|
| Player |  Pellet  | Player moves, earns the point for the pellet and the pellet disappears |
| Player |  /   | Player moves and do not scores point |
| Player | Wall | Player does not move |
| Player | Ghost | Player dies, game over |
| Player | last pellet | Player moves, earns the point for the pellet and wins the game |
| | | |
| Ghost | / | Ghost moves |
| Ghost | Pellet | Ghost moves, pellet disappears |
| Ghost (hidding a pellet) | / | Ghost moves and pellet appaired |
| Ghost | Player | Player dies, the game is over |

#### Combinations of input values

1. (Player, Pellet) -> Player moves, earns the point for the pellet and the pellet disappears
2. (Player, /) -> Player moves and do not scores point
3. (Player, Wall) -> Player does not move
4. (Player, Ghost) -> Player dies, game over
5. (Player, last pellet) -> Player moves, earns the point for the pellet and wins the game
6. (Ghost, /) -> Ghost moves
7. (Ghost, Pellet) -> Ghost moves, pellet disappears
8. (Ghost (hidding a pellet), /) -> Ghost moves and pellet appaired
9. (Ghost, Player) -> Player dies, the game is over

### Output partitions

## 4. Identify boundaries

What if the collidee is null? 
What if the collider is null?
What if both are null?
What if the collidee is an unexpected entity (e.g., a wall for a ghost)?

## 5. Select test cases

1. Test case 1: (Player, Pellet) -> Player moves, earns the point for the pellet and the pellet disappears
2. Test case 2: (Player, /) -> Player moves and do not scores
3. Test case 3: (Player, Wall) -> Player does not move
4. Test case 4: (Player, Ghost) -> Player dies, game over
5. Test case 5: (Player, last pellet) -> Player moves, earns the point for the pellet and wins the game
6. Test case 6: (Ghost, /) -> Ghost moves
7. Test case 7: (Ghost, Pellet) -> Ghost moves, pellet disappears
8. Test case 8: (Ghost (hidding a pellet), /) -> Ghost moves and pellet appeared
9. Test case 9: (Ghost, Player) -> Player dies, the game is over
10. Test case 10: (Player, null) -> Handle null collidee
11. Test case 11: (null, Pellet) -> Handle null collider
12. Test case 12: (null, null) -> Handle both collider and collidee null
13. Test case 13: (Ghost, Wall) -> Handle unexpected collidee for ghost
14. Test case 14: (Pellet, Player) -> Handle unexpected collider for pellet
