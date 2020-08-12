package com.efulltech.efull_nibss_bridge.epms.packager.ISO8583.security;

/**
 * ISOMacGenerator
 * @author Mohsen Beiranvand
 */
public abstract class ISOMacGenerator {

    public abstract byte[] generate(byte[] data);
        
}