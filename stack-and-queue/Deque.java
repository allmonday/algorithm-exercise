import edu.princeton.cs.algs4.StdOut;

import java.util.Iterator;

/**
 * Created by tangmin on 17/2/5.
 */
public class Deque<Item> implements Iterable<Item> {
    private Node first = null,
            last = null;
    private int n = 0;

    private class Node {
        private Item item;
        private Node next;
        private Node prev;
    }

    public Iterator<Item> iterator() {
        return new ListIterator();
    }

    private class ListIterator implements Iterator<Item> {
        private Node current = first;
        public boolean hasNext() {
            return current != null;
        }
        public void remove() {
            throw new java.lang.UnsupportedOperationException();
        }
        public Item next() {
            if (!hasNext()) {
                throw new java.util.NoSuchElementException();
            }
            Item item = current.item;
            current = current.next;
            return item;
        }
    }


    public boolean isEmpty() {
        return n == 0;
    }

    public int size() {
        return n;
    }

    public void addFirst(Item item) {  // addFirst and addLast should set first, last to the same Node at the same time.
        if (item == null) {
            throw new java.lang.NullPointerException();
        }

        Node newFirst = new Node();
        newFirst.item = item;
        newFirst.prev = null;
        newFirst.next = first;

        if (this.isEmpty()) {  // first node
            first = newFirst;
            last = newFirst;
        }
        else {
            first.prev = newFirst;
            first = newFirst;
        }
        n++;
    }

    public void addLast(Item item) {
        if (item == null) {
            throw new java.lang.NullPointerException();
        }

        Node newLast = new Node();
        newLast.item = item;
        newLast.prev = last;
        newLast.next = null;

        if (this.isEmpty()) {  // first Node
            last = newLast;
            first = newLast;
        }
        else {
            last.next = newLast;
            last = newLast;
        }
        n++;
    }

    public Item removeFirst() {
        if (this.isEmpty()) {
            throw new java.util.NoSuchElementException();
        }
        else {
            Item item = first.item;
            first = first.next;
            if (first == null) {  // if prev is null, it's empty
                last = null;
            }
            else {
                first.prev = null;
            }
            n--;
            return item;
        }
    }

    public Item removeLast() {
        if (this.isEmpty()) {
            throw new java.util.NoSuchElementException();
        }
        else {
            Item item = last.item;
            last = last.prev;

            if (last == null) {  // if prev is null, it's empty
                first = null;
            }
            else {
                last.next = null;
            }
            n--;
            return item;
        }
    }

    public static void main(String[] args) {
        Deque<String> d = new Deque<>();
        d.addFirst("world");
        d.addFirst("hello");
        d.removeLast();

        for (String i: d) {
            StdOut.println(i);
        }
    }
}
