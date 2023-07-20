package hr.fer.oprpp1.custom.collections;

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.lang.Math;


public class SimpleHashtable<K, V> implements Iterable<SimpleHashtable.TableEntry<K,V>> {

    int size = 0;
    TableEntry<K, V>[] hashTable;
    int modificationCount = 0; 

    public static class TableEntry<K, V> {
        private K key;
        private V value;
        private TableEntry<K, V> next;

        public TableEntry(K key, V value) {
            this.key = key;
            this.value = value;
        };

        public K getKey() {
            return key;
        };

        public V getValue() {
            return value;
        };

        public void setValue(V value) {
            this.value = value;
        };
    };

    @SuppressWarnings("unchecked")
    public SimpleHashtable() {
        hashTable = (TableEntry<K, V>[]) new TableEntry[16];
    };

    @SuppressWarnings("unchecked")
    public SimpleHashtable(int size) {
        if (size < 1) {
            throw new IllegalArgumentException("Illegal argument exception.");
        }
        int cap = 1;
        while (cap < size) {
            cap *= 2;
        }
        hashTable = (TableEntry<K, V>[]) new TableEntry[cap];
    };
    @SuppressWarnings("unchecked")
    public V put(K key, V value) {
        if (key == null)
            throw new NullPointerException("Null pointer exception");
        if((float)size/(float)hashTable.length>=0.75){
            var table = this.toArray();
            hashTable = (TableEntry<K,V>[])new TableEntry[hashTable.length*2];
            size = 0;
            for(int i = 0;i<table.length;i++){
                this.put(table[i].key, table[i].value);
            };
        };
        int index = Math.abs(key.hashCode()) % hashTable.length;
        if (hashTable[index] == null) {
            TableEntry<K, V> entry = new TableEntry<>(key, value);
            hashTable[index] = entry;
            size++;
            modificationCount++;
            return null;
        } else {
            TableEntry<K, V> entry = hashTable[index];
            while (!entry.key.equals(key) && entry.next != null){
                entry = entry.next;
            };
            if (entry.key.equals(key)) {
                V value2 = entry.value;
                entry.value = value;
                return value2;
            } else {
                TableEntry<K, V> e = new TableEntry<>(key, value);
                entry.next = e;
                size++;
                modificationCount++;
                return null;
            }
        }
    };

    public V get(Object key) {
        if (key == null)
            return null;
        int index = Math.abs(key.hashCode()) % hashTable.length;
        if (hashTable[index] == null)
            return null;
        else {
            TableEntry<K, V> e = hashTable[index];
            while (!e.key.equals(key) && e.next != null){
                e = e.next;
            };
            if (key.equals(e.key))
                return e.value;
            else
                return null;
        }
    };

    public int size() {
        return size;
    };

    public boolean containsKey(Object key) {
        if (key == null)
            return false;
        int index = Math.abs(key.hashCode()) % hashTable.length;
        if (hashTable[index] == null)
            return false;
        else {
            TableEntry<K, V> e = hashTable[index];
            while (!e.key.equals(key) && e.next != null){
                e = e.next;
            };
            if (key.equals(e.key))
                return true;
            else
                return false;
        }
    };

    public boolean containsValue(Object value) {
        for (int i = 0; i < hashTable.length; i++) {
            if (hashTable[i] == null)
                continue;
            var e = hashTable[i];
            while (!e.value.equals(value) && e.next != null){
                e = e.next;
            };
            if (e.value.equals(value))
                return true;
        }
        return false;
    };

    public V remove(Object key) {
        if (key == null)
            return null;
        int index = Math.abs(key.hashCode()) % hashTable.length;
        if (hashTable[index] == null)
            return null;
        else if (hashTable[index].key.equals(key)) {
            var v = hashTable[index].value;
            hashTable[index] = hashTable[index].next;
            size--;
            modificationCount++;
            return v;
        } else {
            TableEntry<K, V> e = hashTable[index];
            while (e.next != null && !e.next.key.equals(key)){
                e = e.next;
            };
            if (e.next == null) {
                return null;
            } else {
                var e2 = e.next;
                e.next = e2.next;
                size--;
                modificationCount++;
                return e2.value;
            }

        }

    };

    public boolean isEmpty() {
        if (size == 0)
            return true;
        return false;
    };

    public String toString() {
        String s = new String();
        for (int i = 0; i < hashTable.length; i++) {
            s += "[";
            if (hashTable[i] == null)
                continue;
            var e = hashTable[i];
            while (e.next != null) {
                s += e.key.toString() + " " + e.value.toString() + ", ";
                e = e.next;
            }
            ;
            s = s.substring(0, s.length() - 2);
            s += "]\n";
        }
        return s;
    };
    @SuppressWarnings("unchecked")
    public TableEntry<K, V>[] toArray() {
        TableEntry<K, V>[] table = (TableEntry<K,V>[])new TableEntry[size];
        int added = 0;
        for (int i = 0; i < hashTable.length; i++) {
            if (hashTable[i] == null)
                continue;
            var e = hashTable[i];
            for(;e.next!=null;e=e.next){
                table[added++]=e;
            };
            table[added++]=e;
        }
        return table;
    };

    public void clear(){
        for(int i = 0;i<hashTable.length;i++){
            hashTable[i] = null;
        };
    };

    private class IteratorImpl implements Iterator<SimpleHashtable.TableEntry<K,V>> {
        int slot = 0;
        TableEntry<K,V> e;
        int flag = 0;
        int modificationCount;

        private IteratorImpl(){
            this.modificationCount = SimpleHashtable.this.modificationCount;
        }
        public boolean hasNext() {
            if(modificationCount!=SimpleHashtable.this.modificationCount)
                throw new ConcurrentModificationException();
            int slot = this.slot;
            if(e != null){
                if(e.next == null)
                    slot++;
                else 
                    return true;
            }
            for(;slot<hashTable.length;slot++){
                if(hashTable[slot] == null)
                    continue;
                return true;
            }
            return false;
        }
        public SimpleHashtable.TableEntry<K,V> next() {
            if(modificationCount!=SimpleHashtable.this.modificationCount)
                throw new ConcurrentModificationException();
            if (flag==1)
                flag = 0;
            if(e != null){
                if(e.next != null){
                    e = e.next;
                    return e;
                }
                else{
                    e = null;
                    slot++;
                }
            }
            for(;slot<hashTable.length;slot++){
                if(hashTable[slot] == null)
                    continue;
                e = hashTable[slot];
                return e;
            }
            throw new NoSuchElementException();


        }
        public void remove() {
            if(modificationCount!=SimpleHashtable.this.modificationCount)
                throw new ConcurrentModificationException();
            if(flag==1)
                throw new IllegalStateException();
            SimpleHashtable.this.remove(e.key);
            modificationCount++;
        }
    }

    public Iterator<SimpleHashtable.TableEntry<K,V>> iterator() {
        return new IteratorImpl();
    };


}
