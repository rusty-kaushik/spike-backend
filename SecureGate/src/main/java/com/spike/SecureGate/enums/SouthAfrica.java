package com.spike.SecureGate.enums;

import java.util.Arrays;

public enum SouthAfrica {
    EASTERN_CAPE("Eastern Cape"),
    FREE_STATE("Free State"),
    GAUTENG("Gauteng"),
    KWA_ZULU_NATAL("KwaZulu-Natal"),
    LIMPOPO("Limpopo"),
    MPUMALANGA("Mpumalanga"),
    NORTHERN_CAPE("Northern Cape"),
    NORTH_WEST("North West"),
    WESTERN_CAPE("Western Cape");

    private final String regionName;

    SouthAfrica(String regionName) {
        this.regionName = regionName;
    }

    public String getRegionName() {
        return regionName;
    }

    public static boolean isValidProvince(String province) {
        return Arrays.stream(SouthAfrica.values())
                .anyMatch(p -> p.getRegionName().equalsIgnoreCase(province));
    }
}
