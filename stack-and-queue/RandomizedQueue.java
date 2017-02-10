import java.util.Iterator;
import java.util.Objects;

import edu.princeton.cs.algs4.StdRandom;

/**
 * Created by tangmin on 17/2/5.
 */
public class RandomizedQueue<Item> implements Iterable<Item> {
    private Item[] s;
    private int n = 0;
    public RandomizedQueue() {
        s = (Item []) new Object[1];
    }

    public Iterator<Item> iterator() {
        return new ListIterator();
    }

    private class ListIterator implements Iterator<Item> {
        private int current = n;
        Item [] copy;

        public ListIterator() {
            copy = (Item []) new Object[n];
            for (int i = 0; i < n; i++) {
                copy[i] = s[i];
            }
            StdRandom.shuffle(copy);
        }


        public boolean hasNext() {
            return current > 0;
        }
        public void remove() {
            throw new java.lang.UnsupportedOperationException();
        }
        public Item next() {
            if (!hasNext()) {
                throw new java.util.NoSuchElementException();
            }
            Item item = copy[--current];
            return item;
        }
    }

    public Item sample() {
        if (isEmpty()) {
            throw new java.util.NoSuchElementException();
        }
        int rand = StdRandom.uniform(n);
        return s[rand];
    }

    public void enqueue(Item item) {
        if (item == null) {
            throw new java.lang.NullPointerException();
        }
        if (n == s.length) resize(2 * s.length);
        s[n++] = item;
    }

    public int size() {
        return n;
    }

    public boolean isEmpty() {
        return n == 0;
    }

    public Item dequeue() {
        if (isEmpty()) {
            throw new java.util.NoSuchElementException();
        }
        int rand = StdRandom.uniform(n);
        Item item = s[rand];
        Item tmp = s[--n];
        s[rand] = tmp;

        s[n] = null;
        if (n > 0 && n == s.length / 4) resize(s.length / 2);
        return item;
    }

    private void resize(int capacity) {
        Item [] copy = (Item []) new Object[capacity];
        for (int i = 0; i < n; i++) {
            copy[i] = s[i];
        }
        s = copy;
    }
}

