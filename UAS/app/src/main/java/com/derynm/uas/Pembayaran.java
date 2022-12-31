package com.derynm.uas;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class Pembayaran extends AppCompatActivity {

    TextView judul_page;

    SharedPreferences username_shared;
    String total_pembayaran,jumlah_paket,username;

    TextView text_total_pembayaran,text_jumlah_paket,status;

    Button btn_gallery, btn_kirim;
    ImageView iv_gambar,back_btn;
    Uri selectedImageUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pembayaran);

        judul_page = findViewById(R.id.judul_page);
        judul_page.setText("Pembayaran");
        back_btn = findViewById(R.id.back_btn);

        text_jumlah_paket = findViewById(R.id.jumlah_paket);
        text_total_pembayaran = findViewById(R.id.total_pembayaran);
        status = findViewById(R.id.tv_status);

        username_shared=getSharedPreferences("username",MODE_PRIVATE);

        total_pembayaran = String.valueOf(getIntent().getIntExtra("total_bayar",0));
        jumlah_paket = String.valueOf(getIntent().getIntExtra("total_pilihan",0));
        username = username_shared.getString("username","");

        btn_gallery = findViewById(R.id.btn_gallery);
        btn_kirim = findViewById(R.id.btn_kirim);
        iv_gambar = findViewById(R.id.iv_gambar);

        text_total_pembayaran.setText(total_pembayaran);
        text_jumlah_paket.setText(jumlah_paket);


        btn_gallery.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view) {
                Intent i = new Intent();
                i.setType("image/*");
                i.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(i, "Select Picture"), 200);
            }
        });

        btn_kirim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (selectedImageUri == null) {
                    Toast.makeText(getApplicationContext(), "Upload bukti pembayaran dulu!", Toast.LENGTH_LONG).show();
                } else {
                    btn_gallery.setVisibility(View.GONE);
                    btn_kirim.setVisibility(View.GONE);
                    pembayaranApi(username,total_pembayaran,jumlah_paket);
                    status.setText("Sudah bayar");
                    status.setTextColor(Color.GREEN);
                }
            }
        });

        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)   {
                onBackPressed();
                finish();
            }
        });


    }

    private void pembayaranApi(String username, String total_pembayaran, String jumlah_paket){
        String URL = "http://10.0.2.2/wisata_tempelmahbang/rest_api/pembayaran.php";

        RequestQueue queue = Volley.newRequestQueue(Pembayaran.this);

        StringRequest request = new StringRequest(Request.Method.POST, URL,
                response -> Toast.makeText(Pembayaran.this,"Pembayaran Berhasil", Toast.LENGTH_SHORT).show(),
                error -> Toast.makeText(Pembayaran.this,"Data gagal di input", Toast.LENGTH_SHORT).show()){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("username",username);
                params.put("total_bayar",total_pembayaran);
                params.put("total_paket",jumlah_paket);
                return params;
            }
        };
        queue.add(request);
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && data != null) {
            selectedImageUri = data.getData();
            if (selectedImageUri != null) {
                iv_gambar.setImageURI(selectedImageUri);
            }
        }
    }
}