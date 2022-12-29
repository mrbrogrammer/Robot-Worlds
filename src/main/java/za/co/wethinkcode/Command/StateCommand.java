package za.co.wethinkcode.Command;

import org.json.JSONArray;
import org.json.JSONObject;
import za.co.wethinkcode.Protocol;
import za.co.wethinkcode.Server.ClientHandler;
import za.co.wethinkcode.Server.RobotWorldServer;

import java.util.ArrayList;
import java.util.List;

public class StateCommand extends Command{


    public StateCommand(JSONObject clientRequest) {
        super(clientRequest);
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

        clientResponse.put("state", new JSONObject(RobotWorldServer.getWorld().getPlayers().get(client)));

        return clientResponse.toString();
    }
}
