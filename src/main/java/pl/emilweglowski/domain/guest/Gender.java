package pl.emilweglowski.domain.guest;

import pl.emilweglowski.util.Properties;

public enum Gender {

    MALE(Properties.MALE),
    FEMALE(Properties.FEMALE);

    private String asString;

    Gender(String asString) {
        this.asString = asString;
    }

    @Override
    public String toString() {
        return asString;
    }
}
