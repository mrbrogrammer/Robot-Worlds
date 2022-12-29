package za.co.wethinkcode.Command;

import org.json.JSONArray;
import org.json.JSONObject;
import za.co.wethinkcode.Position;
import za.co.wethinkcode.Protocol;
import za.co.wethinkcode.Robot;
import za.co.wethinkcode.Server.ClientHandler;
import za.co.wethinkcode.Server.RobotWorldServer;
import za.co.wethinkcode.World.IWorld;
import za.co.wethinkcode.World.World;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
public class FireCommand extends Command {

    public FireCommand(JSONObject clientRequest) {
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

        if (args.size() != 0) {
            return protocol.parseArgumentsError();
        }

        if (!RobotWorldServer.getWorld().getPlayers().containsKey(clientHandler)) {
            return protocol.robotDoesNotExistError();
        }

        clientResponse.put("Result", "OK");
        Position oldPosition = RobotWorldServer.getWorld().getPlayers().get(clientHandler).getPosition();

        RobotWorldServer.getWorld().getPlayers().get(clientHandler).setShotsLeft(RobotWorldServer.getWorld().getPlayers().get(clientHandler).getShotsLeft() - 1);

        for (int i = 1; i <= RobotWorldServer.getWorld().getMaxShots(); i++) {
            IWorld.UpdateResponse updateResponse = RobotWorldServer.getWorld().updatePosition(i, clientHandler);

            RobotWorldServer.getWorld().getPlayers().get(clientHandler).setPosition(oldPosition);

            JSONObject data = new JSONObject();

            if (updateResponse.equals(IWorld.UpdateResponse.FAILED_OTHER_PLAYER)) {
                RobotWorldServer.getWorld().getHitRobot().setShieldHealth(RobotWorldServer.getWorld().getHitRobot().getShieldHealth() - 1);

                data.put("message", "Hit");
                data.put("distance", i);
                String name = RobotWorldServer.getWorld().getHitRobot().getName();
                data.put("robot", name);
                data.put("state", new JSONObject(RobotWorldServer.getWorld().getHitRobot()));

                clientResponse.put("data", data);
                clientResponse.put("state", new JSONObject(RobotWorldServer.getWorld().getPlayers().get(clientHandler)));

                if (RobotWorldServer.getWorld().getHitRobot().getShieldHealth() <=0) {
                    for (Map.Entry<ClientHandler, Robot> entry : RobotWorldServer.getWorld().getPlayers().entrySet()) {
                        if (entry.getValue().equals(RobotWorldServer.getWorld().getHitRobot())) {
                            RobotWorldServer.getWorld().removePlayers(entry.getKey());
                            break;
                        }
                    }
                }

                return clientResponse.toString();

            } else if (updateResponse.equals(IWorld.UpdateResponse.FAILED_OBSTRUCTED)) {
                break;
            }
        }

        clientResponse.put("data", new JSONObject().put("message", "Miss"));
        clientResponse.put("state", new JSONObject(RobotWorldServer.getWorld().getPlayers().get(clientHandler)));

        return clientResponse.toString();
    }
}
