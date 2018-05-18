package enumeration;

public enum Representation {
    ADJACENCY_LIST("AL"), ADJACENCY_MATRIX("AM");

    Representation(String type) {
        this.type = type;
    }

    private final String type;

    public String getType() {
        return this.type;
    }
}
