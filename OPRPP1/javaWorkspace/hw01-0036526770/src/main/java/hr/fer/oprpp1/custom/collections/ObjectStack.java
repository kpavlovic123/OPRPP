package hr.fer.oprpp1.custom.collections;

public class ObjectStack {
	private ArrayIndexedCollection adaptee;
	public ObjectStack() {
		this.adaptee = new ArrayIndexedCollection();
	};
	public boolean isEmpty() {
		return adaptee.isEmpty();
	};
	public int size() {
		return adaptee.size();
	};
	public void push(Object value) {
		adaptee.add(value);
	};
	public Object pop() {
		if(adaptee.isEmpty())
			throw new EmptyStackException("Stack is empty");
		int last = adaptee.size()-1;
		Object o = adaptee.get(last);
		adaptee.remove(last);
		return o;
	};
	public Object peek() {
		if(adaptee.isEmpty())
			throw new EmptyStackException("Stack is empty");
		int last = adaptee.size()-1;
		Object o = adaptee.get(last);
		return o;
	};
	public void clear() {
		adaptee.clear();
	};
}
