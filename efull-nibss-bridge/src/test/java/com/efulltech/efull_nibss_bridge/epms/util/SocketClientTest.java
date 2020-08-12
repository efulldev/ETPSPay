package com.efulltech.efull_nibss_bridge.epms.util;

import org.junit.Test;

import static org.junit.Assert.*;

public class SocketClientTest {

    @Test
    public void Request(){
        SocketClient.Requester requester = new SocketClient.Requester();
        requester.run();
//        requester.sendMessage("1000001000111000000000000000000000000000000000000000000000000000");
    }

}