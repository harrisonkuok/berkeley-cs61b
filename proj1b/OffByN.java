public class OffByN implements CharacterComparator {

    private int N;

    public OffByN(int num) {
        N = num;
    }

    @Override
    public boolean equalChars(char x, char y) {
        int first = x;
        int second = y;
        return (first - second) == N || (first - second == -N);
    }
}
