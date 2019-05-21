package edu.miracosta.cs113;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;
import java.util.AbstractSet;
import java.util.NoSuchElementException;
import java.util.Collection;


/**
 *
 * @author James Lu
 * @version 1.0
 *
 */


public class HashTableChain<K, V> implements Map<K, V> {

    private LinkedList<Entry<K, V>>[] table;

    private int numKeys;

    private static final int DEFAULT_CAPACITY = 101;

    private static final double LOAD_THRESHOLD = 3.0;

    public HashTableChain() { //default constructor
        table = new LinkedList[DEFAULT_CAPACITY];
    }

    public HashTableChain(int capacity){ //constructor with a capacity used for rehashing
        table = new LinkedList[capacity];
        numKeys = 0;
    }

    @Override
    public int size() {
        return numKeys;
    }

    @Override
    public boolean isEmpty() {
        boolean empty = true;
        for (int i = 0; i < table.length; i++){
            if (table[i] != null){
                empty = false;
            }
        }

        return empty;
    }

    @Override
    public boolean containsKey(Object key) {
        //find index, check each node in index
        int index = key.hashCode() % table.length;

        if (index < 0){
            index = index + size();
        }

        Iterator iter = table[index].listIterator();
        Entry<K, V> entry = (Entry<K, V>) iter.next(); //init entry with first object in list

        if(entry.getKey().equals(key)){
            return true;
        }else {
            while (iter.hasNext()) {
                entry = (Entry<K, V>) iter.next(); //next object in list
                if (entry.getKey().equals(key)){
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public boolean containsValue(Object value) {
        for(int i = 0; i < table.length; i++) {
            if (table[i] != null) {
                Iterator iter = table[i].listIterator(); //init to index 0 of list in bucket i
                Entry<K, V> entry = (Entry<K, V>) iter.next();

                if (entry.getValue().equals(value)) {
                    return true;
                } else {
                    while (iter.hasNext()) {
                        entry = (Entry<K, V>) iter.next();
                        if (entry.getValue().equals(value)) {
                            return true;
                        }
                    }
                }
            }
        }

        return false;
    }

    private void rehash(){
        HashTableChain<K, V> rehash;
        Iterator<Map.Entry<K, V>> iterator = this.entrySet().iterator();
        int size = table.length;


        size = (size * 2) + 1;
        rehash = new HashTableChain<K, V>(size);

        for(int i = 0; i < this.numKeys; i++) {
            Map.Entry<K, V> entry = iterator.next();
            rehash.put(entry.getKey(), entry.getValue());
        }

        this.table = rehash.table;

    }

    @Override
    public V get(Object key) {
        int index = key.hashCode() % table.length;
        if (index < 0)
            index += table.length;
        if (table[index] == null)
            return null; // key is not in the table.
        // Search the list at table[index] to find the key.
        for (Entry<K, V> nextItem : table[index]) {
            if (nextItem.getKey().equals(key))
                return nextItem.getValue();
        }
        // assert: key is not in the table.
        return null;
    }

    @Override
    public V put(K key, V value) {
        int index = key.hashCode() % table.length;
        if (index < 0)
            index += table.length;
        if (table[index] == null) {
            // Create a new linked list at table[index].
            table[index] = new LinkedList<>();
        }

        // Search the list at table[index] to find the key.
        for (Entry<K, V> nextItem : table[index]) {
            // If the search is successful, replace the old value.
            if (nextItem.getKey().equals(key)) {
                // Replace value for this key.
                V oldVal = nextItem.getValue();
                nextItem.setValue(value);
                return oldVal;
            }
        }
        // assert: key is not in the table, add new item.
        table[index].addFirst(new Entry<>(key, value));
        numKeys++;
        if (numKeys > (LOAD_THRESHOLD * table.length))
            rehash();
        return null;
    }

    @Override
    public V remove(Object key) {
        int index = key.hashCode() % table.length;
        if (index < 0){
            index = index + table.length;
        }

        if (table[index] == null){
            return null;
        }

        for (int i = 0; i < table.length; i++){
            Iterator iter = table[index].listIterator();
            Entry<K, V> entry = (Entry<K, V>) iter.next(); //init entry with first object in list

            if(entry.getKey().equals(key)){
                V oldValue = (V) entry.getValue();
                iter.remove();
                numKeys--;
                if (table[index].isEmpty()){
                    table[index] = null;
                }
                return oldValue;
            }else {
                while (iter.hasNext()) {
                    entry = (Entry<K, V>) iter.next(); //next object in list
                    if (entry.getKey().equals(key)){
                        V oldValue = (V) entry.getValue();
                        iter.remove();
                        numKeys--;
                        return oldValue;
                    }
                }
            }
        }
        return null;
    }



    @Override
    public void clear() {
        for (int i = 0; i < table.length; i++){
            table[i] = null;
        }
    }

    @Override
    public Set<Map.Entry<K, V>> entrySet() {
        return new EntrySet();
    }

    @Override
    public Set<K> keySet() {
        return new KeySet();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof Map)) {
            return false;
        }

        return this.entrySet().equals(((Map) obj).entrySet());
    }

    @Override
    public int hashCode() {
        return new EntrySet().hashCode() + 1;
    }

    @Override
    public Collection<V> values() { //stubbed
        return null;
    }

    @Override
    public void putAll(Map<? extends K, ? extends V> m) { //stubbed

    }


    private static class Entry<K, V> implements Map.Entry<K, V>{ //entry class
        private K key;
        private V value;

        public Entry(K key, V value){
            this.key = key;
            this.value = value;
        }

        public K getKey(){
            return key;
        }

        public V getValue(){
            return value;
        }

        public V setValue(V newValue){
            V oldValue = value;
            this.value = newValue;
            return oldValue;
        }

        @Override
        public int hashCode() {
            return key.hashCode() * value.hashCode();
        }

        @Override
        public String toString() {
            return getKey() + "=" + getValue();
        }
    }

    /**
     * Class that generated a set of keys from the map
     */
    private class KeySet extends AbstractSet<K> {

        @Override
        public Iterator<K> iterator() {
            return new SetIterator();
        }

        @Override
        public int size() {
            return HashTableChain.this.numKeys;
        }

        /**
         * Class that implements iterator and iterates over a set of keys
         */
        private class SetIterator implements Iterator<K> {

            private Iterator<Map.Entry<K, V>> entrySetIterator = HashTableChain.this.entrySet().iterator();

            @Override
            public boolean hasNext() {
                return this.entrySetIterator.hasNext();
            }

            @Override
            public K next() throws NoSuchElementException {
                return this.entrySetIterator.next().getKey();
            }

            @Override
            public void remove() throws NoSuchElementException{
                this.entrySetIterator.remove();
            }
        }
    }

    /**
     * Class to implement a set generated by the map
     */
    private class EntrySet extends AbstractSet<Map.Entry<K, V>> {

        @Override
        public Iterator<Map.Entry<K, V>> iterator() {
            return new SetIterator();
        }

        @Override
        public int size() {
            return HashTableChain.this.numKeys;
        }

        @Override
        public String toString() {
            StringBuilder toString = new StringBuilder();
            Iterator<Map.Entry<K, V>> iterator = this.iterator();

            while(iterator.hasNext()) {
                toString.append(iterator.next().toString() + ", ");
            }
            return "["+ toString.substring(0, toString.length()-2) +"]";
        }

        @Override
        public int hashCode() {
            int hashCode = 0;
            Iterator<Map.Entry<K, V>> iterator = this.iterator();

            while(iterator.hasNext()) {
                hashCode += iterator.next().hashCode();
            }
            return hashCode;
        }

        /**
         * Iterator for a set generated by the map
         */
        private class SetIterator implements Iterator<Map.Entry<K, V>> {

            private int index;
            private int tableIndex;
            private Map.Entry<K, V> lastItemReturned;
            private Iterator<Entry<K, V>> listIterator;

            public SetIterator() {
                this.index = 0;
                this.lastItemReturned = null;
                this.tableIndex = this.getTableIndex();

                if(this.tableIndex < 0) {
                    this.tableIndex = 0;
                }
                this.listIterator = HashTableChain.this.table[this.tableIndex].iterator();
            }

            /**
             * Returns the next index in table with another Entry. If there is not another Entry in the list, then
             *  0 is returned.
             * @return Next index
             */
            private int getTableIndex() {
                int nextIndex = 0;
                boolean found = false;

                for(int i = this.tableIndex+1; i < HashTableChain.this.table.length && !found; i++) {
                    if(HashTableChain.this.table[i] != null) {
                        if(!HashTableChain.this.table[i].isEmpty()) {
                            nextIndex = i;
                            found = true;
                        }
                    }
                }
                return nextIndex;
            }

            @Override
            public boolean hasNext() {
                return this.index < HashTableChain.this.numKeys;
            }

            @Override
            public Map.Entry<K, V> next() throws NoSuchElementException {
                Map.Entry<K, V> next;

                try {
                    next = this.listIterator.next();
                }
                catch(NoSuchElementException e) {
                    if(this.hasNext()) {
                        this.tableIndex = this.getTableIndex();
                        this.listIterator = HashTableChain.this.table[this.tableIndex].iterator();
                        next =  this.listIterator.next();
                    }
                    else {
                        throw new NoSuchElementException();
                    }
                }
                this.index++;
                this.lastItemReturned = next;
                return next;
            }

            @Override
            public void remove() throws NoSuchElementException{
                if(this.lastItemReturned == null) {
                    throw new NoSuchElementException();
                }
                else {
                    this.listIterator.remove();
                    this.lastItemReturned = null;
                }
            }
        }
    }
}
