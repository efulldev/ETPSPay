package com.efulltech.efull_nibss_bridge.epms;

public class Constant {
  public static final boolean DEBUG = true;
  
  public static final int DATABASE_VERSION = 5;
  
  public static final String DATABASE_NAME = "besldb.db";
  
  public static final String TRANSACTION_TABLE = "eft";
  
  public static final String COLUMN_ID = "_id";
  
  public static final String COLUMN_BATCHNO = "batchno";
  
  public static final String COLUMN_SEQNO = "seqno";
  
  public static final String COLUMN_STAN = "stan";
  
  public static final String COLUMN_TERMINALID = "terminalid";
  
  public static final String COLUMN_MERCHANTID = "marchantid";
  
  public static final String COLUMN_FROMAC = "fromac";
  
  public static final String COLUMN_TOAC = "toac";
  
  public static final String COLUMN_TRANSTYPE = "transtype";
  
  public static final String COLUMN_TRANSNAME = "transname";
  
  public static final String COLUMN_PAN = "pan";
  
  public static final String COLUMN_AMOUNT = "amount";
  
  public static final String COLUMN_CASHBACK = "cashback";
  
  public static final String COLUMN_EXPIRY = "expiry";
  
  public static final String COLUMN_MCC = "mcc";
  
  public static final String COLUMN_ICCDATA = "iccdata";
  
  public static final String COLUMN_PANSEQNO = "panseqno";
  
  public static final String COLUMN_BIN = "bin";
  
  public static final String COLUMN_TRACK1 = "track1";
  
  public static final String COLUMN_TRACK2 = "track2";
  
  public static final String COLUMN_TRACK3 = "track3";
  
  public static final String COLUMN_REFNO = "refno";
  
  public static final String COLUMN_SERVICECODE = "servicecode";
  
  public static final String COLUMN_MERCHANT = "marchant";
  
  public static final String COLUMN_CURRENCYCODE = "currencycode";
  
  public static final String COLUMN_PINBLOCK = "pinblock";
  
  public static final String COLUMN_MTI = "mti";
  
  public static final String COLUMN_DATETIME = "datetime";
  
  public static final String COLUMN_SETTLEMENTAMOUNT = "smount";
  
  public static final String COLUMN_TRANSACTIONFEE = "tfee";
  
  public static final String COLUMN_SETTLEMENTFEE = "sfee";
  
  public static final String COLUMN_AID = "aid";
  
  public static final String COLUMN_CARDHOLDERNAME = "cardholdername";
  
  public static final String COLUMN_LABEL = "label";
  
  public static final String COLUMN_TVR = "tvr";
  
  public static final String COLUMN_TSI = "tsi";
  
  public static final String COLUMN_AC = "ac";
  
  public static final String COLUMN_DATE = "date";
  
  public static final String COLUMN_TIME = "time";
  
  public static final String COLUMN_STATUS = "status";
  
  public static final String COLUMN_CODE = "code";
  
  public static final String COLUMN_MESSAGE = "message";
  
  public static final String COLUMN_AUTHID = "authid";
  
  public static final String COLUMN_MODE = "mode";
  
  public static final String COLUMN_BALANCE = "balance";
  
  public static final String COLUMN_DELETED = "deleted";
  
  public static final String COLUMN_INSTITUTIONID = "institutionid";
  
  public static final String COLUMN_LOCALTIME = "localtime";
  
  public static final String COLUMN_LOCALDATE = "localdate";
  
  public static final int TRAN_TYPE_PURCHASE = 1;
  
  public static final int TRAN_TYPE_PURCHASECASH = 2;
  
  public static final int TRAN_TYPE_CASHBACK = 3;
  
  public static final int TRAN_TYPE_REVERSAL = 4;
  
  public static final int TRAN_TYPE_REFUND = 5;
  
  public static final int TRAN_TYPE_AUTHONLY = 6;
  
  public static final int TRAN_TYPE_BALANCE = 7;
  
  public static final int TRAN_TYPE_CHANGEPIN = 8;
  
  public static final int TRAN_TYPE_MINISTAT = 9;
  
  public static final int TRAN_TYPE_TRANSFERS = 10;
  
  public static final int TRAN_TYPE_DEPOSITS = 11;
  
  public static final int TRAN_TYPE_ROLLBACK = 12;
  
  public static final int TRAN_TYPE_VALUEADDVOUCHER = 13;
  
  public static final int TRAN_TYPE_VALUEADDRECHARGE = 15;
  
  public static final int TRAN_TYPE_FUELFLEET = 16;
  
  public static final int TRAN_TYPE_FUEL = 17;
  
  public static final int TRAN_TYPE_CHURCH = 18;
  
  public static final int TRAN_TYPE_BREWERY = 19;
  
  public static final int TRAN_TYPE_VOUCHERPURCHASE = 20;
  
  public static final int TRAN_TYPE_TICKETPURCHASE = 21;
  
  public static final int TRAN_TYPE_EMBASSY = 26;
  
  public static final int TRAN_TYPE_PREAUTH = 27;
  
  public static final int TRAN_TYPE_SURVEY = 28;
  
  public static final int TRAN_TYPE_PHCN = 29;
  
  public static final int TRAN_TYPE_PREAUTH_PURCHASE = 33;
  
  public static final int TRAN_TYPE_PREAUTH_LIFECYCLE = 34;
  
  public static final int TRAN_TYPE_PREAUTH_ADJUSTMENT = 35;
  
  public static final int TRAN_TYPE_BILL_PAYMENT = 39;
  
  public static final int TRAN_TYPE_ONLINE_VOUCHER = 40;
  
  public static final int TRAN_TYPE_MBL = 41;
  
  public static final int TRAN_TYPE_FMC = 43;
  
  public static final int TRAN_TYPE_CASHADVANCE = 45;
  
  public static final int TRAN_TYPE_WITHDRAWAL = 46;
  
  public static final int TRAN_TYPE_PIN_SELECTION = 47;
  
  public static final int TRAN_TYPE_SELECT = 99;
  
  public static final int TRAN_TYPE_AUTO_REVERSAL = 100;
  
  public static final int TRAN_TYPE_PREPAID = 101;
  
  public static final int TRAN_TYPE_LINK_ACCOUNT_ENQUIRY = 102;
  
  public static final int TRAN_TYPE_REVERSAL2 = 103;
  
  public static final int TRACK1_LEN = 79;
  
  public static final int TRACK2_LEN = 41;
  
  public static final int HOT_PANSIZE = 25;
  
  public static final int AX_DEFAULT_RECEIPT_PRINT_CHARACTERS = 1;
  
  public static final int MERCHANT_COPY = 1;
  
  public static final int CUSTOMER_COPY = 2;
  
  public static final int REPRINT_COPY = 3;
  
  public static final int DECLINED_COPY = 4;
  
  public static final int ACC_SAVINGS = 1;
  
  public static final int ACC_CHEQUE = 2;
  
  public static final int ACC_CREDIT = 3;
  
  public static final int ACC_DEFAULT = 4;
  
  public static final int GPRS = 1;
  
  public static final int ETHERNET = 2;
  
  public static final int DHCP = 1;
  
  public static final int STATIC = 2;
  
  public static final int ISO = 1;
  
  public static final int XML = 2;
  
  public static final byte accDEFAULT = 0;
  
  public static final byte accSAVINGS = 16;
  
  public static final byte accCHEQUE_DEBIT = 32;
  
  public static final byte accCREDIT = 48;
  
  public static final int NONE = 0;
  
  public static final int CHIP = 1;
  
  public static final int MAG = 2;
  
  public static final int RF = 3;
  
  public static final int MANUAL = 4;
  
  public static final int FALLBACK = 5;
  
  public static final int TRANSACTION_RESPONSE_DATA = 0;
}


/* Location:              /Users/MAC/Documents/classes.jar!/com/arke/sdk/util/epms/Constant.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.0.7
 */