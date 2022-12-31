package com.derynm.uas;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class Register extends AppCompatActivity {

    EditText nama_lengkap,username,password;
    Button submit,reset;
    TextView login;

    String nm_lkp,usrnm,pass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        nama_lengkap = findViewById(R.id.nama_lengkap_register);
        username = findViewById(R.id.username_register);
        password = findViewById(R.id.password_register);
        submit = findViewById(R.id.submit_btn);
        reset = findViewById(R.id.reset_btn_register);
        login = findViewById(R.id.login_txt);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nm_lkp = nama_lengkap.getText().toString();
                usrnm = username.getText().toString();
                pass = password.getText().toString();

                if (nm_lkp.length() > 1 && usrnm.length() > 1 && pass.length() > 1) {
                    addDataUser(nm_lkp,usrnm,pass);
                    Toast.makeText(Register.this,"Register Berhasil", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(Register.this,Login.class);
                    startActivity(intent);
                }else {
                    Toast.makeText(Register.this,"Mohon isi semua kolom", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    private void addDataUser(String nama, String username, String password){
        String URL = "http://10.0.2.2/wisata_tempelmahbang/rest_api/createUser.php";

        RequestQueue queue = Volley.newRequestQueue(Register.this);

        StringRequest request = new StringRequest(Request.Method.POST, URL,
                response -> Toast.makeText(Register.this,"Data Behasil Masuk", Toast.LENGTH_SHORT).show(),
                error -> Toast.makeText(Register.this,"Data gagal di input", Toast.LENGTH_SHORT).show()){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("username",username);
                params.put("nama",nama);
                params.put("password",password);
                return params;
            }
        };
        queue.add(request);
    }
}