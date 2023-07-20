package hr.fer.oprpp1.custom.collections;

import java.util.ConcurrentModificationException;
import java.util.NoSuchElementException;

public class LinkedListIndexedCollection implements List{
	private static class ListNode{
		ListNode previous;
		ListNode next;
		Object value;
	};
	private int size = 0;
	private ListNode first;
	private ListNode last;
	private long modificationCount;
	
	public LinkedListIndexedCollection(){
		this.first = null;
		this.last = null;
		this.modificationCount = 0;
	};
	
	public LinkedListIndexedCollection(Collection col) {
		this();
		this.addAll(col);
	};
	
	public boolean isEmpty() {
		if(size==0)
			return true;
		return false;
	};
	
	public int size() {
		return this.size;
	};
	
	public void add(Object value) {
		if(value == null)
			throw new NullPointerException("Null pointer reference");
		ListNode ins = new ListNode();
		ins.value = value;
		if(this.first == null) {
			this.first = ins;
			this.last = ins;
			size++;
			return;
		}
		last.next=ins;
		ins.previous = last;
		last = ins;
		size++;
		this.modificationCount++;
	};
	
	public boolean contains(Object value) {
		if (first == null)
				return false;
		ListNode tmp = first;
		while(tmp != null) {
			if(tmp.value == value)
				return true;
			tmp = tmp.next;
		};
		return false;
	};

	public boolean remove(Object value) {
		ListNode tmp = first;
		while(tmp != null) {
			if(tmp.value == value) {
				this.modificationCount++;
				tmp.previous.next = tmp.next;
				tmp.next.previous = tmp.previous;
				if(tmp == first)
					first = tmp.next;
				if(tmp == last)
					last = tmp.previous;
				size--;
				return true;
			}
			tmp = tmp.next;
		}
		return false;
	};
	
	public Object[] toArray() {
		Object[] a = new Object[size];
		ListNode tmp = first;
		int i = 0;
		while(tmp != null) {
			a[i++]=tmp.value;
			tmp = tmp.next;
		};
		return a;
	};
	
	
	public void clear() {
		this.modificationCount++;
		first = null;
		last = null;
		size = 0;
	};
	
	public Object get (int index) {
		if(index < 0 || index >= size)
			throw new IndexOutOfBoundsException("Index out of bounds.");
		if(index<=(size-1)/2) {
			ListNode tmp = first;
			while(index != 0) {
				index--;
				tmp = tmp.next;
			}
			return tmp.value;
		}
		else {
			ListNode tmp = last;
			while(index != size-1) {
				tmp = tmp.previous;
				index++;
			}
			return tmp.value;
		}
	};
	
	public void insert(Object value,int position) {
		if(position < 0 || position > size)
			throw new IndexOutOfBoundsException("Illegal argument.");
		if(value == null){
			throw new NullPointerException("Null pointer.");
		}
		if (position == size) {
			this.add(value);
			return;
		}
		this.modificationCount++;
		ListNode tmp = first;
		while(position != 0) {
			position--;
			tmp = tmp.next;
		}
		ListNode ins = new ListNode();
		ins.value = value;
		ins.next = tmp;
		ins.previous = tmp.previous;
		tmp.previous.next = ins;
		tmp.previous = ins;
		size++;
		if (tmp == first) 
			first = tmp.previous;
	};
	
	public int indexOf(Object value) {
		int index = 0;
		ListNode tmp = first;
		while(tmp != null) {
			if(tmp.value.equals(value)) {
				return index;
			}
			tmp = tmp.next;
			index++;
		}
		return -1;
	};
	
	public void remove (int index) {
		if(index < 0 || index>=size)
			throw new IndexOutOfBoundsException("Index out of bounds");
		ListNode tmp = first;
		this.modificationCount++;
		while(index != 0) {
			tmp = tmp.next;
			index--;
		}
		tmp.previous.next = tmp.next;
		tmp.next.previous = tmp.previous;
		if(tmp == first)
			first = tmp.next;
		if(tmp == last)
			last = tmp.previous;
		size--;
	};
	
	private static class LinkedListGetter implements ElementsGetter{
		private LinkedListGetter (LinkedListIndexedCollection col){
			this.col = col;
			this.node = col.first;
			this.savedModificationCount = col.modificationCount;
		};
		private long savedModificationCount;
		private LinkedListIndexedCollection col;
		private ListNode node;
		public boolean hasNextElement() {
			if(savedModificationCount != col.modificationCount)
				throw new ConcurrentModificationException("Collection modified.");
			return node.next != null ? true : false;
		};
		public Object getNextElement() {
			if(savedModificationCount != col.modificationCount)
				throw new ConcurrentModificationException("Collection modified.");
			if(this.hasNextElement()) {
				Object v = node.value;
				node = node.next;
				return v;
			}
			throw new NoSuchElementException("No such element.");
		};
	}
	
	public ElementsGetter createElementsGetter() {
		return new LinkedListGetter(this);
	};
	
}






