package za.co.wethinkcode.Command;

import org.json.JSONArray;
import org.json.JSONObject;
import za.co.wethinkcode.Protocol;
import za.co.wethinkcode.Server.ClientHandler;
import za.co.wethinkcode.Server.RobotWorldServer;

import java.util.ArrayList;
import java.util.List;

public class ReloadCommand extends Command{

    public ReloadCommand(JSONObject clientRequest) {
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
            for (int i = 0; i < RobotWorldServer.getWorld().getReloadTime(); i++) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            int health = RobotWorldServer.getWorld().getMaxShots();
            RobotWorldServer.getWorld().getPlayers().get(clientHandler).setShotsLeft(health);
            RobotWorldServer.getWorld().getPlayers().get(clientHandler).setStatus("reload");

            clientResponse.put("result", "OK");
            clientResponse.put("data", new JSONObject().put("message", "Reloaded"));

            return clientResponse.toString();
        }
    }
