package za.co.wethinkcode.obstacle;

import za.co.wethinkcode.Position;
import za.co.wethinkcode.obstacle.Obstacle;

public class SquareObstacle implements Obstacle {
    private final int x;
    private final int y;

    public SquareObstacle(int x, int y){
        this.x = x;
        this.y = y;

    }
    @Override
    public int getBottomLeftX() {
        return x;
    }

    @Override
    public int getBottomLeftY() {
        return y;
    }


    @Override
    public boolean blocksPosition(Position position) {
        return position.getX() == x && position.getY() == y;
    }

    @Override
    public boolean blocksPath(Position a, Position b) {
        int distance = (int) Math.sqrt(Math.pow((b.getX() - a.getX()), 2) + Math.pow((b.getY() - a.getY()), 2));

        int diff_x = b.getX() - a.getX();
        int diff_y = b.getY() - a.getY();

        int interval_x = 0;
        int interval_y = 0;

        try{
            interval_x = diff_x / distance;
            interval_y = diff_y / distance;
        }catch (Exception e){
            return false;
        }

        for(int i = 1; i < distance + 1; i++){
            if (blocksPosition(new Position((a.getX() + interval_x * i), (a.getY() + interval_y * i))))
                return true;
        }
        return false;

    }
}




