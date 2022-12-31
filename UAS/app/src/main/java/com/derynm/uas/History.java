package com.derynm.uas;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class History extends AppCompatActivity {

    RecyclerView recyclerView;
    RecycleViewAdapterHistory recycleViewAdapterHistory;
    ArrayList<ModelHistory> dataHistory = new ArrayList<>();
    ImageView back_btn;
    SharedPreferences username_shared;
    TextView judul_page;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        back_btn = findViewById(R.id.back_btn);
        recyclerView = findViewById(R.id.recycleView_history);
        recycleViewAdapterHistory = new RecycleViewAdapterHistory(dataHistory);
        recyclerView.setAdapter(recycleViewAdapterHistory);
        username_shared=getSharedPreferences("username",MODE_PRIVATE);
        judul_page = findViewById(R.id.judul_page);

        readDataFromAPI(username_shared.getString("username",""));

        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)   {
                onBackPressed();
                finish();
            }
        });

        judul_page.setText("History");

    }

    private void readDataFromAPI(String username) {
        String URL = "http://10.0.2.2/wisata_tempelmahbang/rest_api/getPembayaranHistory.php";
        RequestQueue queue = Volley.newRequestQueue(History.this);
        StringRequest request = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray pembayaran = jsonObject.getJSONArray("pembayaran");
                    for (int i = 0; i < pembayaran.length(); i++) {
                        JSONObject jsonObject2 = pembayaran.getJSONObject(i);
                        dataHistory.add(new ModelHistory(
                                jsonObject2.getString("id_pembayaran"),//yang dalam kurung key yang ada di api
                                jsonObject2.getString("username"),
                                jsonObject2.getString("waktu"),
                                jsonObject2.getString("total_pembayaran"),
                                jsonObject2.getString("total_paket"),
                                jsonObject2.getString("status")
                        ));
                        recyclerView.setLayoutManager(new LinearLayoutManager(History.this));
                        recyclerView.setAdapter(recycleViewAdapterHistory);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(History.this, "Fail to get data..", Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("username",username);
                return params;
            }
        };
        queue.add(request);
    }
}