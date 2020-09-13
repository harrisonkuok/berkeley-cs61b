public class ArrayDequeTest {
    public static void main(String[] args) {
        ArrayDeque<Integer> test = new ArrayDeque<Integer>();

        int x = 0;


        while (x != 32) {
            test.addFirst(1);
            x += 1;
        }

        while (x != 1) {
            test.removeFirst();
            x -= 1;
        }

    }
}
