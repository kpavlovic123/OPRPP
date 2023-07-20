package hr.fer.oprpp1.custom.collections;

public interface Collection {
	
	default boolean isEmpty() {
		if(this.size()!=0) {
			return true;
		}
		else {
			return false;
		}
	};
	
	int size();
	
	void add(Object value);
	
	boolean contains(Object value);
	
	boolean remove(Object value);
	
	Object[] toArray();
	
	default void forEach(Processor processor) {
	    ElementsGetter e = createElementsGetter();
	    while(e.hasNextElement()) {
	        Object o = e.getNextElement();
	        processor.process(o);
	    }
	};
	
	default void addAll(Collection other) {
		class Adder implements Processor {
			public void process(Object value) {
				Collection.this.add(value);
			};
		};
		Adder adder = new Adder();
		other.forEach(adder);
	};
	
	void clear();
	
	ElementsGetter createElementsGetter();

	default void addAllSatisfying(Collection col, Tester tester){
		class Adder implements Processor {
			public void process(Object obj){
				if(tester.test(obj))
				    add(obj);
			};
		}
		Adder adder = new Adder();
		col.forEach(adder);
	};
}
