package za.co.wethinkcode.World;

import za.co.wethinkcode.Position;
import za.co.wethinkcode.Server.ClientHandler;
import za.co.wethinkcode.obstacle.Obstacle;

import java.util.List;

public interface IWorld {


    /**
     * prints out all obstacles and robots int the world.
     */
    void showWorld();

    /**
     * Enum used to track direction
     */
    enum Direction {
        UP, RIGHT, DOWN, LEFT
    }

    
    /**
     * Updates the current direction your robot is facing in the world by cycling through the directions UP, RIGHT, BOTTOM, LEFT.
     * @param turnRight if true, then turn 90 degrees to the right, else turn left.
     */
    void updateDirection(boolean turnRight, ClientHandler client);
    
    /**
     * Enum that indicates response for updatePosition request
     */
    enum UpdateResponse {
        SUCCESS, //position was updated successfully
        FAILED_OUTSIDE_WORLD, //robot will go outside world limits if allowed, so it failed to update the position
        FAILED_OBSTRUCTED, //robot obstructed by at least one obstacle, thus cannot proceed.
        FAILED_OTHER_PLAYER // robot cannot occupy the same spot as another robot
    }

    Position CENTRE = new Position(0,0);

    /**
     * Updates the position of your robot in the world by moving the nrSteps in the robots current direction.
     * @param nrSteps steps to move in current direction
     * @return true if this does not take the robot over the world's limits, or into an obstacle.
     */
    UpdateResponse updatePosition(int nrSteps, ClientHandler client);


    /**
     * Checks if the new position will be allowed, i.e. falls within the constraints of the world, and does not overlap an obstacle.
     * @param position the position to check
     * @return true if it is allowed, else false
     */
    boolean isNewPositionAllowed(Position position);


    /**
     * @return the list of obstacles, or an empty list if no obstacles exist.
     */
    List<Obstacle> getObstacles();


    /**
     * check to see of another robot occupies that spot
     * @param newPosition
     * @return
     */
    boolean isAnotherOnSpot(Position newPosition);

}
