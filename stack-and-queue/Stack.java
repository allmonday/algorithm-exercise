import java.util.Iterator;
import java.util.ListIterator;

/**
 * Created by tangmin on 17/2/5.
 */
public class Stack<Item> implements Iterable <Item> {
    private Node first;

    public Iterator<Item> iterator() {
        return new ListIterator();
    }

    private class ListIterator implements Iterator <Item> {
        private Node current = first;

        public boolean hasNext() {
            return current != null;
        }
        public void remove() {

        }
        public Item next() {
            Item item = current.item;
            current = current.next;
            return item;
        }
    }

    private class Node {
        Item item;
        Node next;
    }

    public boolean isEmpty() {
        return first == null;
    }

    public void push(Item item) {
        Node old = first;
        first = new Node();
        first.item = item;
        first.next = old;
    }

    public Item pop() {
        Item item = first.item;
        first = first.next;
        return item;
    }
}
