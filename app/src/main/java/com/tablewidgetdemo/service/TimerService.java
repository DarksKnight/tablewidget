package com.tablewidgetdemo.service;

import com.tablewidgetdemo.R;
import com.tablewidgetdemo.widget.WidgetClock;

import android.app.Service;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.RemoteViews;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

/**
 * 时钟服务
 * Created by apple on 15/11/8.
 */
public class TimerService extends Service {

    private Timer timer;
    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                updateViews();
            }
        }, 0, 1000);
    }

    private void updateViews() {
        String time = sdf.format(new Date());
        Log.i("TEST", "time = " + time);
        RemoteViews rv = new RemoteViews(getPackageName(), R.layout.widget_clock);
        rv.setTextViewText(R.id.tvClock, time);
        AppWidgetManager manager = AppWidgetManager.getInstance(getApplicationContext());
        ComponentName cn = new ComponentName(getApplicationContext(), WidgetClock.class);
        manager.updateAppWidget(cn, rv);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        timer = null;
    }
}
