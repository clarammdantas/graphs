package library;

import java.util.HashMap;

public class DisjointSet {
	private HashMap<Integer, Integer> size;
	private HashMap<Integer, Integer> parent;
	
	public DisjointSet() {
	}
	
	private void createSet(int x) {
		size.put(x, 1);
		parent.put(x, x);
	}
	
	public int findSet(int x) {
		if(!size.containsKey(x)) {
			createSet(x);
		}
		
		if(x != parent.get(x)) {
			parent.put(x, findSet(parent.get(x)));
		}
		
		return parent.get(x);
	}
	
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
