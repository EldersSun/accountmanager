package com.accountingmanager.Sys.Utils;

import android.text.TextPaint;
import android.text.style.UnderlineSpan;

/**
 * Created by Home-Pc on 2017/5/31.
 */

public class NoUnderlineSpan extends UnderlineSpan {

    @Override
    public void updateDrawState(TextPaint ds) {
        ds.setUnderlineText(false);
    }


}
