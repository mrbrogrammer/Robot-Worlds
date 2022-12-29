package za.co.wethinkcode.Client;

import za.co.wethinkcode.Protocol;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import org.json.JSONObject;

public class RobotWorldClient {
    public static void main(String[] args) throws IOException {
        if (args.length == 0) {
            return;
        }

        System.out.println("Waiting for server...");
        Socket socket = new Socket(args[1], Integer.parseInt(args[0]));
        System.out.println("connected");

        BufferedReader userInput = new BufferedReader(new InputStreamReader(System.in));
        PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

        System.out.println("What would you like to name your robot: ");
        String robotName = userInput.readLine();

        while (true) {
            Protocol protocol = new Protocol();

            System.out.println(robotName + "> What must I do next?");
            String command = userInput.readLine().toLowerCase();

            String request = protocol.createRequest(robotName, command);

            out.println(request);
            try {
                System.out.println(new JSONObject(in.readLine()).toString(2));
            }catch (NullPointerException e){
                System.out.println("server is down");
                command = "off";

            }

            if (command.equals("off")) {
                socket.close();
                out.close();
                userInput.close();
                System.out.println("");
                return;
            }
        }
    }

}
