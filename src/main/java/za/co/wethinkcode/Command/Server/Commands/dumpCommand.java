package za.co.wethinkcode.Command.Server.Commands;

import org.json.JSONObject;
import za.co.wethinkcode.Server.RobotWorldServer;

public class dumpCommand extends ServerCommands{

    public dumpCommand(JSONObject clientRequest) {
        super(clientRequest);

    }

    @Override
    public String execute() {
        RobotWorldServer.getWorld().showWorld();
        return "";
    }
}
