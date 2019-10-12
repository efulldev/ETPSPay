/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.efulltech.efull_nibss_bridge.parser.field;

/**
 *
 * @author shemistone
 */
public interface CompoundField extends Field {

    void setValue(int fieldId, String value);

    String getValue(int fieldId);

    void setValue(int fieldId, int subFieldId, String value);

    String getValue(int fieldId, int subFieldId);

    void setField(int fieldId, Field field);

    Field getField(int fieldId);

    void setField(int fieldId, int subFieldId, Field subField);

    Field getField(int fieldId, int subFieldId);

}
