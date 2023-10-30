package pl.emilweglowski.domain.guest;

import pl.emilweglowski.util.SystemUtils;

public enum Gender {

    MALE(SystemUtils.MALE),
    FEMALE(SystemUtils.FEMALE);

    private String asString;

    Gender(String asString) {
        this.asString = asString;
    }

    @Override
    public String toString() {
        return asString;
    }
}
