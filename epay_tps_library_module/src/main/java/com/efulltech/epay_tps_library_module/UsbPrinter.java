package com.efulltech.epay_tps_library_module;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.telpo.tps550.api.printer.UsbThermalPrinter;

public class UsbPrinter {
    private static int leftDistance;
    private static Context ctx;
    private String printVersion;
    private static final int NOPAPER = 3;
    private static final int LOWBATTERY = 4;
    private static final int PRINTVERSION = 5;
    private static final int PRINTBARCODE = 6;
    private static final int PRINTQRCODE = 7;
    private static final int PRINTPAPERWALK = 8;
    private static final int PRINTCONTENT = 9;
    private static final int CANCELPROMPT = 10;
    private static final int PRINTERR = 11;
    private static final int OVERHEAT = 12;
    private static final int MAKER = 13;
    private static final int PRINTPICTURE = 14;
    private static final int NOBLACKBLOCK = 15;
    static MyHandler handler;


    private LinearLayout print_text, print_pic;
//    private TextView text_index, pic_index,textPrintVersion;
//    private EditText editTextLeftDistance,editTextLineDistance,editTextWordFont,editTextPrintGray,
//            editTextBarcode,editTextQrcode,editTextPaperWalk,editTextContent,
//            edittext_maker_search_distance,edittext_maker_walk_distance;
//    private Button buttonBarcodePrint,buttonPaperWalkPrint,buttonContentPrint,buttonQrcodePrint,
//            buttonGetExampleText,buttonGetZhExampleText,buttonGetFRExampleText,buttonClearText,
//            button_maker,button_print_picture;

    private static String Result;
    private static Boolean nopaper = false;
    private static boolean LowBattery = false;

    public static String barcodeStr;
    public static String qrcodeStr;
    public static int paperWalk;
    public static String printContent;
//    private static int leftDistance = 0;
    private static int lineDistance;
    private static int wordFont;
    private static int printGray;
    private static ProgressDialog progressDialog;
    private final static int MAX_LEFT_DISTANCE = 255;
    static ProgressDialog dialog;
    static UsbThermalPrinter mUsbThermalPrinter;
    private String picturePath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/111.bmp";


    public UsbPrinter(Context ctx){
        this.ctx = ctx;
        this.mUsbThermalPrinter = new UsbThermalPrinter(ctx);
    }


    private static class MyHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case NOPAPER:
                    noPaperDlg();
                    break;
                case LOWBATTERY:
                    AlertDialog.Builder alertDialog = new AlertDialog.Builder(ctx);
                    alertDialog.setTitle(ctx.getString(R.string.operation_result));
                    alertDialog.setMessage(ctx.getString(R.string.LowBattery));
                    alertDialog.setPositiveButton(ctx.getString(R.string.dialog_comfirm), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                        }
                    });
                    alertDialog.show();
                    break;
                case NOBLACKBLOCK:
                    Toast.makeText(ctx, ctx.getString(R.string.maker_not_find), Toast.LENGTH_SHORT).show();
                    break;
                case PRINTVERSION:
                    dialog.dismiss();
                    if (msg.obj.equals("1")) {
//                        textPrintVersion.setText(printVersion);
                    } else {
                        Toast.makeText(ctx, ctx.getString(R.string.operation_fail), Toast.LENGTH_LONG).show();
                    }
                    break;
                case PRINTBARCODE:
//                    new barcodePrintThread().start();
                    break;
                case PRINTQRCODE:
//                    new qrcodePrintThread().start();
                    break;
                case PRINTPAPERWALK:
//                    new paperWalkPrintThread().start();
                    break;
                case PRINTCONTENT:
                    new contentPrintThread().start();
                    break;
                case MAKER:
//                    new MakerThread().start();
                    break;
                case PRINTPICTURE:
//                    new printPicture().start();
                    break;
                case CANCELPROMPT:
                    if (progressDialog != null /*&& ! ctx.isFinishing()*/) {
                        progressDialog.dismiss();
                        progressDialog = null;
                    }
                    break;
                case OVERHEAT:
                    AlertDialog.Builder overHeatDialog = new AlertDialog.Builder(ctx);
                    overHeatDialog.setTitle(ctx.getString(R.string.operation_result));
                    overHeatDialog.setMessage(ctx.getString(R.string.overTemp));
                    overHeatDialog.setPositiveButton(ctx.getString(R.string.dialog_comfirm), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                        }
                    });
                    overHeatDialog.show();
                    break;
                default:
                    Toast.makeText(ctx, "Print Error!", Toast.LENGTH_LONG).show();
                    break;
            }
        }
    }


    private static void noPaperDlg() {
        AlertDialog.Builder dlg = new AlertDialog.Builder(ctx);
        dlg.setTitle(ctx.getString(R.string.noPaper));
        dlg.setMessage(ctx.getString(R.string.noPaperNotice));
        dlg.setCancelable(false);
        dlg.setPositiveButton(R.string.sure, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
            }
        });
        dlg.show();
    }

    private static class contentPrintThread extends Thread {
        @Override
        public void run() {
            super.run();
            try {
                mUsbThermalPrinter.reset();
                mUsbThermalPrinter.setAlgin(UsbThermalPrinter.ALGIN_LEFT);
                mUsbThermalPrinter.setLeftIndent(leftDistance);
                mUsbThermalPrinter.setLineSpace(lineDistance);
                if (wordFont == 4) {
                    mUsbThermalPrinter.setFontSize(55);
                } else if (wordFont == 3) {
                    mUsbThermalPrinter.setTextSize(45);
                } else if (wordFont == 2) {
                    mUsbThermalPrinter.setTextSize(35);
                } else if (wordFont == 1) {
                    mUsbThermalPrinter.setTextSize(25);
                }
                mUsbThermalPrinter.setGray(printGray);



                mUsbThermalPrinter.addString(formatAlignedJustified("RSMARKET ASABA", "LA"));
                mUsbThermalPrinter.addString(formatAlignedJustified("12/08/19", "17:11:46"));
                mUsbThermalPrinter.addString( formatAlignedJustified("TERMINAL ID", "65437"));
                mUsbThermalPrinter.addString( formatAlignedJustified("MERCHNANT ID", "21200"));
                mUsbThermalPrinter.addString("***********************");
                mUsbThermalPrinter.addString("*********APPROVED********");
                mUsbThermalPrinter.addString("-------------------------");
                mUsbThermalPrinter.addString("*********DECLINED********");
                mUsbThermalPrinter.addString("-------------------------");
                mUsbThermalPrinter.addString("*********REPRINT*********");
                mUsbThermalPrinter.addString("-------------------------");
                mUsbThermalPrinter.addString(formatAlignedJustified("PAN", "58843XXXXX1222"));
                mUsbThermalPrinter.addString(formatAlignedJustified("EXPIRY", "2000"));
                mUsbThermalPrinter.addString(formatAlignedJustified("READER", "CONTACT CHIP"));
                mUsbThermalPrinter.addString(formatAlignedJustified("NAME", "CUSTOMER"));
                mUsbThermalPrinter.addString(formatAlignedJustified("BATCH NO", "1"));
                mUsbThermalPrinter.addString(formatAlignedJustified("SEQ NO", "10"));
                mUsbThermalPrinter.addString(formatAlignedJustified("STAN", "313788"));
                mUsbThermalPrinter.addString(formatAlignedJustified("RRN", "37894658"));
                mUsbThermalPrinter.addString(formatAlignedJustified("ACCOUNT TYPE", "SAVINGS"));
                mUsbThermalPrinter.addString(formatAlignedJustified("AMOUNT", "NGN1.00"));
                mUsbThermalPrinter.addString("***COMMUNICATION ERROR***");
                mUsbThermalPrinter.addString(formatAlignedJustified("PTSP", "Wi-Pay"));
                mUsbThermalPrinter.addString("-------------------------");
                mUsbThermalPrinter.addString(formatAlignedJustified("AID", "A000041010"));
                mUsbThermalPrinter.addString(formatAlignedJustified("CARD", "Debit MasterCard"));
                mUsbThermalPrinter.addString(formatAlignedJustified("AC", "1989257"));
                mUsbThermalPrinter.addString(formatAlignedJustified("TVR", "24434324"));
                mUsbThermalPrinter.addString(formatAlignedJustified("TSI", "E800"));
                mUsbThermalPrinter.addString("*************************");
                mUsbThermalPrinter.addString("***Wi-Pay EfuPay 0.1***");
                mUsbThermalPrinter.addString("www.etpspay.efull.ng | pos@etpspay.com");
                mUsbThermalPrinter.addString("Powered by pay mode");






                mUsbThermalPrinter.printString(); //prints string
//                mUsbThermalPrinter.printStringAndWalk(UsbThermalPrinter.DIRECTION_FORWORD, UsbThermalPrinter.WALK_DOTLINE, 10);
                mUsbThermalPrinter.walkPaper(20); //adds spaces after printing is done
            } catch (Exception e) {
                e.printStackTrace();
                Result = e.toString();
                if (Result.equals("com.telpo.tps550.api.printer.NoPaperException")) {
                    nopaper = true;
                } else if (Result.equals("com.telpo.tps550.api.printer.OverHeatException")) {
                    handler.sendMessage(handler.obtainMessage(OVERHEAT, 1, 0, null));
                } else {
                    handler.sendMessage(handler.obtainMessage(PRINTERR, 1, 0, null));
                }

            } finally {
                handler.sendMessage(handler.obtainMessage(CANCELPROMPT, 1, 0, null));
                if (nopaper){
                    handler.sendMessage(handler.obtainMessage(NOPAPER, 1, 0, null));
                    nopaper = false;
                    return;
                }
            }
        }
    }

    private class paperWalkPrintThread extends Thread {
        @Override
        public void run() {
            super.run();
            try {
                mUsbThermalPrinter.reset();
                mUsbThermalPrinter.walkPaper(paperWalk);
            } catch (Exception e) {
                e.printStackTrace();
                Result = e.toString();
                if (Result.equals("com.telpo.tps550.api.printer.NoPaperException")) {
                    nopaper = true;
                } else if (Result.equals("com.telpo.tps550.api.printer.OverHeatException")) {
                    handler.sendMessage(handler.obtainMessage(OVERHEAT, 1, 0, null));
                } else {
                    handler.sendMessage(handler.obtainMessage(PRINTERR, 1, 0, null));
                }
            } finally {
                handler.sendMessage(handler.obtainMessage(CANCELPROMPT, 1, 0, null));
                if (nopaper){
                    handler.sendMessage(handler.obtainMessage(NOPAPER, 1, 0, null));
                    nopaper = false;
                    return;
                }
            }
        }
    }



    public void PrintDemoText() {
				String str = "\n---------------------------\n" +
						     "Print Test:\n" +
			                 "Device Base Information\n" +
			                 "Printer Version:\n" +
						     "V05.2.0.3\n" +
			                 "Printer Gray:3\n" +
						     "Soft Version:\n"+
			                 "Demo.G50.0.Build140313\n" +
						     "Battery Level:100%\n" +
			                 "CSQ Value:24\n" +
						     "IMEI:86378902177527\n" +
			                 "---------------------------\n" +
			                 "---------------------------\n" +
						     "Print Test:\n" +
			                 "Device Base Information\n" +
			                 "Printer Version:\n" +
						     "V05.2.0.3\n" +
			                 "Printer Gray:3\n" +
						     "Soft Version:\n"+
			                 "Demo.G50.0.Build140313\n" +
						     "Battery Level:100%\n" +
			                 "CSQ Value:24\n" +
						     "IMEI:86378902177527\n" +
			                 "---------------------------\n" +
					         "---------------------------\n" +
						     "Print Test:\n" +
			                 "Device Base Information\n" +
			                 "Printer Version:\n" +
						     "V05.2.0.3\n" +
			                 "Printer Gray:3\n" +
						     "Soft Version:\n"+
			                 "Demo.G50.0.Build140313\n" +
						     "Battery Level:100%\n" +
			                 "CSQ Value:24\n" +
						     "IMEI:86378902177527\n" +
			                 "---------------------------\n";
                printString(str);
   }

    private static String formatAlignedJustified(String left, String right) {
        if (left != null && right != null) {
            int leftlen = left.length();
            int rightlen = right.length();
            int space = 32 - (leftlen + rightlen);
            String sp = "";

            for(int i = 0; i < space; ++i) {
                sp = sp + " ";
            }

            return left + sp + right;
        } else {
            return "";
        }
    }

    public static void printString(String editText){
       leftDistance = 1;
       lineDistance = 1;
       printContent = editText;
       wordFont = 1;
       printGray = 5;


       handler = new MyHandler();

       if (leftDistance > MAX_LEFT_DISTANCE) {
           Toast.makeText(ctx, ctx.getString(R.string.outOfLeft), Toast.LENGTH_LONG).show();
           return;
       }
       if (lineDistance > 255) {
           Toast.makeText(ctx, ctx.getString(R.string.outOfLine), Toast.LENGTH_LONG).show();
           return;
       }
       if (wordFont > 4 || wordFont < 1) {
           Toast.makeText(ctx, ctx.getString(R.string.outOfFont), Toast.LENGTH_LONG).show();
           return;
       }
       if (printGray < 0 || printGray > 7) {
           Toast.makeText(ctx, ctx.getString(R.string.outOfGray), Toast.LENGTH_LONG).show();
           return;
       }
       if (printContent == null || printContent.length() == 0) {
           Toast.makeText(ctx, ctx.getString(R.string.empty), Toast.LENGTH_LONG).show();
           return;
       }
       if (LowBattery == true) {
           handler.sendMessage(handler.obtainMessage(LOWBATTERY, 1, 0, null));
       } else {
           if (!nopaper) {
               progressDialog = ProgressDialog.show(ctx,""+ ctx.getString(R.string.bl_dy), ctx.getString(R.string.printing_wait));
               handler.sendMessage(handler.obtainMessage(PRINTCONTENT, 1, 0, null));
           } else {
               Toast.makeText(ctx, ctx.getString(R.string.ptintInit), Toast.LENGTH_LONG).show();
           }
       }

   }
}




