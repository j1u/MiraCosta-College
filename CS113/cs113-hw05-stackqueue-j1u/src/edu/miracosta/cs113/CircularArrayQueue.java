package edu.miracosta.cs113;

import java.util.Collection;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Queue;

public class CircularArrayQueue<E> implements Queue<E> {

    private int size;
    private int capacity;
    private int front;
    private int rear;
    private E[] theData;

    private static final int DEFAULT_CAPACITY = 10;

    /**
     * default constructor which calls the other constructor
     */
    public CircularArrayQueue(){
        this(DEFAULT_CAPACITY);
    }

    /**
     * constructor which creates a new circular array queue with a specified capacity
     * @param capacity the desired initial capacity of the circular array queue
     */
    public CircularArrayQueue(int capacity){
        this.size = 0;
        this.capacity = capacity;
        this.front = 0;
        this.rear = capacity - 1;
        this.theData = (E[]) new Object[capacity];
    }

    @Override
    public boolean add(E e) throws IllegalStateException{
            return offer(e);
    }

    @Override
    public boolean offer(E e) {
        if(size == capacity){
            reallocate();
        }
        size++;
        rear = (rear + 1) % capacity;
        theData[rear]= e;
        return true;
    }

    @Override
    public E remove() {
        E element = null;

        if(size != 0) {
            element = poll();
        }else {
            throw new NoSuchElementException();
        }
        return element;
    }

    @Override
    public E poll() {
        if(size == 0){
            return null;
        }
        E result = theData[front];
        front = (front + 1) % capacity;
        size--;
        return result;
    }

    @Override
    public E element() {
        if(size != 0){
            return theData[front];
        }
        throw new NoSuchElementException();
    }

    @Override
    public E peek() {
        if(size == 0){
            return null;
        }else{
            return theData[front];
        }
    }

    @Override
    public int size() {
        return 0;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public boolean contains(Object o) {
        return false;
    }

    @Override
    public Iterator<E> iterator() {
        return null;
    }

    @Override
    public Object[] toArray() {
        return new Object[0];
    }

    @Override
    public <T> T[] toArray(T[] a) {
        return null;
    }

    @Override
    public boolean remove(Object o) {
        return false;
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        return false;
    }

    @Override
    public boolean addAll(Collection<? extends E> c) {
        return false;
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        return false;
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        return false;
    }

    @Override
    public void clear() {

    }

    /** Double the capacity and reallocate the data.
     @pre The array is filled to capacity.
     @post The capacity is doubled and the first half of the expanded array is
     filled with data.
     */
    @SuppressWarnings("unchecked")
    private void reallocate() {
        int newCapacity = 2 * capacity;
        E[] newData = (E[]) new Object[newCapacity];
        int j = front;
        for (int i = 0; i < size; i++) {
            newData[i] = theData[j];
            j = (j + 1) % capacity;
        }
        front = 0;
        rear = size - 1;
        capacity = newCapacity;
        theData = newData;
    }
}
