package hr.fer.oprpp1.custom.collections;

public class Dictionary<K,V> {
    @SuppressWarnings("unchecked")
    private class Pair <K2,V2> {
        K2 key;
        V2 value;
        private Pair(K2 key,V2 value){
            this.key = key;
            this.value = value;
        };
        public boolean equals(Object o){
            if(!(o instanceof Pair)){
                return false;
            }
            Pair<K2,V2> e = (Pair<K2,V2>) o;
            if(e.key.equals(key))
                return true;
            return false;
        }
    }

    public Dictionary(){
        this.adaptee = new ArrayIndexedCollection<>();
    }
    
    ArrayIndexedCollection<Pair<K,V>> adaptee;
    public int size(){
        return adaptee.size();
    }

    public void clear(){
        adaptee.clear();
    };

    public V put(K key,V value){
        Pair<K,V> e = new Pair<>(key,value);
        int i;
        if((i = adaptee.indexOf(e))!=-1){
            Pair<K,V> e2 = adaptee.get(i);
            var v = e2.value;
            e2.value = e.value;
            return v;
        }
        else{
            adaptee.add(e);
            return null;
        }
    };

    public V get(Object key){
            Pair<Object,?> e = new Pair<>(key,null);
            int i;
            if((i = adaptee.indexOf(e)) != -1){
                return adaptee.get(i).value;
            }
            else   
                return null;
        
    };

    public V remove (K key){
        Pair<K,V> e = new Pair<>(key,null);
        int i;
        if((i = adaptee.indexOf(e)) != -1){
            Pair<K,V> e2 = adaptee.get(i);
            adaptee.remove(i);
            return e2.value;
        }
        else   
            return null;
    };
}
