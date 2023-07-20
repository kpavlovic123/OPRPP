import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import hr.fer.oprpp1.custom.collections.*;

public class Tests {
    //Zadatak 2. Dictionary

    @Test
	public void testPut() {
		Dictionary<String,Integer> d = new Dictionary<>();
        d.put("test", 1);
        d.put("test2", 2);
        d.put("test", 2);
		
		assertEquals(2,d.size(),"Put");

    }
    @Test
	public void testClear() {
		Dictionary<String,Integer> d = new Dictionary<>();
        d.put("test", 1);
        d.put("test2", 2);
        d.put("test", 2);
        d.clear();
		
		assertEquals(0,d.size(),"Clear");
    }

    @Test
	public void testGet() {
		Dictionary<String,Integer> d = new Dictionary<>();
        d.put("test", 1);
        d.put("test2", 2);
        d.put("test", 2);
		
		assertEquals(2,d.get("test"),"Get");
    }

    
    @Test
	public void testRemove() {
		Dictionary<String,Integer> d = new Dictionary<>();
        d.put("test", 1);
        d.put("test2", 2);
        d.put("test", 2);
        d.remove("test");
		
		assertEquals(1,d.size(),"Remove");

    }

    //Zadatak 3. SimpleHashTable

    @Test
	public void testPut2() {
		SimpleHashtable<String,Integer> d = new SimpleHashtable<>();
        d.put("test", 1);
        d.put("test2", 2);
        d.put("test", 2);
		
		assertEquals(2,d.size(),"Put");

    }

    @Test
	public void testClear2() {
		Dictionary<String,Integer> d = new Dictionary<>();
        d.put("test", 1);
        d.put("test2", 2);
        d.put("test", 2);
        d.clear();
		
		assertEquals(0,d.size(),"Clear");
    }

    @Test
	public void testGet2() {
		Dictionary<String,Integer> d = new Dictionary<>();
        d.put("test", 1);
        d.put("test2", 2);
        d.put("test", 2);
		
		assertEquals(2,d.get("test"),"Get");
    }

    @Test
	public void testRemove2() {
		Dictionary<String,Integer> d = new Dictionary<>();
        d.put("test", 1);
        d.put("test2", 2);
        d.put("test", 2);
        d.remove("test");
		
		assertEquals(1,d.size(),"Remove");

    }

    
}

