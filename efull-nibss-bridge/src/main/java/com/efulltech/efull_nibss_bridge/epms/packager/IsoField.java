package com.efulltech.efull_nibss_bridge.epms.packager;

public class IsoField {

    private int fieldId, value_int;
    private String value;
    private String MTI;

    public IsoField(int fieldId, String value){
        this.fieldId = fieldId;
        this.value = value;
    }

    public IsoField(int fieldId, int value){
        this.fieldId = fieldId;
        this.value_int = value;
    }


    public IsoField(){

    }

    public int getFieldId() {
        return fieldId;
    }

    public void setFieldId(int fieldId) {
        this.fieldId = fieldId;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getMTI() {
        return MTI;
    }

    public void setMTI(String MTI) {
        this.MTI = MTI;
    }

}
