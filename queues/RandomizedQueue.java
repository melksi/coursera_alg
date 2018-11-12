/* *****************************************************************************
 *  Name: Oksana Melnikova
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;
import java.util.NoSuchElementException;


public class RandomizedQueue<Item> implements Iterable<Item> {
    private Item[] a;
    private int n;

    public RandomizedQueue()                 // construct an empty randomized queue
    {
        a = (Item[]) new Object[2];
        n = 0;
    }

    public boolean isEmpty()                 // is the randomized queue empty?
    {
        return n == 0;
    }

    public int size()                        // return the number of items on the randomized queue
    {
        return n;
    }

    private void resize(int capacity) {
        assert capacity >= n;
        Item[] temp = (Item[]) new Object[capacity];
        for (int i = 0; i < n; i++) {
            temp[i] = a[i];
        }
        a = temp;
    }

    public void enqueue(Item item)           // add the item
    {
        if (item == null) throw new java.lang.IllegalArgumentException();
        if (n == a.length) resize(2 * a.length);    // double size of array if necessary
        a[n++] = item;
    }

    public Item dequeue()                    // remove and return a random item
    {
        if (isEmpty()) throw new NoSuchElementException("Stack underflow");
        int rnd = StdRandom.uniform(0, n);
        int length = a.length;
        Item[] temp = (Item[]) new Object[length - 1];
        for (int i = 0; i < rnd; i++) temp[i] = a[i];
        for (int i = rnd; i < length - 1; i++) temp[i] = a[i + 1];
        n = n - 1;
        Item s = a[rnd];
        if (temp.length > 1) a = temp;
        else
            a[rnd] = null;
        if (n > 0 && n == a.length / 4) resize(a.length / 2);
        return s;
    }

    public Item sample()                     // return a random item (but do not remove it)
    {
        if (isEmpty()) throw new NoSuchElementException("Stack underflow");
        int rnd = StdRandom.uniform(0, n);
        return a[rnd];
    }

    public Iterator<Item> iterator()         // return an independent iterator over items in random order
    {
        return new RandomizedQueueIterator();
    }

    private class RandomizedQueueIterator implements Iterator<Item> {
        private int i;
        private int[] iterItems;

        public RandomizedQueueIterator() {
            iterItems = StdRandom.permutation(n);
            i = 0;
        }

        public boolean hasNext() {
            return i < n;
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }

        public Item next() {
            if (!hasNext()) throw new NoSuchElementException();
            return a[iterItems[i++]];
        }
    }

    public static void main(String[] args)   // unit testing (optional)
    {
        RandomizedQueue<String> stack = new RandomizedQueue<String>();
        while (!StdIn.isEmpty()) {
            String item = StdIn.readString();
            if (!item.equals("-")) stack.enqueue(item);
            else if (!stack.isEmpty()) StdOut.print(stack.dequeue() + " ");
        }
        StdOut.println("(" + stack.size() + " left on stack)");

    }

}
