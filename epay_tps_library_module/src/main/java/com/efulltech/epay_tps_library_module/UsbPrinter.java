package com.efulltech.epay_tps_library_module;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;

import android.content.DialogInterface;
import android.content.res.Resources;
import android.os.Environment;
import android.os.Message;
import android.view.View;
import android.os.Handler;

import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.telpo.tps550.api.printer.UsbThermalPrinter;

public class UsbPrinter {
    private Context ctx;
    private String printVersion;
    private final int NOPAPER = 3;
    private final int LOWBATTERY = 4;
    private final int PRINTVERSION = 5;
    private final int PRINTBARCODE = 6;
    private final int PRINTQRCODE = 7;
    private final int PRINTPAPERWALK = 8;
    private final int PRINTCONTENT = 9;
    private final int CANCELPROMPT = 10;
    private final int PRINTERR = 11;
    private final int OVERHEAT = 12;
    private final int MAKER = 13;
    private final int PRINTPICTURE = 14;
    private final int NOBLACKBLOCK = 15;
    MyHandler handler;

//    private LinearLayout print_text, print_pic;
//    private TextView text_index, pic_index,textPrintVersion;
//    private EditText editTextLeftDistance,editTextLineDistance,editTextWordFont,editTextPrintGray,
//            editTextBarcode,editTextQrcode,editTextPaperWalk,editTextContent,
//            edittext_maker_search_distance,edittext_maker_walk_distance;
//    private Button buttonBarcodePrint,buttonPaperWalkPrint,buttonContentPrint,buttonQrcodePrint,
//            buttonGetExampleText,buttonGetZhExampleText,buttonGetFRExampleText,buttonClearText,
//            button_maker,button_print_picture;

    private String Result;
    private Boolean nopaper = false;
    private boolean LowBattery = false;

    public static String barcodeStr;
    public static String qrcodeStr;
    public static int paperWalk;
    public static String printContent;
    private int leftDistance = 0;
    private int lineDistance;
    private int wordFont;
    private int printGray;
    private ProgressDialog progressDialog;
    private final static int MAX_LEFT_DISTANCE = 255;
    ProgressDialog dialog;
    UsbThermalPrinter mUsbThermalPrinter;
    private String picturePath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/111.bmp";


    public UsbPrinter(Context ctx){
        this.ctx = ctx;
        this.mUsbThermalPrinter = new UsbThermalPrinter(ctx);
    }


    private class MyHandler extends Handler {
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


    private void noPaperDlg() {
        AlertDialog.Builder dlg = new AlertDialog.Builder(this.ctx);
        dlg.setTitle(this.ctx.getString(R.string.noPaper));
        dlg.setMessage(this.ctx.getString(R.string.noPaperNotice));
        dlg.setCancelable(false);
        dlg.setPositiveButton(R.string.sure, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
            }
        });
        dlg.show();
    }

    private class contentPrintThread extends Thread {
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

                mUsbThermalPrinter.addString(printContent);

                mUsbThermalPrinter.printString();
                mUsbThermalPrinter.walkPaper(20);
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

   public void printString(String editText){
       leftDistance = 1;
       lineDistance = 1;
       printContent = editText;
       wordFont = 2;
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




