package za.co.wethinkcode.Command;

import org.json.JSONObject;
import za.co.wethinkcode.Command.Command;
import za.co.wethinkcode.Server.ClientHandler;

public class IncorrectCommand extends Command {

    public IncorrectCommand(JSONObject clientRequest) {
        super(clientRequest);
    }

    @Override
    public String execute(ClientHandler clientHandler) {
        JSONObject result = new JSONObject();
        result.put("result", "ERROR");
        result.put("data", new JSONObject().put("message", "Unsupported command"));
        return result.toString();
    }
}
