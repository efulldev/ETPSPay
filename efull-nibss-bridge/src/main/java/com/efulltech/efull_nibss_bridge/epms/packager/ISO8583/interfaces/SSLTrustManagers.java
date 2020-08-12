package com.efulltech.efull_nibss_bridge.epms.packager.ISO8583.interfaces;

import com.efulltech.efull_nibss_bridge.epms.packager.ISO8583.builders.ISOClientBuilder;

import javax.net.ssl.TrustManager;

/**
 * @author Mohsen Beiranvand
 */
public interface SSLTrustManagers
{
    ISOClientBuilder.ClientBuilder setTrustManagers(TrustManager[] trustManagers);
}