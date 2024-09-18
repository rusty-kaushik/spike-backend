package com.spike.SecureGate.enums;

import java.util.Arrays;

public enum China {
    ANHUI("Anhui"),
    BEIJING("Beijing"),
    CHONGQING("Chongqing"),
    FUJIAN("Fujian"),
    GANSU("Gansu"),
    GUANGDONG("Guangdong"),
    GUIZHOU("Guizhou"),
    HAINAN("Hainan"),
    HEBEI("Hebei"),
    HEILONGJIANG("Heilongjiang"),
    HENAN("Henan"),
    HONG_KONG("Hong Kong"),
    HUBEI("Hubei"),
    HUNAN("Hunan"),
    JIANGSU("Jiangsu"),
    JIANGXI("Jiangxi"),
    JILIN("Jilin"),
    LIAONING("Liaoning"),
    MACAO("Macao"),
    NINGXIA("Ningxia"),
    QINGHAI("Qinghai"),
    SHAANXI("Shaanxi"),
    SHANDONG("Shandong"),
    SHANGHAI("Shanghai"),
    SHANXI("Shanxi"),
    SICHUAN("Sichuan"),
    TIANJIN("Tianjin"),
    TIBET("Tibet"),
    XINJIANG("Xinjiang"),
    YUNNAN("Yunnan"),
    ZHEJIANG("Zhejiang");

    private final String regionName;

    China(String regionName) {
        this.regionName = regionName;
    }

    public String getRegionName() {
        return regionName;
    }

    public static boolean isValidRegion(String region) {
        return Arrays.stream(China.values())
                .anyMatch(r -> r.getRegionName().equalsIgnoreCase(region));
    }
}
