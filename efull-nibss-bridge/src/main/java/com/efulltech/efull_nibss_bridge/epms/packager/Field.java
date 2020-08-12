package com.efulltech.efull_nibss_bridge.epms.packager;

import android.util.Log;

import com.efulltech.efull_nibss_bridge.epms.IsoMessage;

import org.jpos.iso.ISOException;
import org.jpos.iso.ISOMsg;
import org.jpos.iso.packager.GenericPackager;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import static com.efulltech.efull_nibss_bridge.epms.util.Converters.byteArrToHex;


public class Field implements IsoMessage {

    private List<IsoField> fields = new ArrayList<>();
    private String packedMessage;
    private int unpackedMessage;

    public Field(){}



    @Override
    public IsoField getField() {
        return null;
    }

    @Override
    public IsoField setField(int fieldId, String value) {
        IsoField mField = new IsoField(fieldId, value);
        fields.add(mField);
        return mField;
    }


    @Override
    public IsoField setField(int fieldId, int value) {
        IsoField mField = new IsoField(fieldId, value);
        fields.add(mField);
        return mField;
    }

    @Override
    public List<IsoField> listFields() {
        return fields;
    }

    @Override
    public String packMessage(List<IsoField> fields, String xmlFilePath) {
        try {
            // Load package from resources directory.
            InputStream is = new FileInputStream("/Users/MAC/StudioProjects/ETPSPay/efull-nibss-bridge/src/main/res/raw/fields.xml");
//            InputStream is = new FileInputStream(xmlFilePath);
            GenericPackager packager = new GenericPackager(is);

            ISOMsg isoMsg = new ISOMsg();
            isoMsg.setPackager(packager);
            // loop
            for (IsoField mField : fields) {
                switch (mField.getFieldId()) {
                    case 0: // MTI
                        isoMsg.setMTI(mField.getValue());
                        System.out.println("MTI :: " + mField.getValue());
                        break;
                    case 1: // bit map
                        isoMsg.set(1, mField.getValue());
                        System.out.println("Bitmap :: " + mField.getValue());
                        break;
                    default:
                        isoMsg.set(mField.getFieldId(), mField.getValue());
                        System.out.println("Field " + mField.getFieldId() + " :: " + mField.getValue());
                        break;
                }
            }

            byte[] result = isoMsg.pack();
            return byteArrToHex(result);
        }catch (ISOException | FileNotFoundException e) {
            try {
                throw new Exception(e);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        return null;
    }

    @Override
    public int unpackMessage(byte[] message, String xmlFilePath) {
        int response = 0;
        try {
            InputStream is = new FileInputStream("/Users/MAC/StudioProjects/ETPSPay/efull-nibss-bridge/src/main/res/raw/fields.xml");
//            InputStream is = new FileInputStream(xmlFilePath);
            GenericPackager packager = new GenericPackager(is);

            ISOMsg isoMsg = new ISOMsg();
            isoMsg.setPackager(packager);

            response = isoMsg.unpack(message);
            unpackedMessage = response;

            Log.d("Unpack Msg", response+"");

        } catch (FileNotFoundException | ISOException e) {
            e.printStackTrace();
        }
        return response;
    }


}
