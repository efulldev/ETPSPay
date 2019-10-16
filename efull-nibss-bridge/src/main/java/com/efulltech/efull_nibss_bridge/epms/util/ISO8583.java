/*   */ package com.efulltech.efull_nibss_bridge.epms.util;


/*   */
/*   */ public class ISO8583 {
/*   */   public native String getVersion();
/*   */   
/*   */   static  {
/* 7 */     System.loadLibrary("epms");
/*   */   }
/*   */   
/*   */   public native String packEPMSISO8583Message(String paramString1, String paramString2);
/*   */   
/*   */   public native String unpackEPMSISO8583Message(String paramString1, String paramString2);

/*   */ }


/* Location:              /Users/MAC/StudioProjects/efull-library-module (update)/May 2019 Update/efull-library-module.jar!/libs/epms.jar!/com/besl/nibss/epms/ISO8583.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.0.7
 */