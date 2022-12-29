package za.co.wethinkcode.Command.Server.Commands;

import org.json.JSONObject;
import za.co.wethinkcode.Server.ClientHandler;
import za.co.wethinkcode.Server.RobotWorldServer;

import java.net.Socket;

public class KillCommand extends ServerCommands{
    private Socket kill;

    public KillCommand(Socket kill) {
        super(new JSONObject("kill"));
        this.kill = kill;
    }

    public KillCommand(JSONObject clientRequest) {
        super(clientRequest);
    }

    @Override
    public String execute() {
        for (ClientHandler client : RobotWorldServer.getPlayerList()) {
            client.close();
        }

        RobotWorldServer.getPool().shutdown();

        return "kill";
    }
}
