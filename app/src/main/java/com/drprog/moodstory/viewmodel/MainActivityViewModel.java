package com.drprog.moodstory.viewmodel;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;

import com.drprog.moodstory.model.persisted.TrackPoint;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import io.realm.Realm;
import io.realm.RealmResults;
import io.realm.Sort;
import lecho.lib.hellocharts.model.Axis;
import lecho.lib.hellocharts.model.AxisValue;
import lecho.lib.hellocharts.model.Column;
import lecho.lib.hellocharts.model.ColumnChartData;
import lecho.lib.hellocharts.model.SubcolumnValue;

/**
 * Created on 15.05.2016.
 */
public class MainActivityViewModel extends BaseViewModel {

    private static final DateFormat DATE_FORMAT = new SimpleDateFormat("dd MMM", Locale.getDefault());
    public static final int[] COLORS = {
            Color.parseColor("#01577A"), // -5
            Color.parseColor("#17AE83"), // -4
            Color.parseColor("#18A1AB"), // -3
            Color.parseColor("#57C4C9"), // -2
            Color.parseColor("#B1DAE0"), // -1
            Color.parseColor("#E0E0E0"), // 0
            Color.parseColor("#F59E35"), // +1
            Color.parseColor("#F08AB1"), // +2
            Color.parseColor("#EC71A8"), // +3
            Color.parseColor("#DD57A0"), // +4
            Color.parseColor("#DB1C5F")  // +5
    };
    private Callback mCallback;

    public MainActivityViewModel(Context context, Bundle savedInstanceState, Callback callback) {
        super(context, savedInstanceState, true);
        mCallback = callback != null ? callback : Callback.EMPTY_CALLBACK;
        loadData();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mCallback = Callback.EMPTY_CALLBACK;
    }

    private void loadData() {
        manageSubscription(getRealm().where(TrackPoint.class).findAllSortedAsync("timestamp", Sort.ASCENDING).asObservable()
                .filter(RealmResults::isLoaded)
                .filter(r -> !r.isEmpty())
                .map(trackPoints -> {
                    final int size = trackPoints.size();

                    final List<AxisValue> xValues = new ArrayList<>(size);
                    List<SubcolumnValue> values = new ArrayList<>(size);
                    TrackPoint point;
                    int value;
                    for (int i = 0; i < size; i++) {
                        point = trackPoints.get(i);
                        value = point.getValue();
                        values.add(new SubcolumnValue(
//                                i, //DATE_FORMAT.format(point.getTimestamp()),
                                value+5).setColor(COLORS[value + 5]));
                        xValues.add(new AxisValue(i).setLabel(DATE_FORMAT.format(point.getTimestamp())));
                    }

                    Column column = new Column(values)
//                            .setColor(Color.BLUE)
//                            .setCubic(true)
//                            .setHasLines(true)
//                            .setFilled(true)
                            ;
                    List<Column> columns = new ArrayList<>(1);
                    columns.add(column);
                    ColumnChartData data = new ColumnChartData();

                    data.setAxisXBottom(new Axis(xValues).setHasLines(true).setTextSize(10).setMaxLabelChars(7));
                    final List<AxisValue> yValues = new ArrayList<>(11);
                    for (int j = -5; j <= 5; j++) {
                        yValues.add(new AxisValue(j+5).setLabel(String.valueOf(j)));
                    }
                    data.setAxisYLeft(new Axis(yValues).setHasLines(true).setTextSize(10).setMaxLabelChars(2));
                    data.setColumns(columns);

                    return data;
                })
                .subscribe((data) -> mCallback.setChartData(data), Throwable::printStackTrace));
    }

    public void onValueChecked(int value) {
        final Realm realm = getRealm();
        if (realm != null && !realm.isClosed()) {
            realm.executeTransaction(realm1 -> realm1.copyToRealmOrUpdate(new TrackPoint(value)));
        }
    }

    public interface Callback {
        void setChartData(ColumnChartData data);

        Callback EMPTY_CALLBACK = new Callback() {
            @Override
            public void setChartData(ColumnChartData data) {
            }
        };
    }
}
