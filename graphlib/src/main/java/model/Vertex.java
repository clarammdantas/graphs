package model;

public class Vertex implements Comparable<Vertex> {
    int number;

    public Vertex(int number) {
        this.number = number;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    @Override
    public boolean equals(Object o) {
        boolean equals;
        if (o == null || !(o instanceof Vertex)) {
            equals = false;
        } else {
            Vertex vertex = (Vertex) o;
            equals = number == vertex.getNumber();
        }
        return equals;
    }
    
    public int compareTo(Vertex o) {
    	return Integer.compare(getNumber(), o.getNumber());
    }
}
