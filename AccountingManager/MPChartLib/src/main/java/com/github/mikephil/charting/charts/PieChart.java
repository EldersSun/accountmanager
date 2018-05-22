
package com.github.mikephil.charting.charts;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.util.AttributeSet;

import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.highlight.PieHighlighter;
import com.github.mikephil.charting.interfaces.datasets.IPieDataSet;
import com.github.mikephil.charting.renderer.PieChartRenderer;
import com.github.mikephil.charting.utils.MPPointF;
import com.github.mikephil.charting.utils.Utils;

import java.util.List;

/**
 * View that represents a pie chart. Draws cake like slices.
 * 表示饼图的视图。画蛋糕片。
 *
 * @author Philipp Jahoda
 */
public class PieChart extends PieRadarChartBase<PieData> {

    /**
     * rect object that represents the bounds of the piechart, needed for
     * drawing the circle
     * <p>
     * 矩形表示图示的界限，画圆的需要
     */
    private RectF mCircleBox = new RectF();

    /**
     * flag indicating if entry labels should be drawn or not
     * <p>
     * 标志，如果输入标签应绘制或不
     */
    private boolean mDrawEntryLabels = true;

    /**
     * array that holds the width of each pie-slice in degrees
     * 数组，它持有每个饼片的宽度度
     */
    private float[] mDrawAngles = new float[1];

    /**
     * array that holds the absolute angle in degrees of each slice
     * <p>
     * 数组，它持有每个切片的绝对角度
     */
    private float[] mAbsoluteAngles = new float[1];

    /**
     * if true, the white hole inside the chart will be drawn
     * <p>
     * 如果设置为true，该图将白孔内侧。
     */
    private boolean mDrawHole = true;

    /**
     * if true, the hole will see-through to the inner tips of the slices
     * <p>
     * 如果是真的，孔将透过切片的内部提示
     */
    private boolean mDrawSlicesUnderHole = false;

    /**
     * if true, the values inside the piechart are drawn as percent values
     * <p>
     * 如果是真的，里面的饼图的值为百分比值
     */
    private boolean mUsePercentValues = false;

    /**
     * if true, the slices of the piechart are rounded
     * <p>
     * 如果是真的，该片是圆饼图
     */
    private boolean mDrawRoundedSlices = false;

    /**
     * variable for the text that is drawn in the center of the pie-chart
     * <p>
     * 用于在饼图中心绘制的文本的变量
     */
    private CharSequence mCenterText = "";

    private MPPointF mCenterTextOffset = MPPointF.getInstance(0, 0);

    /**
     * indicates the size of the hole in the center of the piechart, default:
     * radius / 2
     * <p>
     * 表明在图示，中心孔大小：默认半径/ 2
     */
    private float mHoleRadiusPercent = 50f;

    /**
     * the radius of the transparent circle next to the chart-hole in the center
     * <p>
     * 在中心的图孔旁边的透明圆的半径
     */
    protected float mTransparentCircleRadiusPercent = 55f;

    /**
     * if enabled, centertext is drawn
     * <p>
     * 如果启用，centertext绘制
     */
    private boolean mDrawCenterText = true;

    private float mCenterTextRadiusPercent = 100.f;

    protected float mMaxAngle = 360f;

    public PieChart(Context context) {
        super(context);
    }

    public PieChart(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public PieChart(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected void init() {
        super.init();

        mRenderer = new PieChartRenderer(this, mAnimator, mViewPortHandler);
        mXAxis = null;

        mHighlighter = new PieHighlighter(this);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if (mData == null)
            return;

        mRenderer.drawData(canvas);

        if (valuesToHighlight())
            mRenderer.drawHighlighted(canvas, mIndicesToHighlight);

        mRenderer.drawExtras(canvas);

        mRenderer.drawValues(canvas);

        mLegendRenderer.renderLegend(canvas);

        drawDescription(canvas);

        drawMarkers(canvas);
    }

    @Override
    public void calculateOffsets() {
        super.calculateOffsets();

        // prevent nullpointer when no data set
        if (mData == null)
            return;

        float diameter = getDiameter();
        float radius = diameter / 2f;

        MPPointF c = getCenterOffsets();

        float shift = mData.getDataSet().getSelectionShift();

        // create the circle box that will contain the pie-chart (the bounds of
        // the pie-chart)
        //创建包含饼图的圆框（饼图的边界）
        mCircleBox.set(c.x - radius + shift,
                c.y - radius + shift,
                c.x + radius - shift,
                c.y + radius - shift);

        MPPointF.recycleInstance(c);
    }

    @Override
    protected void calcMinMax() {
        calcAngles();
    }

    @Override
    protected float[] getMarkerPosition(Highlight highlight) {

        MPPointF center = getCenterCircleBox();
        float r = getRadius();

        float off = r / 10f * 3.6f;

        if (isDrawHoleEnabled()) {
            off = (r - (r / 100f * getHoleRadius())) / 2f;
        }

        r -= off; // offset to keep things inside the chart 让事情的内幕图偏移

        float rotationAngle = getRotationAngle();

        int entryIndex = (int) highlight.getX();

        // offset needed to center the drawn text in the slice
        //需要将绘制的文本居中居中的偏移量
        float offset = mDrawAngles[entryIndex] / 2;

        // calculate the text position
        //计算文本位置
        float x = (float) (r
                * Math.cos(Math.toRadians((rotationAngle + mAbsoluteAngles[entryIndex] - offset)
                * mAnimator.getPhaseY())) + center.x);
        float y = (float) (r
                * Math.sin(Math.toRadians((rotationAngle + mAbsoluteAngles[entryIndex] - offset)
                * mAnimator.getPhaseY())) + center.y);

        MPPointF.recycleInstance(center);
        return new float[]{x, y};
    }

    /**
     * calculates the needed angles for the chart slices
     * <p>
     * 计算图表切片所需的角度
     */
    private void calcAngles() {

        int entryCount = mData.getEntryCount();

        if (mDrawAngles.length != entryCount) {
            mDrawAngles = new float[entryCount];
        } else {
            for (int i = 0; i < entryCount; i++) {
                mDrawAngles[i] = 0;
            }
        }
        if (mAbsoluteAngles.length != entryCount) {
            mAbsoluteAngles = new float[entryCount];
        } else {
            for (int i = 0; i < entryCount; i++) {
                mAbsoluteAngles[i] = 0;
            }
        }

        float yValueSum = mData.getYValueSum();

        List<IPieDataSet> dataSets = mData.getDataSets();

        int cnt = 0;

        for (int i = 0; i < mData.getDataSetCount(); i++) {

            IPieDataSet set = dataSets.get(i);

            for (int j = 0; j < set.getEntryCount(); j++) {

                mDrawAngles[cnt] = calcAngle(Math.abs(set.getEntryForIndex(j).getY()), yValueSum);

                if (cnt == 0) {
                    mAbsoluteAngles[cnt] = mDrawAngles[cnt];
                } else {
                    mAbsoluteAngles[cnt] = mAbsoluteAngles[cnt - 1] + mDrawAngles[cnt];
                }

                cnt++;
            }
        }

    }

    /**
     * Checks if the given index is set to be highlighted.
     * <p>
     * 检查给定的索引是否设置为高亮显示。
     *
     * @param index
     * @return
     */
    public boolean needsHighlight(int index) {

        // no highlight
        if (!valuesToHighlight())
            return false;

        for (int i = 0; i < mIndicesToHighlight.length; i++)

            // check if the xvalue for the given dataset needs highlight
            //如果xvalue支票的highlight给定的数据集。
            if ((int) mIndicesToHighlight[i].getX() == index)
                return true;

        return false;
    }

    /**
     * calculates the needed angle for a given value
     *
     * 计算给定值所需的角度
     *
     * @param value
     * @return
     */
    private float calcAngle(float value) {
        return calcAngle(value, mData.getYValueSum());
    }

    /**
     * calculates the needed angle for a given value
     *
     * 计算给定值所需的角度
     *
     * @param value
     * @param yValueSum
     * @return
     */
    private float calcAngle(float value, float yValueSum) {
        return value / yValueSum * mMaxAngle;
    }

    /**
     * This will throw an exception, PieChart has no XAxis object.
     *
     * 这将引发一个异常，饼图没有X对象。
     *
     * @return
     */
    @Deprecated
    @Override
    public XAxis getXAxis() {
        throw new RuntimeException("PieChart has no XAxis");
    }

    @Override
    public int getIndexForAngle(float angle) {

        // take the current angle of the chart into consideration
        //考虑图表的当前角度
        float a = Utils.getNormalizedAngle(angle - getRotationAngle());

        for (int i = 0; i < mAbsoluteAngles.length; i++) {
            if (mAbsoluteAngles[i] > a)
                return i;
        }

        return -1; // return -1 if no index found
    }

    /**
     * Returns the index of the DataSet this x-index belongs to.
     *
     * 返回的索引的数据集这x-index属于。
     *
     * @param xIndex
     * @return
     */
    public int getDataSetIndexForIndex(int xIndex) {

        List<IPieDataSet> dataSets = mData.getDataSets();

        for (int i = 0; i < dataSets.size(); i++) {
            if (dataSets.get(i).getEntryForXValue(xIndex, Float.NaN) != null)
                return i;
        }

        return -1;
    }

    /**
     * returns an integer array of all the different angles the chart slices
     * have the angles in the returned array determine how much space (of 360°)
     * each slice takes
     *
     * 返回一个整数数组的所有不同角度的图表片有返回数组中的角度决定每个切片需要多少空间（360°）
     *
     * @return
     */
    public float[] getDrawAngles() {
        return mDrawAngles;
    }

    /**
     * returns the absolute angles of the different chart slices (where the
     * slices end)
     *
     * 返回不同图表切片的绝对角度（切片结束时）
     *
     * @return
     */
    public float[] getAbsoluteAngles() {
        return mAbsoluteAngles;
    }

    /**
     * Sets the color for the hole that is drawn in the center of the PieChart
     * (if enabled).
     *
     * 套孔，绘制的饼图中心的颜色（如果已启用）。
     *
     * @param color
     */
    public void setHoleColor(int color) {
        ((PieChartRenderer) mRenderer).getPaintHole().setColor(color);
    }

    public void setHoldBackground(Bitmap bitmap){
        ((PieChartRenderer) mRenderer).setCenterBackImg(bitmap);
    }

    /**
     * Enable or disable the visibility of the inner tips of the slices behind the hole
     *
     * 启用或禁用后面的孔片的内部提示的可见性
     */
    public void setDrawSlicesUnderHole(boolean enable) {
        mDrawSlicesUnderHole = enable;
    }

    /**
     * Returns true if the inner tips of the slices are visible behind the hole,
     * false if not.
     *
     * 返回真实的，如果内部提示的片是可见的洞后面， 如果没有。
     *
     * TRUE如果切片可见孔后面。
     *
     * @return true if slices are visible behind the hole.
     */
    public boolean isDrawSlicesUnderHoleEnabled() {
        return mDrawSlicesUnderHole;
    }

    /**
     * set this to true to draw the pie center empty
     *
     * 设置为true将饼中心清空
     *
     * @param enabled
     */
    public void setDrawHoleEnabled(boolean enabled) {
        this.mDrawHole = enabled;
    }

    /**
     * returns true if the hole in the center of the pie-chart is set to be
     * visible, false if not
     *
     * 如果饼图中心的孔设置为可见，则返回true，如果不
     *
     * @return
     */
    public boolean isDrawHoleEnabled() {
        return mDrawHole;
    }

    /**
     * Sets the text String that is displayed in the center of the PieChart.
     *
     * 设置文本字符串，在图示展示中心。
     *
     * @param text
     */
    public void setCenterText(CharSequence text) {
        if (text == null)
            mCenterText = "";
        else
            mCenterText = text;
    }

    /**
     * returns the text that is drawn in the center of the pie-chart
     *
     * 返回在饼图中心绘制的文本
     *
     * @return
     */
    public CharSequence getCenterText() {
        return mCenterText;
    }

    /**
     * set this to true to draw the text that is displayed in the center of the
     * pie chart
     *
     * 将此设置为true，以绘制在饼图中心显示的文本
     *
     * @param enabled
     */
    public void setDrawCenterText(boolean enabled) {
        this.mDrawCenterText = enabled;
    }

    /**
     * returns true if drawing the center text is enabled
     *
     * 如果启用中心文本，返回true
     *
     * @return
     */
    public boolean isDrawCenterTextEnabled() {
        return mDrawCenterText;
    }

    @Override
    protected float getRequiredLegendOffset() {
        return mLegendRenderer.getLabelPaint().getTextSize() * 2.f;
    }

    @Override
    protected float getRequiredBaseOffset() {
        return 0;
    }

    @Override
    public float getRadius() {
        if (mCircleBox == null)
            return 0;
        else
            return Math.min(mCircleBox.width() / 2f, mCircleBox.height() / 2f);
    }

    /**
     * returns the circlebox, the boundingbox of the pie-chart slices
     *
     * 返回张，这张图片框
     *
     * @return
     */
    public RectF getCircleBox() {
        return mCircleBox;
    }

    /**
     * returns the center of the circlebox
     *
     * 返回中心的circlebox
     *
     * @return
     */
    public MPPointF getCenterCircleBox() {
        return MPPointF.getInstance(mCircleBox.centerX(), mCircleBox.centerY());
    }

    /**
     * sets the typeface for the center-text paint
     *
     * 设置中心文字涂料的字体
     *
     * @param t
     */
    public void setCenterTextTypeface(Typeface t) {
        ((PieChartRenderer) mRenderer).getPaintCenterText().setTypeface(t);
    }

    /**
     * Sets the size of the center text of the PieChart in dp.
     *
     * 套在DP的饼图中心文本大小。
     *
     * @param sizeDp
     */
    public void setCenterTextSize(float sizeDp) {
        ((PieChartRenderer) mRenderer).getPaintCenterText().setTextSize(
                Utils.convertDpToPixel(sizeDp));
    }

    /**
     * Sets the size of the center text of the PieChart in pixels.
     *
     * 设置像素的饼图中心文本大小。
     *
     * @param sizePixels
     */
    public void setCenterTextSizePixels(float sizePixels) {
        ((PieChartRenderer) mRenderer).getPaintCenterText().setTextSize(sizePixels);
    }

    /**
     * Sets the offset the center text should have from it's original position in dp. Default x = 0, y = 0
     *
     * 设置中心文本的偏移量，它的初始位置为DP。默认x = 0，Y = 0
     *
     * @param x
     * @param y
     */
    public void setCenterTextOffset(float x, float y) {
        mCenterTextOffset.x = Utils.convertDpToPixel(x);
        mCenterTextOffset.y = Utils.convertDpToPixel(y);
    }

    /**
     * Returns the offset on the x- and y-axis the center text has in dp.
     *
     * 返回在中心文本中的x和y轴的偏移量。
     *
     * @return
     */
    public MPPointF getCenterTextOffset() {
        return MPPointF.getInstance(mCenterTextOffset.x, mCenterTextOffset.y);
    }

    /**
     * Sets the color of the center text of the PieChart.
     *
     * 设置饼图中心的文本颜色。
     *
     * @param color
     */
    public void setCenterTextColor(int color) {
        ((PieChartRenderer) mRenderer).getPaintCenterText().setColor(color);
    }

    /**
     * sets the radius of the hole in the center of the piechart in percent of
     * the maximum radius (max = the radius of the whole chart), default 50%
     *
     * 设置在最大半径的百分比饼图中心孔半径（最大=整个图的半径），默认为50%
     *
     * @param percent
     */
    public void setHoleRadius(final float percent) {
        mHoleRadiusPercent = percent;
    }

    /**
     * Returns the size of the hole radius in percent of the total radius.
     *
     * 返回半径占总半径的百分比。
     *
     * @return
     */
    public float getHoleRadius() {
        return mHoleRadiusPercent;
    }

    /**
     * Sets the color the transparent-circle should have.
     *
     * Sets the color the transparent-circle should have.
     *
     * @param color
     */
    public void setTransparentCircleColor(int color) {

        Paint p = ((PieChartRenderer) mRenderer).getPaintTransparentCircle();
        int alpha = p.getAlpha();
        p.setColor(color);
        p.setAlpha(alpha);
    }

    /**
     * sets the radius of the transparent circle that is drawn next to the hole
     * in the piechart in percent of the maximum radius (max = the radius of the
     * whole chart), default 55% -> means 5% larger than the center-hole by
     * default
     *
     * 套透明的圆的半径，画下在最大半径的百分比在饼图的孔（max =整个图的半径），默认55% -> 5%大于中心孔默认
     *
     * @param percent
     */
    public void setTransparentCircleRadius(final float percent) {
        mTransparentCircleRadiusPercent = percent;
    }

    public float getTransparentCircleRadius() {
        return mTransparentCircleRadiusPercent;
    }

    /**
     * Sets the amount of transparency the transparent circle should have 0 = fully transparent,
     * 255 = fully opaque.
     * Default value is 100.
     *
     * 设置透明度的透明度圆应该有0 =完全透明，255 =完全不透明。默认值为100。
     *
     * @param alpha 0-255
     */
    public void setTransparentCircleAlpha(int alpha) {
        ((PieChartRenderer) mRenderer).getPaintTransparentCircle().setAlpha(alpha);
    }

    /**
     * Set this to true to draw the entry labels into the pie slices (Provided by the getLabel() method of the PieEntry class).
     * Deprecated -> use setDrawEntryLabels(...) instead.
     *
     * 设置为true，出入口标签到饼片（由pieentry班getlabel()方法提供）。弃用->使用setdrawentrylabels代替（…）。
     *
     * @param enabled
     */
    @Deprecated
    public void setDrawSliceText(boolean enabled) {
        mDrawEntryLabels = enabled;
    }

    /**
     * Set this to true to draw the entry labels into the pie slices (Provided by the getLabel() method of the PieEntry class).
     *
     * 设置为true，出入口标签到饼片（由pieentry班getlabel()方法提供）。
     *
     * @param enabled
     */
    public void setDrawEntryLabels(boolean enabled) {
        mDrawEntryLabels = enabled;
    }

    /**
     * Returns true if drawing the entry labels is enabled, false if not.
     *
     * 如果启用了入口标签，返回true，如果没有。
     *
     * @return
     */
    public boolean isDrawEntryLabelsEnabled() {
        return mDrawEntryLabels;
    }

    /**
     * Sets the color the entry labels are drawn with.
     *
     * 设置入口标签的颜色。
     *
     * @param color
     */
    public void setEntryLabelColor(int color) {
        ((PieChartRenderer) mRenderer).getPaintEntryLabels().setColor(color);
    }

    /**
     * Sets a custom Typeface for the drawing of the entry labels.
     *
     * 设置用于输入标签的绘图的自定义字体。
     *
     * @param tf
     */
    public void setEntryLabelTypeface(Typeface tf) {
        ((PieChartRenderer) mRenderer).getPaintEntryLabels().setTypeface(tf);
    }

    /**
     * Sets the size of the entry labels in dp. Default: 13dp
     *
     * 设置DP中的入口标签的大小。默认值：13dp
     *
     * @param size
     */
    public void setEntryLabelTextSize(float size) {
        ((PieChartRenderer) mRenderer).getPaintEntryLabels().setTextSize(Utils.convertDpToPixel(size));
    }

    /**
     * Returns true if the chart is set to draw each end of a pie-slice
     * "rounded".
     *
     * 如果图表被设置为绘制饼状圆的每一个圆的结束，返回true。
     *
     * @return
     */
    public boolean isDrawRoundedSlicesEnabled() {
        return mDrawRoundedSlices;
    }

    /**
     * If this is enabled, values inside the PieChart are drawn in percent and
     * not with their original value. Values provided for the IValueFormatter to
     * format are then provided in percent.
     *
     * 如果启用，里面绘制饼图值%和不与自己原有的价值。值提供给IValueFormatter的格式提出的百分比。
     *
     * @param enabled
     */
    public void setUsePercentValues(boolean enabled) {
        mUsePercentValues = enabled;
    }

    /**
     * Returns true if using percentage values is enabled for the chart.
     *
     * 如果为图表启用了百分比值，则返回true。
     *
     * @return
     */
    public boolean isUsePercentValuesEnabled() {
        return mUsePercentValues;
    }

    /**
     * the rectangular radius of the bounding box for the center text, as a percentage of the pie
     * hole
     * default 1.f (100%)
     *
     * 矩形半径的包围盒为中心文本，作为饼洞默认百分比1。f（100%）
     */
    public void setCenterTextRadiusPercent(float percent) {
        mCenterTextRadiusPercent = percent;
    }

    /**
     * the rectangular radius of the bounding box for the center text, as a percentage of the pie
     * hole
     * default 1.f (100%)
     *
     * 矩形半径的包围盒为中心文本，作为饼洞默认百分比1。f（100%）
     */
    public float getCenterTextRadiusPercent() {
        return mCenterTextRadiusPercent;
    }

    public float getMaxAngle() {
        return mMaxAngle;
    }

    /**
     * Sets the max angle that is used for calculating the pie-circle. 360f means
     * it's a full PieChart, 180f results in a half-pie-chart. Default: 360f
     *
     * 设置用于计算圆饼的最大角度。360f意味着这是一个完整的饼图，180f结果在半饼图。默认值：360f
     *
     * @param maxangle min 90, max 360
     */
    public void setMaxAngle(float maxangle) {

        if (maxangle > 360)
            maxangle = 360f;

        if (maxangle < 90)
            maxangle = 90f;

        this.mMaxAngle = maxangle;
    }

    @Override
    protected void onDetachedFromWindow() {
        // releases the bitmap in the renderer to avoid oom error
        //释放位图渲染避免OOM的错误
        if (mRenderer != null && mRenderer instanceof PieChartRenderer) {
            ((PieChartRenderer) mRenderer).releaseBitmap();
        }
        super.onDetachedFromWindow();
    }
}
