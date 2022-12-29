package za.co.wethinkcode;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

import za.co.wethinkcode.obstacle.Obstacle;
import za.co.wethinkcode.obstacle.SquareObstacle;

public class ObstacleTest {

    @Test
    void testObstacleDimensions() {
        Obstacle obstacle = new SquareObstacle(1,1);
        assertEquals(1, obstacle.getBottomLeftX());
        assertEquals(1, obstacle.getBottomLeftY());
    }

    @Test
    void testBlockPosition(){
        Obstacle obstacle = new SquareObstacle(1,1);
        assertTrue(obstacle.blocksPosition(new Position(1,1)));
        assertFalse(obstacle.blocksPosition(new Position(0,5)));
        assertFalse(obstacle.blocksPosition(new Position(6,5)));
    }

    @Test
    void testBlockPath(){
        Obstacle obstacle = new SquareObstacle(1,1);
        assertTrue(obstacle.blocksPath(new Position(1,0), new Position(1,50)));
        assertTrue(obstacle.blocksPath(new Position(1,-20), new Position(1,50)));
        assertFalse(obstacle.blocksPath(new Position(2,-10), new Position(2, 100)));
        assertFalse(obstacle.blocksPath(new Position(-10,5), new Position(20, 5)));
        assertFalse(obstacle.blocksPath(new Position(0,1), new Position(0, 100)));
        assertFalse(obstacle.blocksPath(new Position(1,6), new Position(1, 100)));
    }
}
