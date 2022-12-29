package za.co.wethinkcode.Command;

import org.json.JSONArray;
import org.json.JSONObject;
import za.co.wethinkcode.Protocol;
import za.co.wethinkcode.Server.ClientHandler;
import za.co.wethinkcode.Server.RobotWorldServer;

import java.util.ArrayList;
import java.util.List;

public class TurnCommand extends Command {

    public TurnCommand(JSONObject clientRequest) {
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

        clientResponse.put("result", "OK");
        clientResponse.put("data", new JSONObject().put("message", "Done"));

        if (args.get(0).equals("right")) {
            RobotWorldServer.getWorld().updateDirection(true, clientHandler);
        } else if (args.get(0).equals("left")) {
            RobotWorldServer.getWorld().updateDirection(false, clientHandler);
        } else {
            return protocol.parseArgumentsError();
        }
        clientResponse.put("state", new JSONObject(RobotWorldServer.getWorld().getPlayers().get(clientHandler)));

        return clientResponse.toString();
    }
}

