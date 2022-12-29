package za.co.wethinkcode.World;

import za.co.wethinkcode.Position;
import za.co.wethinkcode.Robot;
import za.co.wethinkcode.Server.ClientHandler;
import za.co.wethinkcode.obstacle.Obstacle;

import java.util.HashMap;
import java.util.List;

public class World implements IWorld{
    private final Position TOP_LEFT;
    private final Position BOTTOM_RIGHT;
    private HashMap<ClientHandler, Robot> players;
    private Maze maze;

    private int repairTime;
    private int reloadTime;
    private int visibility;
    private final int maximumShieldStrength;
    private final int maxShots;
    private Robot hitRobot;



    public World(int args [], Maze maze) {
        this.TOP_LEFT = new Position((args[0]/2) * -1, args[1]/2);
        this.BOTTOM_RIGHT = new Position(args[0]/2, (args[1]/2) * -1);
        this.players = new HashMap<>();
        this.maze = maze;
        this.repairTime = 3;
        this.reloadTime = args[4];
        this.visibility = args[2];
        this.maximumShieldStrength = args[3];
        this.maxShots = 10;
    }

    @Override
    public UpdateResponse updatePosition(int nrSteps, ClientHandler client) {
        int newX = players.get(client).getPosition().getX();
        int newY = players.get(client).getPosition().getY();

        if (Direction.UP.equals(players.get(client).getCurrentDirection())) {
            newY = newY + nrSteps;
        }else if (Direction.DOWN.equals(players.get(client).getCurrentDirection()))
            newY = newY - nrSteps;
        else if (Direction.RIGHT.equals(players.get(client).getCurrentDirection())){
            newX = newX + nrSteps;
        }else if (Direction.LEFT.equals(players.get(client).getCurrentDirection())){
            newX = newX - nrSteps;
        }

        Position newPosition = new Position(newX, newY);

        if (maze.blocksPath(players.get(client).getPosition(), newPosition)) return UpdateResponse.FAILED_OBSTRUCTED;

        if (isAnotherOnSpot(newPosition)) return UpdateResponse.FAILED_OTHER_PLAYER;

        if (isNewPositionAllowed(newPosition)) {
            players.get(client).setPosition(newPosition);
            return UpdateResponse.SUCCESS;
        }

        return UpdateResponse.FAILED_OUTSIDE_WORLD;
    }

    @Override
    public boolean isAnotherOnSpot(Position newPosition) {
        for (Robot robot : players.values()) {
            if (newPosition.equals(robot.getPosition())) {
                this.hitRobot = robot;
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean isNewPositionAllowed(Position position) {
        return position.isIn(TOP_LEFT,BOTTOM_RIGHT);
    }

    @Override
    public List<Obstacle> getObstacles() {
        return maze.getObstacles();
    }

    @Override
    public void showWorld() {
        System.out.println("TOP_LEFT: " + TOP_LEFT.getX() + " " + TOP_LEFT.getY());
        System.out.println("BOTTOM_RIGHT: " + BOTTOM_RIGHT.getX() + " " + BOTTOM_RIGHT.getY());

        if (getObstacles().size() > 0) {
            System.out.println("There are some obstacle: ");
            for (Obstacle obstacle : getObstacles()) {
                System.out.println("- at position " + obstacle.getBottomLeftX() + "," + obstacle.getBottomLeftY());
            }
        }

        for (Robot robot : players.values()) {
            System.out.println(robot.getName() + " state: " + robot);
        }
    }

    @Override
    public void updateDirection(boolean turnRight, ClientHandler client) {
        switch (players.get(client).getCurrentDirection()) {
            case UP:
                players.get(client).setCurrentDirection(turnRight ? Direction.RIGHT : Direction.LEFT);
                break;
            case RIGHT:
                players.get(client).setCurrentDirection(turnRight ? Direction.DOWN : Direction.UP);
                break;
            case LEFT:
                players.get(client).setCurrentDirection(turnRight ? Direction.UP : Direction.DOWN);
                break;
            case DOWN:
                players.get(client).setCurrentDirection(turnRight ? Direction.LEFT : Direction.RIGHT);
                break;
        }
    }

    public void addPlayers(ClientHandler client, Robot robot) {
        this.players.put(client, robot);
    }

    public void removePlayers(ClientHandler client) {
        this.players.remove(client);
    }

    public HashMap<ClientHandler, Robot> getPlayers() {
        return this.players;
    }
    public int getRepairTime() {
        return repairTime;
    }

    public int getReloadTime() {
        return reloadTime;
    }

    public int getVisibility() {
        return visibility;
    }

    public int getMaximumShieldStrength() {
        return maximumShieldStrength;
    }

    public int getMaxShots() {
        return maxShots;
    }

    public Robot getHitRobot() {
        return hitRobot;

    }

    public Position getTOP_LEFT() {
        return this.TOP_LEFT;
    }
}
