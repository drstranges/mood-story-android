package com.drprog.moodstory.view;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.drprog.moodstory.R;
import com.drprog.moodstory.databinding.ActivityMainBinding;
import com.drprog.moodstory.viewmodel.MainActivityViewModel;

import lecho.lib.hellocharts.gesture.ZoomType;
import lecho.lib.hellocharts.model.ColumnChartData;
import lecho.lib.hellocharts.model.Viewport;
import lecho.lib.hellocharts.view.ColumnChartView;

public class MainActivity extends AppCompatActivity implements MainActivityViewModel.Callback {

    private ActivityMainBinding mBinding;
    private MainActivityViewModel mViewModel;

    public static Intent getIntent(Context context) {
        final Intent intent = new Intent(context, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        initChart();

        mViewModel = new MainActivityViewModel(getApplicationContext(), savedInstanceState, this);
        mBinding.setViewModel(mViewModel);
    }

    @Override
    protected void onDestroy() {
        mViewModel.onDestroy();
        super.onDestroy();
    }

    private void initChart() {

        final ColumnChartView chart = mBinding.chart;
        chart.setZoomType(ZoomType.HORIZONTAL);
    }

    @Override
    public void setChartData(ColumnChartData data) {
        mBinding.chart.setColumnChartData(data);
        final int size = data.getColumns().get(0).getValues().size();
        mBinding.chart.setCurrentViewport(new Viewport(size-10, 10.5f, size, 0));
//        mBinding.chart.resetViewports();
    }

}
