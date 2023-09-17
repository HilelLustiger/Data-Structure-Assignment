import static org.junit.Assert.*;
import org.junit.Test;

public class IndexableSkipListTest {

	@Test
	public void test() {
		IndexableSkipList SL = new IndexableSkipList(0.4);
		for (int i=1; i<=5;i++) {
			SL.insert(i);
		}
		int output = SL.find(2).key();
		int expected = 2; 
		
		assertEquals(output, expected);
	}

}
