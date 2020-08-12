package com.efulltech.efull_nibss_bridge.epms.packager.ISO8583.interfaces;

import com.efulltech.efull_nibss_bridge.epms.packager.ISO8583.enums.MESSAGE_FUNCTION;
import com.efulltech.efull_nibss_bridge.epms.packager.ISO8583.enums.MESSAGE_ORIGIN;

/**
 * @author Mohsen Beiranvand
 */
public interface MessagePacker<T> {

    ProcessCode<T> mti(MESSAGE_FUNCTION mFunction, MESSAGE_ORIGIN mOrigin);

    MessagePacker<T> setLeftPadding(byte character);

    MessagePacker<T> setRightPadding(byte character);
}
