import org.junit.Test;
import static org.junit.Assert.assertEquals;

/**
 * Created by hug.
 */
public class TestRedBlackFloorSet {
    @Test
    public void randomizedTest() {
        AListFloorSet testAList = new AListFloorSet();
        RedBlackFloorSet testRedBlackFloorSet = new RedBlackFloorSet();
        double randomNum;
        for (int index = 0; index < 1000000; index += 1) {
            randomNum = StdRandom.uniform(-5000.0, 5000.0);
            testAList.add(randomNum);
            testRedBlackFloorSet.add(randomNum);
        }

        for (int times = 0; times < 100000; times += 1) {
            randomNum = StdRandom.uniform(-5000.0, 5000.0);
            assertEquals(testAList.floor(randomNum), testRedBlackFloorSet.floor(randomNum), 0.000001);
        }
    }
}
