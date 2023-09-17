import java.util.Random;

public class MultiplicativeShiftingHash implements HashFactory<Long> {
	public HashingUtils Utils;

    public MultiplicativeShiftingHash() {
    	Utils = new HashingUtils();
    }

    @Override
    public HashFunctor<Long> pickHash(int k) {
    	Integer[] numbers = Utils.genUniqueIntegers(1);
    	Integer a = numbers[0];
    	HashFunctor<Long> output = new Functor(a, k);
    	return output;
    }

    public class Functor implements HashFunctor<Long> {
        final public static long WORD_SIZE = 64;
        final private long a;
        final private long k;

        public Functor(long a, long k) {
        	this.a = a;
        	this.k = k;
        }
        
        @Override
        public int hash(Long key) {
            return (int)(a*key)>>>(64-k);
        }

        public long a() {
            return a;
        }

        public long k() {
            return k;
        }
        
        public void print_function() {
        	System.out.println("h(x) = (" + this.a + "X) >>> (" + "64-" +this.k + ")");
        }
     }
    
    public static void main(String[] args) {
    	MultiplicativeShiftingHash A = new MultiplicativeShiftingHash();
		HashFunctor<Long>[] test = new HashFunctor[10];
		for (int i=0; i<10; i++) {
			test[i] = A.pickHash(3);
			((MultiplicativeShiftingHash.Functor) test[i]).print_function();
			for (int j=0; j<30; j++) {
				System.out.println(test[i].hash((long) j));
			}
			System.out.println("----------------------------------");
		}
    } 
}
