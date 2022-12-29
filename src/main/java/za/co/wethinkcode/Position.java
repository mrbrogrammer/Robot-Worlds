package za.co.wethinkcode;

public class Position {
    private final int x;
    private final int y;
    public Position position;

    public Position(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Gets the positions x
     * @return int x.
     */
    public int getX() {
        return x;
    }

    /**
     * Gets the positions y
     * @return int y.
     */
    public int getY() {
        return y;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Position position = (Position) o;

        if (x != position.x) return false;
        return y == position.y;
    }

    /**
     * Checks whether the position is given area
     * @param topLeft and bottomRight are positions that will determine the area that is safe to move in
     * @return true is the position is in the area else false.
     */
    public boolean isIn(Position topLeft, Position bottomRight) {
        boolean withinTop = this.y <= topLeft.getY();
        boolean withinBottom = this.y >= bottomRight.getY();
        boolean withinLeft = this.x >= topLeft.getX();
        boolean withinRight = this.x <= bottomRight.getX();
        return withinTop && withinBottom && withinLeft && withinRight;
    }
}
