public class IndexableSkipList extends AbstractSkipList {
    final protected double probability;
    public IndexableSkipList(double probability) {
        super();
        this.probability = probability;
    }

    @Override
    public Node find(int val) {
    	if (head.getNext(0)==tail) {		// return that its the first element in the DT
    		return head;
    	} else {
    		Node output = head;
    		for (int level=head.height(); level>=0; level=level--) {
    			while (output.getNext(level)!=null & output.getNext(level).height()<=val) {
    				output = output.getNext(level);
    			}
    		}
    		return output;
    	}
    }

    @Override
    public int generateHeight() {
        int height = 0;
        double coin = Math.random();
        while (coin<=probability) {
        	height = height + 1;
        	coin = Math.random();
        }
        return height;
    }

    public int rank(int val) {
        throw new UnsupportedOperationException("Replace this by your implementation");
    }

    public int select(int index) {
        throw new UnsupportedOperationException("Replace this by your implementation");
    }


//---------------------------------------------- Main --------------------------------------------------------------
	
	
	static public void main(String args[]) {
		
		IndexableSkipList L = new IndexableSkipList(0.5);
		
	}	
}
	

