package com.accountingmanager.Fragment.Assets;

import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Typeface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.TextView;

import com.accountingmanager.Base.BaseFragment;
import com.accountingmanager.R;
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;

/**
 * 资产透视
 * Created by Home_Pc on 2017/4/17.
 */

public class AssetsAnalysisFragment extends BaseFragment implements OnChartValueSelectedListener,View.OnClickListener {

    private RadioButton assets_analysis_Assets,assets_analysis_Liabilities;
    private ImageButton assets_analysis_centerImg;
    private Button assets_analysis_share,assets_analysis_find,assets_analysis_file;
    private TextView assets_analysis_ProfitShow,assets_analysis_MobilityShow,assets_analysis_riskShow;

    private PieChart assets_analysis_PieChart;

    protected String[] mParties = new String[] {
            "Party A", "Party B", "Party C", "Party D", "Party E", "Party F", "Party G", "Party H",
            "Party I", "Party J", "Party K", "Party L", "Party M", "Party N", "Party O", "Party P",
            "Party Q", "Party R", "Party S", "Party T", "Party U", "Party V", "Party W", "Party X",
            "Party Y", "Party Z"
    };

    @Override
    protected View initWidgetsViews(LayoutInflater inflater) {
        return inflater.inflate(R.layout.fm_assets_analysis_layout,null);
    }

    @Override
    protected void initWidgets(View fgView) {
        assets_analysis_PieChart= (PieChart) fgView.findViewById(R.id.assets_analysis_PieChart);

        assets_analysis_Assets= (RadioButton) fgView.findViewById(R.id.assets_analysis_Assets);
        assets_analysis_Liabilities= (RadioButton) fgView.findViewById(R.id.assets_analysis_Liabilities);

        assets_analysis_centerImg= (ImageButton) fgView.findViewById(R.id.assets_analysis_centerImg);
        assets_analysis_share= (Button) fgView.findViewById(R.id.assets_analysis_share);
        assets_analysis_find= (Button) fgView.findViewById(R.id.assets_analysis_find);
        assets_analysis_file= (Button) fgView.findViewById(R.id.assets_analysis_file);

        assets_analysis_ProfitShow= (TextView) fgView.findViewById(R.id.assets_analysis_ProfitShow);
        assets_analysis_MobilityShow= (TextView) fgView.findViewById(R.id.assets_analysis_MobilityShow);
        assets_analysis_riskShow= (TextView) fgView.findViewById(R.id.assets_analysis_riskShow);
    }

    @Override
    protected void initEvent() {
        assets_analysis_Assets.setOnClickListener(this);
        assets_analysis_Liabilities.setOnClickListener(this);
        assets_analysis_centerImg.setOnClickListener(this);
        assets_analysis_share.setOnClickListener(this);
        assets_analysis_find.setOnClickListener(this);
        assets_analysis_file.setOnClickListener(this);
        init();
    }

    private void setData(int count, float range) {

        float mult = range;
        ArrayList<PieEntry> entries = new ArrayList<PieEntry>();

        // NOTE: The order of the entries when being added to the entries array determines their position around the center of
        // the chart.
        for (int i = 0; i < count; i++) {
            entries.add(new PieEntry((float) (Math.random() * mult) + mult / 5, mParties[i % mParties.length]));
        }

        PieDataSet dataSet = new PieDataSet(entries, "Election Results");
        dataSet.setSliceSpace(3f);
        dataSet.setSelectionShift(5f);

        // add a lot of colors
        ArrayList<Integer> colors = new ArrayList<Integer>();

        for (int c : ColorTemplate.VORDIPLOM_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.JOYFUL_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.COLORFUL_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.LIBERTY_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.PASTEL_COLORS)
            colors.add(c);

        colors.add(ColorTemplate.getHoloBlue());
        dataSet.setColors(colors);
        //dataSet.setSelectionShift(0f);
        dataSet.setValueLinePart1OffsetPercentage(80.f);
        dataSet.setValueLinePart1Length(0.2f);
        dataSet.setValueLinePart2Length(0.4f);
        //dataSet.setXValuePosition(PieDataSet.ValuePosition.OUTSIDE_SLICE);
        dataSet.setYValuePosition(PieDataSet.ValuePosition.OUTSIDE_SLICE);

        PieData data = new PieData(dataSet);
        data.setValueFormatter(new PercentFormatter());
        data.setValueTextSize(11f);
        data.setValueTextColor(Color.BLACK);
//        data.setValueTypeface(tf);
        assets_analysis_PieChart.setData(data);

        // undo all highlights
        assets_analysis_PieChart.highlightValues(null);
        assets_analysis_PieChart.invalidate();
    }

    private void init(){
        assets_analysis_PieChart.setUsePercentValues(true);
        assets_analysis_PieChart.getDescription().setEnabled(false);
        assets_analysis_PieChart.setExtraOffsets(5, 10, 5, 5);

        assets_analysis_PieChart.setDragDecelerationFrictionCoef(0.95f);

//        tf = Typeface.createFromAsset(getAssets(), "OpenSans-Regular.ttf");
//        assets_analysis_PieChart.setCenterTextTypeface(Typeface.createFromAsset(getAssets(), "OpenSans-Light.ttf"));
//        assets_analysis_PieChart.setCenterText(generateCenterSpannableText());
//        assets_analysis_PieChart.setHoldBackground(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher));

        assets_analysis_PieChart.setExtraOffsets(20.f, 0.f, 20.f, 0.f);

        assets_analysis_PieChart.setDrawHoleEnabled(true);
        assets_analysis_PieChart.setHoleColor(Color.WHITE);

        assets_analysis_PieChart.setTransparentCircleColor(Color.WHITE);
        assets_analysis_PieChart.setTransparentCircleAlpha(110);

        assets_analysis_PieChart.setHoleRadius(58f);
        assets_analysis_PieChart.setTransparentCircleRadius(61f);

        assets_analysis_PieChart.setDrawCenterText(false);

        assets_analysis_PieChart.setRotationAngle(0);
        // enable rotation of the chart by touch
        //通过触摸启用图表的旋转
        assets_analysis_PieChart.setRotationEnabled(true);
        assets_analysis_PieChart.setHighlightPerTapEnabled(true);

        // assets_analysis_PieChart.setUnit(" €");
        // assets_analysis_PieChart.setDrawUnitsInChart(true);

        // add a selection listener
        //添加选择监听器
        assets_analysis_PieChart.setOnChartValueSelectedListener(this);

        setData(4, 100);
        assets_analysis_PieChart.animateY(1400, Easing.EasingOption.EaseInOutQuad);
        // assets_analysis_PieChart.spin(2000, 0, 360);
        Legend l = assets_analysis_PieChart.getLegend();
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
        l.setOrientation(Legend.LegendOrientation.VERTICAL);
        l.setDrawInside(false);
        l.setEnabled(false);
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

    @Override
    public void onValueSelected(Entry e, Highlight h) {

        if (e == null)
            return;
        Log.i("VAL SELECTED",
                "Value: " + e.getY() + ", xIndex: " + e.getX()
                        + ", DataSet index: " + h.getDataSetIndex());
    }

    @Override
    public void onNothingSelected() {
        Log.i("PieChart", "nothing selected");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.assets_analysis_Assets:
                break;
            case R.id.assets_analysis_Liabilities:
                break;
            case R.id.assets_analysis_centerImg:
                break;
            case R.id.assets_analysis_share:
                break;
            case R.id.assets_analysis_find:
                break;
            case R.id.assets_analysis_file:
                break;
        }
    }
}
