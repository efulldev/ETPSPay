package com.efulltech.efull_nibss_bridge.epms.packager.ISO8583.interfaces;

import java.math.BigDecimal;

/**
 * @author Mohsen Beiranvand
 */
public interface Financial<T> {


    DataElement<T> setAmount(BigDecimal amount);
}
