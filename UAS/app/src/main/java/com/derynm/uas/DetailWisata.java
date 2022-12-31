package com.derynm.uas;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

public class DetailWisata extends AppCompatActivity {

    TextView nama_paket,deskripsi,judul_page;
    ImageView gambar,back_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_wisata);

        nama_paket = findViewById(R.id.nama_paket_deskripsi);
        deskripsi = findViewById(R.id.text_deskripsi);
        gambar = findViewById(R.id.gambar_paket_deskripsi);
        back_btn = findViewById(R.id.back_btn);
        judul_page = findViewById(R.id.judul_page);

        nama_paket.setText(getIntent().getStringExtra("Nama"));
        deskripsi.setText(getIntent().getStringExtra("Deskripsi"));
        Glide.with(this)
                .load(getIntent().getStringExtra("Gambar"))
                .into(gambar);

        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)   {
                onBackPressed();
            }
        });

        judul_page.setText("Deskripsi");

    }
}