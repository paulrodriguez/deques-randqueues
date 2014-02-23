/**
 * Author:        Paul Rodriguez
 * Created:       2/22/2014
 * Last Updated:  2/22/214
 * 
 * Compilation:  javac RandomizedQueue.java
 * Execution:    java RandomizedQueue
 * 
 * this creates a queue that adds elements to the back of the queue
 * but can retrieve an element in any position at random.
 * the element in the back is moved to the position where the removed
 * element was in.
 */


import java.util.Iterator;
import java.util.NoSuchElementException;

public class RandomizedQueue<Item> implements Iterable<Item>
{
    private int N;  //  tracks size actual items in array
    private Item[] randQueue;
    
    @SuppressWarnings("unchecked")
    public RandomizedQueue()
    {
        N = 0;
        randQueue = (Item[])new Object[2];
    }
    
    public boolean isEmpty()
    {
        return N == 0;
    }
    
    public int size()
    {
        return N;
    }
    
    @SuppressWarnings("unchecked")
    private void resize(int capacity)
    {
        Item[] copy = (Item[])new Object[capacity];
        for (int i = 0; i < N; i++)
        {
            copy[i] = randQueue[i];
        }
        
        randQueue = copy;
    }
    
    public void enqueue(Item item)
    {
        if (item == null)
        {
            throw new NullPointerException("null items not allowed.");
        }
        
        randQueue[N++] = item;
        if(N == randQueue.length)
        {
            resize(2*randQueue.length);
        }
    }
    
   
    
    public Item dequeue()
    {
        if(isEmpty())
        {
            throw new NoSuchElementException("randomized queue is empty.");
        }
        
        int index = StdRandom.uniform(N);
        Item item = randQueue[index];
        randQueue[index] = randQueue[--N];
        randQueue[N] = null;
        if(N > 0 && N == randQueue.length/4)
        {
            resize(randQueue.length/2);
        }
        
        return item;
    }
    
    public Item sample()
    {
        if(isEmpty())
        {
            throw new NoSuchElementException("ranodmized queue is empty.");
        }
        
        int index = StdRandom.uniform(N);
        
        return randQueue[index];
    }
    
    public Iterator<Item> iterator()
    {
        return new RandomizedQueueIterator();
    }
    
    private class RandomizedQueueIterator implements Iterator<Item>
    {
        private int current_index;
        private Item[] randQueueIt;
        @SuppressWarnings("unchecked")
        public RandomizedQueueIterator()
        {
            current_index = 0;
            randQueueIt = (Item[])new Object[N];
            for(int i = 0; i < N; i++)
            {
                randQueueIt[i] = randQueue[i];
            }
            
            StdRandom.shuffle(randQueueIt);
            
        }
        
         //  throw exception when remove method is called on iterator
        public void remove()
        {
            throw new UnsupportedOperationException();
        }
        
        /*
         * return true if we have not reached end of array
         * returns false otherwise.
         */
        public boolean hasNext()
        {
            return current_index != N;
        }
        
         //  return current's item and move to next node
        public Item next()
        {
            if(!hasNext()) throw new NoSuchElementException();
            
            Item item = randQueueIt[current_index++];
            return item;
        }
    }
    
    public static void main(String[] args)
    {
        RandomizedQueue<Integer> rq = new RandomizedQueue<Integer>();
        
        rq.enqueue(1);
        rq.enqueue(2);
        rq.enqueue(3);
        StdOut.println("samplefunction: "+rq.sample()+", remaining size: "+rq.size());
        StdOut.println("dequeue: "+rq.dequeue()+", remaining size: "+rq.size());
        StdOut.println("dequeue: "+rq.dequeue()+", remaining size: "+rq.size());
        rq.enqueue(5);
        StdOut.println("dequeue: "+rq.dequeue()+", remaining size: "+rq.size());
        for(Integer i : rq)
        {
            StdOut.println(" "+i+" ");
        }
    }
}