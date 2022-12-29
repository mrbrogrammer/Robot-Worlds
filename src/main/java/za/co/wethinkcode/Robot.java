package za.co.wethinkcode;

import org.json.JSONArray;
import org.json.JSONObject;
import za.co.wethinkcode.Server.RobotWorldServer;
import za.co.wethinkcode.World.IWorld;

import java.util.Arrays;

public class Robot {
    private String name;

    private final Position CENTRE = new Position(0,0);
    private Position position;
    private IWorld.Direction currentDirection;
    private String kind;
    private int shieldHealth;
    private int shotsLeft;
    private String status;

    public Robot(String name) {
        this.name = name;
        this.position = CENTRE;
        this.currentDirection = IWorld.Direction.UP;
        this.shieldHealth = RobotWorldServer.getWorld().getMaximumShieldStrength();
        this.shotsLeft = RobotWorldServer.getWorld().getMaxShots();
        this.status = "NORMAL";
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position newPosition) {
        this.position = newPosition;
    }

    public IWorld.Direction getCurrentDirection() {
        return currentDirection;
    }

    public void setCurrentDirection(IWorld.Direction direction) {
        this.currentDirection = direction;
    }

    public String getName(){
        return this.name;
    }

    public void setKind(String kind) {
        switch (kind) {
            case "sniper":
                this.kind = "sniper";
//                this.gunRange = 20;
//                this.gunDamage = 5;
                break;
            case "rifle":
                this.kind = "rifle";
                break;
            default:
                this.kind = "pistol";
                break;
        }
    }

    public int getShieldHealth() {
        return shieldHealth;
    }

    public int getShotsLeft() {
        return shotsLeft;
    }

    public void setShieldHealth(int shieldHealth) {
        this.shieldHealth = shieldHealth;
    }

    public void setShotsLeft(int shotsLeft) {
        this.shotsLeft = shotsLeft;
    }

    public void setStatus(String status) {
        this.status = status;
    }



    public String getKind() {
        return kind;
    }

    @Override
    public String toString() {
        JSONObject details = new JSONObject();
        details.put("position", new JSONArray(Arrays.asList(position.getX(), position.getY())));
        details.put("direction", currentDirection);
        details.put("shields", shieldHealth);
        details.put("shots", shotsLeft);
        details.put("status", status);
        return details.toString();
    }
}
