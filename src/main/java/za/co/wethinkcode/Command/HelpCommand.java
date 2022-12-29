package za.co.wethinkcode.Command;

import org.json.JSONArray;
import org.json.JSONObject;
import za.co.wethinkcode.Protocol;
import za.co.wethinkcode.Server.ClientHandler;
import za.co.wethinkcode.Server.RobotWorldServer;

import java.util.ArrayList;
import java.util.List;

public class HelpCommand extends Command{

    public HelpCommand(JSONObject clientRequest) {
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

        clientResponse.put("LAUNCH"," - launches the robots to the world");
        clientResponse.put("LOOK"," - robot looks around for a maximum distance specified by the world");
        clientResponse.put("STATE"," - gives the state of the robot");
        clientResponse.put("KILL"," - kills the server");
        clientResponse.put("DUMP"," - displays a representation of the world's state showing robots, obstacles");
        clientResponse.put("FORWARD"," - moves the robot forward, e.g. forward 1");
        clientResponse.put("BACK"," - moves the robot back, e.g. back 1");
        clientResponse.put("TURN"," - turns the robot either left or right");
        clientResponse.put("ROBOTS"," - shows all the robots in the world");
        clientResponse.put("FIRE"," - shows all the robots in the world");
        clientResponse.put("REPAIR"," - repairs the shield");
        clientResponse.put("RELOAD"," - instructed to reload the gun");


        return clientResponse.toString();
    }

}
