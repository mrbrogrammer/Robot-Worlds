package za.co.wethinkcode.Command;

import org.json.JSONArray;
import org.json.JSONObject;
import za.co.wethinkcode.Position;
import za.co.wethinkcode.Protocol;
import za.co.wethinkcode.Server.ClientHandler;
import za.co.wethinkcode.Server.RobotWorldServer;
import za.co.wethinkcode.World.IWorld;
import za.co.wethinkcode.World.IWorld.*;

import java.util.ArrayList;
import java.util.List;

public class LookCommand extends Command {
    public LookCommand(JSONObject clientRequest) {
        super(clientRequest);
    }
    @Override
    public String toString() {

        return null;
    }
    @Override
    public String execute(ClientHandler client) {
        JSONObject clientRequest = getClientRequest();
        JSONObject clientResponse = new JSONObject();
        Protocol protocol = new Protocol();
        List<String> args = new ArrayList<>();
        JSONArray jArray = clientRequest.getJSONArray("arguments");

        if (jArray != null) {
            for (int i = 0; i < jArray.length(); i++){
                args.add(jArray.getString(i));
            }
        }
        if (args.size() != 0) {
            return protocol.parseArgumentsError();
        }
        if (!RobotWorldServer.getWorld().getPlayers().containsKey(client)) {
            return protocol.robotDoesNotExistError();
        }

        clientResponse.put("Result", "OK");

        JSONArray objects = new JSONArray();

        Position oldPosition = RobotWorldServer.getWorld().getPlayers().get(client).getPosition();

        for (int i = 0; i <= RobotWorldServer.getWorld().getVisibility(); i++) {
            IWorld.UpdateResponse updateResponse = RobotWorldServer.getWorld().updatePosition(i, client);

            JSONObject object = new JSONObject();

            object.put("direction", RobotWorldServer.getWorld().getPlayers().get(client).getCurrentDirection());

            if (updateResponse.equals(UpdateResponse.SUCCESS)) {
                object.put("type", "There is nothing in the way.");

            } else if (updateResponse.equals(UpdateResponse.FAILED_OTHER_PLAYER)) {
                object.put("type", "There is a another player in way.");
            } else if (updateResponse.equals(UpdateResponse.FAILED_OUTSIDE_WORLD)) {
                break;
            } else if (updateResponse.equals(UpdateResponse.FAILED_OBSTRUCTED)) {
                object.put("type", "There is a obstacles in the way.");
                object.put("distance", i);
                objects.put(object);
                RobotWorldServer.getWorld().getPlayers().get(client).setPosition(oldPosition);
                break;
            }

            object.put("distance", i);
            objects.put(object);
            RobotWorldServer.getWorld().getPlayers().get(client).setPosition(oldPosition);
        }
        clientResponse.put("data", objects);
        clientResponse.put("state", new JSONObject(RobotWorldServer.getWorld().getPlayers().get(client)));

        return clientResponse.toString();
    }
}
