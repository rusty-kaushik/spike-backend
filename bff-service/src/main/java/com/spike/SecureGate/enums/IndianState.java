package com.spike.SecureGate.enums;

import lombok.Getter;

import java.util.Arrays;

@Getter
public enum IndianState {
    // States
    ANDHRA_PRADESH("Andhra Pradesh"),
    ARUNACHAL_PRADESH("Arunachal Pradesh"),
    ASSAM("Assam"),
    BIHAR("Bihar"),
    CHHATTISGARH("Chhattisgarh"),
    GOA("Goa"),
    GUJARAT("Gujarat"),
    HARYANA("Haryana"),
    HIMACHAL_PRADESH("Himachal Pradesh"),
    JHARKHAND("Jharkhand"),
    KARNATAKA("Karnataka"),
    KERALA("Kerala"),
    MADHYA_PRADESH("Madhya Pradesh"),
    MAHARASHTRA("Maharashtra"),
    MANIPUR("Manipur"),
    MEGHALAYA("Meghalaya"),
    MIZORAM("Mizoram"),
    NAGALAND("Nagaland"),
    ODISHA("Odisha"),
    PUNJAB("Punjab"),
    RAJASTHAN("Rajasthan"),
    SIKKIM("Sikkim"),
    TAMIL_NADU("Tamil Nadu"),
    TELANGANA("Telangana"),
    TRIPURA("Tripura"),
    UTTAR_PRADESH("Uttar Pradesh"),
    UTTARAKHAND("Uttarakhand"),
    WEST_BENGAL("West Bengal"),

    // Union Territories
    ANDAMAN_AND_NICOBAR_ISLANDS("Andaman and Nicobar Islands"),
    CHANDIGARH("Chandigarh"),
    DADRA_AND_NAGAR_HAVELI_AND_DAMAN_AND_DIU("Dadra and Nagar Haveli and Daman and Diu"),
    DELHI("Delhi"),
    LAKSHADWEEP("Lakshadweep"),
    LADAKH("Ladakh"),
    PUDUCHERRY("Puducherry"),
    JAMMU_AND_KASHMIR("Jammu and Kashmir");

    private final String stateName;

    IndianState(String stateName) {
        this.stateName = stateName;
    }

    // Static method to check if a state or union territory is valid
    public static boolean isValidState(String state) {
        return Arrays.stream(IndianState.values())
                .anyMatch(indianState -> indianState.getStateName().equalsIgnoreCase(state));
    }
}

