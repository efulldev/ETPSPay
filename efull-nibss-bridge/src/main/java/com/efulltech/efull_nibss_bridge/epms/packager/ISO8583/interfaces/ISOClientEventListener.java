package com.efulltech.efull_nibss_bridge.epms.packager.ISO8583.interfaces;

/**
 * @author Mohsen Beiranvand
 */
public interface ISOClientEventListener {

    void connecting();
    void connected();
    void connectionFailed();
    void connectionClosed();
    void disconnected();
    void beforeSendingMessage();
    void afterSendingMessage();
    void onReceiveData();
    void beforeReceiveResponse();
    void afterReceiveResponse();
}
