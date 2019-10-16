/*     */ package com.efulltech.efull_nibss_bridge.epms;
/*     */ 
/*     */ import java.io.Serializable;
/*     */ 
/*     */ 
/*     */ public class Transaction
/*     */   implements Serializable
/*     */ {
/*     */   private int id;
/*     */   private String batchno;
/*     */   private String seqno;
/*     */   private String stan;
/*     */   private String terminalid;
/*     */   private String marchantid;
/*     */   private int fromac;
/*     */   private int toac;
/*     */   private int transtype;
/*     */   private String transname;
/*     */   private String pan;
/*     */   private String amount;
/*     */   private String cashback;
/*     */   private String expiry;
/*     */   private String mcc;
/*     */   private String iccdata;
/*     */   private String panseqno;
/*     */   private String bin;
/*     */   private String track1;
/*     */   private String track2;
/*     */   private String track3;
/*     */   private String refno;
/*     */   private String servicecode;
/*     */   private String marchant;
/*     */   private String currencycode;
/*     */   private String pinblock;
/*     */   private String mti;
/*     */   private String datetime;
/*     */   private String samount;
/*     */   private String tfee;
/*     */   private String sfee;
/*     */   private String aid;
/*     */   private String cardholdername;
/*     */   private String label;
/*     */   private String tvr;
/*     */   private String tsi;
/*     */   private String ac;
/*     */   private String date;
/*     */   private String time;
/*     */   private String status;
/*     */   private String responsecode;
/*     */   private String responsemessage;
/*     */   private String authid;
/*     */   private int mode;
/*     */   private String balance;
/*     */   private int deleted;
/*     */   private String institutionid;
/*     */   private String localtime;
/*     */   private String localdate;
/*     */   private boolean print;
/*     */   
/*     */   public Transaction(String batchno, String seqno, String stan, String terminalid, String marchantid, int fromac, int toac, int transtype, String transname, String pan, String amount, String cashback, String expiry, String mcc, String iccdata, String panseqno, String bin, String track1, String track2, String track3, String refno, String servicecode, String marchant, String currencycode, String pinblock, String mti, String datetime, String samount, String tfee, String sfee, String aid, String cardholdername, String label, String tvr, String tsi, String ac, String date, String time, String status, String responsecode, String responsemessage, String authid, int mode, String balance, int deleted, String institutionid, String localtime, String localdate) {
/*  61 */     this.batchno = batchno;
/*  62 */     this.seqno = seqno;
/*  63 */     this.stan = stan;
/*  64 */     this.terminalid = terminalid;
/*  65 */     this.marchantid = marchantid;
/*  66 */     this.fromac = fromac;
/*  67 */     this.toac = toac;
/*  68 */     this.transtype = transtype;
/*  69 */     this.transname = transname;
/*  70 */     this.pan = pan;
/*  71 */     this.amount = amount;
/*  72 */     this.cashback = cashback;
/*  73 */     this.expiry = expiry;
/*  74 */     this.mcc = mcc;
/*  75 */     this.iccdata = iccdata;
/*  76 */     this.panseqno = panseqno;
/*  77 */     this.bin = bin;
/*  78 */     this.track1 = track1;
/*  79 */     this.track2 = track2;
/*  80 */     this.track3 = track3;
/*  81 */     this.refno = refno;
/*  82 */     this.servicecode = servicecode;
/*  83 */     this.marchant = marchant;
/*  84 */     this.currencycode = currencycode;
/*  85 */     this.pinblock = pinblock;
/*  86 */     this.mti = mti;
/*  87 */     this.datetime = datetime;
/*  88 */     this.samount = samount;
/*  89 */     this.tfee = tfee;
/*  90 */     this.sfee = sfee;
/*  91 */     this.aid = aid;
/*  92 */     this.cardholdername = cardholdername;
/*  93 */     this.label = label;
/*  94 */     this.tvr = tvr;
/*  95 */     this.tsi = tsi;
/*  96 */     this.ac = ac;
/*  97 */     this.date = date;
/*  98 */     this.time = time;
/*  99 */     this.status = status;
/* 100 */     this.responsecode = responsecode;
/* 101 */     this.responsemessage = responsemessage;
/* 102 */     this.authid = authid;
/* 103 */     this.mode = mode;
/* 104 */     this.balance = balance;
/* 105 */     this.deleted = deleted;
/* 106 */     this.institutionid = institutionid;
/* 107 */     this.localtime = localtime;
/* 108 */     this.localdate = localdate;
/*     */   }
/*     */ 
/*     */   
/*     */   public Transaction(int id, String batchno, String seqno, String stan, String terminalid, String marchantid, int fromac, int toac, int transtype, String transname, String pan, String amount, String cashback, String expiry, String mcc, String iccdata, String panseqno, String bin, String track1, String track2, String track3, String refno, String servicecode, String marchant, String currencycode, String pinblock, String mti, String datetime, String samount, String tfee, String sfee, String aid, String cardholdername, String label, String tvr, String tsi, String ac, String date, String time, String status, String responsecode, String responsemessage, String authid, int mode, String balance, int deleted, String institutionid, String localtime, String localdate) {
/* 113 */     this.id = id;
/* 114 */     this.batchno = batchno;
/* 115 */     this.seqno = seqno;
/* 116 */     this.stan = stan;
/* 117 */     this.terminalid = terminalid;
/* 118 */     this.marchantid = marchantid;
/* 119 */     this.fromac = fromac;
/* 120 */     this.toac = toac;
/* 121 */     this.transtype = transtype;
/* 122 */     this.transname = transname;
/* 123 */     this.pan = pan;
/* 124 */     this.amount = amount;
/* 125 */     this.cashback = cashback;
/* 126 */     this.expiry = expiry;
/* 127 */     this.mcc = mcc;
/* 128 */     this.iccdata = iccdata;
/* 129 */     this.panseqno = panseqno;
/* 130 */     this.bin = bin;
/* 131 */     this.track1 = track1;
/* 132 */     this.track2 = track2;
/* 133 */     this.track3 = track3;
/* 134 */     this.refno = refno;
/* 135 */     this.servicecode = servicecode;
/* 136 */     this.marchant = marchant;
/* 137 */     this.currencycode = currencycode;
/* 138 */     this.pinblock = pinblock;
/* 139 */     this.mti = mti;
/* 140 */     this.datetime = datetime;
/* 141 */     this.samount = samount;
/* 142 */     this.tfee = tfee;
/* 143 */     this.sfee = sfee;
/* 144 */     this.aid = aid;
/* 145 */     this.cardholdername = cardholdername;
/* 146 */     this.label = label;
/* 147 */     this.tvr = tvr;
/* 148 */     this.tsi = tsi;
/* 149 */     this.ac = ac;
/* 150 */     this.date = date;
/* 151 */     this.time = time;
/* 152 */     this.status = status;
/* 153 */     this.responsecode = responsecode;
/* 154 */     this.responsemessage = responsemessage;
/* 155 */     this.authid = authid;
/* 156 */     this.mode = mode;
/* 157 */     this.balance = balance;
/* 158 */     this.deleted = deleted;
/* 159 */     this.institutionid = institutionid;
/* 160 */     this.localtime = localtime;
/* 161 */     this.localdate = localdate;
/*     */   }
/*     */ 
/*     */   
/* 165 */   public boolean isPrint() { return this.print; }
/*     */ 
/*     */ 
/*     */   
/* 169 */   public void setPrint(boolean print) { this.print = print; }
/*     */ 
/*     */ 
/*     */   
/* 173 */   public int getId() { return this.id; }
/*     */ 
/*     */ 
/*     */   
/* 177 */   public void setId(int id) { this.id = id; }
/*     */ 
/*     */ 
/*     */   
/* 181 */   public String getBatchno() { return this.batchno; }
/*     */ 
/*     */ 
/*     */   
/* 185 */   public void setBatchno(String batchno) { this.batchno = batchno; }
/*     */ 
/*     */ 
/*     */   
/* 189 */   public String getSeqno() { return this.seqno; }
/*     */ 
/*     */ 
/*     */   
/* 193 */   public void setSeqno(String seqno) { this.seqno = seqno; }
/*     */ 
/*     */ 
/*     */   
/* 197 */   public String getStan() { return this.stan; }
/*     */ 
/*     */ 
/*     */   
/* 201 */   public void setStan(String stan) { this.stan = stan; }
/*     */ 
/*     */ 
/*     */   
/* 205 */   public String getTerminalid() { return this.terminalid; }
/*     */ 
/*     */ 
/*     */   
/* 209 */   public void setTerminalid(String terminalid) { this.terminalid = terminalid; }
/*     */ 
/*     */ 
/*     */   
/* 213 */   public String getMarchantid() { return this.marchantid; }
/*     */ 
/*     */ 
/*     */   
/* 217 */   public void setMarchantid(String marchantid) { this.marchantid = marchantid; }
/*     */ 
/*     */ 
/*     */   
/* 221 */   public int getFromac() { return this.fromac; }
/*     */ 
/*     */ 
/*     */   
/* 225 */   public void setFromac(int fromac) { this.fromac = fromac; }
/*     */ 
/*     */ 
/*     */   
/* 229 */   public int getToac() { return this.toac; }
/*     */ 
/*     */ 
/*     */   
/* 233 */   public void setToac(int toac) { this.toac = toac; }
/*     */ 
/*     */ 
/*     */   
/* 237 */   public int getTranstype() { return this.transtype; }
/*     */ 
/*     */ 
/*     */   
/* 241 */   public void setTranstype(int transtype) { this.transtype = transtype; }
/*     */ 
/*     */ 
/*     */   
/* 245 */   public String getTransname() { return this.transname; }
/*     */ 
/*     */ 
/*     */   
/* 249 */   public void setTransname(String transname) { this.transname = transname; }
/*     */ 
/*     */ 
/*     */   
/* 253 */   public String getPan() { return this.pan; }
/*     */ 
/*     */ 
/*     */   
/* 257 */   public void setPan(String pan) { this.pan = pan; }
/*     */ 
/*     */ 
/*     */   
/* 261 */   public String getAmount() { return this.amount; }
/*     */ 
/*     */ 
/*     */   
/* 265 */   public void setAmount(String amount) { this.amount = amount; }
/*     */ 
/*     */ 
/*     */   
/* 269 */   public String getCashback() { return this.cashback; }
/*     */ 
/*     */ 
/*     */   
/* 273 */   public void setCashback(String cashback) { this.cashback = cashback; }
/*     */ 
/*     */ 
/*     */   
/* 277 */   public String getExpiry() { return this.expiry; }
/*     */ 
/*     */ 
/*     */   
/* 281 */   public void setExpiry(String expiry) { this.expiry = expiry; }
/*     */ 
/*     */ 
/*     */   
/* 285 */   public String getMcc() { return this.mcc; }
/*     */ 
/*     */ 
/*     */   
/* 289 */   public void setMcc(String mcc) { this.mcc = mcc; }
/*     */ 
/*     */ 
/*     */   
/* 293 */   public String getIccdata() { return this.iccdata; }
/*     */ 
/*     */ 
/*     */   
/* 297 */   public void setIccdata(String iccdata) { this.iccdata = iccdata; }
/*     */ 
/*     */ 
/*     */   
/* 301 */   public String getPanseqno() { return this.panseqno; }
/*     */ 
/*     */ 
/*     */   
/* 305 */   public void setPanseqno(String panseqno) { this.panseqno = panseqno; }
/*     */ 
/*     */ 
/*     */   
/* 309 */   public String getBin() { return this.bin; }
/*     */ 
/*     */ 
/*     */   
/* 313 */   public void setBin(String bin) { this.bin = bin; }
/*     */ 
/*     */ 
/*     */   
/* 317 */   public String getTrack1() { return this.track1; }
/*     */ 
/*     */ 
/*     */   
/* 321 */   public void setTrack1(String track1) { this.track1 = track1; }
/*     */ 
/*     */ 
/*     */   
/* 325 */   public String getTrack2() { return this.track2; }
/*     */ 
/*     */ 
/*     */   
/* 329 */   public void setTrack2(String track2) { this.track2 = track2; }
/*     */ 
/*     */ 
/*     */   
/* 333 */   public String getTrack3() { return this.track3; }
/*     */ 
/*     */ 
/*     */   
/* 337 */   public void setTrack3(String track3) { this.track3 = track3; }
/*     */ 
/*     */ 
/*     */   
/* 341 */   public String getRefno() { return this.refno; }
/*     */ 
/*     */ 
/*     */   
/* 345 */   public void setRefno(String refno) { this.refno = refno; }
/*     */ 
/*     */ 
/*     */   
/* 349 */   public String getServicecode() { return this.servicecode; }
/*     */ 
/*     */ 
/*     */   
/* 353 */   public void setServicecode(String servicecode) { this.servicecode = servicecode; }
/*     */ 
/*     */ 
/*     */   
/* 357 */   public String getMarchant() { return this.marchant; }
/*     */ 
/*     */ 
/*     */   
/* 361 */   public void setMarchant(String marchant) { this.marchant = marchant; }
/*     */ 
/*     */ 
/*     */   
/* 365 */   public String getCurrencycode() { return this.currencycode; }
/*     */ 
/*     */ 
/*     */   
/* 369 */   public void setCurrencycode(String currencycode) { this.currencycode = currencycode; }
/*     */ 
/*     */ 
/*     */   
/* 373 */   public String getPinblock() { return this.pinblock; }
/*     */ 
/*     */ 
/*     */   
/* 377 */   public void setPinblock(String pinblock) { this.pinblock = pinblock; }
/*     */ 
/*     */ 
/*     */   
/* 381 */   public String getMti() { return this.mti; }
/*     */ 
/*     */ 
/*     */   
/* 385 */   public void setMti(String mti) { this.mti = mti; }
/*     */ 
/*     */ 
/*     */   
/* 389 */   public String getDatetime() { return this.datetime; }
/*     */ 
/*     */ 
/*     */   
/* 393 */   public void setDatetime(String datetime) { this.datetime = datetime; }
/*     */ 
/*     */ 
/*     */   
/* 397 */   public String getSamount() { return this.samount; }
/*     */ 
/*     */ 
/*     */   
/* 401 */   public void setSamount(String samount) { this.samount = samount; }
/*     */ 
/*     */ 
/*     */   
/* 405 */   public String getTfee() { return this.tfee; }
/*     */ 
/*     */ 
/*     */   
/* 409 */   public void setTfee(String tfee) { this.tfee = tfee; }
/*     */ 
/*     */ 
/*     */   
/* 413 */   public String getSfee() { return this.sfee; }
/*     */ 
/*     */ 
/*     */   
/* 417 */   public void setSfee(String sfee) { this.sfee = sfee; }
/*     */ 
/*     */ 
/*     */   
/* 421 */   public String getAid() { return this.aid; }
/*     */ 
/*     */ 
/*     */   
/* 425 */   public void setAid(String aid) { this.aid = aid; }
/*     */ 
/*     */ 
/*     */   
/* 429 */   public String getCardholdername() { return this.cardholdername; }
/*     */ 
/*     */ 
/*     */   
/* 433 */   public void setCardholdername(String cardholdername) { this.cardholdername = cardholdername; }
/*     */ 
/*     */ 
/*     */   
/* 437 */   public String getLabel() { return this.label; }
/*     */ 
/*     */ 
/*     */   
/* 441 */   public void setLabel(String label) { this.label = label; }
/*     */ 
/*     */ 
/*     */   
/* 445 */   public String getTvr() { return this.tvr; }
/*     */ 
/*     */ 
/*     */   
/* 449 */   public void setTvr(String tvr) { this.tvr = tvr; }
/*     */ 
/*     */ 
/*     */   
/* 453 */   public String getTsi() { return this.tsi; }
/*     */ 
/*     */ 
/*     */   
/* 457 */   public void setTsi(String tsi) { this.tsi = tsi; }
/*     */ 
/*     */ 
/*     */   
/* 461 */   public String getAc() { return this.ac; }
/*     */ 
/*     */ 
/*     */   
/* 465 */   public void setAc(String ac) { this.ac = ac; }
/*     */ 
/*     */ 
/*     */   
/* 469 */   public String getDate() { return this.date; }
/*     */ 
/*     */ 
/*     */   
/* 473 */   public void setDate(String date) { this.date = date; }
/*     */ 
/*     */ 
/*     */   
/* 477 */   public String getTime() { return this.time; }
/*     */ 
/*     */ 
/*     */   
/* 481 */   public void setTime(String time) { this.time = time; }
/*     */ 
/*     */ 
/*     */   
/* 485 */   public String getStatus() { return this.status; }
/*     */ 
/*     */ 
/*     */   
/* 489 */   public void setStatus(String status) { this.status = status; }
/*     */ 
/*     */ 
/*     */   
/* 493 */   public String getResponsecode() { return this.responsecode; }
/*     */ 
/*     */ 
/*     */   
/* 497 */   public void setResponsecode(String responsecode) { this.responsecode = responsecode; }
/*     */ 
/*     */ 
/*     */   
/* 501 */   public String getResponsemessage() { return this.responsemessage; }
/*     */ 
/*     */   
/* 504 */   public void setResponsemessage(String responsemessage) { this.responsemessage = responsemessage; }
/*     */ 
/*     */   
/* 507 */   public String getAuthid() { return this.authid; }
/*     */ 
/*     */ 
/*     */   
/* 511 */   public void setAuthid(String authid) { this.authid = authid; }
/*     */ 
/*     */   
/* 514 */   public int getMode() { return this.mode; }
/*     */   
/* 516 */   public void setMode(int mode) { this.mode = mode; }
/*     */ 
/*     */   
/* 519 */   public String getBalance() { return this.balance; }
/*     */ 
/*     */ 
/*     */   
/* 523 */   public void setBalance(String balance) { this.balance = balance; }
/*     */ 
/*     */ 
/*     */   
/* 527 */   public int getDeleted() { return this.deleted; }
/*     */ 
/*     */ 
/*     */   
/* 531 */   public void setDeleted(int deleted) { this.deleted = deleted; }
/*     */ 
/*     */ 
/*     */   
/* 535 */   public String getInstitutionid() { return this.institutionid; }
/*     */ 
/*     */ 
/*     */   
/* 539 */   public void setInstitutionid(String institutionid) { this.institutionid = institutionid; }
/*     */ 
/*     */ 
/*     */   
/* 543 */   public String getLocaltime() { return this.localtime; }
/*     */ 
/*     */ 
/*     */   
/* 547 */   public void setLocaltime(String localtime) { this.localtime = localtime; }
/*     */ 
/*     */ 
/*     */   
/* 551 */   public String getLocaldate() { return this.localdate; }
/*     */ 
/*     */ 
/*     */   
/* 555 */   public void setLocaldate(String localdate) { this.localdate = localdate; }
/*     */ }


/* Location:              /Users/MAC/Documents/classes.jar!/com/arke/sdk/util/epms/Transaction.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.0.7
 */