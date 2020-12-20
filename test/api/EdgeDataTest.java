package api;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EdgeDataTest {

    @Test
    void testEquals() {
        EdgeData e = new EdgeData(2,4,5);
        EdgeData f = new EdgeData(2,6,5);
        assertEquals(e.equals(f), false);
        EdgeData r = new EdgeData(2,4,5);
        assertEquals(e.equals(r), true);

    }
}