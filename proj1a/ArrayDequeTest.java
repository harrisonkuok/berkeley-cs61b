public class ArrayDequeTest {
    public static void main(String[] args) {
        ArrayDeque<Integer> test = new ArrayDeque<Integer>();

        int x = 0;

        test.addLast(1);
        while (x != 128) {
            test.addFirst(1);
            x += 1;
        }



    }
}
