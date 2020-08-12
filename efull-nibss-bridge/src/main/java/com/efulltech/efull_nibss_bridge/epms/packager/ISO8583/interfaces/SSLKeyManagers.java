package com.efulltech.efull_nibss_bridge.epms.packager.ISO8583.interfaces;

import javax.net.ssl.KeyManager;

/**
 * @author Mohsen Beiranvand
 */
public interface SSLKeyManagers
{
    SSLTrustManagers setKeyManagers(KeyManager[] keyManagers);
}