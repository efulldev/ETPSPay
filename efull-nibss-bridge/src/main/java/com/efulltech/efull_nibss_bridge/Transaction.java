package com.efulltech.efull_nibss_bridge;
// how to pack ISO
// https://kodejava.org/how-do-i-pack-an-iso-8583-message/



import java.io.Serializable;

public class Transaction implements Serializable {
    private int id;
    private final String appName;
    private final String domainName;
    private String batchNo;
    private String seqNo;
    private String stan;
    private String terminalId;
    private String merchantId;
    private int fromAc;
    private int toAc;
    private int transType;
    private String transName;
    private String pan;
    private String amount;
    private String cashBack;
    private String expiry;
    private String mcc;
    private String iccData;
    private String panSeqNo;
    private String bin;
    private String track1;
    private String track2;
    private String track3;
    private String refNo;
    private String serviceCode;
    private String merchantName;
    private String currencyCode;
    private String pinBlock;
    private String mti;
    private String dateTime;
    private String sAmount;
    private String tFee;
    private String sFee;
    private String aid;
    private String cardHolderName;
    private String label;
    private String tvr;
    private String tsi;
    private String ac;
    private String date;
    private String time;
    private String status;
    private String responseCode;
    private String responseMessage;
    private String authId;
    private int mode;
    private String balance;
    private int deleted;
    private String institutionId;
    private String localTime;
    private String localDate;
    private boolean print;

    public Transaction(int id, String appname, String domainname, String batchno, String seqno, String stan, String terminalid, String marchantid, int fromac, int toac, int transtype, String transname, String pan, String amount, String cashback, String expiry, String mcc, String iccdata, String panseqno, String bin, String track1, String track2, String track3, String refno, String servicecode, String marchant, String currencycode, String pinblock, String mti, String datetime, String samount, String tfee, String sfee, String aid, String cardholdername, String label, String tvr, String tsi, String ac, String date, String time, String status, String responsecode, String responsemessage, String authid, int mode, String balance, int deleted, String institutionid, String localtime, String localdate) {
        this.id = id;
        this.appName = appname;
        this.domainName = domainname;
        this.batchNo = batchno;
        this.seqNo = seqno;
        this.stan = stan;
        this.terminalId = terminalid;
        this.merchantId = marchantid;
        this.fromAc = fromac;
        this.toAc = toac;
        this.transType = transtype;
        this.transName = transname;
        this.pan = pan;
        this.amount = amount;
        this.cashBack = cashback;
        this.expiry = expiry;
        this.mcc = mcc;
        this.iccData = iccdata;
        this.panSeqNo = panseqno;
        this.bin = bin;
        this.track1 = track1;
        this.track2 = track2;
        this.track3 = track3;
        this.refNo = refno;
        this.serviceCode = servicecode;
        this.merchantName = marchant;
        this.currencyCode = currencycode;
        this.pinBlock = pinblock;
        this.mti = mti;
        this.dateTime = datetime;
        this.sAmount = samount;
        this.tFee = tfee;
        this.sFee = sfee;
        this.aid = aid;
        this.cardHolderName = cardholdername;
        this.label = label;
        this.tvr = tvr;
        this.tsi = tsi;
        this.ac = ac;
        this.date = date;
        this.time = time;
        this.status = status;
        this.responseCode = responsecode;
        this.responseMessage = responsemessage;
        this.authId = authid;
        this.mode = mode;
        this.balance = balance;
        this.deleted = deleted;
        this.institutionId = institutionid;
        this.localTime = localtime;
        this.localDate = localdate;
        this.print = true;

    }

    public int getId() {
        return id;
    }

    public String getBatchNo() {
        return batchNo;
    }

    public String getSeqNo() {
        return seqNo;
    }

    public String getStan() {
        return stan;
    }

    public String getTerminalId() {
        return terminalId;
    }

    public String getMerchantId() {
        return merchantId;
    }

    public int getFromAc() {
        return fromAc;
    }

    public int getToAc() {
        return toAc;
    }

    public int getTransType() {
        return transType;
    }

    public String getTransName() {
        return transName;
    }

    public String getPan() {
        return pan;
    }

    public String getAmount() {
        return amount;
    }

    public String getCashBack() {
        return cashBack;
    }

    public String getExpiry() {
        return expiry;
    }

    public String getMcc() {
        return mcc;
    }

    public String getIccData() {
        return iccData;
    }

    public String getPanSeqno() {
        return panSeqNo;
    }

    public String getBin() {
        return bin;
    }

    public String getTrack1() {
        return track1;
    }

    public String getTrack2() {
        return track2;
    }

    public String getTrack3() {
        return track3;
    }

    public String getRefNo() {
        return refNo;
    }

    public String getServiceCode() {
        return serviceCode;
    }

    public String getMerchantName() {
        return merchantName;
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    public String getPinBlock() {
        return pinBlock;
    }

    public String getMti() {
        return mti;
    }

    public String getDateTime() {
        return dateTime;
    }

    public String getsAmount() {
        return sAmount;
    }

    public String gettFee() {
        return tFee;
    }

    public String getsFee() {
        return sFee;
    }

    public String getAid() {
        return aid;
    }

    public String getCardHolderName() {
        return cardHolderName;
    }

    public String getLabel() {
        return label;
    }

    public String getTvr() {
        return tvr;
    }

    public String getTsi() {
        return tsi;
    }

    public String getAc() {
        return ac;
    }

    public String getDate() {
        return date;
    }

    public String getTime() {
        return time;
    }

    public String getStatus() {
        return status;
    }

    public String getResponseCode() {
        return responseCode;
    }

    public String getResponseMessage() {
        return responseMessage;
    }

    public String getAuthId() {
        return authId;
    }

    public int getMode() {
        return mode;
    }

    public String getBalance() {
        return balance;
    }

    public int getDeleted() {
        return deleted;
    }

    public String getInstitutionId() {
        return institutionId;
    }

    public String getLocalTime() {
        return localTime;
    }

    public String getLocalDate() {
        return localDate;
    }

    public boolean isPrint() {
        return print;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setBatchNo(String batchNo) {
        this.batchNo = batchNo;
    }

    public void setSeqNo(String seqNo) {
        this.seqNo = seqNo;
    }

    public void setStan(String stan) {
        this.stan = stan;
    }

    public void setTerminalId(String terminalId) {
        this.terminalId = terminalId;
    }

    public void setMerchantId(String merchantId) {
        this.merchantId = merchantId;
    }

    public void setFromAc(int fromAc) {
        this.fromAc = fromAc;
    }

    public void setToAc(int toAc) {
        this.toAc = toAc;
    }

    public void setTransType(int transType) {
        this.transType = transType;
    }

    public void setTransName(String transName) {
        this.transName = transName;
    }

    public void setPan(String pan) {
        this.pan = pan;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public void setCashBack(String cashBack) {
        this.cashBack = cashBack;
    }

    public void setExpiry(String expiry) {
        this.expiry = expiry;
    }

    public void setMcc(String mcc) {
        this.mcc = mcc;
    }

    public void setIccData(String iccData) {
        this.iccData = iccData;
    }

    public void setPanSeqno(String panSeqno) {
        this.panSeqNo = panSeqno;
    }

    public void setBin(String bin) {
        this.bin = bin;
    }

    public void setTrack1(String track1) {
        this.track1 = track1;
    }

    public void setTrack2(String track2) {
        this.track2 = track2;
    }

    public void setTrack3(String track3) {
        this.track3 = track3;
    }

    public void setRefNo(String refNo) {
        this.refNo = refNo;
    }

    public void setServiceCode(String serviceCode) {
        this.serviceCode = serviceCode;
    }

    public void setMerchantName(String merchantName) {
        this.merchantName = merchantName;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }

    public void setPinBlock(String pinBlock) {
        this.pinBlock = pinBlock;
    }

    public void setMti(String mti) {
        this.mti = mti;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public void setsAmount(String sAmount) {
        this.sAmount = sAmount;
    }

    public void settFee(String tFee) {
        this.tFee = tFee;
    }

    public void setsFee(String sFee) {
        this.sFee = sFee;
    }

    public void setAid(String aid) {
        this.aid = aid;
    }

    public void setCardHolderName(String cardHolderName) {
        this.cardHolderName = cardHolderName;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public void setTvr(String tvr) {
        this.tvr = tvr;
    }

    public void setTsi(String tsi) {
        this.tsi = tsi;
    }

    public void setAc(String ac) {
        this.ac = ac;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setResponseCode(String responseCode) {
        this.responseCode = responseCode;
    }

    public void setResponseMessage(String responseMessage) {
        this.responseMessage = responseMessage;
    }

    public void setAuthId(String authId) {
        this.authId = authId;
    }

    public void setMode(int mode) {
        this.mode = mode;
    }

    public void setBalance(String balance) {
        this.balance = balance;
    }

    public void setDeleted(int deleted) {
        this.deleted = deleted;
    }

    public void setInstitutionId(String institutionId) {
        this.institutionId = institutionId;
    }

    public void setLocalTime(String localTime) {
        this.localTime = localTime;
    }

    public void setLocalDate(String localDate) {
        this.localDate = localDate;
    }

    public void setPrint(boolean print) {
        this.print = print;
    }

    public String getAppName() {
        return this.appName;
    }

    public String getDomainName() {
        return domainName;
    }

    public String getPanSeqNo() {
        return panSeqNo;
    }

    public void setPanSeqNo(String panSeqNo) {
        this.panSeqNo = panSeqNo;
    }
}
