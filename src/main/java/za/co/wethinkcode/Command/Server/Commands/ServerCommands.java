package za.co.wethinkcode.Command.Server.Commands;


import org.json.JSONObject;

public abstract class ServerCommands {
    private JSONObject clientRequest;

    public ServerCommands(JSONObject clientRequest) {
        this.clientRequest = clientRequest;
    }

    public abstract String execute();

    public JSONObject getClientRequest() {
        return clientRequest;
    }

    public static ServerCommands create(String instruction) {

        JSONObject clientRequest = new JSONObject(instruction);

        switch (clientRequest.get("command").toString()){
            case "kill":
                return new KillCommand(clientRequest);
            case "dump":
                return new dumpCommand(clientRequest);
            case "robots":
                return new RobotCommand(clientRequest);
            default:
                throw new IllegalArgumentException("Incorrect command");
        }
    }
}
