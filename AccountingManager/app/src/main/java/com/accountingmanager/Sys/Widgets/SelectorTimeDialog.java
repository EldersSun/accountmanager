package com.accountingmanager.Sys.Widgets;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.accountingmanager.R;
import com.accountingmanager.Sys.Utils.ViewSet;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.CalendarMode;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;

import java.util.Calendar;
import java.util.Date;

/**
 * 自定义事件选择dialog
 * Created by Home-Pc on 2017/5/2.
 */

public class SelectorTimeDialog implements View.OnClickListener {
    private Dialog dialog;
    private Context context;
    private TextView SelectorCalendarYear;
    private TextView SelectorCalendarTime;
    private MaterialCalendarView SelectorCalendarView;
    private Button SelectorCalendarCancel, SelectorCalendarOk;
    private Calendar calendar = Calendar.getInstance();

    private final String day = "日";
    private final String month = "月";
    private final String year = "年";

    private final String Monday = "星期一";
    private final String Tuesday = "星期二";
    private final String Wednesday = "星期三";
    private final String Thursday = "星期四";
    private final String Friday = "星期五";
    private final String Saturday = "星期六";
    private final String Weekend = "星期天";

    public void setDialogOperationclick(onCustomDialogOperationclick dialogOperationclick) {
        this.dialogOperationclick = dialogOperationclick;
    }

    public onCustomDialogOperationclick dialogOperationclick;

    public SelectorTimeDialog(Context context) {
        this.context = context;
        initWidgets();
    }

    private void initWidgets() {
        dialog = new Dialog(context, R.style.dialog);
        LayoutInflater inflater = ((Activity) context).getLayoutInflater();
        final View layout = inflater.inflate(R.layout.v_selector_calendar_layout, null);//获取自定义布局
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);
        layoutParams.setMargins(0, 0, 0, 0);
        dialog.addContentView(layout, layoutParams);
        dialog.setCancelable(false);

        Window dialogWindow = dialog.getWindow();
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        dialogWindow.setGravity(Gravity.CENTER);

        /*
         * 将对话框的大小按屏幕大小的百分比设置
         */
        WindowManager.LayoutParams p = dialogWindow.getAttributes(); // 获取对话框当前的参数值
        p.height = (int) (ViewSet.getScreenHeight() * 0.85);
        p.width = (int) (ViewSet.getScreenWidth() * 0.95);
        dialogWindow.setAttributes(p);

        SelectorCalendarYear = (TextView) layout.findViewById(R.id.SelectorCalendarYear);
        SelectorCalendarTime = (TextView) layout.findViewById(R.id.SelectorCalendarTime);
        SelectorCalendarView = (MaterialCalendarView) layout.findViewById(R.id.SelectorCalendarView);
        SelectorCalendarCancel = (Button) layout.findViewById(R.id.SelectorCalendarCancel);
        SelectorCalendarOk = (Button) layout.findViewById(R.id.SelectorCalendarOk);

        SelectorCalendarCancel.setOnClickListener(this);
        SelectorCalendarOk.setOnClickListener(this);

        SelectorCalendarView.setSelectedDate(Calendar.getInstance());

        SelectorCalendarView.setOnDateChangedListener(new OnDateSelectedListener() {
            @Override
            public void onDateSelected(@NonNull MaterialCalendarView widget, @NonNull CalendarDay date, boolean selected) {
                calendar.setTime(date.getDate());
                SelectorCalendarYear.setText(String.valueOf(calendar.get(Calendar.YEAR)));
                SelectorCalendarTime.setText(String.valueOf(calendar.get(Calendar.MONTH) + 1) +
                        month + String.valueOf(calendar.get(Calendar.DAY_OF_MONTH)) + day + getWeek(calendar));
            }
        });

        SelectorCalendarYear.setText(String.valueOf(calendar.get(Calendar.YEAR)));
        SelectorCalendarTime.setText(String.valueOf(calendar.get(Calendar.MONTH) + 1) +
                month + String.valueOf(calendar.get(Calendar.DAY_OF_MONTH)) + day + getWeek(calendar));

    }

    /*获取星期几*/
    private String getWeek(Calendar calendar) {
        int i = calendar.get(Calendar.DAY_OF_WEEK);
        switch (i) {
            case 1:
                return Weekend;
            case 2:
                return Monday;
            case 3:
                return Tuesday;
            case 4:
                return Wednesday;
            case 5:
                return Friday;
            case 6:
                return Friday;
            case 7:
                return Saturday;
            default:
                return "";
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.SelectorCalendarCancel:
                if (dialogOperationclick != null) {
                    dialogOperationclick.cancel();
                }
                break;
            case R.id.SelectorCalendarOk:
                if (dialogOperationclick != null) {
                    dialogOperationclick.Confirm(calendar);
                }
                break;
        }
        dialog.dismiss();
    }

    public void show() {
        dialog.show();
    }

    public interface onCustomDialogOperationclick {
        /**
         * 确认
         */
        void Confirm(Calendar calendar);

        /**
         * 取消
         */
        void cancel();
    }
}
