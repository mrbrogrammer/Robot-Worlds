package za.co.wethinkcode.Command.Server.Commands;

import org.json.JSONArray;
import org.json.JSONObject;
import za.co.wethinkcode.Robot;
import za.co.wethinkcode.Server.RobotWorldServer;

public class RobotCommand extends ServerCommands {

    public RobotCommand(JSONObject clientRequest) {
        super(clientRequest);
    }

    @Override
    public String execute() {
        JSONObject response = new JSONObject();
        JSONArray robots = new JSONArray();

        for (Robot robot : RobotWorldServer.getWorld().getPlayers().values()) {
            JSONObject information = new JSONObject();
            information.put("Robot", robot.getName());
            information.put("state", robot);
            robots.put(information);
        }
        response.put("robots", robots);

        return response.toString();
    }
}
