package com.terrylmay.duckling.context;

import com.terrylmay.duckling.entity.DigitalTime;

public class DigitalTimeContext extends Context {
    DigitalTime contxt;

    public DigitalTimeContext(DigitalTime context) {
        this.contxt = contxt;
    }

    public DigitalTime getContxt() {
        return contxt;
    }

    public void setContxt(DigitalTime context) {
        this.contxt = contxt;
    }
}
