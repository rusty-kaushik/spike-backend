package com.spike.SecureGate.enums;

import java.util.Arrays;

public enum Countries {
    UNITED_STATES("United States"),
    CHINA("China"),
    JAPAN("Japan"),
    GERMANY("Germany"),
    INDIA("India"),
    SOUTH_AFRICA("South Africa");

    private final String countryName;

    Countries(String countryName) {
        this.countryName = countryName;
    }

    public String getCountryName() {
        return countryName;
    }

    public static boolean isValidCountry(String country) {
        return Arrays.stream(Countries.values())
                .anyMatch(c -> c.getCountryName().equalsIgnoreCase(country));
    }
}
