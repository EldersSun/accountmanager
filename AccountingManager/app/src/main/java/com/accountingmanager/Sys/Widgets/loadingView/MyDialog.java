package com.accountingmanager.Sys.Widgets.loadingView;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.widget.TextView;

import com.accountingmanager.R;


public class MyDialog extends Dialog {
    TextView text;
    String content;
    Activity mOwnerActivity;

    public MyDialog(Context context, Activity ownerActivity, String content) {
        super(context, R.style.AppTheme_Dialog);
        this.mOwnerActivity = ownerActivity;
        this.content = content;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.v_dialog);
        text = (TextView) findViewById(R.id.text);
//		setCancelable(false);
        text.setText(content);
    }


}
