package com.efulltech.efull_nibss_bridge.epms.packager.ISO8583.builders;

import com.efulltech.efull_nibss_bridge.epms.packager.ISO8583.exceptions.ISOException;
import com.efulltech.efull_nibss_bridge.epms.packager.ISO8583.interfaces.DataElement;
import com.efulltech.efull_nibss_bridge.epms.util.FIELDS;

/**
 * Created by Mohsen Beiranvand on 18/04/01.
 */
public class GeneralMessageClassBuilder extends BaseMessageClassBuilder<GeneralMessageClassBuilder> {

    public GeneralMessageClassBuilder(String version, String messageClass) {
        super(version, messageClass);
    }

//    @Override
//    public DataElement<GeneralMessageClassBuilder> setField(FIELDS no, String value) throws ISOException {
//        return null;
//    }
}