package za.co.wethinkcode;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

import org.junit.jupiter.api.Test;

public class WorldConfigTest {

    @Test
    public void worldConfigTestVisiblityInvalid() {
        WorldConfig config = new WorldConfig(new String [] {"visibility", " ",});
        
        assertArrayEquals(new int[]{10, 10, 10, 10, 5}, config.getConfiguredArgs());
    }

    @Test
    public void worldConfigTestVisiblity() {
        WorldConfig config = new WorldConfig(new String [] {"visibility", "4"});

        assertArrayEquals(new int[]{10, 10, 4, 10, 5}, config.getConfiguredArgs());
    }

    @Test
    public void worldConfigTestWorldInvalid() {
        WorldConfig config = new WorldConfig(new String [] {"world", "0", ""});

        assertArrayEquals(new int[]{10, 10, 10, 10, 5}, config.getConfiguredArgs());
    }

    @Test
    public void worldConfigTestWorld() {
        WorldConfig config = new WorldConfig(new String [] {"world", "0", "0"});

        assertArrayEquals(new int[]{0 ,0, 10, 10, 5}, config.getConfiguredArgs());
    }

    @Test
    public void worldConfigTestShieldInvalid() {
        WorldConfig config = new WorldConfig(new String [] {"shield"});

        assertArrayEquals(new int[]{10 ,10, 10, 10, 5}, config.getConfiguredArgs());
    }

    @Test
    public void worldConfigTestShield() {
        WorldConfig config = new WorldConfig(new String [] {"shield", "70"});

        assertArrayEquals(new int[]{10, 10, 10, 70, 5}, config.getConfiguredArgs());
    }

    @Test
    public void WorldConfigTestReload() {
        WorldConfig config = new WorldConfig(new String [] {"reload"});

        assertArrayEquals(new int[]{10, 10, 10, 10, 5}, config.getConfiguredArgs());
    }

    @Test
    public void WorldConfigTestReloadInvalid() {
        WorldConfig config = new WorldConfig(new String [] {"reload", "90"});

        assertArrayEquals(new int[]{10, 10, 10, 10, 90}, config.getConfiguredArgs());
    }
}