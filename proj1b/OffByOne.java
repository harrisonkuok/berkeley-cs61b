public class OffByOne implements CharacterComparator {

    @Override
    public boolean equalChars(char x, char y) {
        int first = x;
        int second = y;
        return (first - second) == 1 || (first - second == -1);
    }
}

