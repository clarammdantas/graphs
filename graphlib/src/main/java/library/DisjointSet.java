package library;

import java.util.HashMap;

/**
 * Data structure that stores disjoint sets and merges them
 */
public class DisjointSet {
	private HashMap<Integer, Integer> size;
	private HashMap<Integer, Integer> parent;
	
	public DisjointSet() {
		size = new HashMap<Integer, Integer>();
		parent = new HashMap<Integer, Integer>();
	}
	
	/**
	 * Create a set. Its size starts by 1
	 * @param x - the label of the set
	 */
	private void createSet(int x) {
		size.put(x, 1);
		parent.put(x, x);
	}
	
	/**
	 * Returns the parent that represents the set
	 * @param x the label
	 * @return The parent of the set
	 */
	public int findSet(int x) {
		if(!size.containsKey(x)) {
			createSet(x);
		}
		
		if(x != parent.get(x)) {
			parent.put(x, findSet(parent.get(x)));
		}
		
		return parent.get(x);
	}
	
	/**
	 * Tries to merge two sets
	 * @param setA
	 * @param setB
	 * @return True if the sets were merged and False if they are already the same set
	 */
	public boolean mergeSets(int setA, int setB) {
		setA = findSet(setA);
		setB = findSet(setB);
		
		if(setA == setB) {
			return false;
		} else {
			if(size.get(setA) > size.get(setB)) {
				parent.put(setB, setA);
				size.put(setA, size.get(setA) + size.get(setB));
			} else {
				parent.put(setA, setB);
				size.put(setB, size.get(setA) + size.get(setB));
			}
			return true;
		}
	}
}
