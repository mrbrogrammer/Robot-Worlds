package za.co.wethinkcode.Server;

import za.co.wethinkcode.Command.Command;
import za.co.wethinkcode.Command.Server.Commands.ServerCommands;
import za.co.wethinkcode.Protocol;

import java.util.Scanner;

public class HandleServerCommands implements Runnable {

    private Scanner in;

    public HandleServerCommands() {
        in = new Scanner(System.in);
    }

    @Override
    public void run() {
        while (true) {
            String input = new Protocol().createRequest("client",in.nextLine());

            try {
                ServerCommands command = ServerCommands.create(input);
                String response = command.execute();

                System.out.println(response);
                if (response.equals("kill")) System.exit(0);
            } catch (IllegalArgumentException e) {
                System.out.println("Command does not exists");
            }
        }
    }
}
