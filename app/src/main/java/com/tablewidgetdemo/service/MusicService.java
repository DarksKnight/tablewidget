package com.tablewidgetdemo.service;

import com.tablewidgetdemo.R;

import android.app.Service;
import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.widget.Toast;

import java.io.FileDescriptor;
import java.io.IOException;

/**
 * 音乐播放服务
 * Created by apple on 15/11/9.
 */
public class MusicService extends Service {

    private MediaPlayer mp = null;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onStart(Intent intent, int startId) {
        super.onStart(intent, startId);
        int flag = intent.getFlags();
        switch (flag) {
            case R.id.btnStart:
                Toast.makeText(getApplication().getApplicationContext(), "开始播放", Toast.LENGTH_SHORT).show();
                start();
                break;
            case R.id.btnStop:
                Toast.makeText(getApplication().getApplicationContext(), "停止播放", Toast.LENGTH_SHORT).show();
                stop();
                break;
        }
    }

    /**
     * 播放音乐
     */
    private void start() {
        AssetManager am = getAssets();
        AssetFileDescriptor afd = null;
        try {
            afd = am.openFd("music.mp3");
            FileDescriptor fd = afd.getFileDescriptor();
            mp = new MediaPlayer();
            mp.setAudioStreamType(AudioManager.STREAM_MUSIC);
            mp.setDataSource(fd);
            mp.prepare();
            mp.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 停止音乐
     */
    private void stop() {
        mp.stop();
    }
}
