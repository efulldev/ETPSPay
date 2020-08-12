package com.efulltech.efull_nibss_bridge.epms.packager.ISO8583.interfaces;

import com.efulltech.efull_nibss_bridge.epms.packager.ISO8583.entities.ISOMessage;
import com.efulltech.efull_nibss_bridge.epms.packager.ISO8583.exceptions.ISOException;

/**
 * @author Mohsen Beiranvand
 */
public interface UnpackMethods {

    ISOMessage build() throws ISOException;
}
