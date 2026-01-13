package nl.tudelft.jpacman.level;

class PlayerCollisionsTest extends CollisionMapTestSuite {
    @Override
    protected CollisionMap createCollisionMap() {
        return new PlayerCollisions();
    }
}