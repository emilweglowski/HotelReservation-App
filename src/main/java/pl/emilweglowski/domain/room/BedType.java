package pl.emilweglowski.domain.room;

import pl.emilweglowski.util.SystemUtils;

public enum BedType {

    SINGLE(1, SystemUtils.SINGLE_BED),
    DOUBLE(2, SystemUtils.DOUBLE_BED),
    KING_SIZE(3, SystemUtils.KING_SIZE);

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
