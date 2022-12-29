package za.co.wethinkcode;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;

public class Protocol {

    private JSONObject jsonObject;

    public Protocol() {
        this.jsonObject = new JSONObject();
    }

    /**
     * Creates json formatted string that contains information about the command and arguments
     * @param name
     * @param command
     * @return
     */
    public String createRequest(String name, String command) {
        jsonObject.clear();
        jsonObject.put("robot", name);

        String [] args = command.split(" ", 2);

        jsonObject.put("command", args[0]);
        try {
            jsonObject.put("arguments", Arrays.asList(args[1].split(" ")));
        }catch (ArrayIndexOutOfBoundsException e) {
            jsonObject.put("arguments", new ArrayList<>());
        }

        return jsonObject.toString();
    }

    /**
     * Returns string containing error message
     * @return
     */
    public String parseArgumentsError() {
        jsonObject.clear();
        jsonObject.put("result", "ERROR");
        jsonObject.put("data", new JSONObject().put("message","Could not parse arguments"));
        return jsonObject.toString();
    }

    /**
     * Returns string containing error message
     * @return
     */
    public String robotDoesNotExistError() {
        jsonObject.clear();
        jsonObject.put("result", "ERROR");
        jsonObject.put("data", new JSONObject().put("message","Please launch Robot"));
        return jsonObject.toString();
    }
}
