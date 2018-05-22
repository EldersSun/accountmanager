package com.accountingmanager.Fragment.Assets;

import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;

import com.accountingmanager.Base.BaseFragment;
import com.accountingmanager.R;
import com.accountingmanager.Sys.Widgets.MyMarkerView;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.LimitLine;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.listener.ChartTouchListener;
import com.github.mikephil.charting.listener.OnChartGestureListener;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.Utils;

import java.util.ArrayList;

/**
 * 理财历程
 * Created by Home-Pc on 2017/5/25.
 */
public class AssetsHomeCourseFragment extends BaseFragment implements OnChartGestureListener, OnChartValueSelectedListener {
    private LineChart Assets_Home_Course_lineChart;
    private RecyclerView Assets_Home_Course_lvShow;

    @Override
    protected View initWidgetsViews(LayoutInflater inflater) {
        return inflater.inflate(R.layout.fm_assets_home_course_layout, null);
    }

    @Override
    protected void initWidgets(View fgView) {
        Assets_Home_Course_lineChart = (LineChart) fgView.findViewById(R.id.Assets_Home_Course_lineChart);
        Assets_Home_Course_lvShow = (RecyclerView) fgView.findViewById(R.id.Assets_Home_Course_lvShow);
    }

    @Override
    protected void initEvent() {
        initChart();
    }

    @Override
    public void onResponsSuccess(int TAG, Object result) {

    }

    @Override
    public void onResponsFailed(int TAG, String result) {

    }

    @Override
    public void onNetConnectFailed(int TAG, String result) {

    }

    private void initChart() {
        Assets_Home_Course_lineChart.setOnChartGestureListener(this);
        Assets_Home_Course_lineChart.setOnChartValueSelectedListener(this);
        Assets_Home_Course_lineChart.setDrawGridBackground(false);

        // no description text
        Assets_Home_Course_lineChart.getDescription().setEnabled(false);

        // enable touch gestures
        Assets_Home_Course_lineChart.setTouchEnabled(true);

        // enable scaling and dragging
        Assets_Home_Course_lineChart.setDragEnabled(true);
        Assets_Home_Course_lineChart.setScaleEnabled(true);
        // Assets_Home_Course_lineChart.setScaleXEnabled(true);
        // Assets_Home_Course_lineChart.setScaleYEnabled(true);

        // if disabled, scaling can be done on x- and y-axis separately
        Assets_Home_Course_lineChart.setPinchZoom(true);

        // set an alternative background color
        // Assets_Home_Course_lineChart.setBackgroundColor(Color.GRAY);

        // create a custom MarkerView (extend MarkerView) and specify the layout
        // to use for it
        MyMarkerView mv = new MyMarkerView(getActivity(), R.layout.v_custom_marker_view_layout);
        mv.setChartView(Assets_Home_Course_lineChart); // For bounds control
        Assets_Home_Course_lineChart.setMarker(mv); // Set the marker to the chart

        // x-axis limit line
        LimitLine llXAxis = new LimitLine(10f, "Index 10");
        llXAxis.setLineWidth(4f);
        llXAxis.enableDashedLine(10f, 10f, 0f);
        llXAxis.setLabelPosition(LimitLine.LimitLabelPosition.RIGHT_BOTTOM);
        llXAxis.setTextSize(10f);

        XAxis xAxis = Assets_Home_Course_lineChart.getXAxis();
        xAxis.enableGridDashedLine(10f, 10f, 0f);
        xAxis.setEnabled(false);
        //xAxis.setValueFormatter(new MyCustomXAxisValueFormatter());
        //xAxis.addLimitLine(llXAxis); // add x-axis limit line


//        Typeface tf = Typeface.createFromAsset(getAssets(), "OpenSans-Regular.ttf");

//        LimitLine ll1 = new LimitLine(150f, "Upper Limit");
//        ll1.setLineWidth(4f);
//        ll1.enableDashedLine(10f, 10f, 0f);
//        ll1.setLabelPosition(LimitLine.LimitLabelPosition.RIGHT_TOP);
//        ll1.setTextSize(10f);
//        ll1.setTypeface(tf);

//        LimitLine ll2 = new LimitLine(-30f, "Lower Limit");
//        ll2.setLineWidth(4f);
//        ll2.enableDashedLine(10f, 10f, 0f);
//        ll2.setLabelPosition(LimitLine.LimitLabelPosition.RIGHT_BOTTOM);
//        ll2.setTextSize(10f);
//        ll2.setTypeface(tf);

        YAxis leftAxis = Assets_Home_Course_lineChart.getAxisLeft();
        leftAxis.removeAllLimitLines(); // reset all limit lines to avoid overlapping lines
//        leftAxis.addLimitLine(ll1);//警戒线
//        leftAxis.addLimitLine(ll2);
        leftAxis.setAxisMaximum(200f);
        leftAxis.setAxisMinimum(-50f);
        //leftAxis.setYOffset(20f);
        leftAxis.enableGridDashedLine(10f, 10f, 0f);
        leftAxis.setDrawZeroLine(false);
        leftAxis.setEnabled(false);

        // limit lines are drawn behind data (and not on top)
//        leftAxis.setDrawLimitLinesBehindData(true);

        Assets_Home_Course_lineChart.getAxisRight().setEnabled(false);

        //Assets_Home_Course_lineChart.getViewPortHandler().setMaximumScaleY(2f);
        //Assets_Home_Course_lineChart.getViewPortHandler().setMaximumScaleX(2f);

        // add data
        setData(9, 100);

//        Assets_Home_Course_lineChart.setVisibleXRange(20);
//        Assets_Home_Course_lineChart.setVisibleYRange(20f, AxisDependency.LEFT);
//        Assets_Home_Course_lineChart.centerViewTo(20, 50, AxisDependency.LEFT);

        Assets_Home_Course_lineChart.animateX(2000);
        //Assets_Home_Course_lineChart.invalidate();

        // get the legend (only possible after setting data)
        Legend l = Assets_Home_Course_lineChart.getLegend();

        // modify the legend ...
        l.setForm(Legend.LegendForm.LINE);

        // // dont forget to refresh the drawing
        // Assets_Home_Course_lineChart.invalidate();
    }

    private void setData(int count, float range) {
        ArrayList<Entry> values = new ArrayList<Entry>();
        for (int i = 0; i < count; i++) {
            float val = (float) (Math.random() * range) + 3;
            values.add(new Entry(i, val, ContextCompat.getDrawable(getActivity(), R.drawable.star)));
        }
        LineDataSet set1;
        if (Assets_Home_Course_lineChart.getData() != null &&
                Assets_Home_Course_lineChart.getData().getDataSetCount() > 0) {
            set1 = (LineDataSet) Assets_Home_Course_lineChart.getData().getDataSetByIndex(0);
            set1.setValues(values);
            Assets_Home_Course_lineChart.getData().notifyDataChanged();
            Assets_Home_Course_lineChart.notifyDataSetChanged();
        } else {
            // create a dataset and give it a type
            set1 = new LineDataSet(values, "");

            set1.setDrawIcons(false);

            // set the line to be drawn like this "- - - - - -"
            set1.enableDashedLine(10f, 5f, 0f);
            set1.enableDashedHighlightLine(10f, 5f, 0f);
            set1.setColor(Color.WHITE);
            set1.setCircleColor(Color.WHITE);
            set1.setLineWidth(1f);
            set1.setCircleRadius(3f);
            set1.setDrawCircleHole(false);
            set1.setValueTextSize(9f);
            set1.setValueTextColor(Color.WHITE);
            set1.setDrawFilled(true);
            set1.setFormLineWidth(1f);
            set1.setFormLineDashEffect(new DashPathEffect(new float[]{10f, 5f}, 0f));
            set1.setFormSize(15.f);

            if (Utils.getSDKInt() >= 18) {
                // fill drawable only supported on api level 18 and above
                Drawable drawable = ContextCompat.getDrawable(getActivity(), R.drawable.fade_red);
                set1.setFillDrawable(drawable);
            } else {
                set1.setFillColor(Color.BLACK);
            }

            ArrayList<ILineDataSet> dataSets = new ArrayList<ILineDataSet>();
            dataSets.add(set1); // add the datasets

            // create a data object with the datasets
            LineData data = new LineData(dataSets);

            // set data
            Assets_Home_Course_lineChart.setData(data);
        }
    }

    @Override
    public void onChartGestureStart(MotionEvent me, ChartTouchListener.ChartGesture lastPerformedGesture) {
        Log.i("Gesture", "START, x: " + me.getX() + ", y: " + me.getY());
    }

    @Override
    public void onChartGestureEnd(MotionEvent me, ChartTouchListener.ChartGesture lastPerformedGesture) {
        Log.i("Gesture", "END, lastGesture: " + lastPerformedGesture);

        // un-highlight values after the gesture is finished and no single-tap
        if (lastPerformedGesture != ChartTouchListener.ChartGesture.SINGLE_TAP)
            Assets_Home_Course_lineChart.highlightValues(null); // or highlightTouch(null) for callback to onNothingSelected(...)
    }

    @Override
    public void onChartLongPressed(MotionEvent me) {
        Log.i("LongPress", "Chart longpressed.");
    }

    @Override
    public void onChartDoubleTapped(MotionEvent me) {
        Log.i("DoubleTap", "Chart double-tapped.");
    }

    @Override
    public void onChartSingleTapped(MotionEvent me) {
        Log.i("SingleTap", "Chart single-tapped.");
    }

    @Override
    public void onChartFling(MotionEvent me1, MotionEvent me2, float velocityX, float velocityY) {
        Log.i("Fling", "Chart flinged. VeloX: " + velocityX + ", VeloY: " + velocityY);
    }

    @Override
    public void onChartScale(MotionEvent me, float scaleX, float scaleY) {
        Log.i("Scale / Zoom", "ScaleX: " + scaleX + ", ScaleY: " + scaleY);
    }

    @Override
    public void onChartTranslate(MotionEvent me, float dX, float dY) {
        Log.i("Translate / Move", "dX: " + dX + ", dY: " + dY);
    }

    @Override
    public void onValueSelected(Entry e, Highlight h) {
        Log.i("Entry selected", e.toString());
        Log.i("LOWHIGH", "low: " + Assets_Home_Course_lineChart.getLowestVisibleX() + ", high: " + Assets_Home_Course_lineChart.getHighestVisibleX());
        Log.i("MIN MAX", "xmin: " + Assets_Home_Course_lineChart.getXChartMin() + ", xmax: " + Assets_Home_Course_lineChart.getXChartMax() + ", ymin: " + Assets_Home_Course_lineChart.getYChartMin() + ", ymax: " + Assets_Home_Course_lineChart.getYChartMax());
    }

    @Override
    public void onNothingSelected() {
        Log.i("Nothing selected", "Nothing selected.");
    }
}
