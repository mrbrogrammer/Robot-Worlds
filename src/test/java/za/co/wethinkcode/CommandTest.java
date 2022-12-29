package za.co.wethinkcode;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import org.json.JSONObject;

import za.co.wethinkcode.Command.*;
import za.co.wethinkcode.Server.ClientHandler;
import za.co.wethinkcode.Server.RobotWorldServer;

public class CommandTest {

    @Test
    public void invalidTest() {
        String request = new Protocol().createRequest("My Guy","lol command");

        Command command = Command.create(request);
        assertEquals(IncorrectCommand.class, command.getClass());
    }

    @Test
    public void launchTest() {
        String request = new Protocol().createRequest("My Guy","launch");

        Command command = Command.create(request);
        assertEquals(LaunchCommand.class, command.getClass());
    }

    @Test
    public void stateTest() {
        String request = new Protocol().createRequest("My Guy","state");

        Command command = Command.create(request);
        assertEquals(StateCommand.class, command.getClass());
    }

    @Test
    public void executeInvalid() {
        RobotWorldServer.createWorld(new int[]{10, 10, 10, 10, 5});
        String request = new Protocol().createRequest("My Guy","invalidCommand");

        Command command = Command.create(request);

        String expected = "{\"result\":\"ERROR\"," +
                "\"data\":{\"message\":\"Unsupported command\"}}";
        String actual = command.execute(new ClientHandler());

        assertEquals(expected, actual);
    }

    @Test
    public void executeLaunchInvalidArgs() {
        RobotWorldServer.createWorld(new int[]{10, 10, 10, 10, 5});
        String request = new Protocol().createRequest("My Guy","launch lol lol");

        Command command = Command.create(request);

        String expected = "{\"result\":\"ERROR\"," +
                "\"data\":{\"message\":\"Could not parse arguments\"}}";
        String actual = command.execute(new ClientHandler());

        assertEquals(expected, actual);
    }

    @Test
    public void executeLaunch() {
        RobotWorldServer.createWorld(new int[]{10, 10, 10, 10, 5});
        String request = new Protocol().createRequest("My Guy","launch sniper");

        Command command = Command.create(request);

        String actual = command.execute(new ClientHandler());

        assertEquals(JSONObject.class, new JSONObject(actual).getClass());
    }
    @Test
    public void executeState() {
        RobotWorldServer.createWorld(new int[]{10, 10, 10, 10, 5});
        ClientHandler testGuy = new ClientHandler();

        String launchRobot = new Protocol().createRequest("My Guy","launch sniper ");
        Command command = Command.create(launchRobot);
        command.execute(testGuy);

        String request = new Protocol().createRequest("My Guy","state");
        command = Command.create(request);

        String actual = command.execute(testGuy);

        assertEquals(JSONObject.class, new JSONObject(actual).getClass());
    }

    @Test
    public void helpTest() {
        String request = new Protocol().createRequest("My Guy","help");

        Command command = Command.create(request);
        assertEquals(HelpCommand.class, command.getClass());
    }

    @Test
    public void ForwardTest() {
        String request = new Protocol().createRequest("My Guy","forward");
        Command command = Command.create(request);
        assertEquals(MovementCommand.class, command.getClass());
    }

    @Test
    public void BackTest() {
        String request = new Protocol().createRequest("My Guy","back");
        Command command = Command.create(request);
        assertEquals(MovementCommand.class, command.getClass());
    }

    @Test
    public void executeHelp() {
        RobotWorldServer.createWorld(new int[]{10, 10, 10, 10, 5});
        String request = new Protocol().createRequest("My Guy","help");

        Command command = Command.create(request);

        String expected = "{\"REPAIR\":\" - repairs the shield\"," +
                "\"DUMP\":\" - displays a representation of the world's state showing robots, obstacles\"," +
                "\"RELOAD\":\" - instructed to reload the gun\"," +
                "\"FIRE\":\" - shows all the robots in the world\","+
                "\"STATE\":\" - gives the state of the robot\"," +
                "\"BACK\":\" - moves the robot back, e.g. back 1\"," +
                "\"FORWARD\":\" - moves the robot forward, e.g. forward 1\"," +
                "\"TURN\":\" - turns the robot either left or right\","+
                "\"LOOK\":\" - robot looks around for a maximum distance specified by the world\"," +
                "\"ROBOTS\":\" - shows all the robots in the world\"," +
                "\"KILL\":\" - kills the server\","+
                "\"LAUNCH\":\" - launches the robots to the world\"}";
        String actual = command.execute(new ClientHandler());

        assertEquals(expected, actual);
    }

    @Test
    public void repairTest() {
        String request = new Protocol().createRequest("My Guy","repair");
        Command command = Command.create(request);
        assertEquals(RepairCommand.class, command.getClass());
    }

    @Test
    public void lookTest() {
        String request = new Protocol().createRequest("My Guy","look");
        Command command = Command.create(request);
        assertEquals(LookCommand.class, command.getClass());
    }
    @Test
    public void reloadTest() {
        String request = new Protocol().createRequest("My Guy","reload");
        Command command = Command.create(request);
        assertEquals(ReloadCommand.class, command.getClass());
    }


    @Test
    public void executeReload() {
        RobotWorldServer.createWorld(new int[]{10, 10, 10, 10, 5});
        ClientHandler testGuy = new ClientHandler();

        String launchRobot = new Protocol().createRequest("My Guy","launch sniper");
        Command command = Command.create(launchRobot);
        command.execute(testGuy);

        String request = new Protocol().createRequest("My Guy","reload");
        command = Command.create(request);


        String expected = "{\"result\":\"OK\"," +
                "\"data\":{\"message\":\"Reloaded\"}}";
        String actual = command.execute(testGuy);

        assertEquals(expected, actual);
    }

    @Test
    public void executeRepair() {
        RobotWorldServer.createWorld(new int[]{10, 10, 10, 10, 5});
        ClientHandler testGuy = new ClientHandler();

        String launchRobot = new Protocol().createRequest("My Guy","launch sniper");
        Command command = Command.create(launchRobot);
        command.execute(testGuy);

        String request = new Protocol().createRequest("My Guy","repair");
        command = Command.create(request);


        String expected = "{\"result\":\"OK\"," +
                "\"data\":{\"message\":\"Repaired\"}}";
        String actual = command.execute(testGuy);

        assertEquals(expected, actual);
    }

    @Test
    public void turnTest() {
        String request = new Protocol().createRequest("My Guy","turn");

        Command command = Command.create(request);
        assertEquals(TurnCommand.class, command.getClass());
    }

    @Test
    public void offTest() {
        String request = new Protocol().createRequest("My Guy","off");

        Command command = Command.create(request);
        assertEquals(OffCommand.class, command.getClass());
    }



}