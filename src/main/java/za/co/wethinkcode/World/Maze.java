package za.co.wethinkcode.World;

import za.co.wethinkcode.Position;
import za.co.wethinkcode.obstacle.Obstacle;
import za.co.wethinkcode.obstacle.SquareObstacle;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Maze implements IMaze{

    private List<Obstacle> obstacles;
    private Random random;

    public Maze(int width, int height) {
        obstacles = new ArrayList<>();
        random = new Random();
        generateObstacles(width, height);
    }

    private void generateObstacles(int width, int height) {
        int amount = (int)((width * height) * 0.01);

        for (int i = 0; i < amount; i++) {
            int x = (int)(random.nextInt(width) - (width/2));
            int y = (int)(random.nextInt(height) - (height/2));

            obstacles.add(new SquareObstacle(x, y));
        }
    }

    @Override
    public List<Obstacle> getObstacles() {
        return obstacles;
    }

    @Override
    public boolean blocksPath(Position a, Position b) {
        for (Obstacle obstacle : obstacles) {
            if (obstacle.blocksPath(a, b)) return true;
        }
        return false;
    }
}
