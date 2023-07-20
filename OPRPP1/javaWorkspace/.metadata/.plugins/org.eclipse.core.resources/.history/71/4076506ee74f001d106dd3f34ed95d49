package hr.fer.oprpp1.custom.collections;

public class Collection {
	
	protected Collection() {};
	
	boolean isEmpty() {
		if(this.size()!=0) {
			return true;
		}
		else {
			return false;
		}
	};
	
	int size() {
		return 0;
	};
	
	void add(Object value) {};
	
	boolean contains(Object value) {
		return false;
	};
	
	boolean remove(Object value) {
		return false;
	};
	
	Object[] toArray() {
		throw new UnsupportedOperationException("UnsupportedOperationException");
	};
	
	void forEach(Processor processor) {};
	
	void addAll(Collection other) {
		class Adder extends Processor {
			public void process(Object value) {
				Collection.this.add(value);
			};
		};
		Adder adder = new Adder();
		other.forEach(adder);
	};
	
	void clear(){};
}
