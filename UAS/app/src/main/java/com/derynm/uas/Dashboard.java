package com.derynm.uas;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Dashboard extends AppCompatActivity implements RecycleViewAdapter.OnNoteListener{

    SharedPreferences username_shared;
    ViewFlipper viewFlipper;
    int images[] ={R.drawable.arungjeram,R.drawable.berenang,R.drawable.bermainatv,
                    R.drawable.kebunstawberry,R.drawable.outbound,R.drawable.peternakan};

    ArrayList<ModelWisata> dataWisata = new ArrayList<>();
    RecyclerView recyclerView;
    RecycleViewAdapter recycleViewAdapter;

    TextView total_harga;
    int total_harga_int;
    int total_wisata_dipilih;
    LinearLayout reset_btn;

    Dialog dialog;
    Button bayar,cancel,hitung_btn_dialog;
    TextView total_harga_dialog,total_paket_dialog,kembalian_dialog,kembalian_txt;
    EditText uang_pembayaran_dialog;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        username_shared=getSharedPreferences("username",MODE_PRIVATE);
        viewFlipper = findViewById(R.id.flipper_dashboard);

        Toolbar toolbar = findViewById(R.id.menu);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Hi, "+username_shared.getString("username",""));

        recyclerView = findViewById(R.id.rcycler_view);
        recycleViewAdapter = new RecycleViewAdapter(dataWisata,Dashboard.this,this);
        recyclerView.setAdapter(recycleViewAdapter);

        total_harga = findViewById(R.id.total_harga);
        total_harga_int = 0;
        total_wisata_dipilih = 0;

        reset_btn = (LinearLayout) findViewById(R.id.reset_btn);



        for (int i =0; i<images.length; i++){
            fliverImages(images[i]);
        }
        for (int image: images)
            fliverImages(image);

        GetDataWisataApi();

        reset_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                total_harga_int =0;
                total_wisata_dipilih =0;
                total_harga.setText("-");
            }
        });

        dialog = new Dialog(Dashboard.this);
        dialog.setContentView(R.layout.pembayaran_dialog);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
            dialog.getWindow().setBackgroundDrawable(getDrawable(R.drawable.rounded_shape));
        }
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.setCancelable(true);

        bayar = dialog.findViewById(R.id.bayar_btn_dialog);
        cancel = dialog.findViewById(R.id.cancel_btn_dialog);
        total_harga_dialog = dialog.findViewById(R.id.Total_harga_dialog);
        total_paket_dialog = dialog.findViewById(R.id.Total_paket_dialog);
        kembalian_dialog = dialog.findViewById(R.id.kembalian_dialog);
        uang_pembayaran_dialog = dialog.findViewById(R.id.uang_pembayaran_dialog);
        hitung_btn_dialog = dialog.findViewById(R.id.hitung_btn_dialog);
        kembalian_txt = dialog.findViewById(R.id.kembalian_txt);

        total_harga.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (total_harga_int == 0){
                    Toast.makeText(Dashboard.this,"And belum memilih paket", Toast.LENGTH_SHORT).show();
                }else {
                    dialog.show();
                    total_paket_dialog.setText(""+total_wisata_dipilih);
                    total_harga_dialog.setText("RP."+total_harga_int);
                }
            }
        });

        hitung_btn_dialog.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onClick(View view) {
                int uang_pembayaran_integer = Integer.parseInt(uang_pembayaran_dialog.getText().toString());
                int tampung_uang_dialog = uang_pembayaran_integer-total_harga_int;
                int uang_kurang = total_harga_int-uang_pembayaran_integer;
                if ( uang_pembayaran_integer>= total_harga_int) {
                    kembalian_dialog.setText("Rp. "+Integer.toString(tampung_uang_dialog));
                    kembalian_dialog.setTextColor(Color.parseColor("#1FA324"));
                    bayar.setEnabled(true);
                    kembalian_dialog.setTextSize(24);
                    kembalian_txt.setVisibility(View.VISIBLE);
                }else {
                    kembalian_dialog.setText("uang anda kurang Rp. "+Integer.toString(uang_kurang));
                    kembalian_dialog.setTextColor(Color.parseColor("#D50A0A"));
                    bayar.setEnabled(false);
                    kembalian_dialog.setTextSize(16);
                    kembalian_txt.setVisibility(View.GONE);
                }

            }
        });

        bayar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(Dashboard.this,"Berhasil Terbayar", Toast.LENGTH_SHORT).show();
                total_harga.setText("Lunas");
                kembalian_dialog.setText("");
                kembalian_txt.setVisibility(View.GONE);
                bayar.setEnabled(false);
                uang_pembayaran_dialog.setText("");
                dialog.dismiss();
                Intent intent = new Intent(Dashboard.this,Pembayaran.class);
                intent.putExtra("total_bayar",total_harga_int);
                intent.putExtra("total_pilihan",total_wisata_dipilih);
                startActivity(intent);
                total_harga_int =0;
                total_wisata_dipilih =0;
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(Dashboard.this,"Pembayaran di batalkan", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.History){
            Intent intent = new Intent(getApplicationContext(),History.class);
            startActivity(intent);
        }else if (id == R.id.call_center_menu){
            Intent intent = new Intent(getApplicationContext(),CallAndSMS.class);
            startActivity(intent);
        }else if (id == R.id.sms_center_menu) {
            Intent intent = new Intent(getApplicationContext(),CallAndSMS.class);
            startActivity(intent);
        }else if (id == R.id.lokasi_menu) {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("geo: -7.12985185120284, 110.4178516612764"));
            startActivity(intent);
        }else if (id == R.id.update_menu) {
            Intent intent = new Intent(getApplicationContext(),UpdateUserPas.class);
            startActivity(intent);
        }else if (id == R.id.logout_menu) {
            Intent intent = new Intent(getApplicationContext(),MainActivity.class);
            startActivity(intent);
            finish();
        }
        return true;
    }

    public void fliverImages(int images){
        ImageView imageView = new ImageView(this);
        imageView.setBackgroundResource(images);
        viewFlipper.addView(imageView);
        viewFlipper.setFlipInterval(4000);
        viewFlipper.setAutoStart(true);

        viewFlipper.setInAnimation(this,android.R.anim.slide_in_left);

        viewFlipper.setOutAnimation(this,android.R.anim.slide_out_right);
    }

    private void GetDataWisataApi(){
        String URL = "http://10.0.2.2/wisata_tempelmahbang/rest_api/getDataWisata.php";
        RequestQueue queue = Volley.newRequestQueue(Dashboard.this);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, URL, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray hasil = response.getJSONArray("Wisata");
                    for (int i = 0; i < hasil.length(); i++) {// looping buat create object DataBarang, propertynya di isi data dari DB
                        JSONObject jsonObject = hasil.getJSONObject(i);
                        dataWisata.add(new ModelWisata(
                                jsonObject.getString("Nama_wisata"),//yang dalam kurung key yang ada di api
                                jsonObject.getString("Harga"),
                                jsonObject.getString("Gambar"),
                                jsonObject.getString("Deskripsi")
                        ));
                        recyclerView.setLayoutManager(new GridLayoutManager(Dashboard.this,2));
                        recyclerView.setAdapter(recycleViewAdapter);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Dashboard.this, "Fail to get data..", Toast.LENGTH_SHORT).show();
            }
        });
        queue.add(jsonObjectRequest);
    }

    @Override
    public void onNoteClick(int position) {
        int i = Integer.parseInt(String.valueOf(dataWisata.get(position).getHarga()));
        total_harga_int = total_harga_int+i;
        total_wisata_dipilih +=1;
        total_harga.setText(String.valueOf("Rp."+total_harga_int));
    }
}