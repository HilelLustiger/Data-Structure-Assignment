import java.util.List;
import java.util.LinkedList;
import java.util.ArrayList;

public class ProbingHashTable<K, V> implements HashTable<K, V> {
    final static int DEFAULT_INIT_CAPACITY = 4;
    final static double DEFAULT_MAX_LOAD_FACTOR = 0.75;
    final static Pair deletedStr = new Pair("DELETED",null);
    final static Pair deletedNum = new Pair(-1 ,null);
    final private HashFactory<K> hashFactory;
    final private double maxLoadFactor;
    private int capacity;
    private HashFunctor<K> hashFunc;
    private Pair<K,V>[] table;
    private int size;
    private int k;

    /*
     * You should add additional private members as needed.
     */

    public ProbingHashTable(HashFactory<K> hashFactory) {
        this(hashFactory, 2, DEFAULT_MAX_LOAD_FACTOR);
    }

    public ProbingHashTable(HashFactory<K> hashFactory, int k, double maxLoadFactor) {
        this.hashFactory = hashFactory;
        this.maxLoadFactor = maxLoadFactor;
        this.capacity = 1 << k;
        this.hashFunc = hashFactory.pickHash(k);
        this.table = new Pair[(int)Math.pow(2, k)];
        //Initialising table to null
        for(int i = 0; i < capacity; i++) {
        	table[i] = null;
        }
        size = 0;
        this.k = k;        
    }

    public V search(K key) {
    	V output = null;
        int index = hashFunc.hash(key);
        for(int i = 0; i < capacity; i++) {
        	if(table[index].first().equals(key)) {
        		output = table[index].second();        		
        		return output;
        	}
        	if(table[index] == null)
        		return null;
        	index = HashingUtils.mod(index+1, capacity);
        }
        return null;
    }

    public void insert(K key, V value) {
    	int index = hashFunc.hash(key);
    	Pair toInsert = new Pair(key,value);  
    	boolean inserted = false;
    	if(size<(double)capacity*maxLoadFactor) {
    		for(int i = 0; i < capacity & !inserted; i++) {
    			if(table[index] == null | table[index] == deletedStr | table[index] == deletedNum) {
    				table[index] = toInsert;
    				inserted = true;
    				size++;
    			}
    			index = HashingUtils.mod(index+1, capacity);
    		}    
    	}
    	else {
    		//re-hash!!
    		ReHash();
    		insert(key,value);
    	}
    }
    
    public void ReHash() {//Utility method
    	//Adjust k and capacity 
    	this.k = k+1;
    	this.capacity = capacity*2;
    	//Create a new hash function for the new scale
    	Pair<K,V>[] newTable = new Pair[capacity];
    	hashFunc = hashFactory.pickHash(k);
    	for(int i = 0; i < capacity; i++) {//Initialising new table
			newTable[i] = null;    			
		}
    	Pair<K,V>[] array = table;
		this.table = newTable;
		size = 0;
    	for(int i = 0; i < capacity/2; i++) {//Copying unDeleted and unNull pairs
			if(array[i]!=null & array[i]!=deletedStr & array[i]!=deletedNum) {
				insert(array[i].first(),array[i].second());
			}
		}
    	System.out.println("rehashed");
    }

    public boolean delete(K key) {
    	int index = hashFunc.hash(key);
    	//V toDelete = search(key);
    	boolean removed = false;
    	if(!table[index].first().equals(key)) {
    	//iterate over the current and next indexes until found the wanted value or found first deleted/null value
    		while(table[index]!=null & !removed) {
    			index = HashingUtils.mod(index+1, capacity);
    			if(table[index].first().equals(key)) {
    				//replace current by the corresponding table DataType
    				if(table[index].first() instanceof String)
    					table[index] = deletedStr;
    				else
    					table[index] = deletedNum;
    				removed = true;
    				size--;
    			}    			
    		}
    	}
    	else
    		removed = true;
    	return removed;
    }

    public HashFunctor<K> getHashFunc() {
        return hashFunc;
    }

    public int capacity() { return capacity; }
    
    public static void main(String[] args) {
    	
    	HashingUtils Utils = new HashingUtils();
    	List<String> String_list = Utils.genUniqueStrings(1000, 1, 10);
   		Integer[] Integer_arr = Utils.genUniqueIntegers(1000);
   		Long[] Long_arr = Utils.genUniqueLong(1000);
    	
       	// -------------------------------------------------------------------------------------
//       	System.out.println("----Integer Hash----");
//       	ModularHash Integerfactory = new ModularHash(); 
//       	ChainedHashTable<Integer, Integer> Integer_table = new ChainedHashTable<>(Integerfactory,2,1);
//       	for (int i=0; i<130; i++) {
//           	Integer_table.insert(Integer_arr[i], i);
//           	System.out.println("Pair (" + Integer_arr[i]+" , " +i+") inserted");
//       	}
//       	
//       	int a = Integer_table.search(Integer_arr[4]);
//       	System.out.println(a);
//       	
//       	Integer_table.delete(Integer_arr[4]);
//       	System.out.println(Integer_table.search(Integer_arr[4]));

       	
    	
       	// -------------------------------------------------------------------------------------
       	System.out.println("----Long Hash----");
       	HashFactory<Long> Long_factory = new MultiplicativeShiftingHash();
       	ProbingHashTable<Long, Integer> Long_table = new ProbingHashTable<>(Long_factory,2,0.125);
       	for (int i=0; i<150; i++) {
       		Long_table.insert(Long_arr[i], i);
           	System.out.println("Pair (" + Long_arr[i]+" , " +i+") inserted");
       	}
       	
       	int b = Long_table.search(Long_arr[4]);
       	System.out.println(b);
       	
       	Long_table.delete(Long_arr[4]);
       	System.out.println(Long_table.search(Long_arr[4]));

      
       	// -------------------------------------------------------------------------------------
//       	System.out.println("----String Hash----");
//    	HashFactory<String> String_factory = new StringHash();
//        ChainedHashTable<String, Integer> String_table = new ChainedHashTable<>(String_factory,2,1);
//        for (int i=0; i<20; i++) {
//        	String_table.insert(String_list.get(i), i);
//           	System.out.println("Pair (" + String_list.get(i)+" , " +i+") inserted");
//       	}
//        
//        int c = String_table.search(String_list.get(4));
//       	System.out.println(c);
//       	
//       	String_table.delete(String_list.get(4));
//       	System.out.println(String_table.search(String_list.get(4)));
    }
    	
    }
    // -------------------------------------------------------------------------
//    public void insert(K key, V value) {
//    	int index = hashFunc.hash(key);
//    	Pair toInsert = new Pair(key,value);  
//    	boolean inserted = false;
//    	if(size<Math.ceil(capacity*maxLoadFactor)) {
//    		for(int i = 0; i < capacity & !inserted; i++) {
//    			if(table[index] == null | table[index] == deletedStr | table[index] == deletedNum) {
//    				table[index] = toInsert;
//    				inserted = true;
//    				size++;
//    			}
//    			index = HashingUtils.mod(index+1, capacity);
//    		}    
//    	}
//    	else {
//    		//re-hash!!
//    		ReHash();
//    		insert(key,value);
//    	}
//    }

