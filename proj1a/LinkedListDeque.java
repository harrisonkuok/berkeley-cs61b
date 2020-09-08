public class LinkedListDeque<T> {
    private class TNode {
        public T item;
        public TNode prev;
        public TNode next;

        public TNode(T x, TNode p, TNode n) {
            item = x;
            prev = p;
            next = n;
        }
    }

    private TNode sentinel;
    private int size;

    public LinkedListDeque() {
        sentinel = new TNode(null, null, null);
        sentinel.prev = sentinel;
        sentinel.next = sentinel;
        size = 0;
    }

    public LinkedListDeque(T x) {
        sentinel = new TNode(null, null, null);
        sentinel.next = new TNode(x, sentinel, null);
        sentinel.prev = sentinel.next;
        sentinel.next.next = sentinel;
        size = 1;
    }

    public void addFirst(T item) {
        TNode temp = new TNode(item, sentinel, sentinel.next);
            sentinel.next.prev = temp;
            sentinel.next = temp;
        size += 1;
    }

    public void addLast(T item) {
        TNode temp = new TNode(item, sentinel.prev, sentinel);
            sentinel.prev.next = temp;
            sentinel.prev = temp;
        size += 1;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return size;
    }

    public void printDeque() {
         TNode temp = sentinel.next;
         while (temp != sentinel) {
             System.out.print(temp.item);
             System.out.print(" ");
         }
         System.out.println();
    }

    public T removeFirst() {
        if (size == 0) {
            return null;
        } else {
            TNode temp = sentinel.next;
            temp.next.prev = sentinel;
            sentinel.next = temp.next;
            size -= 1;
            return temp.item;
        }
    }

    public T removeLast() {
        if (size == 0) {
            return null;
        } else {
            TNode temp = sentinel.prev;
            temp.prev.next = sentinel;
            sentinel.prev = temp.prev;
            size -= 1;
            return temp.item;
        }
    }

    public T get(int index) {
        if (index + 1 > size) {
            return null;
        }
        TNode temp = sentinel.next;
        while (index > 0)  {
            temp = temp.next;
            index -= 1;
        }
        return temp.item;
    }

    private T getRecursiveHelper(int index, TNode n) {
        if (index == 0) {
            return n.item;
        } else {
            return getRecursiveHelper(index - 1, n.next);
        }
    }

    public T getRecursive(int index) {
        if (index + 1 > size) {
            return null;
        } else {
            return getRecursiveHelper(index, sentinel.next);
        }
    }
}



