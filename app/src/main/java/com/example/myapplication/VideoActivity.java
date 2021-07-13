package com.example.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import org.videolan.libvlc.IVLCVout;
import org.videolan.libvlc.LibVLC;
import org.videolan.libvlc.Media;
import org.videolan.libvlc.MediaPlayer;

import java.util.ArrayList;

public class VideoActivity extends Activity implements IVLCVout.Callback  {

    public final static String TAG = "VideoActivity";

    public static final String RTSP_URL = "rtsp://192.168.0.80:8554/mjpeg/1";

    // display surface
    private SurfaceView mSurface;
    private SurfaceHolder holder;

    // media player
    private LibVLC libvlc;
    private MediaPlayer mMediaPlayer = null;
    private int mVideoWidth;
    private int mVideoHeight;
    private final static int VideoSizeChanged = -1;


    private MediaPlayer.EventListener mPlayerListener = new MyPlayerListener(this);

    private String rtspUrl;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sample);



        // Get URL
        Intent intent = getIntent();
        rtspUrl = intent.getExtras().getString(RTSP_URL);
        Log.d(TAG, "Playing back " + rtspUrl);

        mSurface = (SurfaceView) findViewById(R.id.surface);
        holder = mSurface.getHolder();
        //holder.addCallback(this);

        ArrayList<String> options = new ArrayList<String>();
        options.add("--aout=opensles");
        options.add("--audio-time-stretch"); // time stretching
        options.add("-vvv"); // verbosity
        options.add("--aout=opensles");
        options.add("--avcodec-codec=h264");
        options.add("--file-logging");
        options.add("--logfile=vlc-log.txt");

        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        mVideoHeight = displayMetrics.heightPixels;
        mVideoWidth = displayMetrics.widthPixels;


        libvlc = new LibVLC(getApplicationContext(), options);
        holder.setKeepScreenOn(true);

        // Create media player
        mMediaPlayer = new MediaPlayer(libvlc);
        mMediaPlayer.setEventListener(mPlayerListener);

        // Set up video output
        final IVLCVout vout = mMediaPlayer.getVLCVout();
        vout.setVideoView(mSurface);
        vout.setWindowSize(mVideoWidth,mVideoHeight);
        vout.addCallback(this);
        vout.attachViews();

        Media m = new Media(libvlc, Uri.parse(rtspUrl));

        m.setHWDecoderEnabled(true,false);
        m.addOption(":network-caching=100");
        m.addOption(":clock-jitter=0");
        m.addOption(":clock-synchro=0");
        m.addOption(":fullscreen");
        mMediaPlayer.setMedia(m);
        mMediaPlayer.setAspectRatio("16:9");
        mMediaPlayer.setScale(1.8f);
        mMediaPlayer.play();

    }



    @Override
    protected void onResume() {
        super.onResume();
        // createPlayer(mFilePath);
    }

    @Override
    protected void onPause() {
        super.onPause();
        releasePlayer();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        releasePlayer();
    }

    @Override
    public void onSurfacesCreated(IVLCVout vlcVout) {

    }

    @Override
    public void onSurfacesDestroyed(IVLCVout vlcVout) {

    }



    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }


    public void releasePlayer() {
        if (libvlc == null)
            return;
        mMediaPlayer.stop();
        final IVLCVout vout = mMediaPlayer.getVLCVout();
        vout.removeCallback(this);
        vout.detachViews();
        holder = null;
        libvlc.release();
        libvlc = null;

        mVideoWidth = 0;
        mVideoHeight = 0;
    }




}
