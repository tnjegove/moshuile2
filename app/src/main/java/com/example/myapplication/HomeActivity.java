package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class HomeActivity extends AppCompatActivity implements View.OnClickListener {

    public CardView card1, card2, card3, card4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);


        card1 = (CardView) findViewById(R.id.cCamera);
        card2 = (CardView) findViewById(R.id.cUid);
        card3 = (CardView) findViewById(R.id.cFaceRecog);
        card4 = (CardView) findViewById(R.id.cSettings);

        card1.setOnClickListener(this);
        card2.setOnClickListener(this);
        card3.setOnClickListener(this);
        card4.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {

        Intent i;

        switch (view.getId()) {
            case R.id.cCamera:
                i = new Intent(this, CameraActivity.class);
                startActivity(i);
                break;

            case R.id.cUid:
                i = new Intent(this, MainActivity.class);
                startActivity(i);
                break;

            case R.id.cFaceRecog:
                i = new Intent(this, MainActivity.class);
                startActivity(i);
                break;

            case R.id.cSettings:
                i = new Intent(this, MainActivity.class);
                startActivity(i);
                break;


        }

    }
}