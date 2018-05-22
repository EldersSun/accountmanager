package com.accountingmanager.Sys.Widgets;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.CountDownTimer;
import android.util.AttributeSet;
import android.widget.Button;

import com.accountingmanager.R;

/**
 * 带有定时功能的button 用于获取验证码等具有定时操作
 * Created by Home-Pc on 2017/5/31.
 */

@SuppressLint("AppCompatCustomView")
public class CustomGetCodeWidgets extends Button {

    public CustomGetCodeWidgets(Context context) {
        super(context);
    }

    public CustomGetCodeWidgets(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomGetCodeWidgets(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public CustomGetCodeWidgets(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    private class CodeTime extends CountDownTimer {

        /**
         * @param millisInFuture    The number of millis in the future from the call
         *                          to {@link #start()} until the countdown is done and {@link #onFinish()}
         *                          is called.
         * @param countDownInterval The interval along the way to receive
         *                          {@link #onTick(long)} callbacks.
         */
        public CodeTime(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onTick(long millisUntilFinished) {
            if(onTimerInterfaceListener != null){
                onTimerInterfaceListener.OnTick(millisUntilFinished);
            }
        }

        @Override
        public void onFinish() {
            timerStatueFlag = false;
            setEnabled(true);
            if(onTimerInterfaceListener != null){
                onTimerInterfaceListener.OnFinish();
            }

        }
    }

    public interface OnTimerInterfaceListener{
        public void OnTick(long millisUntilFinished);
        public void OnFinish();
    }

    public OnTimerInterfaceListener onTimerInterfaceListener;
    private CodeTime codeTime;
    /**
     * 判断当前是否有计时器在运行
     */
    private boolean timerStatueFlag = false;
    /**
     * 是否允许多次点击，默认不允许
     */
    private boolean isRepeatedly = false;

    public boolean isRepeatedly() {
        return isRepeatedly;
    }

    public void setRepeatedly(boolean repeatedly) {
        isRepeatedly = repeatedly;
    }

    public boolean isTimerStatueFlag() {
        return timerStatueFlag;
    }

    public void setOnTimerInterfaceListener(OnTimerInterfaceListener onTimerInterfaceListener) {
        this.onTimerInterfaceListener = onTimerInterfaceListener;
    }

    public CodeTime setTimer(long millisInFuture, long countDownInterval){
        return codeTime = new CodeTime(millisInFuture,countDownInterval);
    }

    /**
     * 开启定时任务
     */
    public void startTimer(){
        if(codeTime != null && !timerStatueFlag){
            timerStatueFlag = true;
            codeTime.start();
            if(!isRepeatedly){
                setEnabled(false);
            }
        }
    }

    /**
     * 监听定时状态
     */
    public void monitorTimerStatue(){
        if(codeTime != null && timerStatueFlag){
            codeTime.cancel();
        }
    }
}
