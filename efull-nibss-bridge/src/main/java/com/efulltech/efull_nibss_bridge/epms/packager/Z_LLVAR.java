package com.efulltech.efull_nibss_bridge.epms.packager;

import org.jpos.iso.AsciiInterpreter;
import org.jpos.iso.AsciiPrefixer;
import org.jpos.iso.BcdPrefixer;
import org.jpos.iso.ISOStringFieldPackager;
import org.jpos.iso.LiteralInterpreter;
import org.jpos.iso.NullPadder;
import org.jpos.iso.NullPrefixer;

public class Z_LLVAR  extends ISOStringFieldPackager {
    public Z_LLVAR() {
        super(NullPadder.INSTANCE, AsciiInterpreter.INSTANCE, BcdPrefixer.LL);
    }

    public Z_LLVAR(int len, String description) {
        super(len, description, NullPadder.INSTANCE, AsciiInterpreter.INSTANCE, BcdPrefixer.LL);
        this.checkLength(len, 100);
    }

    public void setLength(int len) {
        this.checkLength(len, 100);
        super.setLength(len);
    }
}
