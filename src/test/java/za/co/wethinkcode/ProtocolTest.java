package za.co.wethinkcode;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class ProtocolTest {

    @Test
    public void createRequestTest() {
        Protocol protocol = new Protocol();

        String expected = "{\"robot\":\"My Guy\"," +
                        "\"arguments\":[]," +
                        "\"command\":\"command\"}";
        String actaul = protocol.createRequest("My Guy", "command");

        assertEquals(expected, actaul);
    }

    @Test
    public void createRequestTestWithArgs() {
        Protocol protocol = new Protocol();

        String expected = "{\"robot\":\"My Guy\"," +
                "\"arguments\":[\"arg1\",\"arg2\"]," +
                "\"command\":\"command\"}";
        String actaul = protocol.createRequest("My Guy", "command arg1 arg2");

        assertEquals(expected, actaul);
    }

    @Test
    public void parseArgumentsErrorTest() {
        Protocol protocol = new Protocol();
        String exoected = "{\"result\":\"ERROR\"," +
                            "\"data\":{" +
                                "\"message\":\"Could not parse arguments\"}}";
        String actual = protocol.parseArgumentsError();
        assertEquals(exoected, actual);
    }
}
