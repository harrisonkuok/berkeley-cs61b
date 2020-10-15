package bearmaps;

import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class testNaivePointSet {

    static Point p1 = new Point(1.1, 2.2);
    static Point p2 = new Point(3.3, 4.4);
    static Point p3 = new Point(-2.9, 4.2);

    static NaivePointSet test = new NaivePointSet(List.of(p1, p2, p3));

    @Test
    public void testNearest() {
        Point actual = test.nearest(3.0, 4.0);
        double actualX = actual.getX();
        double actualY = actual.getY();
        assertEquals(p2, actual);
        assertEquals(3.3, actualX, 0);
        assertEquals(4.4, actualY, 0);
    }

}
