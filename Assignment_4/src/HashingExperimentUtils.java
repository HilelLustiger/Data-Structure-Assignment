import java.util.Collections; // can be useful
import java.util.List;

public class HashingExperimentUtils {
    final private static int k = 16;
    
    public static Pair<Double, Double> measureOperationsChained(double maxLoadFactor) {
    	HashingUtils Utils = new HashingUtils(); 
    	ModularHash factory = new ModularHash();
    	ChainedHashTable hash_table = new ChainedHashTable(factory, k, maxLoadFactor);
    	Integer[] arr = Utils.genUniqueIntegers((int)Math.pow(2, 18));
    	
    	//insertion
    	Double avg_ins = 0.0;
    	for (int i=0; i<((int)Math.pow(2, 16)*maxLoadFactor)-1; i++) {
    		double start = System.nanoTime();
    		hash_table.insert(arr[i], arr[i]);
    		double finish = System.nanoTime();
    		avg_ins = avg_ins + (finish - start);
    	}
    	avg_ins = avg_ins / ((Math.pow(2, 16)*maxLoadFactor)-1);
    	
    	//search
    	Double avg_search = 0.0;
    	for (int i=0; i<(2*((int)Math.pow(2, 16)*maxLoadFactor)-1); i++) {
    		double start = System.nanoTime();
    		hash_table.search(arr[i]);
    		double finish = System.nanoTime();
    		avg_search = avg_search + (finish - start);
    	}
    	avg_search = avg_search / (2*((int)Math.pow(2, 16)*maxLoadFactor)-1);
    	
    	return new Pair(avg_ins,avg_search);
    }
    
    public static Pair<Double, Double> measureOperationsProbing(double maxLoadFactor) {
        throw new UnsupportedOperationException("Replace this by your implementation");
    }

    public static Pair<Double, Double> measureLongOperations() {
    	HashingUtils Utils = new HashingUtils(); 
    	MultiplicativeShiftingHash factory = new MultiplicativeShiftingHash();
    	ChainedHashTable hash_table = new ChainedHashTable(factory, k, 1);
    	Long[] arr = Utils.genUniqueLong((int)Math.pow(2, 18));
    	
    	//insertion
    	Double avg_ins = 0.0;
    	for (int i=0; i<((int)Math.pow(2, 16))-1; i++) {
    		double start = System.nanoTime();
    		hash_table.insert(arr[i], arr[i]);
    		double finish = System.nanoTime();
    		avg_ins = avg_ins + (finish - start);
    	}
    	avg_ins = avg_ins / ((Math.pow(2, 16))-1);
    	
    	//search
    	Double avg_search = 0.0;
    	for (int i=0; i<(2*((int)Math.pow(2, 16))-1); i++) {
    		double start = System.nanoTime();
    		hash_table.search(arr[i]);
    		double finish = System.nanoTime();
    		avg_search = avg_search + (finish - start);
    	}
    	avg_search = avg_search / (2*((int)Math.pow(2, 16))-1);
    	
    	return new Pair(avg_ins,avg_search);
    }

    public static Pair<Double, Double> measureStringOperations() {
    	HashingUtils Utils = new HashingUtils(); 
    	StringHash factory = new StringHash();
    	ChainedHashTable hash_table = new ChainedHashTable(factory, k, 1);
    	List<String> arr = Utils.genUniqueStrings((int)Math.pow(2, 18),10,20);
    	
    	//insertion
    	Double avg_ins = 0.0;
    	for (int i=0; i<((int)Math.pow(2, 16))-1; i++) {
    		double start = System.nanoTime();
    		hash_table.insert(arr.get(i), arr.get(i));
    		double finish = System.nanoTime();
    		avg_ins = avg_ins + (finish - start);
    	}
    	avg_ins = avg_ins / ((Math.pow(2, 16))-1);
    	
    	//search
    	Double avg_search = 0.0;
    	for (int i=0; i<(2*((int)Math.pow(2, 16))-1); i++) {
    		double start = System.nanoTime();
    		hash_table.search(arr.get(i));
    		double finish = System.nanoTime();
    		avg_search = avg_search + (finish - start);
    	}
    	avg_search = avg_search / (2*((int)Math.pow(2, 16))-1);
    	
    	return new Pair(avg_ins,avg_search);    }

    public static void main(String[] args) {
        Double[] load_factor = {0.5,0.75,1.0,1.5,2.0};
        for (int i=0; i<5; i++) {
        	double avg_ins = 0.0;
        	double avg_search = 0.0;
        	Pair<Double,Double> rst;
        	for (int j=0; j<30; j++) {
        		rst = measureOperationsChained(load_factor[i]);
        		avg_ins = avg_ins + rst.first();
        		avg_search = avg_search + rst.second();
        	}
        	avg_ins = avg_ins / 30.0;
        	avg_search = avg_search / 30.0;
        	System.out.println("load factor is " + load_factor[i]);
        	System.out.println("avarage insertion is " + avg_ins);
        	System.out.println("avarage search is " + avg_search);
        	System.out.println("--------------------------------");


        }
    	
    	for (int k=0; k<20;k++) {
    	
    		System.out.println("test "+ (k+1));
	        double avg_ins_long = 0.0;
	       	double avg_search_long = 0.0;
	       	Pair<Double,Double> rst_long;
	       	for (int j=0; j<10; j++) {
	       		rst_long = measureOperationsChained(1);
	       		avg_ins_long = avg_ins_long + rst_long.first();
	       		avg_search_long = avg_search_long + rst_long.second();
	       	}
	       	avg_ins_long = avg_ins_long / 30.0;
	       	avg_search_long = avg_search_long / 30.0;
	       	System.out.println("avarage insertion is " + avg_ins_long);
	       	System.out.println("avarage search is " + avg_search_long);
	       	System.out.println("--------------------------------");
	
	       	
	       	double avg_ins_String = 0.0;
	       	double avg_search_String = 0.0;
	       	Pair<Double,Double> rst_String;
	       	for (int j=0; j<10; j++) {
	       		rst_String = measureOperationsChained(1);
	       		avg_ins_String = avg_ins_String + rst_String.first();
	       		avg_search_String = avg_search_String + rst_String.second();
	       	}
	       	avg_ins_String = avg_ins_String / 30.0;
	       	avg_search_String = avg_search_String / 30.0;
	       	System.out.println("avarage insertion is " + avg_ins_String);
	       	System.out.println("avarage search is " + avg_search_String);
	       	System.out.println("--------------------------------");
        }
    }
    
}
