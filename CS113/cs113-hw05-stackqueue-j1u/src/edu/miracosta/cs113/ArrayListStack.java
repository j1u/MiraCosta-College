package edu.miracosta.cs113;

import java.util.ArrayList;
import java.util.EmptyStackException;
import java.util.List;
import java.util.Objects;

public class ArrayListStack<E> implements StackInterface<E>{

    private List<E> theData;

    public ArrayListStack(){
        this.theData = new ArrayList<E>();
    }

    @Override
    public boolean empty() {
        return theData.isEmpty();
    }

    @Override
    public E peek() {
        if(!theData.isEmpty()) {
            return this.theData.get(theData.size() - 1);
        }
        throw new EmptyStackException();
    }

    @Override
    public E pop() {
        if(!theData.isEmpty()) {
            E tempObj = null;
            tempObj = theData.get(theData.size() - 1);
            this.theData.remove(theData.size() - 1);
            return tempObj;
        }
        throw new EmptyStackException();
    }

    @Override
    public E push(E obj) {
        this.theData.add(obj);
        return obj;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ArrayListStack<?> that = (ArrayListStack<?>) o;
        return Objects.equals(theData, that.theData);
    }

    @Override
    public String toString() {
        return "ArrayListStack{" +
                "theData=" + theData +
                '}';
    }

}
