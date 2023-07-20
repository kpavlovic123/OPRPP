package hr.fer.oprpp1.custom.collections;

public class ObjectStack<T> {
	private ArrayIndexedCollection<T> adaptee;
	public ObjectStack() {
		this.adaptee = new ArrayIndexedCollection<>();
	};
	public boolean isEmpty() {
		return adaptee.isEmpty();
	};
	public int size() {
		return adaptee.size();
	};
	public void push(T value) {
		adaptee.add(value);
	};
	public T pop() {
		if(adaptee.isEmpty())
			throw new EmptyStackException("Stack is empty");
		int last = adaptee.size()-1;
		T o = adaptee.get(last);
		adaptee.remove(last);
		return o;
	};
	public T peek() {
		if(adaptee.isEmpty())
			throw new EmptyStackException("Stack is empty");
		int last = adaptee.size()-1;
		T o = adaptee.get(last);
		return o;
	};
	public void clear() {
		adaptee.clear();
	};
}
