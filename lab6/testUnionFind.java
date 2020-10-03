import org.junit.Test;
import static org.junit.Assert.*;

public class testUnionFind {
    static UnionFind unionFind = new UnionFind(10);

    @Test
    public void testFind() {
        assertEquals(1, unionFind.find(1));
    }

    @Test
    public void testConnect() {
        /* unionFind.connect(1, 2);
        unionFind.connect(2, 3);
        unionFind.connect(4, 5);
        unionFind.connect(4, 2);

        assertEquals(2, unionFind.find(4));
        assertEquals(2, unionFind.find(5)); */

        assertFalse(unionFind.isConnected(3, 4));
        unionFind.connect(1, 2);
        assertTrue(unionFind.isConnected(1, 2));
        unionFind.connect(2, 3);
        assertTrue(unionFind.isConnected(1, 3));
        unionFind.connect(4, 5);
        assertTrue(unionFind.isConnected(4, 5));
        unionFind.connect(3, 3);
        assertTrue(unionFind.isConnected(3, 3));
    }
 }
