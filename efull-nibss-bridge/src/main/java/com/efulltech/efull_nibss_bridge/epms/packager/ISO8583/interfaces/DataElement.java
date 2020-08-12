package com.efulltech.efull_nibss_bridge.epms.packager.ISO8583.interfaces;

import com.efulltech.efull_nibss_bridge.epms.packager.ISO8583.entities.ISOMessage;
import com.efulltech.efull_nibss_bridge.epms.packager.ISO8583.enums.FIELDS;
import com.efulltech.efull_nibss_bridge.epms.packager.ISO8583.exceptions.ISOException;
import com.efulltech.efull_nibss_bridge.epms.packager.ISO8583.security.ISOMacGenerator;

/**
 * @author Mohsen Beiranvand
 */
public interface DataElement<T> {


    ISOMessage build() throws ISOException;

    DataElement<T> generateMac(ISOMacGenerator generator) throws ISOException;

    DataElement<T> setField(int no, String value) throws ISOException;
    DataElement<T> setField(FIELDS field, String value) throws ISOException;
    DataElement<T> setField(int no, byte[] value) throws ISOException;
    DataElement<T> setField(FIELDS field, byte[] value) throws ISOException;


    DataElement<T> setHeader(String header);

}
