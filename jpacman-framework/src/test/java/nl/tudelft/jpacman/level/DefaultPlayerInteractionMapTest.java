package nl.tudelft.jpacman.level;

class DefaultPlayerInteractionMapTest extends CollisionMapTestSuite {
    @Override
    protected CollisionMap createCollisionMap() {
        return new DefaultPlayerInteractionMap();
    }
}