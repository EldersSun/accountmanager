package com.accountingmanager.Sys.Widgets.UiContentView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.text.InputFilter;
import android.text.InputType;
import android.text.Spanned;
import android.text.TextWatcher;
import android.text.method.DigitsKeyListener;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.accountingmanager.R;
import com.accountingmanager.Sys.Utils.UIToolkit;

import static android.text.InputType.TYPE_CLASS_TEXT;
import static android.text.InputType.TYPE_TEXT_VARIATION_PASSWORD;

/**
 * 自定义列表文本控件
 *
 * @author sunxianpng
 * 249980336@qq.com
 */
public class UiContentView extends LinearLayout {

    private ImageView UiContent_TitleImg;
    private TextView UiContent_Title;
    private EditText UiContent_Input;
    private ImageView UiContent_ContentImg;

    public TextView getTvShow() {
        return UiContent_Title;
    }

    public EditText getContentView() {
        return UiContent_Input;
    }

    /**
     * true 能输入,false 反之
     */
    private Boolean inputFlag = false;
    /**
     * true 能点击,false 反之
     */
    private Boolean onClickFlag = true;
    /**
     * 内容点击事件
     */
    private OnContentClick mOnContentClick;

    public UiContentView(Context context, AttributeSet attrs) {
        super(context, attrs);
        iniWidgets(context, attrs);
    }

    public UiContentView(Context context) {
        super(context);
        iniWidgets(context, null);
    }

    @SuppressLint("NewApi")
    private void iniWidgets(Context context, AttributeSet attrs) {
        this.setOrientation(HORIZONTAL);
        this.setGravity(Gravity.CENTER_VERTICAL);

        this.setClickable(true);
        this.setFocusable(true);

        UiContent_TitleImg = new ImageView(context);
        UiContent_TitleImg.setPadding(0, 20, 20, 20);

        UiContent_ContentImg = new ImageView(context);
        UiContent_ContentImg.setPadding(20, 20, 0, 20);

        UiContent_Title = new TextView(context);
        UiContent_Title.setTextSize(TypedValue.COMPLEX_UNIT_SP,
                UIToolkit.middleFont());
        UiContent_Title.setTextColor(0xff535353);
        UiContent_Title.setBackground(null);
        UiContent_Title.setSingleLine(false);
        UiContent_Title.setPadding(0, 20, 5, 20);
        UiContent_Title.setGravity(Gravity.LEFT | Gravity.CENTER_VERTICAL);

        UiContent_Input = new EditText(context);
        UiContent_Input.setTextSize(TypedValue.COMPLEX_UNIT_SP,
                UIToolkit.middleFont());
        UiContent_Input.setTextColor(0xff535353);
        UiContent_Input.setBackground(null);
        UiContent_Input.setSingleLine(false);
        UiContent_Input.setPadding(0, 20, 5, 20);
        UiContent_Input.setGravity(Gravity.RIGHT | Gravity.CENTER_VERTICAL);

        LayoutParams titleImgParams = new LayoutParams(
                android.view.ViewGroup.LayoutParams.WRAP_CONTENT,
                android.view.ViewGroup.LayoutParams.WRAP_CONTENT, 0.2f);
        UiContent_TitleImg.setLayoutParams(titleImgParams);

        LayoutParams titleParams = new LayoutParams(
                android.view.ViewGroup.LayoutParams.WRAP_CONTENT,
                android.view.ViewGroup.LayoutParams.MATCH_PARENT, 1.1f);
        UiContent_Title.setLayoutParams(titleParams);
        LayoutParams contentParams = new LayoutParams(
                android.view.ViewGroup.LayoutParams.WRAP_CONTENT,
                android.view.ViewGroup.LayoutParams.MATCH_PARENT, 1);
        UiContent_Input.setLayoutParams(contentParams);

        LayoutParams contentImgParams = new LayoutParams(
                android.view.ViewGroup.LayoutParams.WRAP_CONTENT,
                android.view.ViewGroup.LayoutParams.WRAP_CONTENT, 0.3f);
        UiContent_ContentImg.setLayoutParams(contentImgParams);

        attachViewToParent(UiContent_TitleImg, 0,
                UiContent_TitleImg.getLayoutParams());
        attachViewToParent(UiContent_Title, 1,
                UiContent_Title.getLayoutParams());
        attachViewToParent(UiContent_Input, 2,
                UiContent_Input.getLayoutParams());
        attachViewToParent(UiContent_ContentImg, 3,
                UiContent_ContentImg.getLayoutParams());

        UiContent_TitleImg.setOnTouchListener(touchListener);
        UiContent_Title.setOnTouchListener(touchListener);
        UiContent_Input.setOnTouchListener(touchListener);
        UiContent_ContentImg.setOnTouchListener(touchListener);

        /**
         * FOCUS_BEFORE_DESCENDANTS ViewGroup本身先对焦点进行处理，如果没有处理则分发给child View进行处理
         * FOCUS_AFTER_DESCENDANTS 先分发给Child View进行处理，如果所有的Child View都没有处理，则自己再处理
         * FOCUS_BLOCK_DESCENDANTS ViewGroup本身进行处理，不管是否处理成功，都不会分发给ChildView进行处理
         */
        //setDescendantFocusability(ViewGroup.FOCUS_BLOCK_DESCENDANTS);

        setValueAttributes(context, attrs);
    }

    private ContentTouchListener touchListener = new ContentTouchListener();

    private class ContentTouchListener implements OnTouchListener{
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            if (UiContent_Input != null && mOnContentClick != null
                    && onClickFlag && event.getAction() == MotionEvent.ACTION_DOWN) {
                mOnContentClick.onContentClick(UiContentView.this);
            }
            return false;
        }
    }

    /**
     * 设置控件资源属性
     *
     * @param context
     * @param attrs
     */
    private void setValueAttributes(Context context, AttributeSet attrs) {
        TypedArray ta = null;
        if (attrs != null) {
            try {
                ta = context.obtainStyledAttributes(attrs,
                        R.styleable.UiContentView);
                /**
                 * 是否设置字体颜色
                 */
                Boolean ColorFlag = ta.getBoolean(
                        R.styleable.UiContentView_uiContentcolorFlag, true);
                /**
                 * 默认的统一字体颜色
                 */
                int TextColor = ta.getColor(
                        R.styleable.UiContentView_uiContentUnifiedTextColor,
                        Color.BLACK);
                /**
                 * title color
                 */
                int titleColor = ta.getColor(
                        R.styleable.UiContentView_uiContentTitleColor,
                        Color.BLACK);
                /**
                 * 内容color
                 */
                int contentColot = ta.getColor(
                        R.styleable.UiContentView_uiContentColor, Color.BLACK);
                /**
                 * title text
                 */
                String titleText = ta
                        .getString(R.styleable.UiContentView_uiContentTitleText);
                /**
                 * 内容text
                 */
                String ContntText = ta
                        .getString(R.styleable.UiContentView_uiContentText);
                /**
                 * title 字体大小
                 */
                float titleTextSize = ta.getDimension(
                        R.styleable.UiContentView_uiContentTitleTextSize, 16);
                /**
                 * 内容字体大小
                 */
                float contextTextSize = ta.getDimension(
                        R.styleable.UiContentView_uiContentTextSize, 16);
                /**
                 * 是否可以点击的flag
                 */
                boolean clickflag = ta.getBoolean(
                        R.styleable.UiContentView_uiContentClickFlag, true);
                /**
                 * 是否可以输入的flag
                 */
                boolean inputflag = ta.getBoolean(
                        R.styleable.UiContentView_uiContentInputFlag, false);
                /**
                 * title是否显示
                 */
                int titleVisibility = ta.getInt(
                        R.styleable.UiContentView_uiContentTitleVisibility, 0);
                /**
                 * 内容是否显示
                 */
                int contextVisibility = ta.getInt(
                        R.styleable.UiContentView_uiContentVisibility, 0);
                /**
                 * 内容的输入提示信息
                 */
                String contentHint = ta
                        .getString(R.styleable.UiContentView_uiContentHint);
                /**
                 * title 背景
                 */
                int titleBack = ta
                        .getResourceId(
                                R.styleable.UiContentView_uiContentTitleBackgroundResource,
                                android.R.color.transparent);
                /**
                 * 内容背景
                 */
                int contentBack = ta.getResourceId(
                        R.styleable.UiContentView_uiContentBackgRoundResource,
                        android.R.color.transparent);
                /**
                 * 是否换行,默认换行
                 */
                boolean isWrap = ta.getBoolean(
                        R.styleable.UiContentView_uiContentTextIsWrap, true);
                /**
                 * 内容的文字方向
                 */
                String Gracity = ta
                        .getString(R.styleable.UiContentView_uiContentGravity);

                /**
                 * title标识图标资源
                 */
                int titleImgResource = ta
                        .getResourceId(
                                R.styleable.UiContentView_uiContentTitleImgBackgRoundResource,
                                0);

                /**
                 * content标识图标资源
                 */
                int contentImgResource = ta
                        .getResourceId(
                                R.styleable.UiContentView_uiContentImgBackgRoundResource,
                                0);

                /**
                 * 动态设置title宽度
                 */
                int titleWidth = (int) ta.getDimension(
                        R.styleable.UiContentView_uiContentTitleWidth, 0);

                /**
                 * 动态设置content宽度
                 */
                int contentWidth = (int) ta.getDimension(
                        R.styleable.UiContentView_uiContentWidth, 0);

                /**
                 * 设置内容输入类型
                 */
                int inputType = ta.getInt(
                        R.styleable.UiContentView_uiContentInputType, 0);
                /**
                 * 设置内容输入行数
                 */
                int lines = ta.getInt(R.styleable.UiContentView_uiContentLines, 0);
                setContentLines(lines);
                /**
                 * 最大长度
                 */
                int maxLength = ta.getInt(R.styleable.UiContentView_uiContentLines, 0);
                setContentMaxLength(maxLength);
                switch (inputType) {
                    case 1:
                        setContentInputtype(InputType.TYPE_CLASS_NUMBER);
                        break;
                    case 2:
                        setContentInputtype(InputType.TYPE_CLASS_PHONE);
                        break;
                    case 3:
                        setContentInputtype(TYPE_CLASS_TEXT
                                | InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);
                        break;
                    case 4:
                        setContentInputtype(InputType.TYPE_CLASS_NUMBER
                                | InputType.TYPE_NUMBER_FLAG_DECIMAL);
                        break;
                    case 5:
                        setContentInputtype(InputType.TYPE_CLASS_NUMBER
                                | InputType.TYPE_NUMBER_FLAG_SIGNED
                                | InputType.TYPE_NUMBER_FLAG_DECIMAL);
                        break;
                    case 6:
                        setContentInputtype(TYPE_CLASS_TEXT | TYPE_TEXT_VARIATION_PASSWORD);
                        break;
                    default:
                        break;
                }

                if (titleWidth != 0) {
                    LayoutParams titleParams = (LayoutParams) UiContent_Title
                            .getLayoutParams();
                    titleParams.width = titleWidth;
                    UiContent_Title.setLayoutParams(titleParams);
                }

                if (contentWidth != 0) {
                    LayoutParams contentParams = (LayoutParams) UiContent_Input
                            .getLayoutParams();
                    contentParams.width = contentWidth;
                    UiContent_Input.setLayoutParams(contentParams);
                }

                setTitleBackgroundResource(titleBack);
                setContentBackgroundResource(contentBack);

                setTitleImgResources(titleImgResource);
                setContentImgResources(contentImgResource);

                switch (titleVisibility) {
                    case 0:
                        UiContent_Title.setVisibility(View.VISIBLE);
                        break;
                    case 1:
                        UiContent_Title.setVisibility(View.INVISIBLE);
                        break;
                    case 2:
                        UiContent_Title.setVisibility(View.GONE);
                        break;
                    default:
                        break;
                }

                switch (contextVisibility) {
                    case 0:
                        UiContent_Input.setVisibility(View.VISIBLE);
                        break;
                    case 1:
                        UiContent_Input.setVisibility(View.INVISIBLE);
                        break;
                    case 2:
                        UiContent_Input.setVisibility(View.GONE);
                        break;
                    default:
                        break;
                }

                if (contentHint != null) {
                    setContentHint(contentHint);
                }
                setIsWarp(isWrap);
                setInputOnClickFlag(clickflag);
                setInputFlag(inputflag);
                if (ColorFlag) {
                    UiContent_Title.setTextColor(TextColor);
                    UiContent_Input.setTextColor(TextColor);
                } else {
                    UiContent_Title.setTextColor(titleColor);
                    UiContent_Input.setTextColor(contentColot);
                }

                if (titleText != null) {
                    UiContent_Title.setText(titleText);
                } else {
                    UiContent_Title.setText("");
                }
                if (ContntText != null) {
                    UiContent_Input.setText(ContntText);
                } else {
                    UiContent_Input.setText("");
                }

                UiContent_Title.setTextSize(TypedValue.COMPLEX_UNIT_PX,
                        titleTextSize);
                UiContent_Input.setTextSize(TypedValue.COMPLEX_UNIT_PX,
                        contextTextSize);

                if (Gracity != null && !Gracity.equals("")) {
                    if (Gracity.equals("1")) {
                        UiContent_Input.setGravity(Gravity.LEFT
                                | Gravity.CENTER_VERTICAL);
                    } else if (Gracity.equals("2")) {
                        UiContent_Input.setGravity(Gravity.CENTER
                                | Gravity.CENTER_VERTICAL);
                    } else if (Gracity.equals("3")) {
                        UiContent_Input.setGravity(Gravity.RIGHT
                                | Gravity.CENTER_VERTICAL);
                    }
                }
            } catch (Exception e) {
            } finally {
                ta.recycle();
            }
        }
    }

    /**
     * 最大长度
     */
    public void setContentMaxLength(int length) {
        if (length != 0) {
            UiContent_Input.setFilters(new InputFilter[]{new InputFilter.LengthFilter(length)});
        }
    }

    /**
     * 内容行数
     */
    public void setContentLines(int lines) {
        if (lines != 0) {
            UiContent_Input.setLines(lines);
        }
    }

    /**
     * 监听用户输入信息
     *
     * @param textWatcher
     */
    public void setTextWatcher(TextWatcher textWatcher) {
        if (textWatcher != null) {
            UiContent_Input.addTextChangedListener(textWatcher);
        }
    }

    /**
     * 获取焦点状态
     *
     * @param onFocusChangeListener
     */
    public void setOnContentFocusChangeListener(OnFocusChangeListener onFocusChangeListener) {
        UiContent_Input.setOnFocusChangeListener(onFocusChangeListener);
    }


    /**
     * title背景
     */
    public void setTitleBackgroundResource(int resid) {
        UiContent_Title.setBackgroundResource(resid);
    }

    /**
     * 设置内容背景
     */
    public void setContentBackgroundResource(int resid) {
        UiContent_Input.setBackgroundResource(resid);
    }

    /**
     * 设置title
     */
    public void setTitleVaule(String str) {
        UiContent_Title.setText(str);
    }

    /**
     * 返回title 的内容
     */
    public String getTitleVaule() {
        String str = UiContent_Title.getText().toString().trim();
        if (str != null) {
            return str;
        } else {
            return "";
        }
    }

    public void setContentVaule(String inputStr) {
        UiContent_Input.setText(inputStr);
    }

    public void setContentVaule(Spanned inputStr) {
        UiContent_Input.setText(inputStr);
    }


    public String getContentVaule() {
        String str = UiContent_Input.getText().toString().trim();
        if (str != null) {
            return str;
        } else {
            return "";
        }
    }

    /**
     * 设置edittext 是否能点击
     */
    public void setInputOnClickFlag(Boolean mOnClickFlag) {
        this.onClickFlag = mOnClickFlag;
        if (onClickFlag) {
            UiContent_Input.setFocusable(true);
            UiContent_Input.setFocusableInTouchMode(true);
        } else {
            UiContent_Input.setFocusable(false);
            UiContent_Input.setFocusableInTouchMode(false);
        }
    }

    /**
     * 用于设置edittext 是否能输入
     */
    public void setInputFlag(Boolean mInputFlag) {
        this.inputFlag = mInputFlag;
        if (inputFlag) {
            setIsWarp(true);
        } else {
            UiContent_Input.setInputType(InputType.TYPE_NULL);
            UiContent_Input.setSingleLine(false);
            UiContent_Input.setHorizontallyScrolling(false);
        }
    }

    public Boolean iSInputFlag() {
        return inputFlag;
    }


    public Boolean isClickFlag() {
        return onClickFlag;
    }

    public void setOnContentClick(OnContentClick onContentClick) {
        mOnContentClick = onContentClick;
    }

    public interface OnContentClick {
        void onContentClick(View v);
    }

    /**
     * 设置是否可以换行
     *
     * @param isWrap
     */
    public void setIsWarp(Boolean isWrap) {
        if (isWrap) {
            /**
             * 文本显示位置
             */
            UiContent_Input.setGravity(Gravity.CENTER_VERTICAL);
            /**
             * 改变默认的单行显示
             */
            UiContent_Input.setSingleLine(false);
            /**
             * 水平滑动设置为false
             */
            UiContent_Input.setHorizontallyScrolling(false);
        }
    }

    /**
     * 设置提示信息
     */
    public void setContentHint(String contentHint) {
        if (contentHint != null) {
            UiContent_Input.setHint(contentHint);
        } else {
            UiContent_Input.setHint("");
        }
    }

    /**
     * 设置输入类型
     *
     * @param type
     */
    public void setContentInputtype(int type) {
        UiContent_Input.setInputType(type);
    }

    /**
     * 设置文本方向
     *
     * @param gravity
     */
    public void setContentGravity(int gravity) {
        UiContent_Input.setGravity(gravity);
    }

    /**
     * 设置title图标资源
     *
     * @param res
     */
    public void setTitleImgResources(int res) {
        if (res != 0) {
            UiContent_TitleImg.setVisibility(View.VISIBLE);
            UiContent_TitleImg.setImageResource(res);
        } else {
            UiContent_TitleImg.setVisibility(View.GONE);
        }
    }

    /**
     * 设置内容标示的图标资源
     *
     * @param res
     */
    public void setContentImgResources(int res) {
        if (res != 0) {
            UiContent_ContentImg.setVisibility(View.VISIBLE);
            UiContent_ContentImg.setImageResource(res);
        } else {
            UiContent_ContentImg.setVisibility(View.GONE);
        }
    }

    public void setContentImgBitmap(Bitmap bitmap) {
        if (bitmap != null) {
            UiContent_ContentImg.setVisibility(View.VISIBLE);
            UiContent_ContentImg.setImageBitmap(bitmap);
        } else {
            UiContent_ContentImg.setVisibility(View.GONE);
        }
    }

    /**
     * 设置输入内容类型
     * @param contentInputType
     */
    public void setContentKeyListen(String contentInputType){
        UiContent_Input.setKeyListener(DigitsKeyListener.getInstance(contentInputType));
    }

    /**
     * 设置只能输入的类型
     * InputFilter[] filters = {new CashierInputFilter()};
     * @param inputFilters
     */
    public void setContentFilers(InputFilter [] inputFilters){
        UiContent_Input.setFilters(inputFilters);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,
                MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return super.onInterceptTouchEvent(ev);
    }
}
