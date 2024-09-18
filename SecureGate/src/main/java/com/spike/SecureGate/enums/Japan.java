package com.spike.SecureGate.enums;

import java.util.Arrays;

public enum Japan {
    AICHI("Aichi"),
    AKITA("Akita"),
    AOMORI("Aomori"),
    CHIBA("Chiba"),
    EHIME("Ehime"),
    FUKUI("Fukui"),
    FUKUOKA("Fukuoka"),
    FUKUSHIMA("Fukushima"),
    GIFU("Gifu"),
    GUNMA("Gunma"),
    HIROSHIMA("Hiroshima"),
    HOKKAIDO("Hokkaido"),
    HYOGO("Hyogo"),
    IBARAKI("Ibaraki"),
    ISHIKAWA("Ishikawa"),
    OSAKA("Osaka"),
    KAGOSHIMA("Kagoshima"),
    KANAGAWA("Kanagawa"),
    KOCHI("Kochi"),
    KUMAMOTO("Kumamoto"),
    KYOTO("Kyoto"),
    MIE("Mie"),
    MIYAGI("Miyagi"),
    MIYAZAKI("Miyazaki"),
    NAGANO("Nagano"),
    NAGASAKI("Nagasaki"),
    NARA("Nara"),
    NIIGATA("Niigata"),
    OITA("Oita"),
    OKAYAMA("Okayama"),
    OKINAWA("Okinawa"),
    SAG("Saga"),
    SAITAMA("Saitama"),
    SHIGA("Shiga"),
    SHIMANE("Shimane"),
    SHIZUOKA("Shizuoka"),
    TOCHIGI("Tochigi"),
    TOKUSHIMA("Tokushima"),
    TOKYO("Tokyo"),
    TOTTORI("Tottori"),
    TOYAMA("Toyama"),
    WAKAYAMA("Wakayama"),
    YAMAGATA("Yamagata"),
    YAMAGUCHI("Yamaguchi"),
    YAMANASHI("Yamanashi");

    private final String regionName;

    Japan(String regionName) {
        this.regionName = regionName;
    }

    public String getRegionName() {
        return regionName;
    }

    public static boolean isValidRegion(String region) {
        return Arrays.stream(Japan.values())
                .anyMatch(r -> r.getRegionName().equalsIgnoreCase(region));
    }
}
