package com.efulltech.efull_nibss_bridge.epms.packager.ISO8583.enums;

/**
 * @author Mohsen Beiranvand
 */
public enum MESSAGE_ORIGIN {

    Acquirer("0");

    private final String code;

    MESSAGE_ORIGIN(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}
