package za.co.wethinkcode.Command;

import org.json.JSONObject;
import za.co.wethinkcode.Server.ClientHandler;

public abstract class Command {

    private JSONObject clientRequest;

    public Command(JSONObject clientRequest) {
        this.clientRequest = clientRequest;
    }

    public abstract String execute(ClientHandler clientHandler);

    public JSONObject getClientRequest() {
        return clientRequest;
    }

    /**
     * creates a command according to the instructions.
     *
     * @param instruction used to determind the command and its arguments.
     * @return
     */
    public static Command create(String instruction) {
        JSONObject clientRequest = new JSONObject(instruction);

        switch (clientRequest.get("command").toString()) {
            case "launch":
                return new LaunchCommand(clientRequest);
            case "state":
                return new StateCommand(clientRequest);
            case "look":
                return new LookCommand(clientRequest);
            case "help":
                return new HelpCommand(clientRequest);
            case "forward":
            case "back":
                return new MovementCommand(clientRequest);
            case "repair" :
                return new RepairCommand(clientRequest);
            case "reload" :
                return new ReloadCommand(clientRequest);
            case "turn":
                return new TurnCommand(clientRequest);
            case "off":
                return new OffCommand(clientRequest);
            case "fire":
                return new FireCommand(clientRequest);
            default:
                return new IncorrectCommand(clientRequest);

        }
    }
}

