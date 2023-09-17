import java.util.List;
import java.util.ArrayList;
import java.util.LinkedList;

public class ChainedHashTable<K, V> implements HashTable<K, V> {
    final static int DEFAULT_INIT_CAPACITY = 4;
    final static double DEFAULT_MAX_LOAD_FACTOR = 2;
    final private HashFactory<K> hashFactory;
    final private double maxLoadFactor;
    private int capacity;
    private int k;
    private int size;
    private HashFunctor<K> hashFunc;
    private LinkedList<Pair>[] table;


    public ChainedHashTable(HashFactory<K> hashFactory) {
        this.hashFactory = hashFactory;
        this.maxLoadFactor = DEFAULT_MAX_LOAD_FACTOR;
        this.capacity = DEFAULT_INIT_CAPACITY;
        this.hashFunc = hashFactory.pickHash(2);
        this.table = new LinkedList[DEFAULT_INIT_CAPACITY];
        this.size = 0;
        this.k = 2;
    }

    public ChainedHashTable(HashFactory<K> hashFactory, int k, double maxLoadFactor) {
        this.hashFactory = hashFactory;
        this.maxLoadFactor = maxLoadFactor;
        this.capacity = 1 << k;
        this.hashFunc = hashFactory.pickHash(k);
        this.table = new LinkedList[(int)Math.pow(2,k)];
        this.size = 0;
        this.k = k;
    }

    public V search(K key) {
    	V output = null;
        boolean found = false;
        int index = this.hashFunc.hash(key);
        if (table[index]!=null && !table[index].isEmpty()) {				//we'll check in the linkedlist if it isn't empty	
        	int i=0;
        	while (i<table[index].size() & !found) { //while we didnt find V and there are more links to check
        		if (table[index].get(i).first().equals(key)) {			// we will change found to true if V of the pair is equal to the wanted V.
        			found = true;
        			output = (V)table[index].get(i).second();
        		} else {
        			i=i+1;
        		}
        	}
        }
        if (found) {
        	return output;
        } else {
        	return null;
        }
    }

    public void insert(K key, V value) {
    	Pair toAdd = new Pair(key,value);
        int index = this.hashFunc.hash(key);
        if (table[index]==null) {
        	table[index] = new LinkedList<Pair>();
        	table[index].addFirst(toAdd);
        } else {
        	table[index].addFirst(toAdd);

        }
        size = size + 1;
        rehash();
    }

    public boolean delete(K key) {
    	boolean output = false;
        Pair toDelete;
        int index = this.hashFunc.hash(key);
        boolean found = false;
        if (table[index]!=null && !table[index].isEmpty()) {				//we'll check in the linkedlist if it isn't empty	
        	int i=0;
        	while (i<table[index].size() & !found) { //while we didnt find V and there are more links to check
        		if (table[index].get(i).first().equals(key)) {			// we will change found to true if V of the pair is equal to the wanted V.
        			found = true;
        			toDelete = table[index].get(i);
        			output = table[index].remove(toDelete);
        		} else {
        			i=i+1;
        		}
        	}
        }
        return output;
    }

    public HashFunctor<K> getHashFunc() {
        return hashFunc;
    }

    public int capacity() { return capacity; }
    
    // a function that checks if rehashing is needed, and if so - performs it.
    private void rehash(){
    	if ((double)size / capacity >= maxLoadFactor) {
    		k = k+1;
    		capacity = 2*capacity;
    		hashFunc = this.hashFactory.pickHash(k);
    		LinkedList<Pair>[] oldTable = table;
    		table = new LinkedList[capacity];
    		for (int i=0; i<oldTable.length; i++) {
    			while (oldTable[i]!=null && oldTable[i].size()!=0) {
    				Pair toReHash = oldTable[i].getFirst();
    				size = size -1;
    				this.insert((K)toReHash.first(), (V)toReHash.second());
    				oldTable[i].removeFirst();
    			}
    		}
    		System.out.println("rehashed!");
    	}
    }
    
    
    
    // ---------------------------------- MAIN ---------------------------------------------
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
       	ChainedHashTable<Long, Integer> Long_table = new ChainedHashTable<>(Long_factory,2,0.125);
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
