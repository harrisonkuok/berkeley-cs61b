public class ArrayDeque<T> {
    private T[] items;
    private int size;
    private int nextFirst;
    private int nextLast;

    public ArrayDeque() {
        items = (T[]) new Object[8];
        size = 0;
        nextFirst = 4;
        nextLast = 5;
    }

    private void resize(int capacity) {
        T[] temp = (T[]) new Object[capacity];
        int originalStart = nextFirst + 1;
        nextFirst = capacity / 4 - 1;
        nextLast = nextFirst + size + 1;
        System.arraycopy(items, originalStart, temp, nextFirst + 1, size);
        items = temp;
    }

    private void sizeCheck() {
        if (items.length > 8 && (size / items.length) < 0.25) {
            resize(items.length / 2);
        }
    }

    public void addFirst(T item) {
        if (nextFirst == 0) {
            resize(items.length * 2);
        }

        items[nextFirst] = item;
        nextFirst -= 1;
        size += 1;
    }

    public void addLast(T item) {
        if (nextLast == (items.length - 1)) {
            resize(items.length * 2);
        }

        items[nextLast] = item;
        nextLast += 1;
        size += 1;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return size;
    }

    public void printDeque() {
        int index = nextFirst + 1;
        while (index <= nextFirst + size) {
            System.out.print(items[index]);
            System.out.print(" ");
            index += 1;
        }
        System.out.println();
    }

    public T removeFirst() {
        if (size == 0) {
            return null;
        }
        T temp = get(0);
        System.arraycopy(items, 1, items, 0, size - 1);
        size -= 1;
        sizeCheck();
        return temp;
    }

    public T removeLast() {
        if (size == 0) {
            return null;
        }
        T temp = get(size - 1);
        size = size - 1;
        sizeCheck();
        return temp;
    }

    public T get(int index) {
        if (index > size - 1) {
            return null;
        }
        return items[index];
    }

}
