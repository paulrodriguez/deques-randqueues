/******************
  * Author:        Paul Rodriguez
  * Created:       2/19/2014
  * Last Updated:  2/19/2014
  * 
  * Compilation:  javac Deque.java
  * Execution:    java Deque
  * 
  * this class creates a deque where we can remove an item
  * from front and back. it has an iterator for be used to loop
  * over items.
 *****************/

import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item>
{
    private int size;          //  store the size of deque 
    private Node first;  //  the first is the left-most
    private Node last;   //  the last is the right-most
    
    //  private class for linked list of deque
    private class Node
    {
        Item item;        //  contents of node
        Node next;  //  pointer to next item in deque
        Node prev;  //  pointer to previous item in deque
    }
    
    /**
     * constructor for class Deque
     * 
     */
    public Deque()
    {
        size = 0;
        first = null;
        last = null;
    }
    
    /**
     * returns true if deque is empty
     * returns false otherwise
     */
    public boolean isEmpty()
    {
        return size == 0;
    }
    
    /**
     * returns size of deque
     */
    public int size()
    {
        return size;
    }
    
    /**
     * addsd an item to the front of deque(left-most)
     * @param Item item: takes item to be added
     */
    public void addFirst(Item item)
    {
        if (item == null) 
        {
            throw new NullPointerException("invalid item.");
        }
        
        Node oldFirst = first;
        first = new Node();
        first.item = item;
        first.next = oldFirst;
        first.prev = null;
        
        if (isEmpty())
        {
            last = first;
        }
        else
        {
            oldFirst.prev = first;
        }
        size++;
    }
    
    /**
     * adds item to back of deque(right-most)
     * @param Item item: item to be added
     */
    public void addLast(Item item)
    {
        if (item == null)
        {
            throw new NullPointerException("invalid item.");
        }
        
        Node oldLast = last;  //  reference to the current last item
        last = new Node();
        last.item = item;
        last.prev = oldLast;  //  point new item in back to previous last item
        
        if (isEmpty())
        {
            first = last;
        }
        else
        {
            oldLast.next = last;
        }
        
        size++;
    }
    
    /**
     * removes the front item if deque is not empty
     */
    public Item removeFirst()
    {
        if (isEmpty())
        {
            throw new java.util.NoSuchElementException("cannot remove from empty dequeue");
        }
        
  
        Node next = first.next;
        Item item = first.item;
        if(next != null)
        {
            next.prev = null;
        }
        if(first == last)
        {
            last = next;
        }
        first = next;
        
      
        size--;
        return item;
    }
    
    /**
     * removes back item if deque is not empty
     */
    public Item removeLast()
    {
        if (isEmpty())
        {
            throw new java.util.NoSuchElementException("cannot remove from empty dequeue");
        }
        
        Item item = last.item;
        
        Node prev = last.prev;
        if(prev != null)
        {
            prev.next = null;
        }
        if(first == last)
        {
            first = prev;
        }
        last = prev;
        
        size--;
        return item;
        
    }
    
    /**
     * return iterator for our deque
     */
    public Iterator<Item> iterator()
    {
        return new DequeIterator(first);
    }
    
    //  private class that constructs functions for our iterator
    private class DequeIterator implements Iterator<Item>
    {
        private Node current;  //  points to an item in our list
        
        /**
         * constructor for ListIterator
         * @param Node<Item> first: takes node for current to point to.
         */
        public DequeIterator(Node first)
        {
            current = first;
        }
        
        /**
         * return true if current node points to another.
         * return false otherwise.
         */
        public boolean hasNext() 
        {
            return current != null;
        }
        
        //  throw exception when remove method is called on iterator
        public void remove()
        {
            throw new UnsupportedOperationException();
        }
        
        //  return current's item and move to next node
        public Item next()
        {
            if(!hasNext()) throw new NoSuchElementException();
            
            Item item = current.item;
            current = current.next;
            return item;
        }
        
    }
    
    //  testing unit
    public static void main (String[] args)
    {
        Deque<Integer> deque = new Deque<Integer>();
        deque.addFirst(2);
        System.out.println("size: "+deque.size()+ " removed value: "+deque.removeFirst());
        deque.addFirst(3);
        deque.addLast(7);
        System.out.println("size: "+deque.size()+ " removed value: "+deque.removeLast());
        for (Integer s : deque)
        {
            System.out.print(" " + s + " ");
        }
    }
}