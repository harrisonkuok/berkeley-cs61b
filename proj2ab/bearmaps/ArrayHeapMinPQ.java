package bearmaps;


import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.TreeMap;

public class ArrayHeapMinPQ<T> implements ExtrinsicMinPQ<T> {

    private TreeMap<T, Integer> items;
    private ArrayList<PriorityNode> minHeap;

    public ArrayHeapMinPQ() {
        minHeap = new ArrayList<>();
        minHeap.add(new PriorityNode(null, 0.0));
        items = new TreeMap<>();
    }

    @Override
    public void add(T item, double priority) {
        if (contains(item)) {
            throw new IllegalArgumentException();
        }
        minHeap.add(new PriorityNode(item, priority));
        items.put(item, size());
        int nodeNum = size();
        swimNode(nodeNum);
    }

    @Override
    public boolean contains(T item) {
        return items.containsKey(item);
    }

    @Override
    public T getSmallest() {
        if (size() == 0) {
            throw new NoSuchElementException();
        }
        return minHeap.get(1).getItem();
    }

    @Override
    public T removeSmallest() {
        if (size() == 0) {
            throw new NoSuchElementException();
        }
        T smallest = getSmallest();
        minHeap.set(1, minHeap.get(size()));
        minHeap.remove(minHeap.size() - 1);
        sinkNode(1);
        items.remove(smallest);
        return smallest;
    }

    @Override
    public int size() {
        return minHeap.size() - 1;
    }

    @Override
    public void changePriority(T item, double priority) {
        if (!contains(item)) {
            throw new IllegalArgumentException();
        }
        int originalIndex = items.get(item);
        minHeap.get(originalIndex).setPriority(priority);
        swimNode(originalIndex);
        sinkNode(originalIndex);
    }

    /**@source NaiveMinPQ */
    private class PriorityNode implements Comparable<PriorityNode> {
        private T item;
        private double priority;

        PriorityNode(T i, double p) {
            item = i;
            priority = p;
        }

        public T getItem() {
            return item;
        }

        public double getPriority() {
            return priority;
        }

        void setPriority(double p) {
            priority = p;
        }

        @Override
        public int compareTo(PriorityNode o) {
            if (o == null) {
                return -1;
            }
            return Double.compare(this.getPriority(), o.getPriority());
        }

        @Override
        @SuppressWarnings("unchecked")
        public boolean equals(Object o) {
            if (o == null || o.getClass() != this.getClass()) {
                return false;
            } else {
                return ((PriorityNode) o).getItem().equals(getItem());
            }
        }
    }

    private int parent(int nodeNum) {
        return nodeNum / 2;
    }

    private int leftChild(int nodeNum) {
        return nodeNum * 2;
    }

    private int rightChild(int nodeNum) {
        return nodeNum * 2 + 1;
    }

    private boolean haveLeftChild(int nodeNum) {
        return leftChild(nodeNum) <= size();
    }

    private boolean haveRightChild(int nodeNum) {
        return rightChild(nodeNum) <= size();
    }

    private void swapNode(int n1, int n2) {
        PriorityNode upperNode = minHeap.get(n1);
        PriorityNode lowerNode = minHeap.get(n2);
        minHeap.set(n1, lowerNode);
        minHeap.set(n2, upperNode);
        items.put(upperNode.getItem(), n2);
        items.put(lowerNode.getItem(), n1);
    }

    private void swimNode(int nodeNum) {
        if (nodeNum > 1) {
            if (minHeap.get(parent(nodeNum))
                    .compareTo(minHeap.get(nodeNum)) > 0) {
                swapNode(nodeNum, parent(nodeNum));
                swimNode(parent(nodeNum));
            }
        }
    }

    private void sinkNode(int nodeNum) {
        int smallestNode = 0;
        if (haveLeftChild(nodeNum)) {
            smallestNode = leftChild(nodeNum);
            if (haveRightChild(nodeNum)
                    && minHeap.get(smallestNode).getPriority()
                    > minHeap.get(rightChild(nodeNum)).getPriority()) {
                smallestNode = rightChild(nodeNum);
            }
        }
        if (haveLeftChild(nodeNum)
                && minHeap.get(nodeNum).getPriority()
                > minHeap.get(smallestNode).getPriority()) {
            swapNode(nodeNum, smallestNode);
            sinkNode(smallestNode);
        }
    }


}
