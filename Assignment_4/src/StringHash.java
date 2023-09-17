import java.util.List;
import java.util.Random;

public class StringHash implements HashFactory<String> {
	public HashingUtils Utils;
	public ModularHash ModularHash;

    public StringHash() {
    	Utils = new HashingUtils();
    	ModularHash = new ModularHash();
    }

    @Override
    public HashFunctor<String> pickHash(int k) {
    	HashFunctor<Integer> functor = ModularHash.pickHash(k);
    	int q = Math.abs((int)Utils.genLong((Integer.MAX_VALUE+1/2) +1, Integer.MAX_VALUE));
    	boolean primeFound = false;
    	while (!primeFound) {
    		if (q%2!=0 && Utils.runMillerRabinTest(q, 50)) {
        		primeFound = true;
        	} else {
        		q = Math.abs((int)Utils.genLong((Integer.MAX_VALUE+1/2) +1, Integer.MAX_VALUE));
        	}
    	}
    	int c = (int)Utils.genLong(1, q);
    	HashFunctor<String> output = new Functor(functor,c,q,k);
    	return output;
    }

    public class Functor implements HashFunctor<String> {
        final private HashFunctor<Integer> carterWegmanHash;
        final private int c;
        final private int q;
        private int k;

        public Functor(HashFunctor<Integer> functor, int c, int q, int k) {
        	this.carterWegmanHash = functor;
        	this.c = c;
        	this.q = q;
        	this.k = k;
        }
        @Override
        public int hash(String key) {
            long sum = 0;
            for(int i=0; i<key.length();i++) {
            	sum = sum + HashingUtils.multiplyMod(key.charAt(i),HashingUtils.modPow(c, k-i, q), q);
            }
            sum = HashingUtils.modPow(sum, 1, q);
            int output = carterWegmanHash.hash((int)sum);
            return output;
        }

        public int c() {
            return c;
        }

        public int q() {
            return q;
        }

        public HashFunctor carterWegmanHash() {
            return carterWegmanHash;
        }
        
        public void print_function() {
        	System.out.println("The function is:");
        	((ModularHash.Functor) this.carterWegmanHash).print_function();
        	System.out.println("c = "+this.c + "| q = " + this.q);
        	
        }
    }
    
    
    
    public static void main(String[] args) {
    	StringHash String_factory = new StringHash();
    	int k = 2;
    	
		HashFunctor<String>[] test = new HashFunctor[10];
		for (int i=0; i<10; i++) {
			test[i] = String_factory.pickHash(k);
			((StringHash.Functor) test[i]).print_function();
			List<String> key = String_factory.Utils.genUniqueStrings(30, 2, 4);
			for (int j=0; j<50; j++) {
				System.out.println(test[i].hash(key.get(j)));
			}
			System.out.println("----------------------------------");
		}
    }
}
