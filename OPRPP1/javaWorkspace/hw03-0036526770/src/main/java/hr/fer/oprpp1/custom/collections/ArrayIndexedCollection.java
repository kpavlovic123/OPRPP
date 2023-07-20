package hr.fer.oprpp1.custom.collections;

import java.util.ConcurrentModificationException;
import java.util.NoSuchElementException;

public class ArrayIndexedCollection<T> implements Collection<T>  {
	private int size = 0;
	private T[] elements;
	private long modificationCount = 0;
	@SuppressWarnings("unchecked")
	public ArrayIndexedCollection () {
		
		this.elements = (T[])new Object[16];
	};
	@SuppressWarnings("unchecked")
	public ArrayIndexedCollection (int initialCapacity) {
		if(initialCapacity < 1) {
			throw new IllegalArgumentException("Capacity less than 1.");
		}
		this.elements = (T[])new Object[initialCapacity];
	};
	public ArrayIndexedCollection (Collection<? extends T> col) {
		this();
		if(col == null) {
			throw new NullPointerException("Given collection is a null reference");
		}
		this.addAll(col);
	};
	@SuppressWarnings("unchecked")
	public ArrayIndexedCollection(Collection<? extends T> col,int initialCapacity) {
		this(initialCapacity);
		if(col == null) {
			throw new NullPointerException("Given collection is a null reference");
		}
		if (col.size() > initialCapacity) {
			elements = (T[]) new Object[col.size()];
		}
		this.addAll(col);
	};
	
	public boolean isEmpty() {
		if(this.size == 0) {
			return true;
		}
		return false;
	};
	
	public int size() {
		return this.size;
	};

	@SuppressWarnings("unchecked")
	public void add(T value) {
		if (value == null) {
			throw new NullPointerException("Given object is a null reference");
		}
		if(elements.length <= this.size) {
			T[] elementsTmp = elements;
			elements = (T[]) new Object[elements.length*2];
			for (int i = 0;i<elementsTmp.length;i++) {
				elements[i]=elementsTmp[i];
			}
		}
		this.modificationCount++;
		elements[size++]=value;
	};
	
	public boolean contains(Object value) {
		for(final var e : elements) {
			if(e.equals(value))
				return true;
		}
		return false;
	};
	
	public boolean remove(Object value) {
		for(int i = 0;i<elements.length;i++) {
			if(value.equals(elements[i])) {
				this.modificationCount++;
				elements[i]=null;
				this.size--;
				for(int j = i+1;j<elements.length;j++) {
					elements[j-1]=elements[j];
				}
				return true;
			}
		}
		return false;
	};
	
	@SuppressWarnings("unchecked")
	public T[] toArray() {
		T[] a = (T[]) new Object[size];
		for(int i = 0;i<this.size;i++) {
			a[i] = elements[i];
		}
		return a;
	};
	
	public void forEach(Processor<? super T> processor) {
		for(int i = 0;i<this.size;i++) {
			processor.process(elements[i]);
		}
	};
	
	public void clear(){
		this.modificationCount++;
		for(int i = 0;i>size;i++) {
			elements[i]=null;
		}
		size = 0;
	};
	
	public T get(int index) {
		if(index < 0 || index >= size)
			throw new IndexOutOfBoundsException("Index out of bound.");
		return elements[index];
	};
	
	@SuppressWarnings("unchecked")
	public void insert(T value,int position) {
		if(position < 0 || position > size){
			throw new IndexOutOfBoundsException("Index out of bound.");
		}
		if (size == position) {
			this.add(value);
			return;
		}
		T[] tmp;
		if(size+1 > elements.length)
			tmp = (T[]) new Object[elements.length*2];
		else
			tmp = (T[]) new Object[elements.length];
		for(int i = 0;i<size;i++) {
			if(i == position) {
				tmp[position] = value;
				for(int j = position;j<size;j++) {
					tmp[j+1] = elements[j];
				}
				this.modificationCount++;
				size++;
				elements = tmp;
				return;
			}
			tmp[i] = elements[i];
		}
	};
	public int indexOf(Object value) {
		if (value == null)
				return -1;
		for(int i = 0;i<size;i++) {
			if(value.equals(elements[i]))
				return i;
		}
		return -1;
	};
	
	public void remove (int index) {
		if (index < 0 || index >= size)
			throw new IndexOutOfBoundsException("Index out of bounds.");
		elements[index]=null;
			for (int j = index+1;j<size;j++) {
				elements[j-1]=elements[j];
			}
			this.modificationCount++;
			size--;
	};
	
	private static class ArrayIndexedGetter<T> implements ElementsGetter <T> { 
		private ArrayIndexedGetter (ArrayIndexedCollection<T> col){
			this.col = col;
			this.n = 0;
			this.savedModificationCount = col.modificationCount;
		};
		private int n;
		private long savedModificationCount;
		private ArrayIndexedCollection<T> col;
		public boolean hasNextElement() {
			if(savedModificationCount != col.modificationCount)
				throw new ConcurrentModificationException("Collection modified.");
			return n < col.size ? true : false;
		};
		public T getNextElement() {
			if(savedModificationCount != col.modificationCount)
				throw new ConcurrentModificationException("Collection modified.");
			if(this.hasNextElement()) {
				return col.elements[n++];
			}
			throw new NoSuchElementException("No such element.");
		};
	}
	
	public ElementsGetter<T> createElementsGetter() {
		return new ArrayIndexedGetter<T>(this);
	}

}













