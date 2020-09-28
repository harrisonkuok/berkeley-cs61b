package es.datastructur.synthesizer;
import java.util.Iterator;

public class ArrayRingBuffer<T> implements BoundedQueue<T> {
    /* Index for the next dequeue or peek. */
    private int first;
    /* Index for the next enqueue. */
    private int last;
    /* Variable for the fillCount. */
    private int fillCount;
    /* Array for storing the buffer data. */
    private T[] rb;

    @Override
    public Iterator<T> iterator() {
        return new ArrayRingBufferIterator();
    }

    private class ArrayRingBufferIterator implements Iterator<T> {
        private int pos;

        public ArrayRingBufferIterator() {
            pos = 0;
        }

        @Override
        public boolean hasNext() {
            return pos > fillCount;
        }

        @Override
        public T next() {
            T returnItem = rb[pos];
            pos += 1;
            return  returnItem;
        }
    }

    @Override
    public int fillCount() {
        return fillCount;
    }

    @Override
    public int capacity() {
        return rb.length;
    }

    /**
     * Create a new ArrayRingBuffer with the given capacity.
     */
    public ArrayRingBuffer(int capacity) {
        rb = (T[]) new Object[capacity];
        first = 0;
        last = 0;
        fillCount = 0;
    }

    /**
     * Adds x to the end of the ring buffer. If there is no room, then
     * throw new RuntimeException("Ring buffer overflow").
     */
    @Override
    public void enqueue(T x) {
        if (fillCount() == rb.length) {
            throw new RuntimeException("Ring Buffer overflow");
        }

        rb[last] = x;
        fillCount += 1;

        if (last == rb.length - 1) {
            last = 0;
        } else {
            last += 1;
        }
    }

    /**
     * Dequeue oldest item in the ring buffer. If the buffer is empty, then
     * throw new RuntimeException("Ring buffer underflow").
     */
    @Override
    public T dequeue() {
        if (fillCount() == 0) {
            throw new RuntimeException("Ring Buffer underflow");
        }

        T returnItem = rb[first];
        rb[first] = null;

        if (first == rb.length - 1) {
            first = 0;
        } else {
            first += 1;
        }

        fillCount -= 1;
        return returnItem;
    }

    /**
     * Return oldest item, but don't remove it. If the buffer is empty, then
     * throw new RuntimeException("Ring buffer underflow").
     */
    @Override
    public T peek() {
        if (fillCount() == 0) {
            throw new RuntimeException("Ring Buffer underflow");
        }
        return rb[first];
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null) {
            return false;
        }
        if (o.getClass() != this.getClass()) {
            return false;
        }
        ArrayRingBuffer<T> other = (ArrayRingBuffer<T>) o;
        if (other.fillCount() != this.fillCount()) {
            return false;
        }
        Iterator<T> seer = other.iterator();
        for (T item : this) {
            if (item != seer.next()) {
                return false;
            }
        }
        return true;
    }
}

