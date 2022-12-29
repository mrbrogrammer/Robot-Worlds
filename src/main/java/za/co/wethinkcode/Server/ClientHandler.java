package za.co.wethinkcode.Server;

import za.co.wethinkcode.Command.Command;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientHandler implements Runnable{

    private Socket socket;
    private BufferedReader in;
    private PrintWriter out;

    public ClientHandler(Socket socket) throws IOException {
        this.socket = socket;
        this.in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        this.out = new PrintWriter(socket.getOutputStream(), true);
    }

    public ClientHandler() {
    }

    @Override
    public void run() {
        try {
            while (socket.isConnected()) {
                String input = in.readLine();

                Command command = Command.create(input);
                String response = command.execute(this);

                out.println(response);
            }
        } catch (IOException e) {
            close();
        } catch (NullPointerException e) {
            close();
        }
    }

    public void close() {
        try {
            socket.close();
            in.close();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        RobotWorldServer.getWorld().removePlayers(this);
    }
}
