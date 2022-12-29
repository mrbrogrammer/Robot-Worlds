package za.co.wethinkcode.Command;

import org.json.JSONArray;
import org.json.JSONObject;
import za.co.wethinkcode.Protocol;
import za.co.wethinkcode.Server.ClientHandler;
import za.co.wethinkcode.Server.RobotWorldServer;
import za.co.wethinkcode.World.IWorld;

import java.util.ArrayList;
import java.util.List;

public class MovementCommand extends Command{


    public MovementCommand(JSONObject clientRequest) {
        super(clientRequest);
    }

    @Override
    public String execute(ClientHandler clientHandler) {
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

        if (args.size() != 1) {
            return protocol.parseArgumentsError();
        }

        if (!RobotWorldServer.getWorld().getPlayers().containsKey(clientHandler)) {
            return protocol.robotDoesNotExistError();
        }
        int steps;
        try {
            steps = clientRequest.get("command").toString().equals("forward") ? Integer.parseInt(args.get(0)) : Integer.parseInt(args.get(0)) * -1;
        } catch (IllegalArgumentException e) {
            return protocol.parseArgumentsError();
        }

        IWorld.UpdateResponse updateResponse = RobotWorldServer.getWorld().updatePosition(steps,clientHandler);
        clientResponse.put("result","OK");

        if (updateResponse.equals(IWorld.UpdateResponse.SUCCESS)){
            clientResponse.put("data",new JSONObject().put("message","Done"));

        } else if (updateResponse.equals(IWorld.UpdateResponse.FAILED_OBSTRUCTED)) {
            clientResponse.put("data", new JSONObject().put("message", "Obstructed"));
        } else if (updateResponse.equals(IWorld.UpdateResponse.FAILED_OUTSIDE_WORLD)){
            clientResponse.put("data",new JSONObject().put("message","Outside World"));
        } else if (updateResponse.equals(IWorld.UpdateResponse.FAILED_OTHER_PLAYER)) {
            clientResponse.put("data", new JSONObject().put("message", "Another player occupying spot"));
        }
        clientResponse.put("state",new JSONObject(RobotWorldServer.getWorld().getPlayers().get(clientHandler)));
        return clientResponse.toString();
    }
}
