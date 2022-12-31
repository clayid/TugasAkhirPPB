package com.derynm.uas;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class CallAndSMS extends AppCompatActivity {

    TextView judul_page;
    ImageView back_btn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_call_and_sms);

        judul_page = findViewById(R.id.judul_page);
        back_btn = findViewById(R.id.back_btn);

        judul_page.setText("Call and SMS Center");

        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

    }
}