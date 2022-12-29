package za.co.wethinkcode.Command;

import org.json.JSONArray;
import org.json.JSONObject;

import za.co.wethinkcode.Position;
import za.co.wethinkcode.Protocol;
import za.co.wethinkcode.Robot;
import za.co.wethinkcode.Server.ClientHandler;
import za.co.wethinkcode.Server.RobotWorldServer;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class LaunchCommand extends Command {

    private Random random;

    public LaunchCommand(JSONObject clientRequest) {
        super(clientRequest);
        random = new Random();
    }

    @Override
    public String execute(ClientHandler client) {
        JSONObject clientRequest = getClientRequest();
        JSONObject result = new JSONObject();

        List<String> args = new ArrayList<>();
        JSONArray jArray = clientRequest.getJSONArray("arguments");
        if (jArray != null) {
            for (int i=0;i<jArray.length();i++){
                args.add(jArray.getString(i));
            }
        }

        if (args.size() != 1) {
            return new Protocol().parseArgumentsError();
        }

        if (!RobotWorldServer.getWorld().getPlayers().containsKey(client)) {
            RobotWorldServer.getWorld().addPlayers(client, new Robot(clientRequest.getString("robot")));
            RobotWorldServer.getWorld().getPlayers().get(client).setKind(args.get(0));
            Position p = RobotWorldServer.getWorld().getTOP_LEFT();
            
            int x = random.nextInt(p.getX()*-2) + p.getX();
            int y = random.nextInt(p.getY()*2) - p.getY();

            RobotWorldServer.getWorld().getPlayers().get(client).setPosition(new Position(x, y));
        } else {
            result.put("result", "ERROR");
            result.put("data", new JSONObject().put("message", "Too many of you in this world"));
            return result.toString();
        }

        result.put("result", "OK");

        JSONObject data = new JSONObject();
        data.put("position", "[0,0]");
        data.put("visibility", RobotWorldServer.getWorld().getVisibility());
        data.put("reload", RobotWorldServer.getWorld().getReloadTime() + "s");
        data.put("repair", RobotWorldServer.getWorld().getRepairTime() + ",");
        data.put("shields", RobotWorldServer.getWorld().getMaximumShieldStrength());

        result.put("data", data);
        result.put("state", new JSONObject(RobotWorldServer.getWorld().getPlayers().get(client)));

        return result.toString();
    }
}
