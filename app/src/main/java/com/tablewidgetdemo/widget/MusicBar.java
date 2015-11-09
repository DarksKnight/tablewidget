package com.tablewidgetdemo.widget;

import com.tablewidgetdemo.R;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

/**
 * 音乐widget
 * Implementation of App Widget functionality.
 */
public class MusicBar extends AppWidgetProvider {

    private Intent musicIntent;
    private PendingIntent pendingIntent;
    private RemoteViews views;

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
            int appWidgetId) {

        // Construct the RemoteViews object
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.music_bar);

        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        super.onUpdate(context, appWidgetManager, appWidgetIds);
        //开始播放
        musicIntent = new Intent("com.demo.START_AUDIO_CLICK");
        pendingIntent = PendingIntent.getBroadcast(context, 0,
                musicIntent, 0);
        views = new RemoteViews(context.getPackageName(),
                R.layout.music_bar);
        views.setOnClickPendingIntent(R.id.btnStart, pendingIntent);
        appWidgetManager.updateAppWidget(appWidgetIds, views);

        //停止播放
        musicIntent = new Intent("com.demo.STOP_AUDIO_CLICK");
        pendingIntent = PendingIntent.getBroadcast(context, 0,
                musicIntent, 0);
        views = new RemoteViews(context.getPackageName(),
                R.layout.music_bar);
        //设置监听
        views.setOnClickPendingIntent(R.id.btnStop, pendingIntent);
        //通知manager在当前widget执行一次更新
        appWidgetManager.updateAppWidget(appWidgetIds, views);
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);
        //发送事件，开启服务
        if(intent.getAction().equals("com.demo.START_AUDIO_CLICK")) {
            musicIntent=new Intent("com.demo.START_AUDIO_SERVICE");
            musicIntent.setFlags(R.id.btnStart);
            musicIntent.setPackage(context.getPackageName());
            context.startService(musicIntent);
        }

        if(intent.getAction().equals("com.demo.STOP_AUDIO_CLICK")) {
            musicIntent=new Intent("com.demo.START_AUDIO_SERVICE");
            musicIntent.setFlags(R.id.btnStop);
            musicIntent.setPackage(context.getPackageName());
            context.startService(musicIntent);
        }
    }

    @Override
    public void onEnabled(Context context) {

    }

    @Override
    public void onDisabled(Context context) {
        context.stopService(musicIntent);
    }
}

