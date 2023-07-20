package hr.fer.oprpp1.custom.collections;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class LinkedListIndexedCollectionTest {
	
	@Test
	public void testConstructor1() {
		LinkedListIndexedCollection col = new LinkedListIndexedCollection();
		Integer expected = 0;
		assertEquals(expected,col.size());
	}
	
	@Test
	public void testConstructor2() {
		LinkedListIndexedCollection col2 = new LinkedListIndexedCollection();
		col2.add(1);
		col2.add(2);
		LinkedListIndexedCollection col = new LinkedListIndexedCollection(col2);
		Integer expected = 2;
		assertEquals(expected,col.size());
	}
	
	@Test
	public void testNullAdd() {
		LinkedListIndexedCollection col = new LinkedListIndexedCollection();
		assertThrows(NullPointerException.class, () -> col.add(null));
	}
	
	@Test
	public void testGetThrows() {
		LinkedListIndexedCollection col = new LinkedListIndexedCollection();
		assertThrows(IndexOutOfBoundsException.class, () -> col.get(-1));
	}
	
	@Test
	public void testClearFunction() {
		LinkedListIndexedCollection col = new LinkedListIndexedCollection();
		col.add(2);
		Integer expected = 0;
		col.clear();
		assertEquals(expected,col.size());
	}
	
	@Test
	public void testInsertThrows() {
		LinkedListIndexedCollection col = new LinkedListIndexedCollection();
		assertThrows(IndexOutOfBoundsException.class, () -> col.insert(5,-1));
	}
	
	@Test
	public void testIndexOfFunction() {
		LinkedListIndexedCollection col = new LinkedListIndexedCollection();
		col.add(2);
		col.add(3);
		Integer expected = 1;
		assertEquals(expected,col.indexOf(3));
	}
	
	@Test
	public void testRemoveThrows() {
		LinkedListIndexedCollection col = new LinkedListIndexedCollection();
		assertThrows(IndexOutOfBoundsException.class, () -> col.remove(-1));
	}
}
