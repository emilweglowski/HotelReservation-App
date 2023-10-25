package pl.emilweglowski.domain.room;

import pl.emilweglowski.util.Properties;

public enum BedType {

    SINGLE(1, Properties.SINGLE_BED),
    DOUBLE(2, Properties.DOUBLE_BED),
    KING_SIZE(3, Properties.KING_SIZE);

    private int size;
    private String asString;

    BedType(int size, String asString) {
        this.size = size;
        this.asString = asString;
    }

    public int getSize() {
        return this.size;
    }

    @Override
    public String toString() {
        return this.asString;
    }
}
