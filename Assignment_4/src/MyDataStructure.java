import java.util.List;
import java.util.ArrayList;

public class MyDataStructure {
	
	int[] bloom;
	IndexableSkipList SkipList;
	
	
	
    /*
     * You may add any members that you wish to add.
     * Remember that all the data-structures you use must be YOUR implementations,
     * except for the List and its implementation for the operation Range(low, high).
     */

    /***
     * This function is the Init function described in Part 4.
     *
     * @param N The maximal number of items expected in the DS.
     */
    public MyDataStructure(int N) {
    	SkipList = new IndexableSkipList(0.5);
    	bloom = new int[N];
    	for (int i=0; i<N-1; i++) {
    		bloom[i] = 0;
    	}
    }


    public boolean insert(int value) {
    	boolean output = false;
    	if (Bloom==false){			// 
    		
    	}
        return false;
    }

    public boolean delete(int value) {
        return false;
    }

    public boolean contains(int value) {
        return false;
    }

    public int rank(int value) {
        return -1;
    }

    public int select(int index) {
        return Integer.MIN_VALUE;
    }

    public List<Integer> range(int low, int high) {
        return null;
    }
}
