package com.efulltech.efull_nibss_bridge.utils;

import android.provider.BaseColumns;

final class Constants {

    //constructor
    private Constants(){}

    static final String DB_NAME = "EFT_DB";

    //database constants for user tables
    static final class TransactEntry implements BaseColumns {
        static final String TABLE_NAME = "transactions";
        static final String APP_NAME = "appName";
        static final String DOMAIN_NAME = "domainName";
        static final String BATCH_NO = "batchNo";
        static final String SEQ_NO = "seqNo";
        static final String STAN = "stan";
        static final String TERMINAL_ID = "terminalId";
        static final String MERCHANT_ID = "merchantId";
        static final String FROM_AC = "fromAc";
        static final String TO_AC = "toAc";
        static final String TRANS_TYPE = "transType";
        static final String TRANS_NAME = "transName";
        static final String PAN = "pan";
        static final String AMOUNT = "amount";
        static final String CASHBACK = "cashBack";
        static final String EXPIRY = "expiry";
        static final String MCC = "mcc";
        static final String ICC_DATA = "iccData";
        static final String PAN_SEQ_NO = "panSeqNo";
        static final String BIN = "bin";
        static final String TRACK1 = "track1";
        static final String TRACK2 = "track2";
        static final String TRACK3 = "track3";
        static final String REF_NO = "ref_no";
        static final String SERVICE_CODE = "serviceCode";
        static final String MERCHANT_NAME = "merchantName";
        static final String CURRENCY_CODE = "currencyCode";
        static final String PIN_BLOCK = "pinBlock";
        static final String MTI = "mti";
        static final String DATE_TIME = "dateTime";
        static final String S_AMOUNT = "sAmount";
        static final String T_FEE = "tFee";
        static final String S_FEE = "sFee";
        static final String AID = "aid";
        static final String CARD_HOLDER_NAME = "cardHolderName";
        static final String LABEL = "label";
        static final String TVR = "tvr";
        static final String TSI = "tsi";
        static final String AC = "ac";
        static final String DATE = "date";
        static final String TIME = "time";
        static final String STATUS = "status";
        static final String RESPONSE_CODE = "responseCode";
        static final String RESPONSE_MESSGAE = "responseMessage";
        static final String AUTH_ID = "authId";
        static final String MODE = "mode";
        static final String BALANCE = "balance";
        static final String DELETED = "deleted";
        static final String INSTITUTION_ID = "institutionId";
        static final String LOCAL_TIME = "localTime";
        static final String LOCAL_DATE = "localDate";
        static final String PRINT = "print";
    }

}

