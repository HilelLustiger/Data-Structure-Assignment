import java.util.Random;

public class ModularHash implements HashFactory<Integer> {
	public HashingUtils Utils;
	
	//creating an abject (function family)
    public ModularHash() {						
    	Utils = new HashingUtils();
    }

    @Override
    public HashFunctor<Integer> pickHash(int k) {
    	//generating numbers (int and long)
    	Integer[] numbers = Utils.genUniqueIntegers(2);						
    	Long p = Utils.genLong(Integer.MAX_VALUE+1, Long.MAX_VALUE-1);
    	//performing the rabin miller test to see if p is prime. 
    	boolean primeFound = false;
    	while (!primeFound) {
    		if (p%2!=0 && Utils.runMillerRabinTest(p, 50)) {
        		primeFound = true;
        	} else {
        		p = Utils.genLong(Integer.MAX_VALUE+1, Long.MAX_VALUE-1);
        	} 
    	}
    	//creating a function object from the family
    	HashFunctor<Integer> output = new Functor(numbers[0], numbers[1], p, (int)Math.pow(2, k));
    	return output;
    }
    

    public class Functor implements HashFunctor<Integer> {
        final private int a;
        final private int b;
        final private long p;
        final private int m;

        
        public Functor(int a, int b, long p, int m) {
        	this.a = a;
        	this.b = b;
        	this.p = p;
        	this.m = m;
        }
        
        @Override
        public int hash(Integer key) {
           // a*key+b mod p mod m
            return Utils.mod(Utils.mod(a*key, b),m);
        }

        public int a() {
            return a;
        }

        public int b() {
            return b;
        }

        public long p() {
            return p;
        }

        public int m() {
            return m;
        }
        
        public void print_function() {
        	System.out.println("h(x) = ((" + this.a +"x"+" + "+this.b +") mod " + this.p + " ) mod " + this.m);
        }
    }
    
    
    public static void main(String[] args) {
		ModularHash A = new ModularHash();
		HashFunctor<Integer>[] test = new HashFunctor[10];
		for (int i=0; i<10; i++) {
			test[i] = A.pickHash(3);
			((ModularHash.Functor) test[i]).print_function();
			for (int j=0; j<30; j++) {
				System.out.println(test[i].hash(j));
			}
			System.out.println("----------------------------------");
		}
    }
}


