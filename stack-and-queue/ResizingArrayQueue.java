import java.util.Iterator;

/**
 * Created by tangmin on 17/2/8.
 */
public class ResizingArrayQueue<Item> implements Iterable<Item> {
    private Item[] q;
    private int head = 0;
    private int tail = 0;
    private int capacity;
    private int cnt = 0;  // current total count

    public Iterator<Item> iterator() {
        return new ListIterator();
    }

    private class ListIterator implements Iterator<Item> {
        private int current = head;
        private int current_total = 0;
        public boolean hasNext() {
            return current_total < cnt;
        }
        public void remove() {}
        public Item next() {
            Item item = q[current];
            current = (current + 1) % capacity;
            current_total += 1;
            return item;
        }
    }

    public ResizingArrayQueue() {
        int initCapacity = 2;
        this.capacity = initCapacity;
        q = (Item []) new Object[initCapacity];
    }

    public boolean isEmpty() {
        return head == tail;
    }

    public void enqueue(Item item) {
        if (isFull()) {
            enlarge();
        }
        cnt += 1;
        q[tail] = item;
        tail = ( tail + 1 ) % capacity;
    }

    public Item dequeue() {
        if (cnt == 0) {
            throw new java.util.NoSuchElementException();
        }
        if (cnt == capacity / 4) {
            narrow();
        }
        cnt -= 1;
        Item item = q[head];
        head = ( head + 1 ) % capacity;
        return item;
    }

    private void enlarge() {  // double the size
        int newCapacity = 2 * capacity;
        Item [] copy = (Item []) new Object[newCapacity];

        for (int i = 0; i < capacity; i++) {  // notice: count equals to capacity
            int oldTailIndex = (head + i) % capacity;
            copy[ head+i ] = q[oldTailIndex];
        }

        q = copy;
        tail = head + capacity;
        capacity = newCapacity;
    }

    private void narrow() {  // 1/2 the size if less then 1/4
        int newCapacity = capacity / 2;
        Item [] copy = (Item []) new Object[newCapacity];

        for (int i = 0; i < cnt; i++) {
            int oldHeadIndex = (head + i) % capacity;
            copy[i] = q[oldHeadIndex];
        }

        head = 0;
        tail = cnt;
        capacity = newCapacity;
        q = copy;
    }

    private boolean isFull() {
        return cnt == capacity;
    }
}
