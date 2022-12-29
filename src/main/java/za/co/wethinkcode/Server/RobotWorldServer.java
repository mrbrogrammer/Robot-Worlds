package za.co.wethinkcode.Server;


import za.co.wethinkcode.WorldConfig;
import za.co.wethinkcode.World.Maze;
import za.co.wethinkcode.World.World;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class RobotWorldServer {
    private static List<ClientHandler> playerList = new ArrayList<>();
    private static ExecutorService pool = Executors.newFixedThreadPool(5);
    private static World world;
    private static WorldConfig config;

    public static void main(String[] args){
        if (args.length == 0) return;

        new Thread(new HandleServerCommands()).start();

        config = new WorldConfig(args);

        createWorld(config.getConfiguredArgs());

        try {
            ServerSocket serverSocket = new ServerSocket(Integer.parseInt(args[0]));

            while (true) {
                System.out.println("Waiting for clients to connect");
                Socket socket = serverSocket.accept();
                System.out.println("Client connected");

                ClientHandler clientHandler = new ClientHandler(socket);
                playerList.add(clientHandler);

                pool.execute(clientHandler);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static World getWorld() {
        return world;
    }

    public static void createWorld(int [] configuredArgs) {
        world = new World(configuredArgs, new Maze(configuredArgs[0], configuredArgs[1]));
    }

    public static List<ClientHandler> getPlayerList() {
        return playerList;
    }
    public static ExecutorService getPool(){
        return pool;
    }
}

