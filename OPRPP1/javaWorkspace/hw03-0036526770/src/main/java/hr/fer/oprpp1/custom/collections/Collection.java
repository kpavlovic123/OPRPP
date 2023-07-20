package hr.fer.oprpp1.custom.collections;

public interface Collection <T> {
	
	default boolean isEmpty() {
		if(this.size()!=0) {
			return true;
		}
		else {
			return false;
		}
	};
	
	int size();
	
	void add(T value);
	
	boolean contains(Object value);
	
	boolean remove(T value);
	
	T[] toArray();
	
	void forEach(Processor<? super T> processor);
	
	default void addAll(Collection<? extends T> other) {
		class Adder implements Processor<T> {
			public void process(T value) {
				Collection.this.add(value);
			};
		};
		other.forEach(new Adder());
	};
	
	void clear();
	
	ElementsGetter<T> createElementsGetter();

	default void addAllSatisfying(Collection<? extends T> col, Tester<? super T> tester){
		class Adder implements Processor<T> {
			public void process(T obj){
				if(tester.test(obj))
				    add(obj);
			};
		}
		col.forEach(new Adder());
	};
}
