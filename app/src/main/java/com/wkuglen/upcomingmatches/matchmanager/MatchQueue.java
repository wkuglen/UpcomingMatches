package com.wkuglen.upcomingmatches.matchmanager;

import java.util.ArrayList;

/**
 * Created by wkuglen on 2/10/15.
 */
public class MatchQueue<E> {
    private ArrayList<E> myQueue;
    private int front;
    public MatchQueue()
    {
        myQueue = new ArrayList<E>();
        front = 0;
    }
    public boolean isEmpty()
    {
        return myQueue.size()==0;
    }
    public boolean enQueue(E in)
    {
        return myQueue.add(in);
    }
    public E deQueue()
    {
        return myQueue.remove(front);
    }
    public E peekFront()
    {
        return myQueue.get(front);
    }
    public int getSize()
    {
        return myQueue.size();
    }
}