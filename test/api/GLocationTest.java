package api;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GLocationTest {

    @Test
    void distance() {
        GLocation a = new GLocation(1,2,3);
        GLocation b = new GLocation(1,2,3);
        assertEquals(a.distance(b), 0.0);


    }

    @Test
    void testToString() {
        GLocation a = new GLocation(1,2,3);
        System.out.println(a.toString());
    }
}