/**
 * DoubleLinkedList.java
 * @author James Lu
 * @version 1.0
 */
package edu.miracosta.cs113;

import java.util.*;
import java.util.function.UnaryOperator;

public class DoubleLinkedList<E> implements List<E> {

    private Node<E> head = null;
    private Node<E> tail = null;
    private int size = 0;

    /**
     * default constructor for DLL
     */
    public DoubleLinkedList() {
        this.head = null;
        this.tail = null;
        this.size = 0;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return (head == null && size == 0);
    }

    @Override
    public boolean contains(Object o) {
        Node<E> currrent = head;
        for (int i = 0; i < size; i++) {
            if (o.equals(currrent.data)) {
                return true;
            }
            currrent = currrent.next;
        }
        return false;
    }

    @Override
    public Iterator<E> iterator() {
        return new DoubleListIterator();
    }

    @Override
    public Object[] toArray() {
        return new Object[0];
    } //stub

    @Override
    public <T> T[] toArray(T[] a) {
        return null;
    } //stub

    @Override
    public boolean add(E e) {
        listIterator().add(e);
        return true;
    }

    @Override
    public boolean remove(Object o) {
        ListIterator<E> iter = listIterator(0);
        while (iter.hasNext()) {
            E obj = iter.next();
            if (obj.equals(o)) {
                iter.remove();
                return true;
            }
        }
        return false;
    }

    @Override
    public void clear() {
        head = null;
        size = 0;
    }

    @Override
    public E get(int index) {
        if (isEmpty() || index >= size) {
            throw new IndexOutOfBoundsException();
        }
        return listIterator(index).next();
    }

    @Override
    public E set(int index, E element) {
        ListIterator<E> iter = listIterator(index);
        if (iter.hasNext()) {
            iter.next();
            iter.set(element);
        }
        return get(index);
    }

    @Override
    public void add(int index, E element) {
        listIterator(index).add(element);
    }

    @Override
    public E remove(int index) {
        ListIterator<E> iter = listIterator(index);
        E objRemoved;
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException();
        }
        if (index == 0) { //beginning
            objRemoved = iter.next();
            iter.remove();
        } else if (index > 0 && index < size) { //middle
            objRemoved = iter.next();
            iter.remove();
        } else { //end
            objRemoved = iter.previous();
            iter.remove();
        }
        return objRemoved;
    }

    @Override
    public int indexOf(Object o) {
        Node<E> current = head;
        for (int i = 0; i < size; i++) {
            if (o.equals(current.data)) {
                return i;
            }
            current = current.next;
        }
        return -1;
    }

    @Override
    public int lastIndexOf(Object o) {
        int index = -1;

        if (o == null)
            return -1;

        Node<E> current = head;
        for (int i = 0; i < size; i++) {

            if (o.equals(current.data)) {
                index = i;
            }

            if (current.next != null) {
                current = current.next;
            }

        }
        return index;
    }

    @Override
    public ListIterator<E> listIterator() {
        return new DoubleListIterator();
    }

    @Override
    public ListIterator<E> listIterator(int index) {
        return new DoubleListIterator(index);
    }


    @Override
    public Spliterator<E> spliterator() {
        return null;
    }

    @Override
    public String toString() {
        String list = "";
        Node<E> current = head;
        if (size != 0) {
            for (int i = 0; i < size; i++) {
                list += current.data + ", ";
                current = current.next;
            }
            list = list.substring(0, list.length() - 2);
        }
        return "[" + list + "]";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        DoubleLinkedList<?> that = (DoubleLinkedList<?>) o;
        if(this.size != ((DoubleLinkedList<?>) o).size){
            return false;
        }

        for (int i = 0; i < size; i++){
            if(this.get(i).equals(that.get(i))){
                return true;
            }
        }
        return false;
    }

    @Override
    public List<E> subList(int fromIndex, int toIndex) {
        return null;
    } //stub

    @Override
    public boolean containsAll(Collection<?> c) { //stub
        return false;
    }

    @Override
    public boolean addAll(Collection<? extends E> c) { //stub
        return false;
    }

    @Override
    public boolean addAll(int index, Collection<? extends E> c) { //stub
        return false;
    }

    @Override
    public boolean removeAll(Collection<?> c) { //stub
        return false;
    }

    @Override
    public boolean retainAll(Collection<?> c) { //stub
        return false;
    }

    @Override
    public void replaceAll(UnaryOperator<E> operator) { //stub

    }

    @Override
    public void sort(Comparator<? super E> c) { //stub

    }


    private static class Node<E> {
        private E data;
        private Node<E> next = null;
        private Node<E> prev = null;

        /**
         * constructor for inner class Node, creates object with specified data
         * @param dataItem data to be stored within node
         */
        private Node(E dataItem) {
            this.data = dataItem;
        }
    }


    private class DoubleListIterator implements ListIterator<E> {
        private Node<E> nextItem = null;
        private Node<E> lastItemReturned = null;
        private int index = 0;

        /**
         * default constructor for DLLIterator
         */
        public DoubleListIterator() {
            this.nextItem = null;
            this.lastItemReturned = null;
            this.index = 0;
        }

        /**
         * constructor for DLLIterator at specified index
         * @param i index at which the iterator is to be created
         */
        public DoubleListIterator(int i) {
            if (i < 0 || i > size) {
                throw new IndexOutOfBoundsException("Invalid index " + i);
            }
            lastItemReturned = null;
            if (i == size) {
                index = size;
                nextItem = null;
            } else {
                nextItem = head;
                for (index = 0; index < i; index++) {
                    nextItem = nextItem.next;
                }
            }
        }

        @Override
        public boolean hasNext() {
            return nextItem != null;
        }

        @Override
        public E next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            lastItemReturned = nextItem;
            nextItem = nextItem.next;
            index++;
            return lastItemReturned.data;
        }

        @Override
        public boolean hasPrevious() { //check empty list
            if (size == 0) {
                return false;
            } else {
                return (nextItem == null) || (nextItem.prev != null);
            }
        }

        @Override
        public E previous() {
            if (!hasPrevious()) {
                throw new NoSuchElementException();
            }
            if (nextItem == null) {
                nextItem = tail;
            } else {
                nextItem = nextItem.prev;
            }
            lastItemReturned = nextItem;
            index--;
            return lastItemReturned.data;
        }

        @Override
        public int nextIndex() {
            if (index == 0) {
                return index;
            } else {
                return index;
            }
        }

        @Override
        public int previousIndex() {
            if (index == 0) {
                return index - 1;
            } else {
                return index - 1;
            }
        }

        @Override
        public void remove() {
            if (lastItemReturned == null) {
                throw new IllegalStateException();
            }
            if (lastItemReturned == head) { //beginning of list
                head = head.next;
            } else if (lastItemReturned != head && lastItemReturned != tail) { //middle of list
                nextItem.prev = lastItemReturned.prev;
                lastItemReturned.prev.next = lastItemReturned.next;
            } else { // end of list
                tail.prev.next = null;
                tail = tail.prev;
            }
            lastItemReturned = null;
            index--;
            size--;
        }

        @Override
        public void set(E e) {
            if (lastItemReturned == null) {
                throw new IllegalStateException();
            } else if (nextItem == null) {
                throw new IllegalStateException();
            } else {
                lastItemReturned.data = e;
            }
        }

        @Override
        public void add(E e) {
            if (head == null) { //empty list
                head = new Node<E>(e);
                tail = head;
                lastItemReturned = null;
            } else if (nextItem == head) { //front of list
                Node<E> newNode = new Node<E>(e);
                newNode.next = nextItem;
                nextItem.prev = newNode;
                head = newNode;
                lastItemReturned = null;
            } else if (nextItem == null) { //back of list
                Node<E> newNode = new Node<E>(e);
                tail.next = newNode;
                newNode.prev = tail;
                tail = newNode;
                lastItemReturned = null;
            } else { //middle of list, adds behind iterator
                Node<E> newNode = new Node<E>(e);
                newNode.prev = nextItem.prev;
                nextItem.prev.next = newNode;
                newNode.next = nextItem;
                nextItem.prev = newNode;
                lastItemReturned = null;
            }
            size++;
            index++;
        }
    }
}
