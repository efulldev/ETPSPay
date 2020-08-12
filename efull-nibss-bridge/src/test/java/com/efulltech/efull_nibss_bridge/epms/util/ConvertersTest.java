package com.efulltech.efull_nibss_bridge.epms.util;

import com.efulltech.efull_nibss_bridge.epms.Initiate;

import org.junit.Test;

import static org.junit.Assert.*;

public class ConvertersTest {

    @Test
    public void hexToAscii() {
    }

    @Test
    public void asciiToHex() {
    }

    @Test
    public void hexToBin() {
        String val = "3534353234313445";
        Converters converters = new Converters();
        String res = converters.hexToBin(val);
        System.out.println("HexToBin:: "+res);
    }

    @Test
    public void binToAscii() {
        String val = "000000001000000000001000001000111000000000000000000000000000000000000000000000000000000001000000000000000000000000000000000000000000000000000000000000010000000001000001100000110111000010010001001000110100010101100001100000110111000010010001000000000100000000000001";
        Converters converters = new Converters();
        String res = converters.binToAscii(val);
        System.out.println("binToAscii:: "+res);
    }

    @Test
    public void binToHex() {
    }

    @Test
    public void asciiToBin() {
    }

    @Test
    public void hexToInt() {
    }

    @Test
    public void hexToBytes() {
        String val = "08008238000000000000040000000000000010041837091234561837091004001";
        Converters converters = new Converters();
        byte[] res = converters.hexToBytes(val);
        String result = "";
        for(int i = 0; i < res.length; i++){
            result = result +""+res[i];
        }
        System.out.println("::hexToBytes:: "+result);
    }

    @Test
    public void bytesToHex() {

    }

    @Test
    public void byteArrToHex(){
        Converters converters = new Converters();

    }
}