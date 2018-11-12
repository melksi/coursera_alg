import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

import java.util.Iterator;
import java.util.NoSuchElementException;

/* *****************************************************************************
 *  Name: Oksana Melnikova
 *  Date:
 *  Description:
 **************************************************************************** */
public class Deque<Item> implements Iterable<Item> {
    private int n;
    private Node first;
    private Node last;

    public Deque()                           // construct an empty deque
    {
        first = null;
        n = 0;
    }

    public boolean isEmpty()                 // is the deque empty?
    {
        return (n == 0);
    }

    private class Node {
        private Item item;
        private Node next;
        private Node prev;
    }

    public int size()                        // return the number of items on the deque
    {
        return n;
    }

    public void addFirst(Item item)          // add the item to the front
    {
        if (item == null) throw new java.lang.IllegalArgumentException();

        Node oldfirst = first;
        first = new Node();
        first.item = item;
        first.next = oldfirst;
        if (oldfirst != null) oldfirst.prev = first;

        if (n == 0) {
            last = first;
            last.prev = first;
        }
        if (n == 1) {
            last = oldfirst;
            last.prev = first;
        }
        n++;
    }

    public void addLast(Item item)           // add the item to the end
    {
        if (item == null) throw new java.lang.IllegalArgumentException();

        Node oldlast = last;
        last = new Node();
        last.item = item;
        last.prev = oldlast;
        if (oldlast != null) oldlast.next = last;
        if (n == 0) {
            first = last;
            first.next = last;
        }
        if (n == 1) {
            first = oldlast;
            first.next = last;
        }
        n++;
    }

    public Item removeFirst()                // remove and return the item from the front
    {
        if (isEmpty()) throw new NoSuchElementException("Stack underflow");
        Item item = first.item;        // save item to return
        first = first.next;            // delete first node
        if (n == 2) {
            first = last;
        }
        if (n == 1) {
            first = null;
            last = null;
        }
        n--;
        return item;
    }

    public Item removeLast()                 // remove and return the item from the end
    {
        if (isEmpty()) throw new NoSuchElementException("Stack underflow");
        Item item = last.item;        // save item to return
        last = last.prev;            // delete first node

        if (n == 2) {
            last = first;
        }
        if (n == 1) {
            first = null;
            last = null;
        }
        n--;
        return item;
    }

    public Iterator<Item> iterator()         // return an iterator over items in order from front to end
    {
        return new DequeIterator();
    }

    private class DequeIterator implements Iterator<Item> {
        private Node current = first;

        public boolean hasNext() {
            return current != null;
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }

        public Item next() {
            if (!hasNext()) throw new NoSuchElementException();
            Item item = current.item;
            current = current.next;
            return item;
        }
    }

    public static void main(String[] args)   // unit testing (optional)
    {
        Deque<String> stack = new Deque<String>();
        while (!StdIn.isEmpty()) {
            String item = StdIn.readString();
            if (!item.equals("-"))
                stack.addLast(item);
            else if (!stack.isEmpty())
                StdOut.print(stack.removeLast() + " ");
        }
        StdOut.println("(" + stack.size() + " left on stack)");
    }
}

