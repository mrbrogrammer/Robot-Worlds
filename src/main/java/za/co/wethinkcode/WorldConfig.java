package za.co.wethinkcode;

import java.util.Arrays;
import java.util.List;

public class WorldConfig {
    
    private int [] configuredArgs;

    public WorldConfig(String [] args) {
        List<String> argsList = Arrays.asList(args);

        configuredArgs = new int[5];

        configureWorldSize(argsList);
        configureWorldVisibilty(argsList);
        configureWorldShield(argsList);
        configureWorldReloading(argsList);
    }

    private void configureWorldReloading(List<String> argsList) {
        try {
            int index = 0;

            index = argsList.contains("reload") ? argsList.indexOf("reload") + 1: -1;

            configuredArgs[4] = Integer.parseInt(argsList.get(index));
        } catch (NumberFormatException | IndexOutOfBoundsException e) {
            configuredArgs[4] = 5;
        }
    }

    private void configureWorldShield(List<String> argsList) {
        try {
            int index = 0;

            index = argsList.contains("shield") ? argsList.indexOf("shield") + 1: -1;

            configuredArgs[3] = Integer.parseInt(argsList.get(index));
        } catch (NumberFormatException | IndexOutOfBoundsException e) {
            configuredArgs[3] = 10;
        }
    }

    private void configureWorldVisibilty(List<String> argsList) {
        try {
            int index = 0;

            index = argsList.contains("visibility") ? argsList.indexOf("visibility") + 1: -1;

            configuredArgs[2] = Integer.parseInt(argsList.get(index));
        } catch (NumberFormatException | IndexOutOfBoundsException e) {
            configuredArgs[2] = 10;
        }
    }

    private void configureWorldSize(List<String> argsList) {
        try {
            int index = argsList.indexOf("world");

            configuredArgs[0] = Integer.parseInt(argsList.get(index + 1));
            configuredArgs[1] = Integer.parseInt(argsList.get(index + 2));
        } catch (NumberFormatException | IndexOutOfBoundsException e) {
            configuredArgs[0] = 10;
            configuredArgs[1] = 10;
        }
    }

    public int [] getConfiguredArgs(){
        return this.configuredArgs;
    }
}
