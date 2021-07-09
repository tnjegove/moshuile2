package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class CameraActivity extends AppCompatActivity {

    public final static String TAG = "CameraActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);

        Button startButton = (Button)findViewById(R.id.buttonStart);
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(CameraActivity.this, VideoActivity.class);
                EditText textRTSP = (EditText)findViewById(R.id.textRTSPUrl);
                intent.putExtra(VideoActivity.RTSP_URL, textRTSP.getText().toString());
                startActivity(intent);
            }
        });

    }
}