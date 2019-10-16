/*     */ package com.efulltech.efull_nibss_bridge.epms;
/*     */ 
/*     */ import android.util.Base64;
/*     */ import java.io.ByteArrayOutputStream;
/*     */ import java.io.IOException;
/*     */ import java.security.InvalidKeyException;
/*     */ import java.security.Key;
/*     */ import java.security.NoSuchAlgorithmException;
/*     */ import java.security.spec.InvalidKeySpecException;
/*     */ import java.util.Arrays;
/*     */ import javax.crypto.Cipher;
/*     */ import javax.crypto.CipherOutputStream;
/*     */ import javax.crypto.NoSuchPaddingException;
/*     */ import javax.crypto.SecretKey;
/*     */ import javax.crypto.SecretKeyFactory;
/*     */ import javax.crypto.spec.DESKeySpec;
/*     */ import javax.crypto.spec.DESedeKeySpec;
/*     */ import javax.crypto.spec.SecretKeySpec;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Crypto
/*     */ {
    private static SecretKey key;

    /*     */   public static SecretKey read3DESKey(byte[] rawkey) {
/*     */     try {
/*  30 */       DESedeKeySpec keyspec = new DESedeKeySpec(rawkey);
/*     */       
/*  32 */       SecretKeyFactory keyfactory = SecretKeyFactory.getInstance("DESede");
/*     */ 
/*     */       
/*  35 */       key = keyfactory.generateSecret(keyspec);
/*  36 */       return keyfactory.translateKey(key);
/*     */     
/*     */     }
/*  39 */     catch (InvalidKeySpecException ex) {
/*  40 */       return null;
/*  41 */     } catch (NoSuchAlgorithmException ex) {
/*  42 */       return null;
/*  43 */     } catch (InvalidKeyException ex) {
/*  44 */       return null;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public static SecretKey readDESKey(byte[] rawkey) {
/*     */     try {
/*  51 */       DESKeySpec keyspec = new DESKeySpec(rawkey);
/*  52 */       SecretKeyFactory keyfactory = SecretKeyFactory.getInstance("DES");
/*  53 */       return keyfactory.generateSecret(keyspec);
/*     */     }
/*  55 */     catch (InvalidKeySpecException ex) {
/*  56 */       return null;
/*  57 */     } catch (NoSuchAlgorithmException ex) {
/*  58 */       return null;
/*  59 */     } catch (InvalidKeyException ex) {
/*  60 */       return null;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static String EncryptDES(Key key, byte[] clearText) {
/*     */     try {
/*  68 */       Cipher cipher = Cipher.getInstance("DES/ECB/NoPadding");
/*  69 */       cipher.init(1, key);
/*     */       
/*  71 */       ByteArrayOutputStream bytes = new ByteArrayOutputStream();
/*     */ 
/*     */ 
/*     */       
/*  75 */       CipherOutputStream out = new CipherOutputStream(bytes, cipher);
/*  76 */       out.write(clearText);
/*  77 */       out.flush();
/*  78 */       out.close();
/*  79 */       byte[] ciphertext = bytes.toByteArray();
/*  80 */       bytes.flush();
/*  81 */       bytes.close();
/*     */       
/*  83 */       String encrypted = ToHexString(ciphertext);
/*     */       
/*  85 */       Arrays.fill(clearText, (byte)0);
/*  86 */       Arrays.fill(ciphertext, (byte)0);
/*     */       
/*  88 */       return encrypted;
/*  89 */     } catch (IOException ex) {
/*  90 */       return null;
/*  91 */     } catch (NoSuchPaddingException ex) {
/*  92 */       return null;
/*  93 */     } catch (NoSuchAlgorithmException ex) {
/*  94 */       return null;
/*  95 */     } catch (InvalidKeyException ex) {
/*  96 */       return null;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String Encrypt3DES(Key key, String clearComp) {
/*     */     try {
/* 105 */       Cipher cipher = Cipher.getInstance("DESede/ECB/NoPadding");
/*     */       
/* 107 */       cipher.init(1, key);
/*     */       
/* 109 */       ByteArrayOutputStream bytes = new ByteArrayOutputStream();
/*     */       
/* 111 */       byte[] clearText = hexToByte(clearComp);
/*     */       
/* 113 */       CipherOutputStream out = new CipherOutputStream(bytes, cipher);
/* 114 */       out.write(clearText);
/* 115 */       out.flush();
/* 116 */       out.close();
/* 117 */       byte[] ciphertext = bytes.toByteArray();
/* 118 */       bytes.flush();
/* 119 */       bytes.close();
/*     */       
/* 121 */       String encrypted = ToHexString(ciphertext);
/*     */       
/* 123 */       Arrays.fill(clearText, (byte)0);
/* 124 */       Arrays.fill(ciphertext, (byte)0);
/*     */       
/* 126 */       return encrypted;
/* 127 */     } catch (IOException ex) {
/* 128 */       return null;
/* 129 */     } catch (NoSuchPaddingException ex) {
/* 130 */       return null;
/* 131 */     } catch (NoSuchAlgorithmException ex) {
/* 132 */       return null;
/* 133 */     } catch (InvalidKeyException ex) {
/* 134 */       return null;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static String Decrypt3DES(Key key, String cipherComp) {
/*     */     try {
/* 142 */       Cipher cipher = Cipher.getInstance("DESede/ECB/NoPadding");
/*     */       
/* 144 */       cipher.init(2, key);
/*     */       
/* 146 */       ByteArrayOutputStream bytes = new ByteArrayOutputStream();
/*     */       
/* 148 */       byte[] ciphertext = hexToByte(cipherComp);
/*     */       
/* 150 */       CipherOutputStream out = new CipherOutputStream(bytes, cipher);
/* 151 */       out.write(ciphertext);
/* 152 */       out.flush();
/* 153 */       out.close();
/* 154 */       byte[] deciphertext = bytes.toByteArray();
/* 155 */       bytes.flush();
/* 156 */       bytes.close();
/*     */       
/* 158 */       String decrypted = ToHexString(deciphertext);
/*     */       
/* 160 */       Arrays.fill(ciphertext, (byte)0);
/* 161 */       Arrays.fill(deciphertext, (byte)0);
/*     */       
/* 163 */       return decrypted;
/* 164 */     } catch (IOException ex) {
/* 165 */       return null;
/* 166 */     } catch (NoSuchPaddingException ex) {
/* 167 */       return null;
/* 168 */     } catch (NoSuchAlgorithmException ex) {
/* 169 */       return null;
/* 170 */     } catch (InvalidKeyException ex) {
/* 171 */       return null;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public static String DecryptDES(Key key, String cipherComp) {
/*     */     try {
/* 178 */       Cipher cipher = Cipher.getInstance("DES/ECB/NoPadding");
/* 179 */       cipher.init(2, key);
/*     */       
/* 181 */       ByteArrayOutputStream bytes = new ByteArrayOutputStream();
/*     */       
/* 183 */       byte[] cipherText = hexToByte(cipherComp);
/*     */       
/* 185 */       CipherOutputStream out = new CipherOutputStream(bytes, cipher);
/* 186 */       out.write(cipherText);
/* 187 */       out.flush();
/* 188 */       out.close();
/* 189 */       byte[] deciphertext = bytes.toByteArray();
/* 190 */       bytes.flush();
/* 191 */       bytes.close();
/*     */       
/* 193 */       String decrypted = ToHexString(deciphertext);
/*     */       
/* 195 */       Arrays.fill(cipherText, (byte)0);
/* 196 */       Arrays.fill(deciphertext, (byte)0);
/*     */       
/* 198 */       return decrypted;
/* 199 */     } catch (IOException ex) {
/* 200 */       return null;
/* 201 */     } catch (NoSuchPaddingException ex) {
/* 202 */       return null;
/* 203 */     } catch (NoSuchAlgorithmException ex) {
/* 204 */       return null;
/* 205 */     } catch (InvalidKeyException ex) {
/* 206 */       return null;
/*     */     } 
/*     */   }
/*     */   
/*     */   public static String ToHexString(byte[] toAsciiData) {
/* 211 */     String hexString = "";
/*     */     
/* 213 */     for (byte b : toAsciiData) {
/* 214 */       hexString = hexString + String.format("%02X", new Object[] { Byte.valueOf(b) });
/*     */     } 
/* 216 */     return hexString;
/*     */   }
/*     */   
/*     */   public static String toAscii(String toAsciiData) {
/* 220 */     String hexString = "";
/* 221 */     char[] hexChars = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F' };
/*     */     
/* 223 */     int i = 0;
/* 224 */     while (toAsciiData.length() > i) {
/*     */       
/* 226 */       int hbits = (toAsciiData.charAt(i) & 0xF0) >>> '\004';
/* 227 */       int lbits = toAsciiData.charAt(i) & 0xF;
/*     */       
/* 229 */       hexString = hexString + hexChars[hbits];
/*     */       
/* 231 */       hexString = hexString + hexChars[lbits];
/* 232 */       i++;
/*     */     } 
/* 234 */     return hexString;
/*     */   }
/*     */   
/*     */   public static String MAC(String mwk, String macData) {
/* 238 */     String macValue = "0000000000000000";
/* 239 */     String mwk1 = "";
/* 240 */     String mwk2 = "";
/*     */     
/* 242 */     if (mwk == null) {
/* 243 */       return macValue;
/*     */     }
/*     */     
/*     */     try {
/* 247 */       mwk1 = mwk.substring(0, 16);
/* 248 */       mwk2 = mwk.substring(16, 32);
/*     */     }
/* 250 */     catch (Exception ex) {
/* 251 */       return macValue;
/*     */     } 
/*     */     
/* 254 */     byte[] key1byte = hexToByte(mwk1);
/* 255 */     byte[] key2byte = hexToByte(mwk2);
/*     */     
/* 257 */     SecretKey key1 = readDESKey(key1byte);
/* 258 */     SecretKey key2 = readDESKey(key2byte);
/* 259 */     if (key1 == null || key2 == null) {
/* 260 */       return macValue;
/*     */     }
/*     */     
/* 263 */     macData = toAscii(macData);
/* 264 */     while (macData.length() % 16 != 0) {
/* 265 */       macData = macData + "0";
/*     */     }
/* 267 */     int len = macData.length() / 16;
/* 268 */     for (int i = 0; i < len; i++) {
/* 269 */       byte[] mac1 = hexToByte(macValue);
/* 270 */       byte[] mac2 = hexToByte(macData.substring(i * 16, i * 16 + 16));
/* 271 */       for (int j = 0; j < 8; j++) {
/* 272 */         mac1[j] = (byte)(mac1[j] ^ mac2[j]);
/*     */       }
/* 274 */       macValue = EncryptDES(key1, mac1);
/*     */     } 
/* 276 */     macValue = DecryptDES(key2, macValue);
/* 277 */     return EncryptDES(key1, hexToByte(macValue));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String AESEncrypt(String data, byte[] sessionKey) throws Exception {
/* 289 */     Key key = generateKey(sessionKey);
/* 290 */     Cipher c = Cipher.getInstance("AES");
/* 291 */     c.init(1, key);
/* 292 */     byte[] encVal = c.doFinal(data.getBytes());
/* 293 */     return Base64.encodeToString(encVal, 0);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String AESDecrypt(String encryptedData, byte[] sessionKey) throws Exception {
/* 303 */     Key key = generateKey(sessionKey);
/* 304 */     Cipher c = Cipher.getInstance("AES");
/* 305 */     c.init(2, key);
/* 306 */     byte[] decordedValue = Base64.decode(encryptedData, 0);
/* 307 */     byte[] decValue = c.doFinal(decordedValue);
/* 308 */     return new String(decValue);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 315 */   private static Key generateKey(byte[] sessionKey) throws Exception { return new SecretKeySpec(sessionKey, "AES"); }
/*     */ 
/*     */   
/*     */   public static byte[] hexToByte(String hexString) {
/* 319 */     String str = new String("0123456789ABCDEF");
/* 320 */     byte[] bytes = new byte[hexString.length() / 2];
/* 321 */     for (int i = 0, j = 0; i < hexString.length(); i++) {
/* 322 */       byte firstQuad = (byte)(str.indexOf(hexString.charAt(i)) << 4);
/* 323 */       byte secondQuad = (byte)str.indexOf(hexString.charAt(++i));
/* 324 */       bytes[j++] = (byte)(firstQuad | secondQuad);
/*     */     } 
/*     */     
/* 327 */     return bytes;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void main(String[] ad) {
/* 346 */     String key1 = "5D25072F04832A2329D93E4F91BA23A2";
/* 347 */     String key2 = "86CBCDE3B0A22354853E04521686863D";
/*     */ 
/*     */     
/* 350 */     byte[] keyB1 = hexToByte(key1 + key1.substring(0, 16));
/* 351 */     byte[] keyB2 = hexToByte(key2 + key2.substring(0, 16));
/* 352 */     byte[] keyB3 = new byte[keyB1.length];
/*     */     
/* 354 */     for (int i = 0; i < keyB1.length; i++) {
/* 355 */       keyB3[i] = (byte)(keyB1[i] ^ keyB2[i]);
/*     */     }
/*     */     
/* 358 */     SecretKey key = read3DESKey(keyB3);
/* 359 */     String dmk = Decrypt3DES(key, "7D72B6D5FAF13BF8D2FC258E3D2329EA");
/* 360 */     System.out.println("dmk: " + dmk);
/*     */     
/* 362 */     keyB1 = hexToByte(dmk + dmk.substring(0, 16));
/* 363 */     key = read3DESKey(keyB1);
/* 364 */     String dsk = Decrypt3DES(key, "F1497756E89BB3220762094293D9137C");
/* 365 */     System.out.println("dsk: " + dsk);
/*     */     
/* 367 */     keyB1 = hexToByte(dmk + dmk.substring(0, 16));
/* 368 */     key = read3DESKey(keyB1);
/* 369 */     String dpk = Decrypt3DES(key, "9F2CE4BB234336AF45FE98354553571D");
/* 370 */     System.out.println("dpk: " + dpk);
/*     */   }
/*     */ }


/* Location:              /Users/MAC/Documents/classes.jar!/com/arke/sdk/util/epms/Crypto.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.0.7
 */