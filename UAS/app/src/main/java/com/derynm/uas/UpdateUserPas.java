package com.derynm.uas;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

public class UpdateUserPas extends AppCompatActivity {

    TextView judul_page;
    ImageView back_btn;
    Button update_btn,reset_btn_update;
    EditText username_update,password_update;
    SharedPreferences username_shared;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_user_pas);

        username_shared=getSharedPreferences("username",MODE_PRIVATE);

        judul_page = findViewById(R.id.judul_page);
        back_btn = findViewById(R.id.back_btn);

        update_btn = findViewById(R.id.update_btn);
        reset_btn_update = findViewById(R.id.reset_btn_update);
        username_update = findViewById(R.id.username_update);
        password_update = findViewById(R.id.password_update);

        judul_page.setText("Update Username & Password");

        username_update.setText(username_shared.getString("username",""));

        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        update_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            String password_string = password_update.getText().toString();
            String username_string = username_update.getText().toString();

                if (password_string.length() > 1) {
                    UpdateDataAPI(username_string,password_string);
                    Toast.makeText(UpdateUserPas.this,"Update password berhasil", Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(UpdateUserPas.this,Login.class);
                    startActivity(intent);
                    finish();
                }else {
                    Toast.makeText(UpdateUserPas.this,"Mohon isi kolom password", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private void UpdateDataAPI(String username,String password){
        String URL = "http://10.0.2.2/wisata_tempelmahbang/rest_api/updateUser.php";

        RequestQueue queue = Volley.newRequestQueue(UpdateUserPas.this);
        StringRequest request = new StringRequest(Request.Method.POST, URL,
                response -> Toast.makeText(UpdateUserPas.this,"Succes", Toast.LENGTH_SHORT).show(),
                error -> Toast.makeText(UpdateUserPas.this,"Eror", Toast.LENGTH_SHORT).show()){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("username",username);
                params.put("password",password);
                return params;
            }
        };

        queue.add(request);
    }
}