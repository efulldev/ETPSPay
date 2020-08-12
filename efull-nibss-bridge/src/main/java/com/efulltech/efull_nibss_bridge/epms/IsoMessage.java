package com.efulltech.efull_nibss_bridge.epms;

import com.efulltech.efull_nibss_bridge.epms.packager.Field;
import com.efulltech.efull_nibss_bridge.epms.packager.IsoField;

import java.util.List;

public interface IsoMessage {
    // get an ISO field
    IsoField getField();

    // add a new ISO field
    IsoField setField(int fieldId, String value);

    IsoField setField(int fieldId, int value);

    List<IsoField> listFields();

    String packMessage(List<IsoField> field, String xmlFilePath);

    int unpackMessage(byte[] isoMsg, String xmlFilePath);
}
