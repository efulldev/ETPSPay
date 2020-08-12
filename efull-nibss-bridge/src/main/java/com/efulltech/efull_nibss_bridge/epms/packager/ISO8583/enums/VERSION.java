package com.efulltech.efull_nibss_bridge.epms.packager.ISO8583.enums;

/**
 * @author Mohsen Beiranvand
 */
public enum VERSION {

    V1987("0"),
    V1993("1"),
    V2003("2");

    private final String code;

    VERSION(String versionCode) {
        this.code = versionCode;
    }

    public String getCode() {
        return code;
    }
}
