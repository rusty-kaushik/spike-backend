package com.spike.SecureGate.enums;

import java.util.Arrays;

public enum Germany {
    BADEN_WURTTEMBERG("Baden-Württemberg"),
    BAVARIA("Bavaria"),
    BERLIN("Berlin"),
    BRANDENBURG("Brandenburg"),
    BREMEN("Bremen"),
    HAMBURG("Hamburg"),
    HESSE("Hesse"),
    LOWER_SAXONY("Lower Saxony"),
    MECKLENBURG_VORPOMMERN("Mecklenburg-Vorpommern"),
    NORTH_RHINE_WESTPHALIA("North Rhine-Westphalia"),
    RHINELAND_PALATINATE("Rhineland-Palatinate"),
    SAARLAND("Saarland"),
    SAXONY("Saxony"),
    SAXONY_ANHALT("Saxony-Anhalt"),
    SCHLESWIG_HOLSTEIN("Schleswig-Holstein"),
    THURINGIA("Thuringia");

    private final String regionName;

    Germany(String regionName) {
        this.regionName = regionName;
    }

    public String getRegionName() {
        return regionName;
    }

    public static boolean isValidRegion(String region) {
        return Arrays.stream(Germany.values())
                .anyMatch(r -> r.getRegionName().equalsIgnoreCase(region));
    }
}
