package com.accountingmanager.Sys.Widgets;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.accountingmanager.R;
import com.accountingmanager.Sys.Utils.ViewSet;
import com.accountingmanager.Utils.SelectorBankAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * 自定义选择dialog
 * Created by Home_Pc on 2017/3/14.
 */

public class CustomSelectorDialog {

    private Context context;
    private List<String> dateList = new ArrayList<>();

    private Dialog customViewDialog;
    private Button Custom_Dialog_Selector_cancel, Custom_Dialog_Selector_ok;
    private ListView Custom_Dialog_Selector;
    private TextView Custom_Dialog_title;

    public CustomSelectorDialog(Context context, String title, List<String> dateList) {
        this.context = context;
        this.dateList = dateList;
        iniWidgets(title);
    }

    private String dateString = "";



    public interface onCustomDialogOperationclick {
        /**
         * 确认
         */
        void Confirm(String dateString);

        /**
         * 取消
         */
        void cancel();
    }

    public onCustomDialogOperationclick dialogOperationclick;

    public void setOnDialogOperationclick(onCustomDialogOperationclick dialogOperationclick) {
        this.dialogOperationclick = dialogOperationclick;
    }

    public void show() {
        customViewDialog.show();
    }

    private void iniWidgets(String title) {
        customViewDialog = new Dialog(context, R.style.dialog);
        LayoutInflater inflater = ((Activity) context).getLayoutInflater();
        final View layout = inflater.inflate(R.layout.v_custom_dialog_selector, null);//获取自定义布局
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                800);
        layoutParams.setMargins(0, 0, 0, 0);
        customViewDialog.addContentView(layout, layoutParams);
              /*
         * 获取圣诞框的窗口对象及参数对象以修改对话框的布局设置,
         * 可以直接调用getWindow(),表示获得这个Activity的Window
         * 对象,这样这可以以同样的方式改变这个Activity的属性.
         */
//        Window dialogWindow = customViewDialog.getWindow();
//        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
//        dialogWindow.setGravity(Gravity.CENTER);

        /*
         * lp.x与lp.y表示相对于原始位置的偏移.
         * 当参数值包含Gravity.LEFT时,对话框出现在左边,所以lp.x就表示相对左边的偏移,负值忽略.
         * 当参数值包含Gravity.RIGHT时,对话框出现在右边,所以lp.x就表示相对右边的偏移,负值忽略.
         * 当参数值包含Gravity.TOP时,对话框出现在上边,所以lp.y就表示相对上边的偏移,负值忽略.
         * 当参数值包含Gravity.BOTTOM时,对话框出现在下边,所以lp.y就表示相对下边的偏移,负值忽略.
         * 当参数值包含Gravity.CENTER_HORIZONTAL时
         * ,对话框水平居中,所以lp.x就表示在水平居中的位置移动lp.x像素,正值向右移动,负值向左移动.
         * 当参数值包含Gravity.CENTER_VERTICAL时
         * ,对话框垂直居中,所以lp.y就表示在垂直居中的位置移动lp.y像素,正值向右移动,负值向左移动.
         * gravity的默认值为Gravity.CENTER,即Gravity.CENTER_HORIZONTAL |
         * Gravity.CENTER_VERTICAL.
         *
         * 本来setGravity的参数值为Gravity.LEFT | Gravity.TOP时对话框应出现在程序的左上角,但在
         * 我手机上测试时发现距左边与上边都有一小段距离,而且垂直坐标把程序标题栏也计算在内了,
         * Gravity.LEFT, Gravity.TOP, Gravity.BOTTOM与Gravity.RIGHT都是如此,据边界有一小段距离
         */
//        lp.x = 100; // 新位置X坐标
//        lp.y = 100; // 新位置Y坐标
//        lp.width = 300; // 宽度
//        lp.height = 300; // 高度
//        lp.alpha = 0.7f; // 透明度

        // 当Window的Attributes改变时系统会调用此函数,可以直接调用以应用上面对窗口参数的更改,也可以用setAttributes
        // dialog.onWindowAttributesChanged(lp);
//        dialogWindow.setAttributes(lp);

        /*
         * 将对话框的大小按屏幕大小的百分比设置
         */
//        WindowManager.LayoutParams p = dialogWindow.getAttributes(); // 获取对话框当前的参数值
//        p.height = (int) (ViewSet.getScreenHeight() * 0.6);
//        p.width = (int) (ViewSet.getScreenWidth() * 0.8);
//        dialogWindow.setAttributes(p);
        Custom_Dialog_Selector = (ListView) layout.findViewById(R.id.Custom_Dialog_Selector);
        Custom_Dialog_Selector_cancel = (Button) layout.findViewById(R.id.Custom_Dialog_Selector_cancel);
        Custom_Dialog_Selector_ok = (Button) layout.findViewById(R.id.Custom_Dialog_Selector_ok);
        Custom_Dialog_title = (TextView) layout.findViewById(R.id.Custom_Dialog_title);
        Custom_Dialog_title.setText(title);

        SelectorBankAdapter selectorBankAdapter = new SelectorBankAdapter(context, dateList);
        Custom_Dialog_Selector.setAdapter(selectorBankAdapter);

        Custom_Dialog_Selector.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                dateString = dateList.get(position);
                dialogOperationclick.Confirm(dateString);
                customViewDialog.dismiss();
            }
        });

        Custom_Dialog_Selector_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogOperationclick.cancel();
                customViewDialog.dismiss();
            }
        });

        Custom_Dialog_Selector_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogOperationclick.Confirm(dateString);
                customViewDialog.dismiss();
            }
        });
    }

}
